package com.sizaif.emsdemo.controller;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.PermissionVO;
import com.sizaif.emsdemo.dto.RoleVO;
import com.sizaif.emsdemo.pojo.User.Permission;
import com.sizaif.emsdemo.pojo.User.Role;
import com.sizaif.emsdemo.pojo.User.RolePermissionKey;
import com.sizaif.emsdemo.service.Auth.AuthService;
import com.sizaif.emsdemo.utils.DateUtils;
import com.sizaif.emsdemo.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/7 21:30
 * @description：权限角色控制
 * @modified By：sizaif
 * @version: v1.0$
 */

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * 权限列表
     * @return
     */
    @RequestMapping("/toPermList")
    @ResponseBody
    public ModelAndView permList(){
        logger.debug("获得权限列表！");

        //返回文件路径
        ModelAndView mav = new ModelAndView("production/Admin/permList");
        try {
            List<Permission> permissionList = authService.permList();
            logger.debug("----> sql 获得权限列表 ->> toPermList :" + permissionList);

            mav.addObject("permList",permissionList);
            mav.addObject("msg","获得权限列成功");

        }catch(Exception e){
            e.printStackTrace();
            logger.debug("----> sql 获得权限列表 ->> toPermList 失败异常");
        }
        return  mav;
    }

    /**
     * 添加权限
     * @param permission
     * @return $msg
     */
    @RequestMapping("/addPermission")
    @ResponseBody
    public ModelAndView addPermission(Permission permission) {
        logger.debug("新增权限--permission-" + permission);
        // 重定向 到权限列表
        ModelAndView mav = new ModelAndView("redirect: /auth/toPermList");
        SystemResult systemResult = new SystemResult(100,"failed");
        try {

            if (null != permission) {
                permission.setInsertTime(DateUtils.DatetoString(new Date()));
                systemResult = authService.addPermission(permission);
                logger.debug("新增权限成功！-permission-" + permission);
                mav.addObject("msg", systemResult.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("msg", systemResult.getMsg());
            logger.error("新增权限异常！", e);
        }
        return mav;
    }

    /**
     * 设置权限
     * @param type 0-> 根,新增  1-> 子节点
     * @param permission
     * @return msg
     */
    @RequestMapping("/setPermission")
    @ResponseBody
    public String setPerm(@RequestParam("type") int type,Permission permission){

        logger.debug(" 设置权限, 区分权限类型 type--> "+ type +" -> 0: 直接添加权限.   1: 添加子节点权限");

        try {
            if( null != permission){

                SystemResult systemResult = new SystemResult();
                // 直接编辑
                if ( 0 == type) {
                    //设置修改时间
                    permission.setUpdateTime(DateUtils.DatetoString(new Date()));
                     systemResult = authService.updatePerm(permission);
                }else if( 1 == type){
                    permission.setUpdateTime(DateUtils.DatetoString(new Date()));
                    //增加子节点权限
                    systemResult = authService.addPermission(permission);
                }
                logger.debug("设置权限成功！-permission-" + permission);
                return systemResult.getStatus().toString();
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.debug("设置异常" + e);
        }

        return "设置权限出错，请您稍后再试";
    }


    /**
     *  删除某个权限
     * @param id
     * @return
     */
    @RequestMapping("/delPermission")
    @ResponseBody
    public String del(@RequestParam("id") int id) {
        logger.debug("删除权限--id-" + id);
        SystemResult systemResult = new SystemResult();
        try {
            if (id > 0) {
                systemResult = authService.delPermission(id);
                return  systemResult.getStatus().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除权限异常！", e);
        }
        return "删除权限出错，请您联系管理员";
    }


    /**
     * 获取权限
     * @param id
     * @return
     */
    @RequestMapping("/getPermission")
    @ResponseBody
    public Permission getPerm(@RequestParam("id") int id) {
        logger.debug("获取权限--id-" + id);
        try {
            if (id > 0) {
                Permission perm = this.authService.getPermission(id);
                logger.debug("获取权限成功！-permission-" + perm);
                return perm;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取权限异常！", e);
        }
        return null;
    }



    /**
     * 权限列表
     * @return
     */
    @RequestMapping("/toRoleList")
    @ResponseBody
    public ModelAndView roleList(){
        logger.debug("获得角色列表！");

        //返回文件路径
        ModelAndView mav = new ModelAndView("production/Admin/roleList");
        try {
            List<Role> rolesList = authService.roleList();
            List<Permission> permissionList = authService.permList();
            logger.debug("----> sql 获得角色列表 ->> toRoleList :" + rolesList);

            mav.addObject("roleList",rolesList);
            mav.addObject("msg","获得角色列表成功");
            mav.addObject("permList",permissionList);

        }catch(Exception e){
            e.printStackTrace();
            logger.debug("----> sql 获得角色列表 ->> toRoleList 失败异常");
        }
        return  mav;
    }

    /**
     * 添加角色并授权
     * @return msg
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public String addRole(@RequestParam("rolePermIds") String permIds, Role role) {
        // permIds  角色包含的的权限id 已"," 分隔
        logger.debug("添加角色并授权！角色数据role："+role+"，权限数据permIds："+permIds);
        SystemResult systemResult = new SystemResult();
        try {
            if(StringUtils.isEmpty(permIds)){
                return "未授权，请您给该角色授权";
            }
            if(null == role){
                return "请您填写完整的角色数据";
            }
            // 设置添加时间
            role.setInsertTime(DateUtils.DatetoString(new Date()));
            role.setUpdateTime(DateUtils.DatetoString(new Date()));
            systemResult = authService.addRole(role,permIds);
            return systemResult.getStatus().toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加角色并授权！异常！", e);
        }
        return "操作错误，请您稍后再试";
    }

    /**
     * 更新角色并授权
     * @return
     */
    @RequestMapping("/setRole")
    @ResponseBody
    public String setRole(@RequestParam("rolePermIds") String permIds, Role role) {
        // rolePermIds 角色权限ids
        logger.debug("更新角色并授权！角色数据role："+role+"，权限数据permIds："+permIds);
        SystemResult systemResult = new SystemResult();
        try {
            if(StringUtils.isEmpty(permIds)){
                return "未授权，请您给该角色授权";
            }
            if(null == role){
                return "请您填写完整的角色数据";
            }
            role.setUpdateTime(DateUtils.DatetoString(new Date()));
            systemResult = authService.updateRole(role,permIds);
            return systemResult.getStatus().toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新角色并授权！异常！", e);
        }
        return "操作错误，请您稍后再试";
    }

    /**
     *  获取权限树
     * @return
     */
    @RequestMapping("/getPermTree")
    @ResponseBody
    public String getPermTree(){
        logger.debug("开始执行 获得权限树--- >" );
        List<PermissionVO> pvos = authService.findPerms();
        return JsonUtils.objectToJson(pvos);

//        List<HashMap<String, Object>> result = new ArrayList<>();    //定义一个map处理json键名问题
//        Object f = fun(pvos,result);
//        return JsonUtils.objectToJson(f);
    }

    /**
     *  获取角色权限
     * @param id RoleAndPerms
     * @return json
     */
    @RequestMapping("/getRole")
    @ResponseBody
    public String getRole(@RequestParam("id") int id){
        logger.debug("获取角色--id-" + id);
        try {
            if (id > 0) {
                RoleVO rvo= authService.findRoleAndPerms(id);
                String json = JsonUtils.objectToJson(rvo);
                return  json;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 遍历整个权限树, 设置当前角色的权限的checked = true
     * @param curr
     * @param rpk_perm_id
     */
    public void visitPermTree(PermissionVO curr,int rpk_perm_id){

        for (PermissionVO childPvo : curr.getChildren()) {
            if( rpk_perm_id == childPvo.getId() ) {
                childPvo.setChecked(true);
                logger.debug(" 开始进入: ");
            }else
                visitPermTree(childPvo,rpk_perm_id);
        }
    }
    /**
     * 获取权限树,Role
     * 并设置 checked
     * @param id
     * @return
     */
    @RequestMapping("/getRolePermTree")
    @ResponseBody
    public  String getRolePerm(@RequestParam("id") int id) {
        logger.debug("获取角色权限--id-" + id);
        try {
            if (id > 0) {

                RoleVO rvo= authService.findRoleAndPerms(id);
                //角色下的权限
                List<RolePermissionKey> rpks = rvo.getRolePerms();
                //获取全部权限数据 权限树
                List<PermissionVO> pvos = authService.findPerms();
                for (RolePermissionKey rpk : rpks) {
                    logger.debug("当前角色的权限 ---> "+ rpk.toString());
                    //设置角色下的权限checked状态为：true
                    for (PermissionVO pvo : pvos) {
//                        System.out.println(rpk.getPermitId()+ " ---- "+pvo.getId());
                        visitPermTree(pvo,rpk.getPermitId());
                    }
                }
                logger.debug("获取权限树！-permissionTree-" +  pvos.toArray());


                return JsonUtils.objectToJson(pvos);

//                List<HashMap<String, Object>> result = new ArrayList<>();    //定义一个map处理json键名问题
//                Object f = fun(pvos,result);
//                return JsonUtils.objectToJson(f);

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取权限异常！", e);
        }
        return null;
    }

    /**
     *  解决 返回的json 数据 与树形菜单名称不一致的问题
     *  title	节点标题	String	未命名
     *  id	节点唯一索引，用于对指定节点进行各类操作	String/Number	任意唯一的字符或数字
     *  children	子节点。支持设定选项同父节点	Array	[{title: '子节点1', id: '111'}]
     *  href	点击节点弹出新窗口对应的 url。需开启 isJump 参数	String	任意 URL
     *  spread	节点是否初始展开，默认 false	Boolean	true
     *  checked	节点是否初始为选中状态（如果开启复选框的话），默认 false	Boolean	true
     *  disabled	节点是否为禁用状态。默认 false	Boolean	false
     * @param permVOs
     * @param result
     * @return
     */
    private Object fun(List<PermissionVO> permVOs, List<HashMap<String, Object>> result){
        for (PermissionVO permVO : permVOs) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", permVO.getId());
            map.put("title", permVO.getTitle());
            map.put("spread", true);      //设置是否展开
            List<HashMap<String, Object>> result1 = new ArrayList<>();
            List<PermissionVO> children = permVO.getChildren();    //下级菜单

//            map.put("checked",true);

            map.put("children", fun(children, result1));
            result.add(map);
        }
        return result;
    }

    /**
     * 根据id查询角色
     * @return PermTreeDTO
     */
    @RequestMapping("/updateRole/{id}")
    //@ResponseBody
    public ModelAndView updateRole(@PathVariable("id") Integer id) {
        logger.debug("根据id查询角色id："+id);
        ModelAndView mv=new ModelAndView("/auth/roleManage");
        try {
            if(null==id){
                mv.addObject("msg","请求参数有误，请您稍后再试");
                return mv;
            }
            mv.addObject("flag","updateRole");
            RoleVO rvo= authService.findRoleAndPerms(id);
            //角色下的权限
            List<RolePermissionKey> rpks = rvo.getRolePerms();
            //获取全部权限数据 权限树
            List<PermissionVO> pvos = authService.findPerms();
            for (RolePermissionKey rpk : rpks) {
                //设置角色下的权限checked状态为：true
                for (PermissionVO pvo : pvos) {
                    if(String.valueOf(rpk.getPermitId()).equals(String.valueOf(pvo.getId()))){
                        pvo.setChecked(true);
                    }
                }
            }
            mv.addObject("perms", pvos.toArray());
            //角色详情
            mv.addObject("roleDetail",rvo);
            logger.debug("根据id查询角色数据："+mv);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加角色并授权！异常！", e);
            mv.addObject("msg","请求异常，请您稍后再试");
        }
        return mv;
    }


    /**
     * 删除角色以及它对应的权限
     * @param id
     * @return
     */
    @RequestMapping(value = "/delRole", method = RequestMethod.POST)
    @ResponseBody
    public String delRole(
            @RequestParam("id") int id) {
        logger.debug("删除角色以及它对应的权限--id-" + id);
        try {
            if (id > 0) {
                SystemResult systemResult = this.authService.delRole(id);
                return systemResult.getStatus().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除角色异常！", e);
        }
        return "删除角色出错，联系技术人员";
    }


}
