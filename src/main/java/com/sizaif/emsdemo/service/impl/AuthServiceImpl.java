package com.sizaif.emsdemo.service.impl;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.PermissionVO;
import com.sizaif.emsdemo.dto.RoleVO;
import com.sizaif.emsdemo.mapper.PermissionsMapper;
import com.sizaif.emsdemo.mapper.RoleMapper;
import com.sizaif.emsdemo.mapper.RolePermissionMapper;
import com.sizaif.emsdemo.pojo.Permission;
import com.sizaif.emsdemo.pojo.Role;
import com.sizaif.emsdemo.pojo.RolePermissionKey;
import com.sizaif.emsdemo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionsMapper permissionsMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public SystemResult addPermission(Permission permission) {
        int su = permissionsMapper.insertSelective(permission);
        if(su > 0)
        {
            return new SystemResult(200,"添加权限成功!");
        }else
            return new SystemResult(100,"添加权限失败!");
    }

    @Override
    public List<Permission>  permList() {
        return permissionsMapper.getAllPermissions();
    }

    @Override
    public SystemResult updatePerm(Permission permission) {

        int su = permissionsMapper.updateByPrimaryKeySelective(permission);
        if(su > 0)
        {
            return new SystemResult(200,"更新权限成功!");
        }else
            return new SystemResult(100,"更新权限失败!");
    }

    @Override
    public Permission getPermission(int id) {
        return permissionsMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemResult delPermission(int id) {
        int su = permissionsMapper.deleteByPrimaryKey(id);
        if(su > 0)
        {
            return new SystemResult(200,"删除权限成功!");
        }else
            return new SystemResult(100,"删除权限失败!");
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<Role> roleList() {
        return roleMapper.getRoles();
    }

    /**
     * 关联查询权限树列表
     *
     * @return
     */
    @Override
    public List<PermissionVO> findPerms() {
        return permissionsMapper.findPerms();
    }

    /**
     * 添加角色
     * 并添加角色的权限
     * @param role
     * @param permIds
     * @return
     */
    @Override
    public SystemResult addRole(Role role, String permIds) {

        int su = roleMapper.insertSelective(role);

        if(su > 0)
        {
            int roleId = role.getId();
            String[] permArrays = permIds.split(",");
            logger.debug("权限id =arrays="+ permArrays);
            SystemResult systemResult = setRolePerms(roleId,permArrays);
            if(systemResult.getStatus() == 200){
                return new SystemResult(200,"删除权限成功!");
            }else{
                return  systemResult;
            }
        }else
            return new SystemResult(100,"删除权限失败!");
    }

    @Override
    public RoleVO findRoleAndPerms(Integer id) {
        return roleMapper.findRoleAndPerms(id);
    }

    /**
     * 更新角色并授权
     *  Transactional 开启事务管理
     * @param role
     * @param permIds
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public SystemResult updateRole(Role role, String permIds) {

        int roleId=role.getId();
        String[] arrays=permIds.split(",");
        logger.debug("权限id =arrays = "+arrays);
        //1，更新角色表数据；
        int num = roleMapper.updateByPrimaryKeySelective(role);
        if(num<1){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new SystemResult(100,"更新角色操作失败");
        }else {
            logger.debug(" 开始删除原 角色权限--->");
            //2，删除原角色权限；
            SystemResult systemResult2 = batchDelRolePerms(roleId);
            if( systemResult2.getStatus() == 200){
                //3，添加新的角色权限数据；
                SystemResult systemResult = setRolePerms(roleId, arrays);
                if(systemResult.getStatus() == 200){
                    return new SystemResult(200,"更新角色与权限成功!");
                }else
                    return new SystemResult(100,"更新角色与权限失败!");
            }else
                return systemResult2;
        }
    }

    /**
     * 删除角色以及它对应的权限
     *
     * @param id
     * @return
     */
    @Override
    public SystemResult delRole(int id) {

        SystemResult batchdelResult = new SystemResult();
        try {
            //1.删除角色对应的权限
            batchdelResult = batchDelRolePerms(id);
            if(batchdelResult.getStatus() == 200){
                //2.删除角色
                int num = roleMapper.deleteByPrimaryKey(id);
                if(num < 1){
                    //事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new SystemResult(100,"删除角色操作失败");
                }else {
                    return  new SystemResult(200,"删除角色与权限成功");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new SystemResult(100,"删除角色操作失败");
    }

    /**
     * 查找所有角色
     *
     * @return
     */
    @Override
    public List<Role> getRoles() {
        return roleMapper.getRoles();
    }

    /**
     * 根据用户获取角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> getRoleByUser(Integer userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    /**
     * 根据角色id获取权限数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Permission> findPermsByRoleId(Integer id) {
        return permissionsMapper.findPermsByRole(id);
    }

    /**
     * 根据用户id获取权限数据
     *
     * @param id
     * @return
     */
    @Override
    public List<PermissionVO> getUserPerms(Integer id) {
        return null;
    }



    /**
     * 批量删除角色权限中间表数据
     * @param roleId
     */
    private SystemResult batchDelRolePerms(int roleId) {
        List<RolePermissionKey> rpks = rolePermissionMapper.findByRole(roleId);
        int c = 0;
        if(null != rpks && rpks.size()>0){
            for (RolePermissionKey rpk : rpks) {
                int su = rolePermissionMapper.deleteByPrimaryKey(rpk);
                if( su > 0 )
                    c ++;
            }
            if( c == rpks.size()){
                return new SystemResult(200,"删除所有角色权限中间表成功");
            }else {
                return new SystemResult(100,"删除所有角色权限中间表失败");
            }
        }else
            return new SystemResult(200,"中间表为空");
    }

    /**
     * 批量给当前角色设置权限
     * @param roleId
     * @param arrays
     */
    private SystemResult setRolePerms(int roleId, String[] arrays) {
        int c = 0;
        for (String permid : arrays) {
            RolePermissionKey rpk = new RolePermissionKey();
            rpk.setRoleId(roleId);
            rpk.setPermitId(Integer.valueOf(permid));
            int su = rolePermissionMapper.insert(rpk);
            if( su > 0 )
                c++;

        }
        if( c == arrays.length ){
            return  new SystemResult(200,"当前角色权限添加成功");
        }else{
            return  new SystemResult(100,"当前角色权限添加异常");
        }
    }


}
