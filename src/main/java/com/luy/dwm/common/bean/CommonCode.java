package com.luy.dwm.common.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CommonCode {
    private String codeNo;
    private String codeName;
    private String extName;
    private String parentCodeNo;
    private Integer lastUpdateUserId;
    private Date lastUpdateTime;
    private String isDeleted;
}
