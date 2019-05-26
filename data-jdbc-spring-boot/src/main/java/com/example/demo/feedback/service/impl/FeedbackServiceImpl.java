package com.example.demo.feedback.service.impl;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.sql.FeedbackReplySql;
import com.example.demo.common.sql.FeedbackSql;
import com.example.demo.common.util.StringTools;
import com.example.demo.feedback.dto.request.FeedbackInfo;
import com.example.demo.feedback.dto.request.FeedbackWhere;
import com.example.demo.feedback.domain.FeedbackEntity;
import com.example.demo.feedback.domain.FeedbackReplyEntity;
import com.example.demo.feedback.dto.response.FeedbackDetail;
import com.example.demo.feedback.service.FeedbackService;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource(name = "feedback")
    private RowMapper<FeedbackEntity> feedBackRowMapper;
    @Resource(name = "feedback_reply")
    private RowMapper<FeedbackReplyEntity> feedbackReplyEntityRowMapper;

    @Override
    public FeedbackEntity uploadFeedback(FeedbackInfo feedbackInfo, int userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        jdbcTemplate.update((connection)->{
            PreparedStatement ps = connection.prepareStatement(FeedbackSql.INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, "xiaoming" + userId);
            ps.setString(3, StringTools.getMobileStr(userId));
            ps.setString(4, feedbackInfo.getOnlineStatus());
            ps.setString(5, feedbackInfo.getAppVersion());
            ps.setString(6, feedbackInfo.getSysVersion());
            ps.setString(7, feedbackInfo.getOsType());
            ps.setString(8, feedbackInfo.getContent());
            ps.setInt(9, 0);
            ps.setInt(10, 0);
            ps.setTimestamp(11, timestamp);
            return ps;
        },holder);
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        BeanUtils.copyProperties(feedbackInfo,feedbackEntity);
        feedbackEntity.setId(holder.getKey().intValue());
        feedbackEntity.setCreateTime(timestamp);
        feedbackEntity.setReplyCount(0);
        feedbackEntity.setIgnoreCount(0);
        feedbackEntity.setMobile(StringTools.getMobileStr(userId));
        feedbackEntity.setUserId(userId);
        feedbackEntity.setUserName("xiaoming" + userId);
        return feedbackEntity;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void saveFeedbackInfo(int replyType, int id, String context) {
        if(replyType == 1){
            jdbcTemplate.update("update feedback set reply_count = reply_count + 1 where id = ?",id);
        }else if(replyType == 2){
            jdbcTemplate.update("update feedback set ignore_count = ignore_count + 1 where id = ?",id);
        }else{
            throw new BusinessException("没有此类型的回复id");
        }
        jdbcTemplate.update(FeedbackReplySql.INSERT_SQL,replyType,context,id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean[] canFeedback(int id) {
        String replySring = FeedbackReplySql.SELECT_SQL + "where reply_type = 1 and feedback_id = ?";
        List<FeedbackReplyEntity> listReply = jdbcTemplate.query(replySring,new Object[]{id},feedbackReplyEntityRowMapper);
        String ignoreSring = FeedbackReplySql.SELECT_SQL + "where reply_type = 2 and feedback_id = ?";
        List<FeedbackReplyEntity> listIgnore = jdbcTemplate.query(ignoreSring,new Object[]{id},feedbackReplyEntityRowMapper);
        boolean boolIgnore = true;
        boolean boolReply = true;
        if(listIgnore.size() > 0 || listReply.size() > 0){
            boolIgnore = false;
        }
        if(listReply.size() > 5){
            boolReply = false;
        }
        return new boolean[]{boolReply,boolIgnore};
    }

    @Transactional(readOnly = true)
    @Override
    public Object[] getFeedbackList(FeedbackWhere feedbackWhere) {
        String sqlCount = FeedbackSql.SELECT_COUNT_SQL;
        String sql = FeedbackSql.SELECT_SQL;
        StringBuilder stringBuilder = new StringBuilder("where ");
        List<Object> params = new ArrayList<>();
        boolean flag = false;
        if(StringUtils.hasText(feedbackWhere.getSearchValue())){
            if(feedbackWhere.getFieldNum() == 1){
                stringBuilder.append("user_name = ? ");
                params.add(feedbackWhere.getSearchValue());
                flag = true;
            }
            if(feedbackWhere.getFieldNum() == 2){
                if(flag) stringBuilder.append("and ");
                stringBuilder.append("mobile = ? ");
                params.add(feedbackWhere.getSearchValue());
                flag = true;
            }
        }

        if(feedbackWhere.getHandleStatus() == 1){
            if(flag) stringBuilder.append("and ");
            stringBuilder.append("(reply_count > 0 or ignore_count > 0) ");
            flag = true;
        }

        if(feedbackWhere.getHandleStatus() == 2){
            if(flag) stringBuilder.append("and ");
            stringBuilder.append("reply_count = 0 and ignore_count = 0 ");
            flag = true;
        }

        if(flag){
            sql = sql + stringBuilder.toString();
            sqlCount = sqlCount + stringBuilder.toString();
        }

        sql = sql + "order by create_time desc limit "
                +(feedbackWhere.getPage()-1)+","+feedbackWhere.getPageSize();
        Object[] objects = params.toArray();

        List<FeedbackEntity> list = jdbcTemplate.query(sql,objects,feedBackRowMapper);
        int count = jdbcTemplate.queryForObject(sqlCount,objects,Integer.class);
        return new Object[]{count,list};
    }

    @Transactional(readOnly = true)
    @Override
    public FeedbackDetail getDetail(int id) {
        FeedbackDetail feedbackDetail = new FeedbackDetail();
        try {
            FeedbackEntity feedbackEntity = jdbcTemplate.queryForObject(FeedbackSql.SELECT_BY_ID_SQL,new Object[]{id},feedBackRowMapper);
            feedbackDetail.setFeedbackEntity(feedbackEntity);
            int feedbackId = feedbackEntity.getId();
            List<FeedbackReplyEntity> feedbackReplyEntities = jdbcTemplate.query(FeedbackReplySql.SELECT_SQL + "where feedback_id = ?",new Object[]{feedbackId},feedbackReplyEntityRowMapper);
            feedbackDetail.setFeedbackReplyEntities(feedbackReplyEntities);
        } catch (IncorrectResultSizeDataAccessException e){
            feedbackDetail.setFeedbackEntity(null);
            feedbackDetail.setFeedbackReplyEntities(new ArrayList<>());
        }
        return feedbackDetail;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteBatch(Integer[] ids) {
        List<Object[]> list = new ArrayList<>();
        for (int id : ids) {
            list.add(new Object[]{id});
        }
        jdbcTemplate.batchUpdate(FeedbackSql.DELETE_SQL,list);
        List<Object[]> list1 = new ArrayList<>();
        for (int id : ids) {
            List<FeedbackReplyEntity> feedbackReplyEntities = jdbcTemplate.query(FeedbackReplySql.SELECT_SQL + "where feedback_id = ?",new Object[]{id},feedbackReplyEntityRowMapper);
            for (FeedbackReplyEntity feedbackReplyEntity : feedbackReplyEntities) {
                list1.add(new Object[]{feedbackReplyEntity.getFeedbackId()});
            }
        }
        jdbcTemplate.batchUpdate("delete from feedback_reply where feedback_id = ?",list1);
    }

}
