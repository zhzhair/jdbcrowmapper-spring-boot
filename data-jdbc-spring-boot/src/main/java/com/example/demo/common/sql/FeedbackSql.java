package com.example.demo.common.sql;

/**
 * sql语句是由测试类SampleDataJdbcApplicationTests生成
 */
public interface FeedbackSql {
    String SELECT_COUNT_SQL = "select count(*) from feedback ";
    String SELECT_SQL = "select id,user_id,user_name,mobile,online_status,app_version,sys_version,os_type,content,reply_count,ignore_count,create_time from feedback ";
    String SELECT_BY_ID_SQL = "select id,user_id,user_name,mobile,online_status,app_version,sys_version,os_type,content,reply_count,ignore_count,create_time from feedback where id = ?";
    String INSERT_SQL = "insert into feedback(user_id,user_name,mobile,online_status,app_version,sys_version,os_type,content,reply_count,ignore_count,create_time) values (?,?,?,?,?,?,?,?,?,?,?)";
    String DELETE_SQL = "delete from feedback where id = ?";
}
