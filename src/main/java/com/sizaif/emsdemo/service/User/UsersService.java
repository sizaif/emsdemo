package com.sizaif.emsdemo.service.User;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.User.Users;
import org.apache.catalina.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UsersService {



    SystemResult AddOneUser(Users users,String roleIds);

    SystemResult DeleteUserById(int id);

    SystemResult DeleteUserRolle(int userId,String roleIds);

    List<Users> queryAllUserList()throws Exception;

    List<MemberVO> queryAllUserMemberRoleList();

    Users queryOneUserById(int id) throws Exception;

    List<MemberVO> queryOneUserMemberById(int id)throws Exception;

    Users queryUserByName(String record);

    SystemResult UpdateUserInfo(Users users);
}
