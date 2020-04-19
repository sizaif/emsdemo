package com.sizaif.emsdemo.TestController;

import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.mapper.Contest.ContestMapper;
import com.sizaif.emsdemo.mapper.User.MemberMapper;
import com.sizaif.emsdemo.mapper.User.UserMapper;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.Auth.AuthService;
import com.sizaif.emsdemo.service.Contest.ContestService;
import com.sizaif.emsdemo.service.User.MemberService;
import com.sizaif.emsdemo.service.User.UsersService;
import com.sizaif.emsdemo.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@SpringBootTest
public class TestUserMapper {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberService memberService;

    @Autowired
    private ContestService contestService;
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private AuthService authServicee;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;



    @Test
    public void Test3(){

        Users users= new Users();
        users = userMapper.queryUserByName("test08");
        if(null == users)
            System.out.println("null--->");
//        System.out.println(users.toString());

//        Member member2 = new Member();
//        member2.setEmail("11112223444@qq.com");
//        List<Member> member = memberMapper.QueryMemberInfoByEmailOrPhone(member2);
//        System.out.println(member.size());
    }

    @Test
    public void Test2() throws Exception{

//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("userName","root");
//        Users users = usersService.queryUserByName(hashMap);
//        System.out.println(users.toString());
        List<MemberVO> member = userMapper.queryOneUserMemberById(1);
        for (MemberVO vo : member) {
            System.out.println(vo.toString());
        }
        System.out.println(member.toString());
//        List<MemberVO> members = usersService.queryAllUserMemberRoleList();
//        for (MemberVO user : members) {
//            System.out.println(user.toString());
//        }
    }

}
