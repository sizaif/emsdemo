package com.sizaif.emsdemo.controller;


import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.appoint.UsersServiceAppoint;
import com.sizaif.emsdemo.dto.IndexDto;
import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.pojo.User.Member;

import com.sizaif.emsdemo.pojo.User.Role;
import com.sizaif.emsdemo.pojo.User.UserRoleKey;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.Auth.AuthService;
import com.sizaif.emsdemo.service.User.MemberService;
import com.sizaif.emsdemo.service.User.UsersService;
import com.sizaif.emsdemo.utils.DateUtils;
import com.sizaif.emsdemo.utils.FileUtils;
import com.sizaif.emsdemo.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;

@Api("用户管理类")
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MemberService memberService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthService authService;

    /**
     * 查询所有用户,
     * 并传到用户列表页面
     * @param model
     * @return
     */
    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping("/toUserList")
    public String queryUserList(Model model)
    {
//        List<Member> usersLis = userMapper.queryUserList();
        try {
            List<Users> usersLists = usersService.queryAllUserList();
            List<Member> memberList = memberService.QueryAllMemberInfo();
            List<MemberVO> memberuserList = usersService.queryAllUserMemberRoleList();
            JsonUtils jsonUtils = new JsonUtils();
            String JsonUserList = jsonUtils.objectToJson(usersLists);
            String JsonMemberList = jsonUtils.objectToJson(memberList);
            String JsonMemberUserList = jsonUtils.objectToJson(memberuserList);
            model.addAttribute("JsonUserLists",usersLists);
            model.addAttribute("JsonMemberList",memberList);
            model.addAttribute("JsonMemberUserList",memberuserList);
            model.addAttribute("data",JsonMemberUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //返回到user.html
            return "production/Admin/users";
        }
    }

    @RequestMapping("/myData")
    @ResponseBody
    public String queryUserListJson(){
        List<MemberVO> memberuserList = usersService.queryAllUserMemberRoleList();
        return JsonUtils.objectToJson(memberuserList);
    }



    @RequestMapping("/addUMRInfo")
    @ResponseBody
    public String AddUser(@RequestParam("roleIds") String roleIds, Users users, Member member){


        /**
         * User,Member,user_role add-->
         * userUsers{id=0, isEnabled=null, isLocked=null, createDate='null', modifyDate='null',
         * lastLoginDate='null', lastLoginIp='null', lockDate='null',
         * name='1234', password='1234', role='5'}
         * member:->Member{id=0, memberRankId=0, address='aff1asfaf', birth='2020-04-22 00:00:00', email='QA@SAX.com',
         * gender=1, phone='121414124', truename='ASDQAQ', school='aSDAF', image='null'}
         * roleIds: 5
         */

        users.setModifyDate(DateUtils.DatetoString(new Date()));
        users.setCreateDate(DateUtils.DatetoString(new Date()));
        users.setEnabled(true);
        users.setLocked(false);
        // 默认头像
        member.setImage("user.png");
        logger.debug("User,Member,user_role add-->  user"+users.toString()+" member:->"+member.toString()+" roleIds: "+roleIds);
        //调用service对user进行处理
        try {

            logger.debug(" 开始 对user表进行添加");
            UsersServiceAppoint.setRoleName(users);
            SystemResult addUserResult = usersService.AddOneUser(users, roleIds);

            if (addUserResult.getStatus() == 100)
                return "100";
            else if (addUserResult.getStatus() == 200) {
                logger.debug(" 开始 对member表进行添加");
                // 返回主键得到新插入的userID
                int key = (int) addUserResult.getData();
                logger.debug("得到的主键userId --> " + key);
                // 开始插入 member  必须做
                member.setId(key);
                member.setMemberRankId(1);
                logger.debug("memberMap --> " + member);
                logger.debug("开始添加一个member");
                SystemResult systemResult1 = memberService.AddOneMember(member);
                if (systemResult1.getStatus() == 200) {
                    return "200";
                } else {
                    // 事务回滚 删除刚才添加的user
                    // 插入的member 存在重复的邮箱或手机号
                    usersService.DeleteUserById(key);
                    // 从 用户角色表中删除 关系
                    usersService.DeleteUserRolle(key, roleIds);
                    return "101";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "100";
    }



    @RequestMapping("/updateUMRInfo")
    @ResponseBody
    public String UpdateMemberInfo1(@RequestParam("roleIds") String roleIds, Users users, Member member){

        /**
         * userUsers {id=2, isEnabled=null, isLocked=null, createDate='null',
         * modifyDate='null', lastLoginDate='null', lastLoginIp='null',
         * lockDate='null', name='test01', password='1', role='2'
         * }
         * Member{id=2, memberRankId=0, address='china', birth='null',
         * email='abc@test.com', gender=0, phone='11112',
         * truename='张翰', school='ludong', image='null'
         * } roleIds: 2
         */

        logger.debug("User,Member,user_role update-->  user"+users.toString()+" member:->"+member.toString()+" roleIds: "+roleIds);
        //调用service对user进行处理
        try {

            users.setModifyDate(DateUtils.DatetoString(new Date()));

            /**
             * 对密码加密
             */

            try {

                logger.debug(" 开始 对user表进行更新");
                UsersServiceAppoint.setRoleName(users);
                SystemResult userresult = usersService.UpdateUserInfo(users);
                if(userresult.getStatus()==200){
                    logger.debug(" 开始 对member表进行更新");
                    SystemResult memberresult = memberService.UpdateMember(member);
                    if(memberresult.getStatus()==200){
                        logger.debug("开始 对user_role表进行更新");

                        String[] arrays = roleIds.split(",");

//                        logger.debug("arrays.length:"+arrays.length);
                        int count = 0;
                        for (String roleid : arrays) {

                            UserRoleKey userRoleKey = new UserRoleKey();
                            userRoleKey.setUserId(users.getId());
                            userRoleKey.setRoleId(Integer.valueOf(roleid));
                            SystemResult updateUserRoleresult = usersService.UpdateUserRole(userRoleKey);
                            if( updateUserRoleresult.getStatus() == 200 )
                                count++;
                        }
                        if(count == arrays.length){
                            logger.debug("user_role 关系表更新成功");
                            return "200";
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return "100";

        }catch(Exception e){
            e.printStackTrace();
        }
        return "100";
    }

    /**
     *  跳转到个人信息页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/toProfilesPage/{uid}")
    public String toProfilesInfoPage(Model model,@PathVariable("uid") Integer id){
         try {
             Member memberById = memberService.QueryOneMemberInfoByID(id);
             model.addAttribute("ProfilesInfo",memberById);
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             return "production/CommonUser/profilesinfo";
         }
    }

    @RequestMapping("/toUpdateMemberInfo")
    public String UpdateMemberInfo2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        int id = Integer.parseInt(httpServletRequest.getParameter("uid"));
        try {

            System.out.println("进行了member Update");
            Member member = UsersServiceAppoint.MemberHttpWriteToMap(httpServletRequest, httpServletResponse);
            SystemResult UpdateMemberResult = memberService.UpdateMember(member);
            if(UpdateMemberResult.getStatus() == 200){

                //修改Member成功
                System.out.println(UpdateMemberResult.getMsg());
            }
            else{
                //修改Member失败
                System.out.println(UpdateMemberResult.getMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return "redirect:/users/toProfilesPage/"+ id ;
        }
    }

    /**
     * 删除用户
     * 先删除子表Member
     * 在删除Users
     * @param id
     * @return
     */
    @RequestMapping("/toDeleteUser")
    @ResponseBody
    public String toDeleteUser(@RequestParam("id") Integer id){

        try {

            // 先删除子表member
            // 在删除 用户角色表
            // 再删除用户表

            SystemResult DeleteMember = memberService.DeleteOneMemberById(id);

            if (DeleteMember.getStatus()==200){
                List<Role> roleList = authService.getRoleByUser(id);
                int count = 0;
                for (Role role : roleList) {
                    SystemResult re = usersService.DeleteUserRolle(id, role.getId().toString());
                    if(re.getStatus()==200)
                        count++;
                }
                // 删除用户角色关系表
                if(count == roleList.size()){
                    SystemResult DeleteUser = usersService.DeleteUserById(id);
                    if( DeleteUser.getStatus() == 200){
                        //删除成功
                        System.out.println(DeleteUser.getMsg());
                        return DeleteUser.getStatus().toString();
                    }
                    else {
                        //删除失败
                        System.out.println(DeleteUser.getMsg());
                        return DeleteUser.getStatus().toString();
                    }
                }

            }else{
                System.out.println(DeleteMember.getMsg());
                return DeleteMember.getStatus().toString();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return "100";
    }


    /**
     *  设置用户 是否启用状态
     * @param id
     * @param isEnabled
     * @return
     */
    @RequestMapping("/setEnabled")
    @ResponseBody
    public String setEnabled(@RequestParam("id") Integer id, @RequestParam("isEnabled") Boolean isEnabled){

        logger.debug("设置用户是否启用！id:" + id + ",isEnabled:" + isEnabled);

        String msg = "";
        try {
            if (null == id || null == isEnabled) {
                logger.debug("设置用户是否启用，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }

            // 设置用户是否启用
            Users user = new Users();
            user.setId(id);
            // 是否启用状态
            user.setEnabled(isEnabled);
            user.setModifyDate(DateUtils.DatetoString(new Date()));
            logger.debug(user.toString());
            SystemResult systemResult = usersService.UpdateUserInfo(user);
            if( systemResult.getStatus()== 200){
                logger.debug("更新用户启用状态成功");
                return  "200";
            }else{
                logger.debug("更新用户启用状态失败");
                return "100";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置用户是否启用异常！", e);
            msg = "操作异常，请您稍后再试！";
        }
        return msg;
    }

    /**
     *  设置用户是否锁定状态
     * @param id
     * @param isLocked
     * @return
     */
    @RequestMapping("/setLocked")
    @ResponseBody
    public String setLocked(@RequestParam("id") Integer id, @RequestParam("isLocked") Boolean isLocked){

        logger.debug("设置用户是否锁定！id:" + id + ",isLocked:" + isLocked);

        String msg = "";
        try {
            if (null == id || null == isLocked) {
                logger.debug("设置用户是否锁定，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }

            // 设置用户是否锁定
            Users user = new Users();
            user.setId(id);
            // 是否锁定状态
            user.setLocked(isLocked);
            user.setLockDate(DateUtils.DatetoString(new Date()));
            user.setModifyDate(DateUtils.DatetoString(new Date()));
            logger.debug(user.toString());
            SystemResult systemResult = usersService.UpdateUserInfo(user);
            if( systemResult.getStatus()== 200){
                logger.debug("更新用户锁定状态成功");
                return  "200";
            }else{
                logger.debug("更新用户锁定状态失败");
                return "100";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置用户是否锁定异常！", e);
            msg = "操作异常，请您稍后再试！";
        }
        return msg;
    }


    @Value("${web.upload-path}")
    private String path;

    /**
     * 图片文件上传
     * @param id
     * @param file
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/doUpload/{uid}")
    public String UploadImage(@PathVariable("uid") Integer id,@RequestParam("imgfile")MultipartFile file,HttpServletRequest httpServletRequest){

        /**
         * 如果一开始我捞了数据库的头像，发现这个已经存在文件夹里了，那我需要换头像的时候，
         * 就需要找文件夹里有没有和这一模一样的文件，很明显是有的，因为不管换不换头像之前，
         * 我这个头像一定是保存在数据库字段里面，并且也在文件夹里面，此时我要更换头像了，
         * 那我肯定要把文件的这个头像文件删掉并把新上传的头像文件加进来，不存历史记录，
         * 防止越来越多的头像占内存，并且，我要把这条记录插到数据库里面，，相当于更新了头像。
         */

        // 获取当前用户
        Subject currentUser = SecurityUtils.getSubject();

        Users curru = (Users) currentUser.getPrincipal();

        System.out.println("currenttUser.ID--> " + curru.getId());
        /**
         * 根据User.id  拿到member 全部信息
         */
        Member member = memberService.QueryOneMemberInfoByID(curru.getId());

        /**
         *  获取数据库中当前用户的照片
         *  若存在则删除,替换
         */
        File sqlfile = new File(member.getImage());
        Boolean isExits = sqlfile.exists();
        if(isExits){
            sqlfile.delete();
        }

        //得到将要上传的文件名
        String fileName = file.getOriginalFilename();

        //设置文件上传，并且设置了新的唯一名字XXXXX.jpg
        String newFileName = FileUtils.upload(file, path, fileName);

        /**
         *  连接数据库进行进行更新
         */
        member.setImage(newFileName);
        SystemResult systemResult = memberService.UpdateMember(member);
        if(systemResult.getStatus()!=200){
            // 失败

            System.out.println(systemResult.getMsg());
        }else {
            // 成功
            /**
             *  更新到IndexDto 中
             */
            Member afterUpdateImageMember = memberService.QueryOneMemberInfoByID(curru.getId());
            IndexDto indexDto = new IndexDto();
            UsersServiceAppoint.WriteUsersInfoToDto(indexDto,curru,afterUpdateImageMember);

            Session session =  currentUser.getSession();
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("IndexDto",indexDto);
            session.setAttribute("IndexDto",indexDto);

            System.out.println("进行了图片上传功能,并更新到数据库");
            System.out.println(systemResult.getMsg());
        }
        /**
         *  重定向到--->个人信息页面
         */
        return "redirect:/users/toProfilesPage/"+ id;
    }
}

