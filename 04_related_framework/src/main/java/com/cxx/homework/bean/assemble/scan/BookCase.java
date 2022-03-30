package com.cxx.homework.bean.assemble.scan;

import com.cxx.homework.bean.assemble.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 书柜
 * @author cxx
 * 这种使用component 配置bean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class BookCase {

    @Autowired
    private Book book;


}
