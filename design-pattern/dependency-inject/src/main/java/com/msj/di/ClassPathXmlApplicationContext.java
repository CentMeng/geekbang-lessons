package com.msj.di;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2018 孟少杰 All Rights Reserved
 * @desc
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private BeanFactory beanFactory;

    private BeanConfigParser beanConfigParser;

    public ClassPathXmlApplicationContext(String location){
        this.beanFactory = new BeanFactory();
        this.beanConfigParser = new XmlBeanConfigParser();
        loadBeanDefinitions(location);
    }

    @Override
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    private void loadBeanDefinitions(String location){

        InputStream in = null;

        try{
            in = this.getClass().getResourceAsStream(location);

            if(null == in){
                throw new RuntimeException("Can not find file:"+location);
            }

            List<BeanDefinition> beanDefinitions = beanConfigParser.parse(in);
            beanFactory.addBeanDefinitions(beanDefinitions);
        }finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
