package com.lww.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 一级分类对应实体类
 */

@Getter
@Setter
@ToString
public class Category implements Serializable {
    private Integer cid;
    private String cname;

    private List<CategorySecond> list;
}
