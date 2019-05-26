package com.example.demo.feedback.controller;

import com.example.demo.common.controller.BaseController;
import com.example.demo.common.dto.BaseResponse;
import com.example.demo.feedback.domain.FeedbackEntity;
import com.example.demo.feedback.dto.request.FeedbackInfo;
import com.example.demo.feedback.dto.request.FeedbackWhere;
import com.example.demo.feedback.dto.response.FeedbackDetail;
import com.example.demo.feedback.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("feedback")
@Api(description = "基础 -- 用户反馈")
public class FeedbackController extends BaseController {
    @Resource
    private FeedbackService feedbackService;

    @ApiOperation(value = "用户反馈数据入库", notes = "用户反馈")
    @RequestMapping(value = "upload", method = { RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public BaseResponse<FeedbackEntity> uploadFeedback(@RequestBody FeedbackInfo feedBackInfo, @RequestParam int userId) {
        BaseResponse<FeedbackEntity> baseResponse = new BaseResponse<>();
        FeedbackEntity feedbackEntity = feedbackService.uploadFeedback(feedBackInfo,userId);
        baseResponse.ok();
        baseResponse.setData(feedbackEntity);
        return baseResponse;
    }

    @ApiOperation(value = "用户反馈--回复或忽略操作", notes = "replyType-1：回复,replyType-2：忽略")
    @RequestMapping(value = "saveReplyInfo", method = { RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE })
    public BaseResponse saveReplyInfo(@RequestParam(value = "replyType") int replyType, @RequestParam(value = "id") int id, @RequestParam(value = "context") String context) {
        BaseResponse baseResponse = new BaseResponse();
        boolean[] boolArr = feedbackService.canFeedback(id);
        if(replyType == 1 && !boolArr[0]){
            baseResponse.setCode(-4);
            baseResponse.setMsg("回复次数太多");
            return baseResponse;
        }
        if(replyType == 2 && !boolArr[1]){
            baseResponse.setCode(-4);
            baseResponse.setMsg("已经忽略此反馈");
            return baseResponse;
        }
        feedbackService.saveFeedbackInfo(replyType,id,context);
        baseResponse.ok();
        return baseResponse;
    }

    @ApiOperation(value = "用户反馈列表查询", notes = "用户反馈列表查询，fieldNum:1--用用户名查询，2--用手机号查询")
    @RequestMapping(value = "getFeedbackList", method = { RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE })
    public BaseResponse<Map<String, Object>> getFeedbackList(@RequestBody FeedbackWhere feedbackWhere) {
        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        Object[] object = feedbackService.getFeedbackList(feedbackWhere);
        Map<String, Object> map = new HashMap<>();
        map.put("count",object[0]);
        map.put("list",object[1]);
        baseResponse.ok();
        baseResponse.setData(map);
        return baseResponse;
    }

    @ApiOperation(value = "用户反馈详情查询", notes = "用户反馈详情查询")
    @RequestMapping(value = "getDetail", method = { RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE })
    public BaseResponse<FeedbackDetail> getFeedbackDetail(Integer id) {
        BaseResponse<FeedbackDetail> baseResponse = new BaseResponse<>();
        FeedbackDetail feedbackDetail = feedbackService.getDetail(id);
        baseResponse.ok();
        baseResponse.setData(feedbackDetail);
        return baseResponse;
    }

    @ApiOperation(value = "批量删除反馈信息", notes = "批量删除反馈信息")
    @RequestMapping(value = "deleteBatch", method = { RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE })
    public BaseResponse<Object> deleteBatch(Integer[] ids) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        feedbackService.deleteBatch(ids);
        baseResponse.ok();
        return baseResponse;
    }
}
