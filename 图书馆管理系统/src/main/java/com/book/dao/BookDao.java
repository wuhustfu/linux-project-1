package com.book.dao;

import com.book.domain.Book;
import com.book.util.DouBan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class BookDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static String ADD_BOOK_SQL="INSERT INTO book_info VALUES(NULL ,?,?,?,?,?,?,?,?,?,?)";
    //根据bookID删除书
    private final static String DELETE_BOOK_SQL="delete from book_info where book_id = ?  ";
    private final static String EDIT_BOOK_SQL="update book_info set name= ? ,author= ? ,publish= ? ,ISBN= ? ,introduction= ? ,price= ? ,pubdate= ? ,class_id= ? ,pressmark= ? ,state= ?  where book_id= ? ;";
    private final static String QUERY_ALL_BOOKS_SQL="SELECT * FROM book_info ";
    //根据ISBN或者书名查询书
    private final static String QUERY_BOOK_SQL="SELECT * FROM book_info WHERE isbn like  ?  or name like ? or book_id like ?  ";
    //查询同一本书的个数
    private final static String MATCH_BOOK_SQL="SELECT count(*) FROM book_info WHERE isbn like ?  or name like ? or book_id like ?  ";
    //根据isbn获取图书
    //private final static String GET_BOOK_ISBN_SQL="SELECT * FROM book_info WHERE isbn = ? ";
    //根据bookID获取图书
    private final static String GET_BOOK_SQL="SELECT * FROM book_info WHERE book_id = ? ";


    public int matchBook(String searchWord){
        String swcx="%"+searchWord+"%";
        return jdbcTemplate.queryForObject(MATCH_BOOK_SQL,new Object[]{swcx,swcx,swcx},Integer.class);
    }



    public ArrayList<Book> queryBook(String sw){
        String swcx="%"+sw+"%";
        final ArrayList<Book> books=new ArrayList<Book>();
        jdbcTemplate.query(QUERY_BOOK_SQL, new Object[]{swcx,swcx,swcx}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Book book =new Book();
                    book.setAuthor(resultSet.getString("author"));
                    book.setBookId(resultSet.getLong("book_id"));
                    book.setClassId(resultSet.getInt("class_id"));
                    book.setIntroduction(resultSet.getString("introduction"));
                    book.setIsbn(resultSet.getString("isbn"));
                    book.setName(resultSet.getString("name"));
                    book.setPressmark(resultSet.getInt("pressmark"));
                    book.setPubdate(resultSet.getDate("pubdate"));
                    book.setPrice(resultSet.getBigDecimal("price"));
                    book.setState(resultSet.getInt("state"));
                    book.setPublish(resultSet.getString("publish"));
                    books.add(book);
                }

            }
        });
        return books;
    }

    public ArrayList<Book> getAllBooks(){
        final ArrayList<Book> books=new ArrayList<Book>();

        jdbcTemplate.query(QUERY_ALL_BOOKS_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                    while (resultSet.next()){
                        Book book =new Book();
                        book.setPrice(resultSet.getBigDecimal("price"));
                        book.setState(resultSet.getInt("state"));
                        book.setPublish(resultSet.getString("publish"));
                        book.setPubdate(resultSet.getDate("pubdate"));
                        book.setName(resultSet.getString("name"));
                        book.setIsbn(resultSet.getString("isbn"));
                        book.setClassId(resultSet.getInt("class_id"));
                        book.setBookId(resultSet.getLong("book_id"));
                        book.setAuthor(resultSet.getString("author"));
                        book.setIntroduction(resultSet.getString("introduction"));
                        book.setPressmark(resultSet.getInt("pressmark"));
                        books.add(book);
                    }
            }
        });
        return books;

    }

    public int deleteBook(long bookId) {
        final Book book = new Book();
        jdbcTemplate.query(GET_BOOK_SQL, new Object[]{bookId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                book.setState(resultSet.getInt("state"));
            }

        });
        if (book.getState() != 1) {
            return 0;//因为，当前图书被借出，无法删除
        } else {

            return jdbcTemplate.update(DELETE_BOOK_SQL, bookId);
        }
    }


    public int addBook(Book book){
        String name=book.getName();
        String author=book.getAuthor();
        String publish=book.getPublish();
        String isbn=book.getIsbn();
        String introduction=book.getIntroduction();
        BigDecimal price=book.getPrice();
        Date pubdate=book.getPubdate();
        int classId=book.getClassId();
        int pressmark=book.getPressmark();
        int state=book.getState();

        return jdbcTemplate.update(ADD_BOOK_SQL,new Object[]{name,author,publish,isbn,introduction,price,pubdate,classId,pressmark,state});
    }

    public Book getBook(long bookId){
        final Book book =new Book();
        jdbcTemplate.query(GET_BOOK_SQL, new Object[]{bookId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                book.setAuthor(resultSet.getString("author"));
                book.setBookId(resultSet.getLong("book_id"));
                book.setClassId(resultSet.getInt("class_id"));
                book.setIntroduction(resultSet.getString("introduction"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setName(resultSet.getString("name"));
                book.setPressmark(resultSet.getInt("pressmark"));
                book.setPubdate(resultSet.getDate("pubdate"));
                book.setPrice(resultSet.getBigDecimal("price"));
                book.setState(resultSet.getInt("state"));
                book.setPublish(resultSet.getString("publish"));
            }

        });
        return book;
    }

    public int editBook(Book book){
        Long bookId=book.getBookId();
        String name=book.getName();
        String author=book.getAuthor();
        String publish=book.getPublish();
        String isbn=book.getIsbn();
        String introduction=book.getIntroduction();
        BigDecimal price=book.getPrice();
        Date pubdate=book.getPubdate();
        int classId=book.getClassId();
        int pressmark=book.getPressmark();
        int state=book.getState();

        return jdbcTemplate.update(EDIT_BOOK_SQL,new Object[]{name,author,publish,isbn,introduction,price,pubdate,classId,pressmark,state,bookId});
    }

    public Book addBookol(String isbn) {

            String json = DouBan.getJson(isbn);
            System.out.println(json);
            if ("null".equals(json)) {
                return null;
            }
            Book book = new Book();
            book.setIsbn(isbn);
            book.setBookId(0);
            book.setPressmark(-1);
            book.setClassId(-1);
            book.setAuthor(DouBan.doFilter(json, "author"));
            book.setName(DouBan.doFilter(json, "title"));
            book.setPublish(DouBan.doFilter(json, "publisher"));
        System.out.println(DouBan.doFilter(json, "summary"));
            book.setIntroduction(DouBan.getSummary(json));
            book.setPrice(DouBan.getMoney(json));
            System.out.println(DouBan.doFilter(json, "pubdate"));
        try {
            book.setPubdate(new SimpleDateFormat("yyyy-mm-dd").parse(DouBan.doFilter(json, "pubdate")));
        }catch (ParseException e) {
            System.out.println(e);
        }

        addBook(book);

        return book;
    }

}
