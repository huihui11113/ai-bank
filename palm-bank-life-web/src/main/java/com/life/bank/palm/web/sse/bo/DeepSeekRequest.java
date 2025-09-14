
package com.life.bank.palm.web.sse.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class DeepSeekRequest {

    private List<Message> messages;
    private String model = "deepseek-chat";
    private Integer frequency_penalty = 0;
    private Integer max_tokens = 2048;
    private Integer presence_penalty = 0;
    private String stop;
    private Boolean stream = true;
    private Integer temperature = 1;
    private Integer top_p = 1;
    private Boolean logprobs = false;
    private Object top_logprobs;

    @Data
    @ApiModel("会话请求体")
    public static  class Message {
        @ApiModelProperty("内容，可能是大模型发的也可以是用户发的")
        private String content;
        @ApiModelProperty("system:大模型消息;user用户消息")
        private String role;
    }

}
