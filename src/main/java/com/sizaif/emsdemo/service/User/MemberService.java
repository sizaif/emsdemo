package com.sizaif.emsdemo.service.User;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.pojo.User.Member;

import java.util.HashMap;
import java.util.List;


public interface MemberService {

    /**
     *
     * @return list
     */
    List<Member> QueryAllMemberInfo();

    SystemResult AddOneMemberByHashMap(HashMap<String,Object> map);

    SystemResult DeleteOneMemberById(int id);

    SystemResult UpdateMemberInfoByHashMap(HashMap<String,Object> map);
    /**
     *
     * @param id
     * @return
     */
    Member QueryOneMemberInfoByID(int id);



}
