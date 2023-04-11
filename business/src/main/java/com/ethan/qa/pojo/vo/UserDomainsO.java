package com.ethan.qa.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Ethan 2023/4/11
 */
@Data
public class UserDomainsO {
    private List<UserDomainO> mDomains;

    public UserDomainsO(List<UserDomainO> domains) {
        mDomains = domains;
    }
}
