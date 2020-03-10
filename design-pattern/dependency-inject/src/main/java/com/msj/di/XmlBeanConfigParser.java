package com.msj.di;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc xml解析成 @BeanDefinition
 */
public class XmlBeanConfigParser implements BeanConfigParser {

    @Override
    public List<BeanDefinition> parse(InputStream inputStream) {
        List beanDefinitions = new ArrayList<>();

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputStream);

            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work about why xml need normalize
            doc.getDocumentElement().normalize();

            NodeList beanList = doc.getElementsByTagName("bean");

            for (int i = 0; i < beanList.getLength(); i++) {
                Node node = beanList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;

                Element element = (Element) node;
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setId(element.getAttribute("id"));
                beanDefinition.setClassName(element.getAttribute("class"));
                beanDefinition.setScope(BeanDefinition.Scope.valueOf(element.getAttribute("scope")));
                beanDefinition.setLazy(Boolean.valueOf(element.getAttribute("lazy-init")));
                loadConstructorArgs(
                        element.getElementsByTagName("constructor-arg"),
                        beanDefinition
                );

                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return beanDefinitions;
    }

    private void loadConstructorArgs(NodeList nodes, BeanDefinition beanDefinition) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element element = (Element) node;
            BeanDefinition.ConstructorArg constructorArg = null;
            if (!element.getAttribute("type").isEmpty()) {
                Class type = String.class;
                try{
                    type = Class.forName("java.lang."+element.getAttribute("type"));
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
                constructorArg = new BeanDefinition.ConstructorArg.Builder()
                        .setArg(type == Float.class? Float.valueOf(element.getAttribute("value")):type == Integer.class?Integer.parseInt(element.getAttribute("value")):type == Boolean.class?Boolean.valueOf(element.getAttribute("value")):type == Double.class?Double.valueOf(element.getAttribute("value")):element.getAttribute("value"))
                        .setType(type)
                        .build();
            }

            if (!element.getAttribute("ref").isEmpty()) {
                constructorArg = new BeanDefinition.ConstructorArg.Builder()
                        .setRef(true)
                        .setArg(element.getAttribute("ref"))
                        .build();
            }

            beanDefinition.addConstructorArg(constructorArg);
        }
    }
}
