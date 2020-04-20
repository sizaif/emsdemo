package com.sizaif.emsdemo.appoint;

import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.dto.ContestVO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/20 17:44
 * @description：contest表的补充操作
 * @modified By：sizaif
 * @version: v1.0$
 */

public class ContestServiceAppoint {


    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return  (String)field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据属性名设置属性值
     *
     * @param fieldName
     * @param object
     * @param value  List
     * @return
     */
    private static void setFieldValueByFieldName(String fieldName, Object object, List<String> value) {
        try {
            // 获取obj类的字节文件对象
            Class aClass = object.getClass();
            // 获取该类的成员变量
            Field field = aClass.getDeclaredField(fieldName);
            // 取消语言访问检查
            field.setAccessible(true);
            // 给变量赋值
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 讲数据库中拿到的Tag 标签拆分
     * 因为数据存放在PageInfo类中
     * 需要用的java反射计算 拿取值和设置值
     * @param pageInfo
     */
    public static void TagTotags(PageInfo pageInfo){

        for (Object contestVO : pageInfo.getList()) {
            try {
                String tags = getFieldValueByFieldName("Tag",contestVO);
                List<String> tagsList = new ArrayList<>();
                String[] tagArray = tags.split(",");
                for (String str : tagArray) {
                    tagsList.add(str);
                }
                setFieldValueByFieldName("tags",contestVO,tagsList);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
