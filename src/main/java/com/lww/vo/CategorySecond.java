package com.lww.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 二级分类的实体
 */
@Getter
@Setter
@ToString
public class CategorySecond implements Serializable {
	private Integer csid;
	private String csname;
	private Integer cid;
}
