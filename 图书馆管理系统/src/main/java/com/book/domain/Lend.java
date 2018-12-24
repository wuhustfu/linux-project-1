package com.book.domain;

import java.io.Serializable;
import java.util.Date;

public class Lend implements Serializable {

    private String name;
    private long sernum;
    private long bookId;
    private int readerId;
    private Date lendDate;
    private Date backDate;

    public void setName(String name) {this.name = name;}
    public String getName(){return name;}
    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public void setSernum(long sernum) {
        this.sernum = sernum;
    }

    public int getReaderId() {
        return readerId;
    }

    public long getBookId() {
        return bookId;
    }

    public Date getBackDate() {
        return backDate;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public long getSernum() {
        return sernum;
    }
}
