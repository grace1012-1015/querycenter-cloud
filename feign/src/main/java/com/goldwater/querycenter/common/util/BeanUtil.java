package com.goldwater.querycenter.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtil {
    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }


    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            logger.error("transMap2Bean Error " + e);
        }
        return;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error("transBean2Map Error " + e);
        }
        return map;
    }

    /**
     * 方法说明：将bean转化为另一种bean实体
     * 
     * @param object
     * @param entityClass
     * @return
     */
    public static <T> T convertBean(Object object, Class<T> entityClass) {
        if(null == object) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), entityClass);
    }


    /**
     * 方法说明：对象转换
     * 
     * @param source	原对象
     * @param target	目标对象
     * @param ignoreProperties	排除要copy的属性
     * @return
     */
    public static <T> T copy(Object source, Class<T> target, String...ignoreProperties){
        T targetInstance = null;
        try {
            targetInstance = target.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ArrayUtils.isEmpty(ignoreProperties)) {
            BeanUtils.copyProperties(source, targetInstance);
        }else {
            BeanUtils.copyProperties(source, targetInstance, ignoreProperties);
        }
        return targetInstance;

    }

    /**
     * 方法说明：对象转换(List)
     * 
     * @param list	原对象
     * @param target	目标对象
     * @param ignoreProperties	排除要copy的属性
     * @return
     */
    public static <T, E> List<T> copyList(List<E> list, Class<T> target, String...ignoreProperties){
        List<T> targetList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)) {
            return targetList;
        }
        for(E e : list) {
            targetList.add(copy(e, target, ignoreProperties));
        }
        return targetList;
    }

    /**
     * 方法说明：map转化为对象
     * 
     * @param map
     * @param t
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> t) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        T instance = t.newInstance();
        //org.apache.commons.beanutils.BeanUtils.populate(instance, map);
        populate(map, instance);
        return instance;
    }

    /**
     * 处理 java.util.date
     * @param map
     * @param obj
     */
    public static void populate(Map<String, Object> map, Object obj) {
        //ConvertUtils.register(new DateLocaleConverter(), Date.class);
        ConvertUtils.register(new Converter()
        {
            @SuppressWarnings("rawtypes")
            @Override
            public Object convert(Class arg0, Object arg1)
            {
                logger.error("注册字符串转换为date类型转换器");
                if(arg1 == null)
                {
                    return null;
                }
                if(!(arg1 instanceof String))
                {
                    throw new ConversionException("只支持字符串转换 !");
                }
                String str = (String)arg1;
                if(str.trim().equals(""))
                {
                    return null;
                }
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try{
                    return sd.parse(str);
                }
                catch(Exception e)
                {
                    throw new RuntimeException(e);
                }

            }

        }, java.util.Date.class);
        if (map == null || obj == null) {
            return;
        }
        try {
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        } catch (Exception e) {
            logger.error("Map<String,Object>转化Bean异常：" + e);
        }
    }


    /**
     * 方法说明：对象转化为Map
     * 
     * @param object
     * @return
     */
    public static Map<?, ?> objectToMap(Object object){
        return convertBean(object, Map.class);
    }
}
