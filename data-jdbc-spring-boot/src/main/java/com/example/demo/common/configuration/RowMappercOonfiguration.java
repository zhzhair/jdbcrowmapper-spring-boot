package com.example.demo.common.configuration;

import com.example.demo.feedback.domain.FeedbackEntity;
import com.example.demo.feedback.domain.FeedbackReplyEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

/**
 * 结果集映射配置类，它和实体类的代码都由测试类SampleDataJdbcApplicationTests生成
 */
@Configuration
public class RowMappercOonfiguration {

    @Bean("feedback")
    public RowMapper<FeedbackEntity> getRowMapperFeedback(){
        return (resultSet,i)->{
            FeedbackEntity feedback = new FeedbackEntity();
            feedback.setId(resultSet.getInt("id"));
            feedback.setUserId(resultSet.getInt("user_id"));
            feedback.setUserName(resultSet.getString("user_name"));
            feedback.setMobile(resultSet.getString("mobile"));
            feedback.setOnlineStatus(resultSet.getString("online_status"));
            feedback.setAppVersion(resultSet.getString("app_version"));
            feedback.setSysVersion(resultSet.getString("sys_version"));
            feedback.setOsType(resultSet.getString("os_type"));
            feedback.setContent(resultSet.getString("content"));
            feedback.setReplyCount(resultSet.getInt("reply_count"));
            feedback.setIgnoreCount(resultSet.getInt("ignore_count"));
            feedback.setCreateTime(resultSet.getTimestamp("create_time"));
            return feedback;
        };
    }

    @Bean("feedback_reply")
    public RowMapper<FeedbackReplyEntity> getRowMapperFeedbackReply(){
        return (resultSet,i)->{
            FeedbackReplyEntity feedbackReply = new FeedbackReplyEntity();
            feedbackReply.setId(resultSet.getInt("id"));
            feedbackReply.setReplyType(resultSet.getInt("reply_type"));
            feedbackReply.setReplyContext(resultSet.getString("reply_context"));
            feedbackReply.setFeedbackId(resultSet.getInt("feedback_id"));
            return feedbackReply;
        };
    }
}
