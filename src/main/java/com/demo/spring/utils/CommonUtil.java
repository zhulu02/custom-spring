package com.demo.spring.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>通用工具。</p>
 *
 * 创建日期 2020年03月15日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public class CommonUtil {

    /**
     * 读取properties文件，将其转换成key:value
     * @param properties
     * @return
     */
    public static Map<String, String> getProperties(String properties) {

        Properties props = new Properties();
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream in = CommonUtil.class.getResourceAsStream(properties);
            props.load(in);
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String property = props.getProperty(key);
                map.put(key, property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static void main(String[] args) {
        Map<String, String> map = getProperties("/scan-package.properties");
        for (String s : map.keySet()) {
            System.out.println(s + "=" + map.get(s));
        }
    }
}
