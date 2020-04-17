package com.sizaif.emsdemo.controller;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.appoint.UsersServiceAppoint;
import com.sizaif.emsdemo.dto.IndexDto;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.User.MemberService;
import com.sizaif.emsdemo.service.User.UsersService;

import com.sizaif.emsdemo.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LogController {

    //注入Service
    @Autowired
    private UsersService usersService;
    @Autowired
    private MemberService memberService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping({"/","index","index.html","/usr/login/lang","/toLogin"})
    public String Index(Model model){
        model.addAttribute("msg","welcome!");
        return "production/commons/login";
    }

    @RequestMapping("/login")
    public String Login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, HttpSession httpSession) throws Exception {

        String userName = httpServletRequest.getParameter("userName");
        String passWord = httpServletRequest.getParameter("passWord");
        String rememberme = httpServletRequest.getParameter("rememberme");

        // 获取当前用户
        Subject currentUser = SecurityUtils.getSubject();

        // 封装用户登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        //记住我
        boolean rem = rememberme == null ? false: true;
        token.setRememberMe(rem);

        try {
            // 执行登录
            currentUser.login(token);



            // 获取用户ID
//            HashMap<String, Object> uhashmap = new HashMap<>();
//            uhashmap.put("userName",userName);
//            users = usersService.queryUserByName(uhashmap);

            Users users = (Users) currentUser.getPrincipal();


            // 修改上次登陆时间
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("lastLoginDate",new DateUtils().DatetoString(new Date()));
            hashMap.put("id",users.getId());
            usersService.UpdateUserInfoByHashMap(hashMap);
            Member members = memberService.QueryOneMemberInfoByID(users.getId());

            /**
             * 将信息放入到IndexDto中
             * 放入Session/Cookie
             */
            IndexDto indexDto = new IndexDto();
            UsersServiceAppoint.WriteUsersInfoToDto(indexDto,users,members);

            Session session =  currentUser.getSession();
            httpSession.setAttribute("IndexDto",indexDto);
            session.setAttribute("IndexDto",indexDto);


            return "redirect:/home";
        }catch(UnknownAccountException e){
            // 用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "production/commons/login";
        }catch (IncorrectCredentialsException e) {
            // 密码不存在
            model.addAttribute("msg", "密码错误");
            return "production/commons/login";
        }catch (LockedAccountException e){
            // 用户被锁定
            model.addAttribute("msg", "用户被锁定");
            return "production/commons/login";
        }catch (DisabledAccountException e){
            // 用户未激活
            model.addAttribute("msg", "用户未激活");
            return "production/commons/login";
        }
    }


    @RequestMapping("/logout")
    public String Logout(){
        Subject currentUser = SecurityUtils.getSubject();
        // 登出
        currentUser.logout();
        return "redirect:/";
    }


    @RequestMapping("/registered")
    @ResponseBody
    public String registered(HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest){


        String name = httpServletRequest.getParameter("uname");
        String password = httpServletRequest.getParameter("encodePassword");
        String email = httpServletRequest.getParameter("email");

        logger.debug("---> 注册: "+ name + " " + password + " " + email );

        try {
            HashMap<String,Object> userhashMap = UsersServiceAppoint.UsersHttpWriteToMap(httpServletRequest,httpServletResponse);

            UsersServiceAppoint.UsersOtherInfo(userhashMap,httpServletRequest);

            logger.debug("UserMap---> " + userhashMap);

            HashMap<String,Object> memberMap = UsersServiceAppoint.MemberHttpWriteToMap(httpServletRequest,httpServletResponse);

            Users users = usersService.queryUserByName(userhashMap);
            // 账户已存在
            if( null != users)
                return "100";
            else{
                SystemResult systemResult = usersService.AddOneUser(userhashMap);
                if(systemResult.getStatus()==200){
                    int key = Integer.parseInt(userhashMap.get("id").toString());
                    memberMap.replace("id",key);
                    memberMap.put("memberRankId",1);
                    logger.debug("memberMap --> " + memberMap);
                    SystemResult systemResult1 = memberService.AddOneMemberByHashMap(memberMap);
                    if (systemResult1.getStatus()==200){
                        return "200";
                    }else{
                        // 事务回滚 删除刚才添加的user
                        // 插入的member 存在重复的邮箱或手机号
                        usersService.DeleteUserById(key);
                        return "101";
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return  "100";
    }
}
