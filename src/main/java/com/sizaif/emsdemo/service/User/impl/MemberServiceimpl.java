package com.sizaif.emsdemo.service.User.impl;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.mapper.User.MemberMapper;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.service.User.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MemberServiceimpl implements MemberService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public List<Member> QueryAllMemberInfo() {
        List<Member> members = memberMapper.QueryAllMemberInfo();
        return members;
    }

    @Override
    public SystemResult AddOneMember(Member member) {

        // 插入前 做检测 是否已经存在
        logger.debug("开始验证member的邮箱和手机号是否重复");
        List<Member> memberList = memberMapper.QueryMemberInfoByEmailOrPhone(member);
        // 已存在
        if(memberList.size() > 0)
        {
            logger.debug("member 邮箱或手机号重复");
            return new SystemResult(100,"member 已经存在,不可以重复插入");
        }else {
            int su = memberMapper.AddOneMember(member);
            if(su>0)
            {
                return new SystemResult(200,"addOneMember  sucessful");
            }
            else
                return new SystemResult(100,"addOneMember failed");
        }

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
    public SystemResult UpdateMember(Member member) {


        int su = memberMapper.UpdateMemberSelective(member);
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
