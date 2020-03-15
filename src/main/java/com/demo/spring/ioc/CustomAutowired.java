package com.demo.spring.ioc;

import java.lang.annotation.*;

/**
 * <p>自定义Autowired。</p>
 *
 * 创建日期 2020年03月15日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Target({ElementType.FIELD,  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CustomAutowired {
}
