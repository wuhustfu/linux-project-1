package com.book.domain;

import java.io.Serializable;
import java.util.Date;

public class ReaderInfo implements Serializable{

    private int readerId;
    private String name;
    private String sex;
    private Date birth;
    private String address;
    private String telcode;
    private int current_book;
    //private int current_reserve;
    public static int MAX_BOOK = 5;
    private float money;//拖延应付罚款


    public void setName(String name) {
        this.name = name;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setTelcode(String telcode) {
        this.telcode = telcode;
    }

    public String getName() {
        return name;
    }

    public int getReaderId() {
        return readerId;
    }

    public Date getBirth() {
        return birth;
    }

    public String getAddress() {
        return address;
    }

    public String getSex() {
        return sex;
    }

    public String getTelcode() {
        return telcode;
    }


    public void setMoney(float money) { this.money = money;}

    public float getMoney() {
        return money;
    }

    public void setCurrent_book(int current_book){
        this.current_book=current_book;
    }
    public int getCurrent_book() {return current_book;}
//    public void setCurrent_reserve(int current_reserve){
//        this.current_reserve=current_reserve;
//    }
//    public int getCurrent_reserve(){
//        return this.current_reserve;
//    }


}
