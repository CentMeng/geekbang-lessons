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
import org.springframework.context.ApplicationContext;
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
        //为什么不相等返回false，并且getBeanFactory并不是返回的null，而是返回DefaultListableBeanFactory

        //依赖查找
//        System.out.println(beanFactory.getBean(BeanFactory.class));


        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject());
        System.out.println(userFactory.getObject() == beanFactory);
        //此处相等

        // 依赖来源三：容器內建 Bean （Environment并没有配置，但是可以输出，因为是容器内建Bean）
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取内建Bean Environment："+environment);

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

    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext) {


        // ConfigurableApplicationContext <- ApplicationContext <- BeanFactory

        // ConfigurableApplicationContext#getBeanFactory()


        // 这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

        // ApplicationContext is BeanFactory

    }

}
