package com.sizaif.emsdemo.mapper.User;

import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.User.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这是mybaties 的Mapper类
@Mapper
@Repository
public interface UserMapper {

    /**
     * 获得所有用户以及详细成员信息列表
     * @return
     */
    List<MemberVO> queryAllUserMemberRoleList();

    /**
     * 只返回用户列表 没有详细成员信息
     * @return
     */
    List<Users> queryAllUserList();

    /**
     * 通过ID查询用户
     * @param id
     * @return
     */
    Users queryOneUserById(int id);

    /**
     * 通过ID 查询 用户和用户信息
     * @param id
     * @return
     */
    List<MemberVO> queryOneUserMemberById(int id);


    /**
     * 通过用户名查询用户-> 登录
     * @return
     */
    Users queryUserByName(String name);

    /**
     *  添加一个用户用户
     * @param
     * @return
     */
    int addUserSelective(Users users);

    /**
     *  通过ID删除用户
     * @param id
     * @return
     */
    int deleteUserById(int id);

    /**
     * 更新用户
     * @param
     * @return
     */
    int updateUserSelective(Users users);


}
