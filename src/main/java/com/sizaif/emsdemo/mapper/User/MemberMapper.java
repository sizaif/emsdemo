package com.sizaif.emsdemo.mapper.User;

import com.sizaif.emsdemo.pojo.User.Member;
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
     * @param
     * @return
     */
    int AddOneMember(Member member);

    /**
     * 通过ID 查询一个用户详细信息
     * @param id
     * @return
     */
    Member QueryOneMemberInfoByID(int id);

    /**
     * 更新用户详细信息
     * @param
     * @return
     */
    int UpdateMemberSelective(Member member);


    /**
     * 通过ID删除一个用户member
     * @param id
     * @return
     */
    int DeleteOneMemberById(int id);


    /**
     *  数据库中 邮箱和手机号是唯一索引, 插入时不可重复
     *  插入数据前做检测
     * @param
     * @return
     */
    List<Member> QueryMemberInfoByEmailOrPhone(Member member);

}
