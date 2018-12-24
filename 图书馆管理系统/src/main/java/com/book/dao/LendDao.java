package com.book.dao;

import com.book.domain.FineInfo;
import com.book.domain.Lend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
@Repository
public class LendDao {

    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


//    //预定功能嵌入实现
//    ReserveDao reserveDao = new ReserveDao();
//    reserveDao.setJdbcTemplete(jdbcTemplete);
    //借书
    //第零阶段，查看是否有欠账，如果有，则无法借书
    private final static String IF_READER_FINE_SQL="SELECT money FROM reader_info WHERE reader_id = ?";
    //第零阶段plus，如果书处于预订状态则无法借书
    private final static String IF_BOOK_RESERVED_SQL="SELECT state FROM book_info WHERE book_id = ? ";
    //第一阶段，修改lend_list表
    private final static String BOOK_LEND_SQL_ONE="INSERT INTO lend_list (book_id,reader_id,lend_date) VALUES ( ? , ? , ? )";
    //第二阶段，修改book_info表
    private final static String BOOK_LEND_SQL_TWO="UPDATE book_info SET state = 0 WHERE book_id = ? ";
    //还书
    //第一阶段，修改lend_list表
    private final static String BOOK_RETURN_SQL_ONE="UPDATE lend_list SET back_date = ? WHERE book_id = ? AND back_date is NULL";
    //第二阶段，修改book_info表
    private final static String BOOK_RETURN_SQL_TWO="UPDATE book_info SET state = 1 WHERE book_id = ? ";
    //第三阶段，根据lend_list表查找借书人id和借还日期
    /**************************/
    /**************************/
    /**************************/
    //private final static String Lender_ID_SQL="SELECT reader_id,lend_date,back_date FROM lend_list WHERE book_id= ?";
    private final static String Lender_ID_SQL="SELECT reader_id,lend_date,back_date FROM lend_list WHERE book_id= ? ";
    //第四阶段，修改借书人表
    private final static String ORIGIN_FINE_SQL="SELECT money FROM reader_info WHERE reader_id = ?";
    private final static String FINE_READER_SQL="UPDATE reader_info set money = ? WHERE reader_id = ?";
    //还款
    private final static String LEND_LIST_SQL="SELECT * FROM lend_list";
    private final static String MY_LEND_LIST_SQL="SELECT * FROM lend_list WHERE reader_id = ? ";


    public int bookLendOne(long bookId,int readerId){
        return  jdbcTemplate.update(BOOK_LEND_SQL_ONE,new Object[]{bookId,readerId,df.format(new Date())});
    }
    public int bookLendTwo(long bookId){
        return  jdbcTemplate.update(BOOK_LEND_SQL_TWO,new Object[]{bookId});
    }

    public int bookReturnOne(long bookId){
        return  jdbcTemplate.update(BOOK_RETURN_SQL_ONE,new Object[]{df.format(new Date()),bookId});
    }
    public int bookReturnTwo(long bookId){
        return jdbcTemplate.update(BOOK_RETURN_SQL_TWO,new Object[]{bookId});
    }

    private final static String GET_BOOK_NAME_SQL="SELECT name, lend_list.book_id, lend_date, back_date, state, sernum FROM " +
            "lend_list JOIN book_info where lend_list.book_id=book_info.book_id AND reader_id = ?";
    private final static String GET_ALL_BOOKE_WITH_NAME_SQL="SELECT name, reader_id, sernum, lend_list.book_id, lend_date, back_date, state FROM lend_list JOIN book_info where lend_list.book_id=book_info.book_id";

    /**添加罚款内容**/
    public boolean fine(long bookId) {
        //FineInfo fineInfo = jdbcTemplate.queryForObject(Lender_ID_SQL, FineInfo.class, bookId);//得到用户ID，借还日期
//        final ArrayList<FineInfo> FineInfolist=new ArrayList<FineInfo>();
//        jdbcTemplate.query(Lender_ID_SQL, new Object[]{bookId}, new RowCallbackHandler() {
//            public void processRow(ResultSet resultSet) throws SQLException {
//                resultSet.beforeFirst();
//                while (resultSet.next()){
//                    FineInfo fineInfo = new FineInfo();
//                    fineInfo.setReaderId(resultSet.getInt("reader_id"));
//                    fineInfo.setLendDate(resultSet.getDate("lend_date"));
//                    fineInfo.setBackDate(resultSet.getDate("back_date"));
//                    FineInfolist.add(fineInfo);
//                }//这本书最晚归还者即为该扣费者
//            }
//        });
//        FineInfo LastBack = null;
//        for (int i=0; i<FineInfolist.size(); i++) {
//            if (LastBack == null) {
//                LastBack = FineInfolist.get(i);
//            } else {
//                if (!FineInfolist.get(i).getBackDate().before(LastBack.getBackDate())) {
//                    LastBack = FineInfolist.get(i);
//                }
//            }
//        }

        FineInfo LastBack = LastBack(bookId);
        float originMoney = jdbcTemplate.queryForObject(ORIGIN_FINE_SQL, Float.class, LastBack.getReaderId());
        jdbcTemplate.update(FINE_READER_SQL, LastBack.getMoney()+originMoney, LastBack.getReaderId());
        return true;

    }//扣除罚金
    //1. 根据借还日期计算天数
    //2. 30天之内不需要罚款，30天后每延迟5天需要扣罚金0.5元，计算应扣罚款
    //3. 根据lend_list表查找reader_id
    //4. 修改罚款金额数

