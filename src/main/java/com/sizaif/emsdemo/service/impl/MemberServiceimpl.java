package com.sizaif.emsdemo.service.impl;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.mapper.MemberMapper;
import com.sizaif.emsdemo.pojo.Member;
import com.sizaif.emsdemo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceimpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Override
    public List<Member> QueryAllMemberInfo() {
        List<Member> members = memberMapper.QueryAllMemberInfo();
        return members;
    }

    @Override
    public SystemResult AddOneMemberByHashMap(HashMap<String,Object> map) {
//        HashMap<String,Object> memberMap = new HashMap<String, Object>();
//        memberMap.put("id",member.getId());
//        memberMap.put("memberRankId",member.getMemberRankId());
//        memberMap.put("address",member.getAddress());
//        memberMap.put("birth",member.getBirth());
//        memberMap.put("email",member.getEmail());
//        memberMap.put("gender",member.getGender());
//        memberMap.put("phone",member.getPhone());
//        memberMap.put("truename",member.getTruename());
//        memberMap.put("school",member.getSchool());
//        memberMap.put("image",member.getImage());

        int su = memberMapper.AddOneMember(map);
        if(su>0)
        {
            return new SystemResult(200,"addOneMember  sucessful");
        }
        else
            return new SystemResult(100,"addOneMember failed");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public SystemResult DeleteOneMemberById(int id) {

        int su = memberMapper.DeleteOneMemberById(id);
        if(su>0)
        {
            return new SystemResult(200,"delete OneMember  sucessful");
        }
        else
            return new SystemResult(100,"delete OneMember failed");
    }

    @Override
    public SystemResult UpdateMemberInfoByHashMap(HashMap<String,Object>map) {
        HashMap<String,Object> memberMap = new HashMap<String, Object>();

        /**
         *  put("") 与数据库交换字段
         *  get("") 前端获得字段
         */
        memberMap.put("id",map.get("id"));
        memberMap.put("memberRankId",map.get("memberRankId"));
        memberMap.put("address",map.get("address"));
        memberMap.put("birth",map.get("birth"));
        memberMap.put("email",map.get("email"));
        memberMap.put("gender",map.get("gender"));
        memberMap.put("phone",map.get("phone"));
        memberMap.put("truename",map.get("truename"));
        memberMap.put("school",map.get("school"));
        memberMap.put("image",map.get("image"));

        int su = memberMapper.UpdateMember(memberMap);
        if(su > 0){
            return  new SystemResult(200,"Update MemberInfo sucessful");
        }
        else
            return new SystemResult(100,"Update MemberInfo failed");
    }

    @Override
    public Member QueryOneMemberInfoByID(int id) {
        Member member = memberMapper.QueryOneMemberInfoByID(id);
        return member;
    }
}
