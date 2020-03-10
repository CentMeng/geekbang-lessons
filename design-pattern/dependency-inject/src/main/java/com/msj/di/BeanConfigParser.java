package com.msj.di;

import java.io.InputStream;
import java.util.List;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc 解析，面向接口而非实现编程
 */
public interface BeanConfigParser {

    List<BeanDefinition> parse(InputStream inputStream);
}
