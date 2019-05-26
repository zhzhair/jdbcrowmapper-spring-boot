package com.example.demo.feedback.service;

import com.example.demo.feedback.domain.FeedbackEntity;
import com.example.demo.feedback.dto.request.FeedbackInfo;
import com.example.demo.feedback.dto.request.FeedbackWhere;
import com.example.demo.feedback.dto.response.FeedbackDetail;

public interface FeedbackService {
    /**
     *  上传反馈数据
     */
    FeedbackEntity uploadFeedback(FeedbackInfo feedbackInfo, int userId);
    /**
     *  保存回馈信息
     */
    void saveFeedbackInfo(int replyType, int id, String context);

    /**
     *  保存回馈信息
     */
    boolean[] canFeedback(int id);

    /**
     * 获取反馈详情的数据
     */
    Object[] getFeedbackList(FeedbackWhere feedbackWhere);

    /**
     * 反馈详情和回复列表
     */
    FeedbackDetail getDetail(int id);

    /**
     * 批量删除
     */
    void deleteBatch(Integer[] ids);
}
