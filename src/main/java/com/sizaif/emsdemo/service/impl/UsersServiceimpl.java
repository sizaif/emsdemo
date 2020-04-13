package com.sizaif.emsdemo.service.impl;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.mapper.UserMapper;
import com.sizaif.emsdemo.pojo.Member;
import com.sizaif.emsdemo.pojo.Users;
import com.sizaif.emsdemo.service.UsersService;
import com.sizaif.emsdemo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceimpl implements UsersService {

    //注入mapper
    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public SystemResult AddOneUser(Map<String,Object> map) throws Exception {


        int su = userMapper.addUser(map);
        if(su>0)
        {
            return new SystemResult(200,"addUser successful");
        }
        else
            return new SystemResult(100,"addUser failed");

    }

    @Override
    public SystemResult DeleteUserById(int id) throws Exception {

        int su = userMapper.deleteUserById(id);
        if(su>0)
        {
            return new SystemResult(200,"delete oneuser  successful");
        }else
            return new SystemResult(100,"delete failed");
    }


    @Override
    public List<Users> queryAllUserList() throws Exception {
        List<Users> usersList = userMapper.queryAllUserList();
        return usersList;
    }

    @Override
    public List<Member> queryAllUserMemberList() throws Exception {
        List<Member> usersmemberslist = userMapper.queryAllUserMemberList();
        return usersmemberslist;
    }

    @Override
    public Users queryOneUserById(int uid) throws Exception {
        Users users = userMapper.queryOneUserById(uid);
        return users;
    }

    @Override
    public Member queryOneUserMemberById(int uid) throws Exception {
        Member member = userMapper.queryOneUserMemberById(uid);
        return member;
    }

    @Override
    public Users queryUserByName(Map<String, Object> map) {
        Users users = userMapper.queryUserByName(map);
        return users;
    }

    /**
     * done
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @Override
    public SystemResult UpdateUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        HashMap<String,Object> userMap = new HashMap<String, Object>();

        String name = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("upwd");
        String modifyDate = new DateUtils().DatetoString(new Date());
        String role = "player";
        switch ( Integer.parseInt(httpServletRequest.getParameter("role") )){
            case 0:
                role = "player";
                break;
            case 1:
                role = "checker";
                break;
            case 2:
                role = "worker";
                break;
            case 3:
                role = "judger";
                break;
            case 4:
                role = "administration";
                break;
        }

        //获取ID
        int id = Integer.parseInt(httpServletRequest.getParameter("uid"));
        userMap.put("createDate",null);
        userMap.put("modifyDate",modifyDate);
        userMap.put("isEnabled",null);
        userMap.put("isLocked",null);
        userMap.put("lastLoginDate",null);
        userMap.put("lastLoginIp",null);
        userMap.put("name",name);
        userMap.put("encodePassword",password);
        userMap.put("role",role);
        userMap.put("id",id);

        int su = userMapper.updateUser(userMap);
        if(su > 0){
            return  new SystemResult(200,"Update UserInfo sucessful");
        }
        else
            return new SystemResult(100,"Update UserInfo failed");
    }

    /**
     * done
     * @param map
     * @return
     */
    @Override
    public SystemResult UpdateUserInfoByHashMap(Map map) {
        HashMap<String,Object> userMap = new HashMap<String, Object>();

        userMap.put("createDate",map.get("createDate"));
        userMap.put("modifyDate",map.get("modifyDate"));
        userMap.put("isEnabled",map.get("isEnabled"));
        userMap.put("isLocked",map.get("isLocked"));
        userMap.put("lastLoginDate",map.get("lastLoginDate"));
        userMap.put("lastLoginIp",map.get("lastLoginIp"));
        userMap.put("name",map.get("name"));
        userMap.put("encodePassword",map.get("encodePassword"));
        userMap.put("role",map.get("role"));
        userMap.put("id",map.get("id"));

        int su = userMapper.updateUser(userMap);
        if(su > 0){
            return  new SystemResult(200,"Update UserInfo sucessful");
        }
        else
            return new SystemResult(100,"Update UserInfo failed");
    }
}
