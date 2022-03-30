package com.cxx.homework;

import com.cxx.homework.bean.assemble.Book;
import com.cxx.homework.bean.assemble.SchoolBag;
import com.cxx.homework.bean.assemble.scan.Bedroom;
import com.cxx.homework.bean.assemble.scan.BookCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub。
 * @author xujiaxi
 */
public class Homework2_BeanAssemble {

    public static void main(String[] args) {
//        diFromContextXml();
        diFromScan();
    }

    private static void diFromScan() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookCase bookCase = context.getBean(BookCase.class);
        System.out.println(bookCase);

        Bedroom bedroom = context.getBean(Bedroom.class);
        System.out.println(bedroom);
    }

    /**
     * 注入依赖于扫描
     */
    private static void diFromContextXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Book myBook = (Book) context.getBean("book1");
        System.out.println(myBook);
        SchoolBag schoolBag = context.getBean(SchoolBag.class);
        System.out.println(schoolBag);
    }

}
