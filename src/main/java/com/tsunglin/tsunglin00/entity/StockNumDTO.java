package com.tsunglin.tsunglin00.entity;

import lombok.Data;

/**
 * 庫存修改所需實體
 */

@Data
public class StockNumDTO {
    private Long goodsId;

    private Integer goodsCount;
}
