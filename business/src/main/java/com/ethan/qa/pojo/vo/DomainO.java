package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.Domain;

import java.util.List;

/**
 * @author Ethan 2023/4/7
 */
public class DomainO {

    private List<Domain> mDomains;

    public DomainO(List<Domain> domains) {
        mDomains = domains;
    }

    public List<Domain> getDomains() {
        return mDomains;
    }

    public void setDomains(List<Domain> domains) {
        this.mDomains = domains;
    }
}
