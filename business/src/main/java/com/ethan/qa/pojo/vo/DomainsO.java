package com.ethan.qa.pojo.vo;

import java.util.List;

/**
 * @author Ethan 2023/4/7
 */
public class DomainsO {

    private List<DomainO> mDomains;

    public DomainsO(List<DomainO> domains) {
        mDomains = domains;
    }

    public List<DomainO> getDomains() {
        return mDomains;
    }

    public void setDomains(List<DomainO> domains) {
        this.mDomains = domains;
    }
}
