package com.cxx.example.kimmking.spring02;

import com.cxx.example.kimmking.aop.ISchool;
import com.cxx.example.kimmking.spring01.Student;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School implements ISchool {
    
    // Resource
    /**
     * 启动的时候，先找到这个class， 然后再装配到class1 上
     */
    @Autowired(required = true) //primary
    Klass class1;
    
    @Resource(name = "student100")
    Student student100;
    
    @Override
    public void ding(){
    
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
        
    }
    
}
