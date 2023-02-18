package com.ethan.ucenter.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Ethan 2023/2/17
 */
@Data
public class PageVO<T> {
    private List<T> list;
    private int listSize;
    private int currentPage;
    private int totalPage;
    private int totalCount;
    private boolean hasNextPage;
    private boolean hasPrePage;
}
