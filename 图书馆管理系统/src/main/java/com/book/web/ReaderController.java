package com.book.web;

import com.book.domain.ReaderCard;
import com.book.domain.ReaderInfo;
import com.book.service.LoginService;
import com.book.service.ReaderCardService;
import com.book.service.ReaderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ReaderController {

    private ReaderInfoService readerInfoService;
    @Autowired
    public void setReaderInfoService(ReaderInfoService readerInfoService) {
        this.readerInfoService = readerInfoService;
    }
    private LoginService loginService;


    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    private ReaderCardService readerCardService;

    @Autowired
    public void setReaderCardService(ReaderCardService readerCardService) {
        this.readerCardService = readerCardService;
    }

    @RequestMapping("allreaders.html")
    public ModelAndView allBooks(){
        ArrayList<ReaderInfo> readers=readerInfoService.readerInfos();
        ModelAndView modelAndView=new ModelAndView("admin_readers");
        modelAndView.addObject("readers",readers);
        return modelAndView;
    }

    @RequestMapping("reader_delete.html")
    public String readerDelete(HttpServletRequest request,RedirectAttributes redirectAttributes){
        int readerId= Integer.parseInt(request.getParameter("readerId"));
        boolean success=readerInfoService.deleteReaderInfo(readerId) && readerInfoService.deleteReaderCard(readerId);

        if(success){
            redirectAttributes.addFlashAttribute("succ", "Delete reader successfully!");
            return "redirect:/allreaders.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete reader!This reader has fine or has books not to return!");
            return "redirect:/allreaders.html";
        }

    }

    @RequestMapping("/reader_info.html")
    public ModelAndView toReaderInfo(HttpServletRequest request) {
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        ReaderInfo readerInfo=readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView=new ModelAndView("reader_info");
        modelAndView.addObject("readerinfo",readerInfo);
        return modelAndView;
    }

    @RequestMapping("reader_edit.html")
    public ModelAndView readerInfoEdit(HttpServletRequest request){
        int readerId= Integer.parseInt(request.getParameter("readerId"));
        ReaderInfo readerInfo=readerInfoService.getReaderInfo(readerId);
        ModelAndView modelAndView=new ModelAndView("admin_reader_edit");
        modelAndView.addObject("readerInfo",readerInfo);
        return modelAndView;
    }

    @RequestMapping("reader_edit_do.html")
    public String readerInfoEditDo(HttpServletRequest request,String name,String sex,String birth,String address,String telcode,RedirectAttributes redirectAttributes){
        int readerId= Integer.parseInt(request.getParameter("id"));
        ReaderCard readerCard = loginService.findReaderCardByUserId(readerId);
        String oldName=readerCard.getName();
        boolean succo = readerCardService.updateName(readerId, name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nbirth = new Date();
        try {
            Date date = sdf.parse(birth);
            nbirth = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ReaderInfo readerInfo = new ReaderInfo();
        readerInfo.setAddress(address);
        readerInfo.setBirth(nbirth);
        readerInfo.setName(name);
        readerInfo.setReaderId(readerId);
        readerInfo.setTelcode(telcode);
        readerInfo.setSex(sex);
        readerInfo.setMoney(0);
        boolean succ = readerInfoService.editReaderInfo(readerInfo);
        if (succo && succ) {
            redirectAttributes.addFlashAttribute("succ", "Edit reader information successfully!");
            return "redirect:/allreaders.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to edit reader information!");
            return "redirect:/allreaders.html";
        }

    }

    @RequestMapping("reader_add.html")
    public ModelAndView readerInfoAdd(){
        ModelAndView modelAndView=new ModelAndView("admin_reader_add");
        return modelAndView;

    }

    //管理员功能--读者信息添加
    @RequestMapping("reader_add_do.html")
    public String readerInfoAddDo(String name,String sex,String birth,String address,String telcode,int readerId,RedirectAttributes redirectAttributes){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date nbirth=new Date();
        try{
            Date date=sdf.parse(birth);
            nbirth=date;
        }catch (ParseException e){
            e.printStackTrace();
        }

        ReaderInfo readerInfo=new ReaderInfo();
        readerInfo.setAddress(address);
        readerInfo.setBirth(nbirth);
        readerInfo.setName(name);
        readerInfo.setReaderId(readerId);
        readerInfo.setTelcode(telcode);
        readerInfo.setSex(sex);

        if (readerCardService.addReaderCard(readerInfo)&&readerInfoService.addReaderInfo(readerInfo)){
            ArrayList<ReaderInfo> readers=readerInfoService.readerInfos();
            redirectAttributes.addFlashAttribute("succ", "Add reader successfully!");
            return "redirect:/allreaders.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "Failed to add reader! ID already exists!");
            return "redirect:/allreaders.html";
        }
    }



    //用户功能--进入修改密码页面
    @RequestMapping("reader_repasswd.html")
    public ModelAndView readerRePasswd(){
        ModelAndView modelAndView=new ModelAndView("reader_repasswd");
        return modelAndView;
    }

    //用户功能--修改密码执行
    @RequestMapping("reader_repasswd_do.html")
    public String readerRePasswdDo(HttpServletRequest request,String oldPasswd,String newPasswd,String reNewPasswd,RedirectAttributes redirectAttributes){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        int readerId=readerCard.getReaderId();
        String passwd=readerCard.getPasswd();

        if (newPasswd.equals(reNewPasswd)){
            if(passwd.equals(oldPasswd)){
                boolean succ=readerCardService.updatePasswd(readerId,newPasswd);
                if (succ){
                    ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerId);
                    request.getSession().setAttribute("readercard", readerCardNew);
                    redirectAttributes.addFlashAttribute("succ", "Modify password successfully!");
                    return "redirect:/reader_repasswd.html";
                }else {
                    redirectAttributes.addFlashAttribute("succ", "Failed to modify password successfully!");
                    return "redirect:/reader_repasswd.html";
                }

            }else {
                redirectAttributes.addFlashAttribute("error", "Failed to modify password successfully,because old password is wrong!");
                return "redirect:/reader_repasswd.html";
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "Failed to modify password successfully,because new password is not consistent!");
            return "redirect:/reader_repasswd.html";
        }

    }

    //用户功能--读者信息修改
    @RequestMapping("reader_info_edit.html")
    public ModelAndView readerInfoEditReader(HttpServletRequest request){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        ReaderInfo readerInfo=readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView=new ModelAndView("reader_info_edit");
        modelAndView.addObject("readerinfo",readerInfo);
        return modelAndView;

    }
    @RequestMapping("reader_edit_do_r.html")
    public String readerInfoEditDoReader(HttpServletRequest request,String name,String sex,String birth,String address,String telcode,RedirectAttributes redirectAttributes){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");

            boolean succo = readerCardService.updateName(readerCard.getReaderId(), name);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nbirth = new Date();
            try {
                Date date = sdf.parse(birth);
                nbirth = date;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ReaderInfo readerInfo = new ReaderInfo();
            readerInfo.setAddress(address);
            readerInfo.setBirth(nbirth);
            readerInfo.setName(name);
            readerInfo.setReaderId(readerCard.getReaderId());
            readerInfo.setTelcode(telcode);
            readerInfo.setSex(sex);

            boolean succ = readerInfoService.editReaderInfo(readerInfo);
            if (succ && succo) {
                ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerCard.getReaderId());
                request.getSession().setAttribute("readercard", readerCardNew);
                redirectAttributes.addFlashAttribute("succ", "Edit reader information successfully!");
                return "redirect:/reader_info.html";
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to edit reader information successfully!");
                return "redirect:/reader_info.html";
            }
    }
}
