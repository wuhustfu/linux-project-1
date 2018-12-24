package com.book.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
//Spring 自 2.0 版本开始，陆续引入了一些注解用于简化 Spring 的开发。
// @Repository注解便属于最先引入的一批，它用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean。
// 具体只需将该注解标注在 DAO类上即可。
@Repository
public class AdminDao {

    private JdbcTemplate jdbcTemplate;
    //为了使 JDBC 更加易于使用,Spring 在 JDBCAPI 上定义了一个抽象层, 以此建立一个JDBC存取框架.
    //作为 SpringJDBC 框架的核心, JDBC 模板的设计目的是为不同类型的JDBC操作提供模板方法.
    //每个模板方法都能控制整个过程,并允许覆盖过程中的特定任务.
    //通过这种方式,可以在尽可能保留灵活性的情况下,将数据库存取的工作量降到最低.
    //Spring 2.5 引入了 @Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
    // 通过 @Autowired的使用来消除 set ，get方法。
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //相当于构造函数

    // ? 是占位符，再程序里需要进行设置的参数
    private static final String MATCH_ADMIN_SQL="SELECT COUNT(*) FROM admin where admin_id = ? and password = ? ";
    private static final String RE_PASSWORD_SQL="UPDATE admin set password = ? where admin_id = ? ";
    private static final String GET_PASSWD_SQL="SELECT password from admin where admin_id = ?";

    private static final String GET_ADMIN_FINE_SQL="SELECT fine from admin where admin_id = ?";
    private static final String SET_ADMIN_FINE_SQL="UPDATE admin set fine = ? where admin_id = ?";

    public int getMatchCount(int adminId,String password){
        return jdbcTemplate.queryForObject(MATCH_ADMIN_SQL,new Object[]{adminId,password},Integer.class);
    }
    //验证用户名和密码，返回1表示验证成功，0表示在admin表中未找到匹配的账号
    public int rePassword(int adminId,String newPasswd){
        return jdbcTemplate.update(RE_PASSWORD_SQL,new Object[]{newPasswd,adminId});
    }
    //更新密码，返回1表示成功
    public String getPasswd(int id){
        return jdbcTemplate.queryForObject(GET_PASSWD_SQL,new Object[]{id},String.class);
    }
    //返回指定用户id的密码

    public float getAdminFine(int id){

        return jdbcTemplate.queryForObject(GET_ADMIN_FINE_SQL,new Object[]{id},float.class);
    }

    public int setAdminFine(int id,float fine){
        return jdbcTemplate.update(SET_ADMIN_FINE_SQL,new Object[]{fine,id});
    }


}
