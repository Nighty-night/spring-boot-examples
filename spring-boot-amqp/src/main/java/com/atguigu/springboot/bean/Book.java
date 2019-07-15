package com.atguigu.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @description：book类，顺便试一下lombok
 * @author： wangkang
 * @date： 2019/7/15 16:39
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Data //包含了@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor的功能
public class Book {
    private @Getter @Setter String bookName;
    private @Getter @Setter String author;
}
