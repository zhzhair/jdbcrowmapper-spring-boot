package com.example.demo.feedback.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

public class FeedbackReplyEntity {
    @Id
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "回馈类型")
    private Integer replyType;

    @ApiModelProperty(value = "回复内容")
    private String replyContext;

    @ApiModelProperty(value = "用户反馈id")
    private Integer feedbackId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public String getReplyContext() {
        return replyContext;
    }

    public void setReplyContext(String replyContext) {
        this.replyContext = replyContext;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }
}
