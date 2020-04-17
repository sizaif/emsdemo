package com.sizaif.emsdemo.appoint;

import com.sizaif.emsdemo.dto.IndexDto;
import com.sizaif.emsdemo.mapper.User.PermissionsMapper;
import com.sizaif.emsdemo.pojo.User.Member;
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
    public static HashMap<String,Object> MemberHttpWriteToMap(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HashMap<String, Object> hmap = new HashMap<>();
        hmap.put("address",httpServletRequest.getParameter("address"));
        hmap.put("email",httpServletRequest.getParameter("email"));
        hmap.put("birth",httpServletRequest.getParameter("birth"));
        hmap.put("truename",httpServletRequest.getParameter("truename"));
        hmap.put("phone",httpServletRequest.getParameter("phone"));
        hmap.put("school",httpServletRequest.getParameter("school"));
        /**
         *  前端没有image 信息  设置默认为 user.png
         */
        hmap.put("image","user.png");
        hmap.put("gender",httpServletRequest.getParameter("gender"));
        hmap.put("id",httpServletRequest.getParameter("uid"));
        hmap.put("memberRankId",httpServletRequest.getParameter("memberRankId"));
        return hmap;
    }

    public static HashMap<String,Object> UsersHttpWriteToMap(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HashMap<String, Object> umap = new HashMap<>();
        // users
        umap.put("uname",httpServletRequest.getParameter("uname"));
        umap.put("encodePassword",httpServletRequest.getParameter("encodePassword"));

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
            umap.put("role",defaut_role);
        }catch(Exception e){
            e.printStackTrace();
        }
        return umap;
    }

    public static void UsersOtherInfo(HashMap<String,Object> hashMap,HttpServletRequest httpServletRequest){
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
        hashMap.put("createDate",new DateUtils().DatetoString(new Date()));
        // 修改时间
        hashMap.put("modifyDate",new DateUtils().DatetoString(new Date()));
        // 默认启用状态
        hashMap.put("isEnabled",1);
        // 默认未锁定状态
        hashMap.put("isLocked",0);
        // 默认上次登录日期 null
        hashMap.put("lastLoginDate",null);
        // 待修改登录IP  默认未 本地
        hashMap.put("lastLoginIp",IPUtils.getIpAddress(httpServletRequest));
        // 默认锁定日期null
        hashMap.put("lockDate",null);

    }






}
