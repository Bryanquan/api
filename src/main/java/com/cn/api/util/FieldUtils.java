package com.cn.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FieldUtils {
    private static Logger logger = LoggerFactory.getLogger(FieldUtils.class);

    /**
     * 调用field的set方法给bean的field赋值
     * @param bean
     * @param fieldname
     * @param value
     */
    public static void setValueForField(Object bean,  String fieldname, Object value)  {
        try {
            Class clazz = bean.getClass();
            Field field = clazz.getDeclaredField(fieldname);
            field.setAccessible(true);//打开私有成员访问权限
            field.set(bean, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error("message={}", e.getMessage());
        }catch (NoSuchFieldException e){
            e.printStackTrace();
            logger.error("message={}", e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("message={}", e.getMessage());
        }
    }

    /**
     * 调用method的invoke方法给bean的field赋值
     * @param methodname
     * @param bean
     * @param value
     */
    public static void setValueForField(String methodname, Object bean,  Object value){
        Class clazz = bean.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodname);
            method.setAccessible(true);//打开私有方法访问权限
            method.invoke(bean, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error("message={}", e.getMessage());
        }catch (InvocationTargetException e){
            logger.error("message={}", e.getMessage());
        }catch (IllegalAccessException e){
            logger.error("message={}", e.getMessage());
        }

    }
}
