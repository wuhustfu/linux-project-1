package com.book.service;

import com.book.dao.LendDao;
import com.book.dao.ReaderCardDao;
import com.book.dao.ReaderInfoDao;
import com.book.dao.ReserveDao;
import com.book.domain.Lend;
import com.book.domain.ReserveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LendService {
    private LendDao lendDao;
    private ReaderCardDao readerCardDao;
    private ReaderInfoDao readerInfoDao;
    private ReserveDao reserveDao;
    @Autowired
    public void setLendDao(LendDao lendDao) {
        this.lendDao = lendDao;
    }
    @Autowired
    public void setReaderCardDao(ReaderCardDao readerCardDao){
        this.readerCardDao=readerCardDao;
    }
    @Autowired
    public void setReaderInfoService(ReaderInfoDao readerInfoDao) {this.readerInfoDao = readerInfoDao;}
    @Autowired
    public void setReserveDao(ReserveDao reserveDao) {this.reserveDao = reserveDao;}

    /**添加内容,罚款，并借阅中数量减一**/
    public boolean bookReturn(long bookId){
        return readerInfoDao.DecreaseBorrowingBooks(lendDao.getReaderID(bookId))>0 &&
                lendDao.bookReturnOne(bookId)>0 &&
                lendDao.bookReturnTwo(bookId)>0 &&
                lendDao.fine(bookId);
    }

    /**添加内容：检测是否欠款**/
    public boolean bookLend(long bookId,int readerId){
        return ((!lendDao.IfFine(readerId)) &&
                lendDao.bookLendOne(bookId,readerId)>0 &&
                lendDao.bookLendTwo(bookId)>0 &&
                (readerInfoDao.IncreaseBorrowingBooks(lendDao.getReaderID(bookId))>0) &&
                (reserveDao.RmItem(bookId)>=0)
        );
    }

    /**检测账户是否存在**/
    public boolean IsIDExist(int  readerId) {
        return readerCardDao.AuthenReaderByReaderId(readerId);
    }

    //管理员功能--清空用户欠款
    /**添加内容：清空用户欠款**/
    public boolean FineReset(int readerId) {
        return lendDao.PayBackMoney(readerId);
    }

    public ArrayList<Lend> lendList(){
        return lendDao.lendList();
    }

    public ArrayList<Lend> myLendList(int readerId){
        //return lendDao.myLendList(readerId);
        return lendDao.MyBookWithName(readerId);
    }

    public ArrayList<ReserveInfo> reservelist(){
        return reserveDao.reservelist();
    }
    public ArrayList<ReserveInfo> myReserveList(int readerId) {
        return reserveDao.myReserveList(readerId);
    }

}
