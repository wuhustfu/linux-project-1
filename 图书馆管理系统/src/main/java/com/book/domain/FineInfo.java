package com.book.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ambition on 17/11/7.
 */
public class FineInfo implements Serializable {
    private int readerId;
    private Date lendDate;
    private Date backDate;
    private float money;

    public  FineInfo(){}
    public FineInfo(int readerId, Date lendDate, Date backDate) {
        this.readerId = readerId;
        this.lendDate = lendDate;
        this.backDate = backDate;
    }

    public void setReaderId(int readerId) {this.readerId = readerId;}
//    public void setLendDate(Date date) {this.lendDate = lendDate;}
//    public void setBackDate(Date date) {this.backDate = backDate;}
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }
    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public int getReaderId() {return readerId;}//返回读者ID
    private long getDates() {//返回相差天数
        return (backDate.getTime()-lendDate.getTime())/(24*60*60*1000);
    }
    public float getMoney() {
        if (getDates() <30 ) {
            return 0;
        } else {

            System.out.println(getDates()/5);
            money = (float) (0.5 + getDates()/10);
            System.out.println(money);
            return money;
        }
    }
    public Date getBackDate(){return backDate;}
    public Date getLendDate(){return lendDate;}
    //1. 根据借还日期计算天数
    //2. 30天之内不需要罚款，30天后每延迟5天需要扣罚金0.5元，计算应扣罚款
    //3. 根据lend_list表查找reader_id
    //4. 修改罚款金额数
}

