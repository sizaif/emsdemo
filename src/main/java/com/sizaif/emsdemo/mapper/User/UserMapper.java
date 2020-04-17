package com.sizaif.emsdemo.mapper.User;

import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.User.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// 这是mybaties 的Mapper类
@Mapper
@Repository
public interface UserMapper {

    /**
     * 获得所有用户以及详细成员信息列表
     * @return
     */
    List<Member> queryAllUserMemberList();

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
    Member queryOneUserMemberById(int id);



    /**
     * 通过用户名查询用户-> 登录
     * @return
     */
    Users queryUserByName(Map<String,Object> map);

    /**
     *  添加一个用户用户
     * @param map
     * @return
     */
    int addUser(Map<String,Object> map);

    /**
     *  通过ID删除用户
     * @param id
     * @return
     */
    int deleteUserById(int id);

    /**
     * 更新用户
     * @param map
     * @return
     */
    int updateUser(Map<String,Object> map);


}
