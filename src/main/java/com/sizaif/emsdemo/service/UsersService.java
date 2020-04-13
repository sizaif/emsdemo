package com.sizaif.emsdemo.service;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.pojo.Member;
import com.sizaif.emsdemo.pojo.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UsersService {



    SystemResult AddOneUser(Map<String,Object> map) throws Exception;

    SystemResult DeleteUserById(int id) throws Exception;


    List<Users> queryAllUserList()throws Exception;

    List<Member> queryAllUserMemberList() throws Exception;

    Users queryOneUserById(int id) throws Exception;

    Member queryOneUserMemberById(int id)throws Exception;

    Users queryUserByName(Map<String,Object> map);

    SystemResult UpdateUserInfo(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);

    SystemResult UpdateUserInfoByHashMap(Map<String,Object> map);
}
