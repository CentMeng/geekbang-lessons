package com.msj.di;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc Spring中
 */
public interface ApplicationContext {

    Object getBean(String beanId);
}
