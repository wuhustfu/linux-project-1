package com.book.dao;

import com.book.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class ReaderInfoDao {

    private JdbcTemplate jdbcTemplate;

    private final static String ADD_READER_INFO_SQL="INSERT INTO reader_info VALUES(?,?,?,?,?,?,?,?)";
    private final static String DELETE_READER_INFO_SQL="DELETE FROM reader_info where reader_id = ? ";
    private final static String DELETE_READER_CARD_SQL="DELETE FROM reader_card where reader_id = ? ";
    private final static String GET_READER_INFO_SQL="SELECT * FROM reader_info where reader_id = ? ";
    private final static String UPDATE_READER_INFO="UPDATE reader_info set name = ? ,sex = ? ,birth = ? ,address = ? ,telcode = ? where reader_id = ? ";
    private final static String ALL_READER_INFO_SQL="SELECT * FROM reader_info";
    private final static String BOOK_NUMBERS_NOW_SQL="SELECT curr_books FROM reader_info WHERE reader_id = ?";
    private final static String CHANGE_BOOK_NUMBER_SQL="UPDATE reader_info set curr_books = ? WHERE reader_id = ?";
    private final static String GET_READER_FINE_SQL="SELECT money FROM reader_info where reader_id = ? ";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<ReaderInfo> getAllReaderInfo() {
        final ArrayList<ReaderInfo> readers=new ArrayList<ReaderInfo>();
        jdbcTemplate.query(ALL_READER_INFO_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    ReaderInfo reader=new ReaderInfo();
                    reader.setAddress(resultSet.getString("address"));
                    reader.setBirth(resultSet.getDate("birth"));
                    reader.setName(resultSet.getString("name"));
                    reader.setReaderId(resultSet.getInt("reader_id"));
                    reader.setSex(resultSet.getString("sex"));
                    reader.setTelcode(resultSet.getString("telcode"));
                    reader.setMoney(resultSet.getFloat("money"));
                    reader.setCurrent_book(resultSet.getInt("curr_books"));
                    readers.add(reader);
                }
            }
        });
        return readers;
    }

    public float getReaderFine(int readerId){
        return jdbcTemplate.queryForObject(GET_READER_FINE_SQL,new Object[]{readerId},float.class);
    }

    public ReaderInfo findReaderInfoByReaderId(int readerId){
        final ReaderInfo reader=new ReaderInfo();
        jdbcTemplate.query(GET_READER_INFO_SQL, new Object[]{readerId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                reader.setAddress(resultSet.getString("address"));
                reader.setBirth(resultSet.getDate("birth"));
                reader.setName(resultSet.getString("name"));
                reader.setReaderId(resultSet.getInt("reader_id"));
                reader.setSex(resultSet.getString("sex"));
                reader.setTelcode(resultSet.getString("telcode"));
                reader.setMoney(resultSet.getFloat("money"));
                reader.setCurrent_book(resultSet.getInt("curr_books"));
            }
        });
        return reader;
    }

    public int deleteReaderInfo(int readerId){
        final ReaderInfo reader=new ReaderInfo();
        jdbcTemplate.query(GET_READER_INFO_SQL, new Object[]{readerId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                reader.setMoney(resultSet.getFloat("money"));
                reader.setCurrent_book(resultSet.getInt("curr_books"));
            }
        });
        if(reader.getMoney()>0||reader.getCurrent_book()>0){
            return -1;
        }
        //有欠款，未还图书，不能被删除
        else{
            return jdbcTemplate.update(DELETE_READER_INFO_SQL,readerId);
        }
    }
    public int deleteReaderCard(int readerId){
        final ReaderInfo reader=new ReaderInfo();
        jdbcTemplate.query(GET_READER_INFO_SQL, new Object[]{readerId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                reader.setMoney(resultSet.getFloat("money"));
                reader.setCurrent_book(resultSet.getInt("curr_books"));
            }
        });
        if(reader.getMoney()>0||reader.getCurrent_book()>0){
            return -1;
        }
        //有欠款，未还图书，不能被删除
        else{
            return jdbcTemplate.update(DELETE_READER_CARD_SQL,readerId);
        }
    }

    public int editReaderInfo(ReaderInfo readerInfo){
        String address=readerInfo.getAddress();
        Date birth=readerInfo.getBirth();
        String name=readerInfo.getName();
        int readerId=readerInfo.getReaderId();
        String sex=readerInfo.getSex();
        String telcode=readerInfo.getTelcode();
        return jdbcTemplate.update(UPDATE_READER_INFO,new Object[]{name,sex,birth,address,telcode,readerId});
    }

    public int addReaderInfo(ReaderInfo readerInfo){
        String address=readerInfo.getAddress();
        Date birth=readerInfo.getBirth();
        String name=readerInfo.getName();
        String sex=readerInfo.getSex();
        String telcode=readerInfo.getTelcode();
        int readerId=readerInfo.getReaderId();
        return jdbcTemplate.update(ADD_READER_INFO_SQL,new Object[]{readerId,name,sex,birth,address,telcode,0,0});
    }

    public int getCurrBorrowNumber(int readerId) {
        final int []ans = new int[1];
        jdbcTemplate.query(BOOK_NUMBERS_NOW_SQL, new Object[]{readerId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
               ans[0] = resultSet.getInt("curr_books");
            }
        });
        return ans[0];
    }

    public int IncreaseBorrowingBooks(int readerId) {
        int now_numbers = getCurrBorrowNumber(readerId);
        if (now_numbers < ReaderInfo.MAX_BOOK) {
            return jdbcTemplate.update(CHANGE_BOOK_NUMBER_SQL, new Object[]{now_numbers+1, readerId});
        }
        return 0;
    }
    public int DecreaseBorrowingBooks(int readerId) {
        int now_numbers = getCurrBorrowNumber(readerId);
        if (now_numbers > 0) {
            return jdbcTemplate.update(CHANGE_BOOK_NUMBER_SQL, new Object[]{now_numbers - 1, readerId});
        }
        return 0;
    }

    public boolean IsBorrowingBookMatchMax(int readerId) {
        return getCurrBorrowNumber(readerId) >= ReaderInfo.MAX_BOOK;
    }


}
