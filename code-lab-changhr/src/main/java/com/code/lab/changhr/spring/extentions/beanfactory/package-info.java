package com.code.lab.changhr.spring.extentions.beanfactory;
/*
* Spring IoC 容器允许 BeanFactoryPostProcessor 在容器实际实例化任何其他的 bean 之前读取配置元数据，并有可能修改它。
*
* 通过设置 “order” 属性来控制 BeanFactoryPostProcessor 的执行次序。
* 仅当 BeanFactoryPostProcessor 实现了 Ordered 接口时你才可以设置此属性，
* 因此在实现 BeanFactoryPostProcessor 时，就应该考虑实现 Ordered 接口。
*
* BeanFactoryPostProcessor 的作用域范围是容器级的。它只和你所使用的容器有关。
* 如果你在容器中定义一个 BeanFactoryPostProcessor，它仅仅对此容器中的 bean 进行后置处理。
*
* BeanFactoryPostProcessor 不会对定义在另一个容器中的 bean 进行后置处理，
* 即使这两个容器都是在同一层次上。
*
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 在 Spring 中存在对于 BeanFactoryPostProcessor 的典型应用，比如 PropertyPlaceholderConfigurer。
*
* PropertyPlaceholderConfigurer 这个类间接继承了 BeanFactoryPostProcessor 接口。
* 当 Spring 加载任何实现了这个接口的 bean 的配置时，
* 都会在 bean 工厂载入所有 bean 的配置之后执行 postProcessBeanFactory 方法。
*
* 在 PropertyResourceConfigurer 类中实现了 postProcessBeanFactory 方法，
* 方法中先后调用了 mergeProperties、convertProperties、processProperties 这 3 个方法，
* 分别得到配置，将得到的配置转换为合适的而理性，最后将配置内容告知 BeanFactory。
*
* 通过实现 BeanFactoryPostProcessor 接口，BeanFactory 会在实例化任何 bean 之前获得配置信息，
* 从而能够正确解析 bean 描述文件中的变量印用。
*
* */