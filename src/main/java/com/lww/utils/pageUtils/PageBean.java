package com.lww.utils.pageUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PageBean<T> {
    private int page;    // 当前页数
    private int totalCount; // 总记录数
    private int totalPage; // 总页数
    private int limit;    // 每页显示的记录数
    private List<T> list; // 每页显示数据的集合.


}
