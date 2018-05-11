package com.sqshine.readinglist.util;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Bean工具类
 */
public class BeanKit {

    /**
     * 判断是否为Bean对象
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     */
    public static boolean isBean(Class<?> clazz) {
        if (ClassKit.isNormalClass(clazz)) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 1 && method.getName().startsWith("set")) {
                    //检测包含标准的setXXX方法即视为标准的JavaBean
                    return true;
                }
            }
        }
        return false;
    }

    public static PropertyEditor findEditor(Class<?> type) {
        return PropertyEditorManager.findEditor(type);
    }

    /**
     * 获得Bean字段描述数组
     *
     * @param clazz Bean类
     * @return 字段描述数组
     * @throws IntrospectionException
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws IntrospectionException {
        return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
    }

    /**
     * 获得字段名和字段描述Map
     *
     * @param clazz Bean类
     * @return 字段名和字段描述Map
     * @throws IntrospectionException
     */
    public static Map<String, PropertyDescriptor> getFieldNamePropertyDescriptorMap(Class<?> clazz) throws IntrospectionException {
        final PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        Map<String, PropertyDescriptor> map = new HashMap<>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            map.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return map;
    }


    /**
     * 对象转Map
     *
     * @param bean bean对象
     * @return Map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        return beanToMap(bean, false);
    }

    /**
     * 对象转Map
     *
     * @param bean              bean对象
     * @param isToUnderlineCase 是否转换为下划线模式
     * @return Map
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean isToUnderlineCase) {

        if (bean == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            final PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(bean.getClass());
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
                    if (null != value) {
                        map.put(isToUnderlineCase ? StrKit.toUnderlineCase(key) : key, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }


    /**
     * 值提供者，用于提供Bean注入时参数对应值得抽象接口<br>
     * 继承或匿名实例化此接口<br>
     * 在Bean注入过程中，Bean获得字段名，通过外部方式根据这个字段名查找相应的字段值，然后注入Bean<br>
     *
     * @author Looly
     */
    public static interface ValueProvider {
        /**
         * 获取值
         *
         * @param name Bean对象中参数名
         * @return 对应参数名的值
         */
        public Object value(String name);
    }

    /**
     * 属性拷贝选项<br>
     * 包括：<br>
     * 1、限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类<br>
     * 2、是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null<br>
     * 3、忽略的属性列表，设置一个属性列表，不拷贝这些属性值<br>
     *
     * @author Looly
     */
    public static class CopyOptions {
        /**
         * 限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
         */
        private Class<?> editable;
        /**
         * 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         */
        private boolean isIgnoreNullValue;
        /**
         * 忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         */
        private String[] ignoreProperties;

        /**
         * 创建拷贝选项
         *
         * @return 拷贝选项
         */
        public static CopyOptions create() {
            return new CopyOptions();
        }

        /**
         * 创建拷贝选项
         *
         * @param editable          限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
         * @param isIgnoreNullValue 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         * @param ignoreProperties  忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         * @return 拷贝选项
         */
        public static CopyOptions create(Class<?> editable, boolean isIgnoreNullValue, String... ignoreProperties) {
            return new CopyOptions(editable, isIgnoreNullValue, ignoreProperties);
        }

        /**
         * 构造拷贝选项
         */
        public CopyOptions() {
        }

        /**
         * 构造拷贝选项
         *
         * @param editable          限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
         * @param isIgnoreNullValue 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         * @param ignoreProperties  忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         */
        public CopyOptions(Class<?> editable, boolean isIgnoreNullValue, String... ignoreProperties) {
            this.editable = editable;
            this.isIgnoreNullValue = isIgnoreNullValue;
            this.ignoreProperties = ignoreProperties;
        }

        /**
         * 设置限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
         *
         * @param editable 限制的类或接口
         * @return CopyOptions
         */
        public CopyOptions setEditable(Class<?> editable) {
            this.editable = editable;
            return this;
        }

        /**
         * 设置是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         *
         * @param isIgnoreNullVall 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         * @return CopyOptions
         */
        public CopyOptions setIgnoreNullValue(boolean isIgnoreNullVall) {
            this.isIgnoreNullValue = isIgnoreNullVall;
            return this;
        }

        /**
         * 设置忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         *
         * @param ignoreProperties 忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         * @return CopyOptions
         */
        public CopyOptions setIgnoreProperties(String... ignoreProperties) {
            this.ignoreProperties = ignoreProperties;
            return this;
        }
    }
}
