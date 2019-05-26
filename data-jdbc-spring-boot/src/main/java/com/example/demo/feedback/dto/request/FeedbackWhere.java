package com.example.demo.feedback.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FeedbackWhere {
    @ApiModelProperty(value = "字段code码", example = "1")
    private Integer fieldNum;
    @ApiModelProperty(value = "处理状态码", required = true, example = "1")
    private Integer handleStatus;
    @ApiModelProperty(value = "搜索关键词", required = true, example = "二哈")
    private String searchValue;
    @ApiModelProperty(value = "开始页", required = true, example = "1")
    private Integer page;
    @ApiModelProperty(value = "每页显示条数", required = true, example = "20")
    private Integer pageSize;

    @Override
    public String toString() {
        return "FeedbackWhere{" +
                "fieldNum=" + fieldNum +
                ", handleStatus=" + handleStatus +
                ", searchValue='" + searchValue + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }

    public Integer getFieldNum() {
        return fieldNum;
    }

    public void setFieldNum(Integer fieldNum) {
        this.fieldNum = fieldNum;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
