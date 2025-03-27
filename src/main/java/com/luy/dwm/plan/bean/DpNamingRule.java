package com.luy.dwm.plan.bean;

import lombok.Data;

import java.util.Date;

@Data
public class DpNamingRule {
    private Long id;
    private String ruleName;
    private String rulePrefix;
    private String ruleBody;
    private String ruleDesc;
    private Long lastUpdateUserId;
    private Date lastUpdateTime;
    private String isDeleted;
}