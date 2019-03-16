package com.common.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * Created by  on 2017-09-04.
 */
public class MyMapWrapper extends MapWrapper {
    public MyMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {

        if (useCamelCaseMapping  && ((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z')
                || name.indexOf("_") >= 0)) {
            return underlineToCamelhump(name);
        }
        return name;
    }



    @Override
    public void set(PropertyTokenizer prop, Object value) {
        value=value==null?"":value;
        super.set(prop,value);

    }



    /**
     * 将下划线风格替换为驼峰风格
     *
     * @param inputString
     * @return
     */
    public static String underlineToCamelhump(String inputString) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
            } else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }
}