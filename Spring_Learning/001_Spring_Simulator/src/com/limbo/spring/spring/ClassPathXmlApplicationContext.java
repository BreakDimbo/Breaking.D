package com.limbo.spring.spring;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by main on 7/4/16.
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    public Map<String, Object> beans = new HashMap<>();

    @Override
    public Object getBean(String id) {
        return beans.get(id);
    }

    public ClassPathXmlApplicationContext() throws Exception {

        SAXReader saxReader = new SAXReader();

        //获取DOM
        Document document = saxReader.read(this.getClass().getClassLoader().getResourceAsStream("beans.xml"));

        //获取根节点
        Element root = document.getRootElement();

        //获取子节点集
        List<Element> list = root.elements("bean");
        for (int i = 0; i < list.size(); i++) {
            Element element = list.get(i);
            //拿到属性值
            String id = element.attributeValue("id");
            String clazz = element.attributeValue("class");
            //利用反射,生成对象并保存在map 中
            Object o = Class.forName(clazz).newInstance();
            //测试
            System.out.println(id + " " + o);

            beans.put(id, o);

            for(Element propertyElement : (List<Element>)element.elements("property")) {

                String name = propertyElement.attributeValue("name");
                String bean = propertyElement.attributeValue("bean");

                Object beanObject = beans.get(bean);

                //利用反射生成 set 方法
                //构造字符串
                String methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
                System.out.println("Methoname " + methodName);
                //生成方法
                Method m = o.getClass().getMethod(methodName, beanObject.getClass().getInterfaces()[0]);
                m.invoke(o, beanObject);
            }
        }

    }


}

