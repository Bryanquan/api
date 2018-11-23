package com.cn.api.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtils {

    /**
     * 用对象var1的值属性给对象var2属性赋值
     * 只对var2与var1相同的fieldname赋值
     * @param var1
     * @param var2
     */
    public static void setValue(Object var1, Object var2){
        Class<?> clazz1 = var1.getClass();
        Class<?> clazz2 = var2.getClass();
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        for (Field field1: fields1){
            String fieldname1 = field1.getName();
            for (Field field2: fields2){
                String fieldname2 = field2.getName();
                if (fieldname2.equals(fieldname1)){
                    Method getMethod = MethodUtils.getGetmethod(var1, fieldname1);
                    Object value = FieldUtils.getFieldValue(var1, getMethod);
                     if (CommonUtils.isNotNull(value)){
                         FieldUtils.setValueForField(var2,fieldname2, value);
                     }else {
                         break;//值为空，退出内循环
                     }
                     break;//完成set，退出内循环
                }else {
                    continue;
                }
            }
        }
    }

}
