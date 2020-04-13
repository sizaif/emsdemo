package com.sizaif.emsdemo.service;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.pojo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
