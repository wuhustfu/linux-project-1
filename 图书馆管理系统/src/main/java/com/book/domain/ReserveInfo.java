package com.book.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ambition on 17/11/9.
 */
public class ReserveInfo implements Serializable{
    private int readerId;
    private long bookId;
    private Date date;
    private Timestamp timestamp;
    public ReserveInfo() {}
    public ReserveInfo(int readerId, long bookId, Date date) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.date = date;

    }

    @Override
    public String toString() {
        return "ReserveInfo{" +
                "readerId=" + readerId +
                ", bookId=" + bookId +
                ", date=" + date +
                '}';
    }

    public int getReaderId() {return readerId;}
    public long getBookId() {return bookId;}
    public Date getDate() {return date;}

    public void setReaderId(int readerId) {this.readerId = readerId;}
    public void setBookId(long bookId) {this.bookId = bookId;}
    public void setDate(Date date) {this.date = date;}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;

        ReserveInfo item = (ReserveInfo)obj;
        if (this.getReaderId() == item.getReaderId() && this.getBookId() == item.getBookId())
            return true;
        return false;
    }
}
