package com.book.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by ambition on 17/11/9.
 */
public class Reserve extends TimerTask {
    private final static String IF_BOOK_LENDED_SQL="SELECT state from book_info WHERE book_id = ? ";//
    private final static String BOOK_RESERVE_SQL="UPDATE book_info SET state = ? WHERE book_id = ?";
    private final static String RESERVE_ADD_SQL="INSERT INTO reserve_list (reader_id,book_id, reserve_date) values ( ? , ?, ?)";
    private final static String RESERVE_DELETE_SQL="DELETE FROM reserve_list WHERE book_id = ?  ";
    private final static String ALL_RESERVED_SQL="SELECT * FROM reserve_list";
    public static int times = 1000*600;//10 mins
    //public static ArrayList<ReserveInfo> reserveings = new ArrayList<ReserveInfo>();
    int readerId;
    long bookId;
    public JdbcTemplate jdbcTemplate;
    public  Reserve(int readerId, long bookId, JdbcTemplate jdbcTemplate) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.jdbcTemplate = jdbcTemplate;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        //reserveings.add(new ReserveInfo(readerId, bookId));
        jdbcTemplate.update(RESERVE_ADD_SQL, readerId, bookId, dateFormat.format(now));
    }

    public Reserve(int readerId, long bookId) {
        this.readerId = readerId;
        this.bookId = bookId;
    }
    int num = 0;
    public Reserve(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        num ++;
    }
    public Reserve() {}
    @Override
    public void run() {
        beforeResering();
        CountDown();
        afterReserving();

    }

    private void CountDown() {
        try {
            Thread.sleep(Reserve.times);
            System.out.println("---->       " + bookId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void beforeResering(){//没用
        System.out.println(readerId + " reserve " + bookId + "success");
        //System.out.println("add item " + reserveings.size());
    }

    private void afterReserving() {

        System.out.println(readerId + " reserve " + bookId + " time out ");
        System.out.println("######## now " + num--);
        jdbcTemplate.update(RESERVE_DELETE_SQL, bookId);//删除记录

        final  boolean []IsLended = new boolean[1];
        jdbcTemplate.query(IF_BOOK_LENDED_SQL, new Object[]{bookId},  new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                int state = resultSet.getInt("state");
                if (state == 0)
                    IsLended[0] = true;
                else if (state == -1) {
                    IsLended[0] = false;
                }
            }
        });

        if (!IsLended[0]) {
            jdbcTemplate.update(BOOK_RESERVE_SQL,1, bookId);
        }

        //System.out.println("remove item " + reserveings.size());
    }//倒计时结束
    // 1. 从预订链表中删除该条目
    // 2. 如果书已被借走则书的状态不变（state=0），否则修改书状态为1



    public static int MatchInfo(int readerId, long bookId, JdbcTemplate jdbcTemplate) {
        final ArrayList<ReserveInfo> reserveInfos = new ArrayList<ReserveInfo>();
        jdbcTemplate.query(ALL_RESERVED_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    ReserveInfo reserveInfo=new ReserveInfo(
                            resultSet.getInt("reader_id"),
                            resultSet.getLong("book_id"),
                            resultSet.getDate("reserve_date")
                    );
                    reserveInfos.add(reserveInfo);
                }
            }
        });

        for (int i=0; i<reserveInfos.size(); i++) {
            if (reserveInfos.get(i).getBookId() == bookId) {
                if (reserveInfos.get(i).getReaderId() == readerId)
                    return 1;
                return -1;
            }
        }
        return 0;
    }//1. 如果书被预订，且预定人匹配，则信息匹配，返回1
    //2. 如果书被预订，预定人不匹配，则信息失配，返回－1
    //3. 如果书没被预订，则信息不匹配，返回0

}
