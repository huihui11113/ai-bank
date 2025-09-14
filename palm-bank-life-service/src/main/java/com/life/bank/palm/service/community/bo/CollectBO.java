package com.life.bank.palm.service.community.bo;


import lombok.Data;

import java.util.Date;

@Data
public class CollectBO {

    private String id;
    private String title;
    private String content;
    private String richContent;
    private Integer zanCnt;
    private Integer collectCnt;
    private Integer viewCnt;
    private Date createTime;
    private Integer collectId;

}
