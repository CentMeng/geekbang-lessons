/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geekbang.thinking.in.spring.ioc.overview.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 *
 * @author <a href="mailto:mengshaojie@188.com">CentMeng</a>
 * @since
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {

        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        //自定义Bean
        // 依赖来源一：自定义 Bean UserRepository
        UserRepository userRepository = beanFactory.getBean("userRepository",UserRepository.class);
        //依赖注入
        System.out.println(userRepository.getUsers());
        // 依赖来源二：依赖注入（容器內建依赖） beanFactory是内建的依赖
        System.out.println(userRepository.getBeanFactory());

        System.out.println(userRepository.getBeanFactory() == beanFactory);
        //不相等返回false，并且getBeanFactory并不是返回的null，而是返回DefaultListableBeanFactory,Spring的维护和生命周期管理均在BeanFactory实现类中，绝大多数是指DefaultListableBeanFactory。具体看whoIsIoCContainer方法中讲解

        //依赖查找
//        System.out.println(beanFactory.getBean(BeanFactory.class));


        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject());
        System.out.println(userFactory.getObject() == beanFactory);
        //此处相等

        // 依赖来源三：容器內建 Bean （Environment并没有配置，但是可以输出，因为是容器内建Bean）
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取内建Bean Environment："+environment);

        //ApplicationContext 是 BeanFactory的子接口，所以使用ApplicationContext也可以

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
//
//        // 依赖来源一：自定义 Bean
//        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
//
////        System.out.println(userRepository.getUsers());
//
//        // 依赖来源二：依赖注入（內建依赖）
//        System.out.println(userRepository.getBeanFactory());
//
//
//        ObjectFactory userFactory = userRepository.getObjectFactory();
//
//        System.out.println(userFactory.getObject() == applicationContext);
//
//        // 依赖查找（错误）
////        System.out.println(beanFactory.getBean(BeanFactory.class));
//
//        // 依赖来源三：容器內建 Bean
//        Environment environment = applicationContext.getBean(Environment.class);
//        System.out.println("获取 Environment 类型的 Bean：" + environment);
    }

    /**
     * 谁才是IoC容器 FactoryBean or ApplicationContext
     * @param userRepository
     * @param beanFactory
     */
    private static void whoIsIoCContainer(UserRepository userRepository, BeanFactory beanFactory) {


        // ConfigurableApplicationContext定义了getBeanFactory ，它继承ApplicationContext ，而ApplicationContext是BeanFactory的子接口

        // ConfigurableApplicationContext 为什么要定义getBeanFactory()方法，它本身就是个BeanFactory
        /**
         * 实现类是AbstractRefreshableApplication，这里是组合的模式引入了DefaultListableBeanFactory，所以为什么返回的是DefaultListableBeanFactory
         */


        // 这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == beanFactory);

        //重点：ApplicationContext 就是 BeanFactory，是BeanFactory的子接口

    }

}
