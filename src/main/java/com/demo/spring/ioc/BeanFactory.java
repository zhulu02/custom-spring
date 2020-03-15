package com.demo.spring.ioc;

import com.demo.spring.utils.CommonUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>bean工厂。</p>
 *
 * 创建日期 2020年03月15日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public class BeanFactory {

    public static Map<String, Object> classMap = new HashMap();
    private static ClasspathPackageScanner classpathPackageScanner;

    /**
     * 初始IOC 容器
     */
    static {
        String basePackage = CommonUtil.getProperties("/scan-package.properties").get("scan.package");
        autoInjection(basePackage, classMap);
    }

    public static Object getBean(String name) {
        return classMap.get(name);
    }


    /**
     *  加载指定包路径下的所有class
     * @param basePackage
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class> loadClasses(String basePackage)
            throws IOException, ClassNotFoundException {
        classpathPackageScanner = new ClasspathPackageScanner(basePackage);
        List<String> list = classpathPackageScanner.getFullyQualifiedClassNameList();
        List<Class> classList = new ArrayList<Class>();
        for (String string : list) {
            classList.add(Class.forName(string));
        }
        return classList;

    }


    /**
     * 自动注入
     * @param basePackage
     * @param mmp
     */
    public static void autoInjection(String basePackage, Map mmp) {
        try {
            List<Class> classes = loadClasses(basePackage); //加载指定包路径下的所有classes文件
            for (Class aClass : classes) {
                Map<String, Object> judgeMap = new HashMap();
                inject(mmp, aClass, judgeMap);//注入
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 递归注入
     * @param classMap
     * @param classes
     * @param judgeMap
     * @throws Exception
     */
    private static void inject(Map classMap, Class classes, Map judgeMap)
            throws Exception {
        boolean isExist = classes.isAnnotationPresent(CustomBean.class);
        if (!isExist) {
            return;
        }
        CustomBean customBean = (CustomBean) classes.getAnnotation(CustomBean.class);
        String beanName = customBean.value();//获取bean名称
        if (null == judgeMap.get(beanName))
            judgeMap.put(beanName, true);
        else { //又返回依赖他
            throw new Exception("循环依赖");
        }

        if (null == classMap.get(beanName)) { //还没有被注入
            Object beanObj = classes.newInstance(); //获得bean实例
            Field[] fields = classes.getDeclaredFields();
            boolean fieldExist;
            for (Field field : fields) {
                fieldExist = field.isAnnotationPresent(CustomAutowired.class);
                if (!fieldExist) {
                    continue;
                }
                String classType = field.getGenericType().toString();
                Class fieldClass = Class.forName(classType.substring(6));
                //强制设置值 破坏了封装性
                field.setAccessible(true);
                if (fieldClass.isAnnotationPresent(CustomBean.class)) {//该属性依赖其它Bean
                    CustomBean tbean = (CustomBean) fieldClass.getAnnotation(CustomBean.class);
                    inject(classMap, fieldClass, judgeMap);
                    field.set(beanObj, classMap.get(tbean.value()));
                } else { //该属性不依赖其它Bean
                    Object object = fieldClass.newInstance();
                    field.set(beanObj, object);
                }
            }
            classMap.put(beanName, beanObj);

        }

    }


}
