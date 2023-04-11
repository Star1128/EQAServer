package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.UserDomain;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ethan 2023/4/11
 */
@Data
public class UserDomainO {
    private Long userId;
    private Long domainId;
    private String domainName;
    private Integer exp;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public UserDomainO(UserDomain userDomain, String domainName) {
        this.userId = userDomain.getUserId();
        this.domainId = userDomain.getDomainId();
        this.domainName = domainName;
        this.exp = userDomain.getExp();
        this.createTime = userDomain.getCreateTime();
        this.updateTime = userDomain.getUpdateTime();
    }
}
