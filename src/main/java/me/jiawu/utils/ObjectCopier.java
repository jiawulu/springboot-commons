package me.jiawu.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 *
 * 对象复制器
 */
@Slf4j
public class ObjectCopier {

    public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

    public static void copyProperties(Object target, Object source) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    public static void copyPropertiesSpring(Object dest, Object src) {
        try {
            BeanUtils.copyProperties(src, dest);
        } catch (BeansException e) {
            log.error("copy with exception ", e);
        }
    }

    public static <T> T from(Object object, Class<T> clazz) {

        try {
            Object dest = clazz.newInstance();
            copyProperties(dest, object);
            return (T)dest;
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static <T> List<T> from(List<Object> objects,
                                   Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (Object obj : objects) {
            list.add(from(obj, clazz));
        }
        return list;
    }

    /**
     * 合并属性的时候，skip null
     * @param dest
     * @param src
     */
    public static void copyPropertiesNonAware(Object dest, Object src) {
        try {
            NonAwareBeanUtilsBean.getInstance().copyProperties(dest, src);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("copy with exception ", e);
        }
    }

    public static class NonAwareBeanUtilsBean extends BeanUtilsBean2 {

        private static NonAwareBeanUtilsBean sNonAwareBeanUtilsBean = new NonAwareBeanUtilsBean();

        public static NonAwareBeanUtilsBean getInstance() {
            return sNonAwareBeanUtilsBean;
        }

        private NonAwareBeanUtilsBean() {
        }

        @Override
        public void copyProperty(Object bean, String name, Object srcValue)
            throws IllegalAccessException, InvocationTargetException {

            if (null == srcValue) {
                return;
            }

            if (srcValue.getClass() == long.class && (long)srcValue == 0) {
                return;
            }
            if (srcValue.getClass() == int.class && (int)srcValue == 0) {
                return;
            }

            try {
                super.copyProperty(bean, name, srcValue);
            } catch (Throwable e) {

            }

        }

    }

}
