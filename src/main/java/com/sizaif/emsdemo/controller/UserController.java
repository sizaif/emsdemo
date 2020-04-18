package com.sizaif.emsdemo.controller;


import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.appoint.UsersServiceAppoint;
import com.sizaif.emsdemo.dto.IndexDto;
import com.sizaif.emsdemo.pojo.User.Member;

import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.User.MemberService;
import com.sizaif.emsdemo.service.User.UsersService;
import com.sizaif.emsdemo.utils.FileUtils;
import com.sizaif.emsdemo.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MemberService memberService;
    @Autowired
    private UsersService usersService;

    /**
     * 查询所有用户,
     * 并传到用户列表页面
     * @param model
     * @return
     */
    @RequestMapping("/toUserList")
    public String queryUserList(Model model)
    {
//        List<Member> usersLis = userMapper.queryUserList();
        try {
            List<Users> usersLists = usersService.queryAllUserList();
            List<Member> memberList = memberService.QueryAllMemberInfo();
            List<Member> memberuserList = usersService.queryAllUserMemberList();
            JsonUtils jsonUtils = new JsonUtils();
            String JsonUserList = jsonUtils.objectToJson(usersLists);
            String JsonMemberList = jsonUtils.objectToJson(memberList);
            String JsonMemberUserList = jsonUtils.objectToJson(memberuserList);
            model.addAttribute("JsonUserLists",usersLists);
            model.addAttribute("JsonMemberList",memberList);
            model.addAttribute("JsonMemberUserList",memberuserList);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //返回到user.html
            return "production/Admin/users";
        }
    }

    /**
     * 定向到添加用户界面
     * @param model
     * @return
     */
    @GetMapping("/toAddPage")
    public String toAddPage(Model model) {

        return "production/Admin/adduser";
    }

    @PostMapping("/toAddPage/addUser")
    public String AddUser(@RequestParam("roleIds") String roleIds,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){


        Member memberMap = UsersServiceAppoint.MemberHttpWriteToMap(httpServletRequest,httpServletResponse);
        Users usersMap = UsersServiceAppoint.UsersHttpWriteToMap(httpServletRequest,httpServletResponse);

        UsersServiceAppoint.UsersOtherInfo(usersMap,httpServletRequest);

        logger.debug("设置用户[新增或更新]！user:" + usersMap+ " "+ memberMap + ",roleIds:" + roleIds);

        System.out.println(memberMap.toString());
        System.out.println(usersMap.toString());

        //调用service对user进行处理
        try {

            SystemResult addUserResult = usersService.AddOneUser(usersMap,roleIds);
            int id = usersMap.getId();
            if(addUserResult.getStatus() == 200){

                //添加成功
                System.out.println(addUserResult.getMsg());

                memberMap.setId(id);

                /**
                 *  待处理  ,后续开发更改
                 *  设置 memberRankId
                 */
                memberMap.setMemberRankId(1);
                SystemResult addMemberResult = memberService.AddOneMember(memberMap);
                if (addMemberResult.getStatus() == 200){

                    //添加成功
                    System.out.println(addUserResult.getMsg());

                    /**
                     *  添加用户 和 用户信息成功后 设置 根据Role 角色设定权限
                     *  修改user_permissions表
                     */


                }else {
                    //添加失败
                    System.out.println(addUserResult.getMsg());
                }
            }
            else{
                //添加失败
                System.out.println(addUserResult.getMsg());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/users/toUserList";
    }

    /**
     * 跳转到更新页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/toUpdateMemberUserInfoPage/{uid}")
    public String toUpdateUser(Model model,@PathVariable("uid")Integer id){

        // 查出用户信息
        try {
            Member memberuserById = usersService.queryOneUserMemberById(id);
            model.addAttribute("MemberInfo",memberuserById);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return "production/Admin/updatememberinfo";
        }
    }


    @RequestMapping("/toUpdateMemberUserInfoPage/updateInfo")
    public String UpdateMemberInfo1(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        //调用service对user进行处理
        try {
            Member hmap = UsersServiceAppoint.MemberHttpWriteToMap(httpServletRequest,httpServletResponse);

            Users users = UsersServiceAppoint.UsersHttpWriteToMap(httpServletRequest,httpServletResponse);
            UsersServiceAppoint.UsersOtherInfo(users,httpServletRequest);
            SystemResult UpdateUserResult = usersService.UpdateUserInfo(users);

            if(UpdateUserResult.getStatus() == 200){
                //修改User成功
                System.out.println(UpdateUserResult.getMsg());


                SystemResult UpdateMemberResult = memberService.UpdateMember(hmap);

                if(UpdateMemberResult.getStatus() == 200){

                    //修改Member成功
                    System.out.println(UpdateMemberResult.getMsg());

                    /**
                     *  修改用户 和 用户信息成功后 设置 根据Role 角色设定权限
                     *  修改user_permissions表
                     */

                }
                else{
                    //修改Member失败
                    System.out.println(UpdateMemberResult.getMsg());
                }
            }else {
                //修改User失败
                System.out.println(UpdateUserResult.getMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/users/toUserList";
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
    @GetMapping("/toDeleteUser/{id}")
    public String toDeleteUser(@PathVariable("id") Integer id){

        try {

            // 先删除子表member
            // 再删除用户表
            SystemResult DeleteMember = memberService.DeleteOneMemberById(id);

            if (DeleteMember.getStatus()==200){
                SystemResult DeleteUser = usersService.DeleteUserById(id);
                if( DeleteUser.getStatus() == 200){
                    //删除成功
                    System.out.println(DeleteUser.getMsg());
                }
                else {
                    //删除失败
                    System.out.println(DeleteUser.getMsg());
                }
            }else{
                System.out.println(DeleteMember.getMsg());
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //返回到user.html
            return "redirect:/users/toUserList";
        }

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

//        IndexDto indexDto = new IndexDto();
//        HttpSession session = httpServletRequest.getSession();
//        try {
//            indexDto = (IndexDto) session.getAttribute("IndexDto");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

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

