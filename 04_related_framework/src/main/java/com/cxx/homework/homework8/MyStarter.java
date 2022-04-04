package com.cxx.homework.homework8;

import com.cxx.homework8springbootstarter.EnableMyStarter;
import com.cxx.homework8springbootstarter.homework.Klass;
import com.cxx.homework8springbootstarter.homework.School;
import com.cxx.homework8springbootstarter.homework.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @ClassName MyStarter
 * @Description TODO
 * @Date 2022/4/4 17:28
 */
@SpringBootApplication
@EnableMyStarter
public class MyStarter {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyStarter.class,args);
        Student student = context.getBean(Student.class);
        System.out.println(student);
//        School school = context.getBean(School.class);
//        System.out.println(school);
        Klass k = context.getBean(Klass.class);
        System.out.println(k);
    }
}
