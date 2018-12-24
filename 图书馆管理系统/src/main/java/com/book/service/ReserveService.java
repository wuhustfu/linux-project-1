package com.book.service;

import com.book.dao.ReserveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ambition on 17/11/9.
 */

@Service
public class ReserveService {


    private ReserveDao reserveDao;

    @Autowired
    public void setReserveDao(ReserveDao reserveDao) {
        this.reserveDao = reserveDao;
    }

    public boolean ReserveBook(int readerId, long bookId) {
        if ( reserveDao.CanBeReserved(bookId) && reserveDao.ReservingBookNums(readerId)<ReserveDao.RESERVE_MAX)
            return reserveDao.reserve(readerId, bookId);
        return false;
    }

    public boolean CanBeBorrowed(int readerId, long bookId) {
        return reserveDao.IsReservedAndCanBeBorrowed(readerId, bookId);
    }


}
