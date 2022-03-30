package com.cxx.homework.bean.assemble;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 书包
 */
@Data
@ToString
public class SchoolBag {

    private List<Book> books = new ArrayList<>();

    public SchoolBag() {
    }

    public SchoolBag(Book books) {
        this.books.addAll(Arrays.asList(books));
    }


}
