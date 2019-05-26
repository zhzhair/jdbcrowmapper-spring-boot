package com.example.demo.feedback.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

public class FeedbackEntity {
    @ApiModelProperty(value = "id")
    @Id
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "app版本")
    private String appVersion;

    @ApiModelProperty(value = "系统版本")
    private String sysVersion;

    @ApiModelProperty(value = "反馈内容")
    private String content;

    @ApiModelProperty(value = "反馈时间")
    @JSONField(serialize = false)
    private Timestamp createTime;

    @ApiModelProperty(value = "手机操作系统类型状态")
    private String osType;

    @ApiModelProperty(value = "网络状态")
    private String onlineStatus;

    @ApiModelProperty(value = "回复将次数")
    private Integer replyCount;

    @ApiModelProperty(value = "忽略次数")
    private Integer ignoreCount;

    @Override
    public String toString() {
        return "FeedbackEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", mobile='" + mobile + '\'' +
                ", userName='" + userName + '\'' +
                ", osType='" + osType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", sysVersion='" + sysVersion + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", osType='" + osType + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                ", replyCount=" + replyCount +
                ", ignoreCount=" + ignoreCount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getIgnoreCount() {
        return ignoreCount;
    }

    public void setIgnoreCount(Integer ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
