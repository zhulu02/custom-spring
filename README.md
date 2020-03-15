实现一个简单的spring框架

ioc的实现:要想托管程序中的所有bean,需要有一个针对bean的管理，对bean进行定位、注册、初始化、注入
1.定义要扫描的包配置（scan-package.properties）,两个注解（CustomBean和CustomAutowired）
2.获取需要指定包下的所有类，逐一实例化，缓存到本地map
3.根据类名从map中获取bean对象（测试类：BeanTest）


