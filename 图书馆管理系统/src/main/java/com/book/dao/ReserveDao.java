package com.book.dao;

import com.book.domain.Reserve;
import com.book.domain.ReserveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by ambition on 17/11/9.
 */
@Repository
public class ReserveDao {
    private JdbcTemplate jdbcTemplate;
    public static final int RESERVE_MAX = 3;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //Reserve reserve = new Reserve(jdbcTemplate);

    private final static String IF_BOOK_CAN_SQL="SELECT state from book_info WHERE book_id = ? ";
    private final static String BOOK_RESERVE_SQL="UPDATE book_info SET state = ? WHERE book_id = ?";
    private final static String MY_RESERVE_LIST_SQL="SELECT * from reserve_list WHERE reader_id = ? ";
    private final static String RESERVE_LIST_SQL="SELECT * from reserve_list";
    private final static String REMOVE_BORROWED_ITEM_SQL="DELETE FROM reserve_list where book_id = ?";
    private final static String COUNT_RESERVE_NUMS_SQL="SELECT COUNT(*) counts FROM reserve_list WHERE reader_id = ?";

    //检测是否可被预订
    //当state＝1时，可被预订
    public boolean CanBeReserved(long bookId) {
        final boolean []canBeReserved = new boolean[1];
        jdbcTemplate.query(IF_BOOK_CAN_SQL, new Object[]{bookId},  new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                //resultSet.beforeFirst();
                if (resultSet.getInt("state") == 1)
                    canBeReserved[0] = true;
                else
                    canBeReserved[0] = false;
            }
        });
        return canBeReserved[0];
    }

    public int ReservingBookNums(final int readerId) {
        final int []count = new int[1];
        jdbcTemplate.query(COUNT_RESERVE_NUMS_SQL, new Object[]{readerId},  new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
               count[0] = resultSet.getInt("counts");
            }
        });
        System.out.println(count[0]);
        return count[0];
    }

//    public boolean ReserveBook(int readerId, long bookId) {
//        boolean succ = false;
//        if (!CanBeReserved(bookId)) return succ;
//
//        if (jdbcTemplate.update(BOOK_RESERVE_SQL, -1, bookId) > 0) {
//            Timer timer = new Timer(); //不同的timer是并行执行的
//            timer.schedule(new Reserve(readerId, bookId, jdbcTemplate), 0);
//
//            succ = true;
//        }//预订成功
//        // 1. 修改书的状态为 -1
//        // 2. 进入倒计时
//        return succ;
//    }

    public boolean reserve(int readerId, long bookId) {
        if (jdbcTemplate.update(BOOK_RESERVE_SQL, -1, bookId) > 0 ) {
            Timer timer = new Timer(); //不同的timer是并行执行的
            timer.schedule(new Reserve(readerId, bookId, jdbcTemplate), 0);
            return true;
        }
        return false;
    }
    public boolean IsReservedAndCanBeBorrowed(int readerId, long bookId) {
        if (Reserve.MatchInfo(readerId, bookId, jdbcTemplate) != -1) return true;
        return false;

    }//检查是否被预订

    public ArrayList<ReserveInfo> myReserveList(int readerId) {
        final ArrayList<ReserveInfo> list=new ArrayList<ReserveInfo>();

        jdbcTemplate.query(MY_RESERVE_LIST_SQL, new Object[]{readerId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    System.out.println(resultSet.getTimestamp("reserve_date"));
                    ReserveInfo reserveInfo = new ReserveInfo(
                            resultSet.getInt("reader_id"),
                            resultSet.getLong("book_id"),
                            resultSet.getTimestamp("reserve_date")
                    );

                    list.add(reserveInfo);
                }
            }
        });
        return list;
    }
    public ArrayList<ReserveInfo> reservelist() {
        final ArrayList<ReserveInfo> list=new ArrayList<ReserveInfo>();

        jdbcTemplate.query(RESERVE_LIST_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    System.out.println(resultSet.getTimestamp("reserve_date"));
                    ReserveInfo reserveInfo = new ReserveInfo(
                            resultSet.getInt("reader_id"),
                            resultSet.getLong("book_id"),
                            resultSet.getTimestamp("reserve_date")
                    );

                    list.add(reserveInfo);
                }
            }
        });
        return list;
    }

    public int RmItem(long bookId) {
        return jdbcTemplate.update(REMOVE_BORROWED_ITEM_SQL, bookId);
    }

}
