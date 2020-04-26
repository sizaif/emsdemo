package com.sizaif.emsdemo.controller;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.pojo.Announce.Announce;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.Announce.AnnounceService;
import com.sizaif.emsdemo.utils.DateUtils;
import com.sizaif.emsdemo.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/25 21:29
 * @description：公告栏 控制层
 * @modified By：sizaif
 * @version: v1.0$
 */

@Controller
@RequestMapping("/admin/announce")
public class AnnounceController {

    private static final Logger logger = LoggerFactory.getLogger(AnnounceController.class);

    @Autowired
    private AnnounceService announceService;


    /**
     * 去公告界面
     * @param model
     * @return
     */
    @RequestMapping("/toManagePage")
    public String toManagePage(Model model){

        List<Announce> allannounce = announceService.getAllAnnounce("all");
        model.addAttribute("AnnounceList",allannounce);
        return "production/Admin/announceList";
    }
    /**
     *  获得所有的公告
     * @return
     */
    @RequestMapping("/getAllAnnounce")
    @ResponseBody
    public String getAllAnnounce(@RequestParam(defaultValue = "true") String isEnabled){

        logger.debug(" 开始获取所有公告");

        try {
            List<Announce> allAnnounce = announceService.getAllAnnounce(isEnabled);
            return JsonUtils.objectToJson(allAnnounce);
        }catch(Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 设置公告是否启用关闭
     * @param id
     * @param isEnabled
     * @return
     */
    @RequestMapping("/setEnabled")
    @ResponseBody
   public String switchEnabled(@RequestParam("id") Integer id,
                               @RequestParam("isEnabled") Integer isEnabled){

        logger.debug("设置公告是否启用！id:" + id + ",isEnabled:" + isEnabled);
        String msg = "";
        try {
            if (null == id || null == isEnabled) {
                logger.debug("请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }

            // 设置用户是否启用
            Announce announce = new Announce();
            announce.setId(id);
            // 是否启用状态
            announce.setIsEnabled(isEnabled);
            announce.setModifyDate(DateUtils.DatetoString(new Date()));
            logger.debug(announce.toString());
            SystemResult systemResult = announceService.updateSelective(announce);
            if (systemResult.getStatus() == 200) {
                logger.debug("更新公告是否启用状态成功");
                return "200";
            } else {
                logger.debug("更新公告是否启用状态失败");
                return "100";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置公告是否启用异常！", e);
            msg = "操作异常，请您稍后再试！";
        }
        return msg;
    }


    @RequestMapping("/addAnnounce")
    @ResponseBody
    public String addAnnounce(Announce announce){



        try {
            announce.setIsEnabled(0);
            announce.setCreateDate(DateUtils.DatetoString(new Date()));
            announce.setModifyDate(DateUtils.DatetoString(new Date()));
            logger.debug("开始添加 announce " +announce.toString());
            SystemResult systemResult = announceService.insertSelective(announce);
            if(systemResult.getStatus()==200){
                return "200";
            }else
                return "100";

        }catch(Exception e){
            e.printStackTrace();
        }
        return "100";
    }

    /**
     * 更新
     * @param announce
     * @return
     */
   @RequestMapping("/updateAnnounce")
   @ResponseBody
   public String updateAnnounce(Announce announce){

        try {
            //  这是 修改时间
            announce.setModifyDate(DateUtils.DatetoString(new Date()));
            logger.debug("开始 更新 announce" + announce.toString());
            SystemResult systemResult = announceService.updateSelective(announce);
            if(systemResult.getStatus()==200){
                return "200";
            }else
                return "100";
        }catch(Exception e){
            e.printStackTrace();
        }
        return "100";
   }


    /**
     * 删除
     * @param id
     * @return
     */
   @RequestMapping("/delAnnounce")
   @ResponseBody
   public String delAnnounce(@RequestParam("id") Integer id) {

        try {
         logger.debug("开始 删除 某个公告");
            Announce announce = new Announce();
            announce.setId(id);
            SystemResult systemResult = announceService.deleteByPrimaryKey(announce);
            if(systemResult.getStatus()==200){
                return "200";
            }else
                return "100";
        }catch(Exception e){
            e.printStackTrace();
        }
        return "100";
   }

}
