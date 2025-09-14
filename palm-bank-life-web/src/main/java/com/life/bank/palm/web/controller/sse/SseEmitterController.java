package com.life.bank.palm.web.controller.sse;

import com.life.bank.palm.common.result.CommonResponse;
import com.life.bank.palm.web.anotations.LoginRequired;
import com.life.bank.palm.web.sse.SseEmitterService;
import com.life.bank.palm.web.sse.bo.DeepSeekRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@RestController
@Api(tags = "ai智能问答接口")
@RequestMapping("/ai/conversation")
public class SseEmitterController {

    @Autowired
    private SseEmitterService sseEmitterService;

    /**
     * 创建SSE连接
     *
     */
    @LoginRequired
    @GetMapping(path = "connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ApiOperation("建立链接")
    public SseEmitter connect() {
        return sseEmitterService.connect();
//        return CommonResponse.buildSuccess(connect);

    }

    @LoginRequired
    @PostMapping("un-connect")
    @ApiOperation("断开链接")
    public CommonResponse<Boolean> unConnect() {
        sseEmitterService.removeClient(null);
        return CommonResponse.buildSuccess(Boolean.TRUE);
    }

    @PostMapping("send-question")
    @ApiOperation("发送问题")
    @LoginRequired
    public CommonResponse<Boolean> sendMessage(@RequestBody @ApiParam("问题对话入参") List<DeepSeekRequest.Message> messages) {
        sseEmitterService.sendAndReceiveMessage(messages);

        return CommonResponse.buildSuccess(Boolean.TRUE);
    }



}

