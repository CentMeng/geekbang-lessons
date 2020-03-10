package com.msj.di;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2018 孟少杰 All Rights Reserved
 * @desc
 */
public class DIMain {

    public static void main(String[] args){

        String xmlResourcePath = "/META-INF/dependency-injection.xml";

        ApplicationContext context = new ClassPathXmlApplicationContext(xmlResourcePath);
        User user = (User) context.getBean("user");
        System.out.println(user.toString());
    }
}
