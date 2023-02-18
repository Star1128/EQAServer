package com.ethan.ucenter.pojo.vo;

/**
 * @author Ethan 2023/2/17
 */
public class UserAdminVO extends UserVO {
    private Long userId;

    public UserAdminVO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
