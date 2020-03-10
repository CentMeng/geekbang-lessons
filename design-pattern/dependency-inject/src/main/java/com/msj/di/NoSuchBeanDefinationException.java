package com.msj.di;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc 没有注册BeanDefination异常，运行时一场呢
 */
public class NoSuchBeanDefinationException extends RuntimeException {

    public NoSuchBeanDefinationException(String message){
        super(message);
    }
}
