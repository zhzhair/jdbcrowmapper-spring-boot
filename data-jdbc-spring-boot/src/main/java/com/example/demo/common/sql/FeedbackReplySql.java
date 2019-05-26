package com.example.demo.common.sql;

/**
 * sql语句是由测试类SampleDataJdbcApplicationTests生成
 */
public interface FeedbackReplySql {
    String SELECT_COUNT_SQL = "select count(*) from feedback_reply ";
    String SELECT_SQL = "select id,reply_type,reply_context,feedback_id from feedback_reply ";
    String INSERT_SQL = "insert into feedback_reply(reply_type,reply_context,feedback_id) values (?,?,?)";
}
