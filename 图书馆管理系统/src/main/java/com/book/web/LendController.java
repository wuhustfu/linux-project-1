package com.book.web;

import com.book.domain.Book;
import com.book.domain.ReaderCard;
import com.book.domain.ReaderInfo;
import com.book.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LendController {

    private LendService lendService;
    @Autowired
    public void setLendService(LendService lendService) {
        this.lendService = lendService;
    }
    private AdminService adminService;
    @Autowired
    public void setAdminService(AdminService adminService){
        this.adminService=adminService;
    }
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    private ReserveService reserveService;
    @Autowired
    public void setReserveService(ReserveService reserveService) {this.reserveService = reserveService;}
    private ReaderInfoService readerInfoService;
    @Autowired
    public void setReaderInfoService(ReaderInfoService readerInfoService) {this.readerInfoService = readerInfoService;}

    @RequestMapping("/lendbook.html")
    public ModelAndView bookLend(HttpServletRequest request){
        long bookId=Integer.parseInt(request.getParameter("bookId"));
        Book book=bookService.getBook(bookId);
       ModelAndView modelAndView=new ModelAndView("admin_book_lend");
       modelAndView.addObject("book",book);
       return modelAndView;
    }

    @RequestMapping("/lendbookdo.html")
    public String bookLendDo(HttpServletRequest request,RedirectAttributes redirectAttributes,int readerId){
        long bookId=Integer.parseInt(request.getParameter("id"));
        boolean CanBeBorrowed = reserveService.CanBeBorrowed(readerId, bookId);
        if (CanBeBorrowed) {
            if (lendService.IsIDExist(readerId)) {
                if (!readerInfoService.IsBorrowingBookMatchMax(readerId)) {
                    boolean lendsucc = lendService.bookLend(bookId, readerId);
                    if (lendsucc) {
                        redirectAttributes.addFlashAttribute("succ", "Borrow book successfully!");
                        return "redirect:/allbooks.html";
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Failed to borrow book! Check whether there is a fine");
                        return "redirect:/allbooks.html";
                    }
                }else {
                    redirectAttributes.addFlashAttribute("error", "The number of books you borrowed is up to the limit");
                    return "redirect:/allbooks.html";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to borrow book! Check your ID number!");
                return "redirect:/allbooks.html";
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "Failed to borrow book! Check whether the book is reserved by others!");
            return "redirect:/allbooks.html";
        }


    }

    @RequestMapping("/repayment.html")
    public String Repayment(HttpServletRequest request, RedirectAttributes redirectAttributes ) {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        int adminId = Integer.parseInt(request.getParameter("adminId"));

        float fine = readerInfoService.getReaderFine(readerId);
        float adminNewFine=adminService.getAdminFine(adminId)+fine;
        //System.out.println(readerId);
        boolean FineSucc = lendService.FineReset(readerId);

        boolean CalFineSucc = adminService.setAdminFine(adminId, adminNewFine)> 0;

        if (FineSucc == true&&CalFineSucc == true) {
            redirectAttributes.addFlashAttribute("succ", "Fine pay successfully!");
            return "redirect:/allreaders.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "Fine payment failure!");
            return "redirect:/allbooks.html";
        }

    }

    @RequestMapping("/returnbook.html")
    public String bookReturn(HttpServletRequest request,RedirectAttributes redirectAttributes){
        long bookId=Integer.parseInt(request.getParameter("bookId"));
        boolean retSucc=lendService.bookReturn(bookId);
        if (retSucc){
            redirectAttributes.addFlashAttribute("succ", "Return book successfully!");
            return "redirect:/allbooks.html";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Failed to return book! ");
            return "redirect:/allbooks.html";
        }
    }


    @RequestMapping("/lendlist.html")
    public ModelAndView lendList(){

        ModelAndView modelAndView=new ModelAndView("admin_lend_list");
        modelAndView.addObject("list",lendService.lendList());
        return modelAndView;
    }

    @RequestMapping("/reservelist.html")
    public ModelAndView Reservelist(){

        ModelAndView modelAndView=new ModelAndView("admin_reserve_list");
        modelAndView.addObject("list",lendService.reservelist());
        return modelAndView;
    }

    @RequestMapping("/mylend.html")
    public ModelAndView myLend(HttpServletRequest request){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        ModelAndView modelAndView=new ModelAndView("reader_lend_list");
        modelAndView.addObject("list",lendService.myLendList(readerCard.getReaderId()));
        return modelAndView;
    }

    @RequestMapping("/myReserve.html")
    public ModelAndView myReserve(HttpServletRequest request){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        ModelAndView modelAndView=new ModelAndView("reader_reserve_list");
        modelAndView.addObject("list",lendService.myReserveList(readerCard.getReaderId()));
        System.out.println(lendService.myReserveList(readerCard.getReaderId()));
        return modelAndView;
    }

    @RequestMapping("/readerreservedetail.html")
    public String ReserveDetail(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        long bookId = Long.parseLong(request.getParameter("bookId"));
        boolean ResSucc = (readerInfoService.getBorrowingBooksNumber(readerId)< ReaderInfo.MAX_BOOK)&&
                            (reserveService.ReserveBook(readerId, bookId));
        if (ResSucc) {
            redirectAttributes.addFlashAttribute("succ", "book reserved successfully!");
            return "redirect:/reader_querybook.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "fail to reserve book! This book can't be reserved or You have reserved or borrowed so many books");
            return "redirect:/reader_querybook.html";
        }
    }

//    @RequestMapping("reservebook.html")
//    public String ReserveBookDo(HttpServletRequest request, RedirectAttributes redirectAttributes) {
//        int readerId=Integer.parseInt(request.getParameter("readerId"));
//        boolean resvsucc=reserveService.ReserveBook(readerId, bookId);
//        if (resvsucc){
//            redirectAttributes.addFlashAttribute("succ", "borrow your reserved book successfully!");
//            return "redirect:/allbooks.html";
//        }else {
//            redirectAttributes.addFlashAttribute("error", "Failed to borrow book! Check whether there is a fine or the book is reserved by others!");
//            return "redirect:/allbooks.html";
//        }
//    }
//    @RequestMapping("reservebook.html")
//    public ModelAndView bookDetail(HttpServletRequest request, RedirectAttributes redirectAttributes){
//        long bookId=Integer.parseInt(request.getParameter("bookId"));
//        int readerId=Integer.parseInt(request.getParameter("readerId"));
//                boolean resvsucc=reserveService.ReserveBook(readerId, bookId);
//        if (resvsucc){
//            redirectAttributes.addFlashAttribute("succ", "borrow your reserved book successfully!");
//        }else {
//            redirectAttributes.addFlashAttribute("error", "Failed to borrow book! Check whether there is a fine or the book is reserved by others!");
//        }
//        Book book=bookService.getBook(bookId);
//        ModelAndView modelAndView=new ModelAndView("reader_book_detail");
//        modelAndView.addObject("detail",book);
//        return modelAndView;
//    }
}
