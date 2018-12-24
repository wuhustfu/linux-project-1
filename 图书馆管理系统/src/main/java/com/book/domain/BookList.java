package com.book.domain;

import java.io.Serializable;

/**
 * Created by ambition on 17/11/15.
 */
public class BookList implements Serializable{
    private String name;
    private long bookId;
    private int pressmark;//(图书馆书目上的)书架号码，等于location
    private int state;

}
