package com.sizaif.emsdemo.service.User.impl;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.mapper.User.UserMapper;
import com.sizaif.emsdemo.mapper.User.UserRoleMapper;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.User.UserRoleKey;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.User.UsersService;
import com.sizaif.emsdemo.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //注入mapper
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    /**
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public SystemResult AddOneUser(Users user,String roleIds) {

        logger.debug("开始验证用户是否存在");
        Users existusers = null;
        existusers = userMapper.queryUserByName(user.getName());

        if( null != existusers ){
            return new SystemResult(100,"用户已存在",existusers);
        }else{
            logger.debug("开始添加用户");
            int su = userMapper.addUserSelective(user);
            if(su>0){
                int userId = user.getId();
                // 给用户授角色
                logger.debug("开始给用户授角色");
                SystemResult setUserRoleKeyresult = setUserRoleKey(userId, roleIds);
                // 添加角用户色成功
                if(setUserRoleKeyresult.getStatus()==200){

                    // 添加用户和角色全部成功
                    return  new SystemResult(200,"添加用户,并授予角色成功",userId);
                }
                else
                    return  new SystemResult(100,"添加用户,并授予角色失败");
            }else{
                return  new SystemResult(100,"添加用户,失败");
            }

        }
    }

    @Override
    public SystemResult DeleteUserById(int id){

        int su = userMapper.deleteUserById(id);
        if(su>0)
        {
            return new SystemResult(200,"delete oneuser  successful");
        }else
            return new SystemResult(100,"delete failed");
    }

    @Override
    public SystemResult DeleteUserRolle(int userId, String roleIds) {
        String[] arrays = roleIds.split(",");
        int c = 0;

        for (String roleId : arrays) {
            UserRoleKey urk = new UserRoleKey();
            urk.setRoleId(Integer.valueOf(roleId));
            urk.setUserId(userId);
            int su = userRoleMapper.deleteByPrimaryKey(urk);
            if( su > 0)
                c++;
        }
        if( c == arrays.length)
            return new SystemResult(200,"删除用户角色成功",arrays);
        else
            return new SystemResult(100,"删除用户角色失败",arrays);

    }


    @Override
    public List<Users> queryAllUserList() throws Exception {
        List<Users> usersList = userMapper.queryAllUserList();
        return usersList;
    }

    @Override
    public List<MemberVO> queryAllUserMemberRoleList()  {
        List<MemberVO> usersmemberslist = userMapper.queryAllUserMemberRoleList();
        return usersmemberslist;
    }

    @Override
    public Users queryOneUserById(int uid) throws Exception {
        Users users = userMapper.queryOneUserById(uid);
        return users;
    }

    @Override
    public List<MemberVO> queryOneUserMemberById(int uid) throws Exception {
        List<MemberVO> member = userMapper.queryOneUserMemberById(uid);
        return member;
    }

    @Override
    public Users queryUserByName(String record) {
        return userMapper.queryUserByName(record);
    }

//    /**
//     * done
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @return
//     */
//    @Override
//    public SystemResult UpdateUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//
//        HashMap<String,Object> userMap = new HashMap<String, Object>();
//
//        String name = httpServletRequest.getParameter("username");
//        String password = httpServletRequest.getParameter("upwd");
//        String modifyDate = new DateUtils().DatetoString(new Date());
//        String role = "player";
//        switch ( Integer.parseInt(httpServletRequest.getParameter("role") )){
//            case 0:
//                role = "player";
//                break;
//            case 1:
//                role = "checker";
//                break;
//            case 2:
//                role = "worker";
//                break;
//            case 3:
//                role = "judger";
//                break;
//            case 4:
//                role = "administration";
//                break;
//        }
//
//        //获取ID
//        int id = Integer.parseInt(httpServletRequest.getParameter("uid"));
//        userMap.put("createDate",null);
//        userMap.put("modifyDate",modifyDate);
//        userMap.put("isEnabled",null);
//        userMap.put("isLocked",null);
//        userMap.put("lastLoginDate",null);
//        userMap.put("lastLoginIp",null);
//        userMap.put("name",name);
//        userMap.put("encodePassword",password);
//        userMap.put("role",role);
//        userMap.put("id",id);
//
//        int su = userMapper.updateUser(userMap);
//        if(su > 0){
//            return  new SystemResult(200,"Update UserInfo sucessful");
//        }
//        else
//            return new SystemResult(100,"Update UserInfo failed");
//    }

    /**
     * done
     * @param users
     * @return
     */
    @Override
    public SystemResult UpdateUserInfo(Users users) {

        int su = userMapper.updateUserSelective(users);
        if(su > 0){
            return  new SystemResult(200,"Update UserInfo sucessful");
        }
        else
            return new SystemResult(100,"Update UserInfo failed");
    }

    /**
     * 对用户角色表进行更新
     *
     * @param userRoleKey
     * @return
     */
    @Override
    public SystemResult UpdateUserRole(UserRoleKey userRoleKey) {

        // 先删除原先的关系
        List<UserRoleKey> existkeyList = userRoleMapper.findByUserId(userRoleKey.getUserId());
        int count = 0;
        if( null != existkeyList){

            for (UserRoleKey roleKey : existkeyList) {
                logger.debug("BBBB");
                int su  = userRoleMapper.deleteByPrimaryKey(roleKey);
                if(su > 0)
                    count++;
            }
            if(count == existkeyList.size()){

                // 插入新的关系表
                int re = userRoleMapper.insertSelective(userRoleKey);
                if( re > 0 ){
                    return  new SystemResult(200,"更新关系表成功");
                }else
                    return  new SystemResult(100,"更新关系表失败");
            }
        }

        return  new SystemResult(100,"更新关系表失败");
    }

    public SystemResult setUserRoleKey(int userId,String roleIds) {

        String[] arrays = roleIds.split(",");
        int c = 0;

        for (String roleId : arrays) {
            UserRoleKey urk = new UserRoleKey();
            urk.setRoleId(Integer.valueOf(roleId));
            urk.setUserId(userId);
            int su = userRoleMapper.insertSelective(urk);
            if( su > 0)
                c++;
        }
        if( c == arrays.length)
            return new SystemResult(200,"添加用户角色成功",arrays);
        else
            return new SystemResult(100,"添加用户角色失败",arrays);

    }
}
