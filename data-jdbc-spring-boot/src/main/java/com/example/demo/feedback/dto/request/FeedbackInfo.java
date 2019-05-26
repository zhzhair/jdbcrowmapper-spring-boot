package com.example.demo.feedback.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FeedbackInfo {
    @ApiModelProperty(value = "app版本", required = true, example = "1.0")
    private String appVersion;
    @ApiModelProperty(value = "系统版本", required = true, example = "7.1")
    private String sysVersion;
    @ApiModelProperty(value = "操作系统类型", required = true, example = "Android")
    private String osType;
    @ApiModelProperty(value = "反馈内容", required = true, example = "后台代码写的真棒")
    private String content;
    @ApiModelProperty(value = "网络状态", example = "WIFI")
    private String onlineStatus;

    @Override
    public String toString() {
        return "FeedbackInfo{" +
                ", appVersion='" + appVersion + '\'' +
                ", sysVersion='" + sysVersion + '\'' +
                ", osType='" + osType + '\'' +
                ", content='" + content + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                '}';
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

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
