package com.sizaif.emsdemo.mapper;


import com.sizaif.emsdemo.dto.PermissionVO;
import com.sizaif.emsdemo.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);



    /**
     *  查询所有权限
     * @return
     */
    List<Permission> getAllPermissions();

    /**
     * 查询所有子节点
     * @param pid
     * @return
     */
    List<Permission> findChildPerm(int pid);

    /**
     * 查询权限树列表
     * @return
     */
    List<PermissionVO> findPerms();

    /**
     * 根据角色id获取权限数据
     * @param roleId
     * @return
     */
    List<Permission> findPermsByRole(Integer roleId);

    List<PermissionVO> getUserPerms(Integer userId);

}
