package com.ethan.ucenter.pojo.vo;

import com.ethan.ucenter.pojo.po.UserInfo;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Ethan 2023/2/15
 */
@Data
public class UserVO {
    private String userName;
    private int status;
    private String sex;
    private String avatar;
    private String motto;
    private String phone;
    private String email;
    private String company;
    private String position;
    private LocalDate birthday;
    private String location;

    public UserInfo toUserInfoPO(long uid) {
        return new UserInfo(uid, sex, avatar, motto, phone, company, position, birthday, location);
    }
}
