package com.sizaif.emsdemo.appoint;

import com.sizaif.emsdemo.dto.IndexDto;
import com.sizaif.emsdemo.mapper.User.PermissionsMapper;
import com.sizaif.emsdemo.mapper.User.RoleMapper;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.User.Role;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.utils.DateUtils;
import com.sizaif.emsdemo.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

public class UsersServiceAppoint {

    @Autowired
    private static PermissionsMapper permissionsMapper;
    @Autowired static RoleMapper roleMapper;
    /**
     *  将User info 写入到 导航栏dto 中
     * @param dto
     * @param users Member
     */
    public static void WriteUsersInfoToDto(IndexDto dto, Users users, Member member){

        //ID;
        dto.setUid(users.getId());
        //用户名
        dto.setuName(users.getName());
        //昵称
        dto.setuNickName(member.getTruename());
//        //密码
//        dto.setPassWord(user.getUpwd());
        //头像
        dto.setuImage(member.getImage());
    }
    public static Member MemberHttpWriteToMap(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Member member = new Member();
        member.setAddress(httpServletRequest.getParameter("address"));
        member.setEmail(httpServletRequest.getParameter("email"));
        member.setBirth(httpServletRequest.getParameter("birth"));
        member.setTruename(httpServletRequest.getParameter("truename"));
        member.setPhone(httpServletRequest.getParameter("phone"));
        member.setSchool(httpServletRequest.getParameter("school"));
        /**
         *  前端没有image 信息  设置默认为 user.png
         */
        member.setImage("user.png");
        String gender = httpServletRequest.getParameter("gender");
        if ( null != gender)
            member.setGender(Integer.parseInt(gender));
        String id = httpServletRequest.getParameter("uid");
        if( null != id)
            member.setId(Integer.parseInt(id));

        return member;
    }

    public static Users UsersHttpWriteToMap(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Users umap = new Users();
        // users
        umap.setName(httpServletRequest.getParameter("uname"));
        umap.setPassword(httpServletRequest.getParameter("encodePassword"));

        /**
         *  前台的role 传来的值 0,1,2,3,4  转化为对应的 角色名称
         */
        String defaut_role = "player";
        try {
            int role_number = 0;
            if(null != httpServletRequest.getParameter("role"))
                role_number = Integer.parseInt(httpServletRequest.getParameter("role"));
            switch ( role_number ){
                case 0:
                    defaut_role = "player";
                    break;
                case 1:
                    defaut_role = "checker";
                    break;
                case 2:
                    defaut_role = "worker";
                    break;
                case 3:
                    defaut_role = "judger";
                    break;
                case 4:
                    defaut_role = "administration";
                    break;
                default:
                    defaut_role = "player";
                    break;
            }
            umap.setRole(defaut_role);
        }catch(Exception e){
            e.printStackTrace();
        }
        return umap;
    }

    public static void UsersOtherInfo(Users users,HttpServletRequest httpServletRequest){
        /**
         *   createDate
         *   modifyDate
         *   isEnabled
         *   isLocked
         *   lastLoginDate
         *   lastLoginIp  待修改
         *   lockDate
         */
        // 当前创建时间
        users.setCreateDate(DateUtils.DatetoString(new Date()));
        // 修改时间
        users.setModifyDate(DateUtils.DatetoString(new Date()));
        // 默认启用状态
        users.setEnabled(true);
        // 默认未锁定状态
        users.setLocked(false);
        // 默认上次登录日期 null
        users.setLastLoginDate(null);
        // 待修改登录IP  默认未 本地
        users.setLastLoginIp(IPUtils.getIpAddress(httpServletRequest));
        // 默认锁定日期null
        users.setLockDate(null);

    }

    public static void setRoleName(Users user){

        if(null != user.getRole()){
            int roleid = Integer.parseInt(user.getRole());
            Role role = roleMapper.selectByPrimaryKey(roleid);
            user.setRole(role.getDescpt());
        }
    }





}
