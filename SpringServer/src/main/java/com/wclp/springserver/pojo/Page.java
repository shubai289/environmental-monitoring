package com.wclp.springserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private int totalCount;  //总记录数
    private int totalPage; // 总页数
    private List<T> list;   //每页的数据list集合
    private int currentPage; //当前页码
    private int rows;       //每页显示的条数
}
