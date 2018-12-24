package com.book.service;

import com.book.dao.ReaderInfoDao;
import com.book.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReaderInfoService {

    private ReaderInfoDao readerInfoDao;
    @Autowired
    public void setReaderInfoDao(ReaderInfoDao readerInfoDao) {
        this.readerInfoDao = readerInfoDao;
    }

    public ArrayList<ReaderInfo> readerInfos(){
        return readerInfoDao.getAllReaderInfo();
    }

    public boolean deleteReaderInfo(int readerId){
        return readerInfoDao.deleteReaderInfo(readerId)>0;
    }

    public boolean deleteReaderCard(int readerId){
        return  readerInfoDao.deleteReaderCard(readerId)>0;
    }

    public ReaderInfo getReaderInfo(int readerId){
        return readerInfoDao.findReaderInfoByReaderId(readerId);
    }

    public boolean editReaderInfo(ReaderInfo readerInfo){
        return readerInfoDao.editReaderInfo(readerInfo)>0;
    }

    public boolean addReaderInfo(ReaderInfo readerInfo){
        return  readerInfoDao.addReaderInfo(readerInfo)>0;
    }

    public int getBorrowingBooksNumber(int readerId) {return readerInfoDao.getCurrBorrowNumber(readerId);}
    public boolean IsBorrowingBookMatchMax(int readerId) {return readerInfoDao.IsBorrowingBookMatchMax(readerId);}
    public boolean IncreaseBorrowingBooksNumber(int readerId) {return readerInfoDao.IncreaseBorrowingBooks(readerId)>0;}
    public boolean DecreaseBorrowingBoolsNumber(int readerId) {return readerInfoDao.DecreaseBorrowingBooks(readerId)>0;}


    public float getReaderFine(int id){
        return readerInfoDao.getReaderFine(id);
    }

}
