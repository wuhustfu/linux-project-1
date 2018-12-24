package com.book.service;

import com.book.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminDao adminDao;
    @Autowired
    public void setAdminDao(AdminDao adminDao){
        this.adminDao=adminDao;
    }

    public float getAdminFine(int id){
        return adminDao.getAdminFine(id);
    }
    public int setAdminFine(int id,float fine){
        return adminDao.setAdminFine(id,fine);
    }

}
