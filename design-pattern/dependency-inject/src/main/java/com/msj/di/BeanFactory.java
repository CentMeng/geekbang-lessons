package com.msj.di;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc
 */
public class BeanFactory {

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefinition> beanDefinations = new ConcurrentHashMap<>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
        for (BeanDefinition beanDefinition: beanDefinitionList) {
            this.beanDefinations.putIfAbsent(beanDefinition.getId(), beanDefinition);
        }

        //最好能都放入缓存中，再初始化，防止关联的类型没有加入到缓存中
        for(BeanDefinition beanDefinition:beanDefinitionList){
            //不需要延迟加载的单例bean，注册时候就初始化
            if (beanDefinition.isLazy() == false && beanDefinition.isSingleton()) {
                createBean(beanDefinition);
            }
        }

    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = beanDefinations.get(beanId);
        if (null == beanDefinition) {
            throw new NoSuchBeanDefinationException("Bean is not defined:" + beanId);
        }
        return createBean(beanDefinition);
    }

    /**
     * 有参无参构造函数创建bean
     * @param beanDefinition
     * @return
     * @VisibleForTesting注解是说明此方法为private，但是为了方便单元测试，所以用了protected
     */
    @VisibleForTesting
    protected Object createBean(BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton() && singletonObjects.containsKey(beanDefinition.getId())) {
            return singletonObjects.get(beanDefinition.getId());
        }

        Object bean = null;

        try {
            Class beanClass = Class.forName(beanDefinition.getClassName());
            List<BeanDefinition.ConstructorArg> args = beanDefinition.getConstructorArgs();
            if (args.isEmpty()) {
                //无参数
                bean = beanClass.newInstance();
            } else {
                //有参构造函数创建
                Class[] argClasses = new Class[args.size()];
                Object[] argObjects = new Object[args.size()];

                for (int i = 0; i < args.size(); i++) {
                    BeanDefinition.ConstructorArg arg = args.get(i);
                    if (arg.isRef()) {
                        BeanDefinition refBeanDefinition = beanDefinations.get(arg.getArg());
                        if (refBeanDefinition == null) {
                            throw new NoSuchBeanDefinationException("Bean is not defined: " + arg.getArg());
                        }
                        argObjects[i] = createBean(refBeanDefinition);
                        argClasses[i] = argObjects[i].getClass();
                    } else {
                        argClasses[i] = arg.getType();
                        argObjects[i] = arg.getArg();
                    }
                }
                bean = beanClass.getConstructor(argClasses).newInstance(argObjects);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(null != bean && beanDefinition.isSingleton()){
            singletonObjects.putIfAbsent(beanDefinition.getId(),bean);
            //防止异常多次加载情况
            return singletonObjects.get(beanDefinition.getId());
        }

        return bean;

    }
}