    /**添加检测是否有欠款**/
    public boolean IfFine(int readerId) {
        final boolean []IsFine = new boolean[1];
        jdbcTemplate.query(IF_READER_FINE_SQL, new Object[]{readerId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                if (resultSet.getFloat("money") > 0)
                    IsFine[0] = true;
                else
                    IsFine[0] = false;
            }
        });
        return IsFine[0];
    }

    /**还款后清零**/
    public boolean PayBackMoney(int readerId) {
        if(jdbcTemplate.update(FINE_READER_SQL, 0, readerId) == 0)
            return false;
        return true;
    }
    /**罚款机制就此结束**/


    private FineInfo LastBack(long bookId) {
        final ArrayList<FineInfo> FineInfolist=new ArrayList<FineInfo>();
        jdbcTemplate.query(Lender_ID_SQL, new Object[]{bookId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    FineInfo fineInfo = new FineInfo();
                    fineInfo.setReaderId(resultSet.getInt("reader_id"));
                    fineInfo.setLendDate(resultSet.getDate("lend_date"));
                    fineInfo.setBackDate(resultSet.getDate("back_date"));
                    FineInfolist.add(fineInfo);
                }//这本书最晚归还者即为该扣费者
            }
        });
        FineInfo LastBack = null;
        for (int i=0; i<FineInfolist.size(); i++) {
            if (LastBack == null) {
                LastBack = FineInfolist.get(i);
            } else {
                if (!FineInfolist.get(i).getLendDate().before(LastBack.getLendDate())) {
                    LastBack = FineInfolist.get(i);
                }
            }
        }
        return LastBack;
    }

    /**根据借还记录中书ID查找借书人ID**/
    public int getReaderID(long bookId) {
        return LastBack(bookId).getReaderId();
    }

    public ArrayList<Lend> lendList(){
        final ArrayList<Lend> list=new ArrayList<Lend>();

        jdbcTemplate.query(GET_ALL_BOOKE_WITH_NAME_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Lend lend=new Lend();
                    lend.setName(resultSet.getString("name"));
                    lend.setBackDate(resultSet.getDate("back_date"));
                    lend.setBookId(resultSet.getLong("book_id"));
                    lend.setLendDate(resultSet.getDate("lend_date"));
                    lend.setReaderId(resultSet.getInt("reader_id"));
                    lend.setSernum(resultSet.getLong("sernum"));
                    list.add(lend);
                }
            }
        });
        return list;
    }

    public ArrayList<Lend> myLendList(int readerId){
        final ArrayList<Lend> list=new ArrayList<Lend>();

        jdbcTemplate.query(MY_LEND_LIST_SQL, new Object[]{readerId},new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Lend lend=new Lend();
                    lend.setBackDate(resultSet.getDate("back_date"));
                    lend.setBookId(resultSet.getLong("book_id"));
                    lend.setLendDate(resultSet.getDate("lend_date"));
                    lend.setReaderId(resultSet.getInt("reader_id"));
                    lend.setSernum(resultSet.getLong("sernum"));
                    list.add(lend);
                }
            }
        });
        return list;

    }

    public ArrayList<Lend> MyBookWithName(final int readerId) {
        final ArrayList<Lend> list=new ArrayList<Lend>();

        jdbcTemplate.query(GET_BOOK_NAME_SQL, new Object[]{readerId},new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Lend lend=new Lend();
                    lend.setName(resultSet.getString("name"));
                    lend.setBackDate(resultSet.getDate("back_date"));
                    lend.setBookId(resultSet.getLong("book_id"));
                    lend.setLendDate(resultSet.getDate("lend_date"));
                    lend.setReaderId(readerId);
                    lend.setSernum(resultSet.getLong("sernum"));
                    list.add(lend);
                }
            }
        });
        return list;
    }

    public static void main(String args[]) {

    }
}
