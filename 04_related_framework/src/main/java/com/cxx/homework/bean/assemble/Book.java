package com.cxx.homework.bean.assemble;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 书籍
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {

    private String bookId;

    private String bookName;

    private String version;



}
