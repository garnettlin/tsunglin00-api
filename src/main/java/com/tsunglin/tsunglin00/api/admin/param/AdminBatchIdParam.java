package com.tsunglin.tsunglin00.api.admin.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminBatchIdParam implements Serializable {
    //id
    Long[] ids;
}
