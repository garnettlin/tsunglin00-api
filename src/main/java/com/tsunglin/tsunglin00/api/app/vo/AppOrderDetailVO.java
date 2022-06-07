package com.tsunglin.tsunglin00.api.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 訂單詳情頁頁面VO
 */
@Data
public class AppOrderDetailVO implements Serializable {

    @ApiModelProperty("訂單號")
    private String orderNo;

    @ApiModelProperty("訂單價格")
    private Integer totalPrice;

    @ApiModelProperty("訂單支付狀態碼")
    private Byte payStatus;

    @ApiModelProperty("訂單支付方式")
    private Byte payType;

    @ApiModelProperty("訂單支付方式")
    private String payTypeString;

    @ApiModelProperty("訂單支付時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

    @ApiModelProperty("訂單狀態碼")
    private Byte orderStatus;

    @ApiModelProperty("訂單狀態")
    private String orderStatusString;

    @ApiModelProperty("創建時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("訂單項列表")
    private List<AppOrderItemVO> appOrderItemVOS;
}
