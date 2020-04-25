package com.sizaif.emsdemo.TestController;

import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.dto.ContestVO2;
import com.sizaif.emsdemo.mapper.Contest.ContestMapper;
import com.sizaif.emsdemo.mapper.User.MemberMapper;
import com.sizaif.emsdemo.mapper.User.RoleMapper;
import com.sizaif.emsdemo.mapper.User.UserMapper;
import com.sizaif.emsdemo.pojo.User.Role;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.Auth.AuthService;
import com.sizaif.emsdemo.service.Contest.ContestService;
import com.sizaif.emsdemo.service.User.MemberService;
import com.sizaif.emsdemo.service.User.UsersService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
    @Autowired
    private RoleMapper roleMapper;
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

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return  (String)field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据属性名设置属性值
     *
     * @param fieldName
     * @param object
     * @param value  List
     * @return
     */
    private static void setFieldValueByFieldName(String fieldName, Object object, List<String> value) {
        try {
            // 获取obj类的字节文件对象
            Class aClass = object.getClass();
            // 获取该类的成员变量
            Field field = aClass.getDeclaredField(fieldName);
            // 取消语言访问检查
            field.setAccessible(true);
            // 给变量赋值
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test() throws Exception{
        ContestVO contestByMemberId = contestMapper.findMembersByContestId(2);
        System.out.println(contestByMemberId.getMemberList());

    }

    @Test
    public void Test2() throws Exception{

        PageInfo pageInfo = contestService.findAllUserByPageS(1,5,"level","nation",false);
        for (Object contestVO : pageInfo.getList()) {
//            System.out.println(contestVO);
            try {
                String tags = getFieldValueByFieldName("Tag",contestVO);
                List<String> tagsList = new ArrayList<>();
                String[] tagArray = tags.split(",");
                for (String str : tagArray) {
                    tagsList.add(str);
                }
                setFieldValueByFieldName("tags",contestVO,tagsList);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        for (Object o : pageInfo.getList()) {
            System.out.println(o);
        }
//        System.out.println(pageInfo.getList());
    }

}
