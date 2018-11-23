package com.cn.api.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;

/**
 * @author qba
 * 2018-11-23
 * 方法工具类
 */
public class MethodUtils {

    private static final Log logger = LogFactory.getLog(MethodUtils.class);

    /**
     * 获取某个对象的get方法
     * @param bean
     * @param fieldname
     * @return
     */
    public static Method getGetmethod(Object bean, String fieldname){
        Class<?> clazz = bean.getClass();
        String firstLetter = FieldUtils.getFieldFirstLetter(fieldname);
        String getMethodName = getGetmethodName(firstLetter, fieldname);
        Method getMethod = null;
        try {
            getMethod = clazz.getMethod(getMethodName, new Class[]{});
        } catch (NoSuchMethodException e) {
            logger.error("该对象未定义字段"+ fieldname + ":的get方法");
            e.printStackTrace();
        }
        return getMethod;
    }

    /**
     * 获取某个对象的set方法
     * @param bean
     * @param fieldname
     * @return
     */
    public static Method getSetmethod(Object bean, String fieldname){
        Class<?> clazz = bean.getClass();
        String firstLetter = FieldUtils.getFieldFirstLetter(fieldname);
        String setMethodName = getSetmethodName(firstLetter, fieldname);
        Method setMethod = null;
        try {
            setMethod = clazz.getMethod(setMethodName, new Class[]{});
        } catch (NoSuchMethodException e) {
            logger.error("该对象未定义字段"+ fieldname + ":的set方法");
            e.printStackTrace();
        }
        return setMethod;
    }

    /**
     * 获取对象get方法名
     * @param firstLetter
     * @param fieldname
     * @return
     */
   public static String getGetmethodName(String firstLetter, String fieldname){
       String getMethodName = "get" + firstLetter + fieldname.substring(1);
       return getMethodName;
   }

    /**
     * 获取对象set方法名
     * @param firstLetter
     * @param fieldname
     * @return
     */

    public static String getSetmethodName(String firstLetter, String fieldname){
        String setMethodName = "set" + firstLetter + fieldname.substring(1);
        return setMethodName;
    }
}
