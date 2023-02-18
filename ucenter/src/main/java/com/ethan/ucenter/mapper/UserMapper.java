package com.ethan.ucenter.mapper;

import com.ethan.ucenter.pojo.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ethan.ucenter.pojo.vo.UserAdminVO;
import com.ethan.ucenter.pojo.vo.UserVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
public interface UserMapper extends BaseMapper<User> {

    UserVO selectUserAllInfoById(String userId);

    List<UserAdminVO> selectAllUser(int size, int offset, int page, String phone, String email, String username, String id, String status);
}
