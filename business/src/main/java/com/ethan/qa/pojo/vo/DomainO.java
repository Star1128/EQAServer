package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.Domain;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@Data
public class DomainO implements Serializable {
    private String domainId;
    private String domainName;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public DomainO(Domain domain) {
        this.domainId = String.valueOf(domain.getDomainId());
        this.domainName = domain.getDomainName();
        this.viewCount = domain.getViewCount();
        this.createTime = domain.getCreateTime();
        this.updateTime = domain.getUpdateTime();
    }
}
