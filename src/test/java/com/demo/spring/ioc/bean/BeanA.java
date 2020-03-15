package com.demo.spring.ioc.bean;

import com.demo.spring.ioc.CustomBean;

/**
 * <p>实体类a。</p>
 *
 * 创建日期 2020年03月15日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@CustomBean("beanA")
public class BeanA {

    private BeanB beanB;

}
