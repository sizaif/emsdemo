package com.sizaif.emsdemo.mapper;

import com.sizaif.emsdemo.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MemberMapper {

    /**
     * 查询所有member 详细info
     * @return
     */
    List<Member> QueryAllMemberInfo();

    /**
     *  添加一个memeber 用户信息, (必须先获得Users的主键ID)
     * @param map
     * @return
     */
    int AddOneMember(Map<String,Object> map);

    /**
     * 通过ID 查询一个用户详细信息
     * @param id
     * @return
     */
    Member QueryOneMemberInfoByID(int id);

    /**
     * 更新用户详细信息
     * @param map
     * @return
     */
    int UpdateMember(HashMap<String,Object> map);


    /**
     * 通过ID删除一个用户member
     * @param id
     * @return
     */
    int DeleteOneMemberById(int id);

}
