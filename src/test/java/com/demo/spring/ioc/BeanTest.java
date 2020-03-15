package com.demo.spring.ioc;


import com.demo.spring.ioc.bean.BeanA;
import com.demo.spring.ioc.bean.BeanB;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 *
 * 创建日期 2020年03月15日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public class BeanTest {

    public static void main(String[] args) {
        BeanA beanA = (BeanA) BeanFactory.getBean("beanA");
        BeanB beanB = (BeanB) BeanFactory.getBean("beanB");
        System.out.println(beanA.toString());
        System.out.println(beanB.toString());
    }
}
