package com.baidu.ocrqa.sdk.model.mysql;

import lombok.Data;

@Data
public class BasicResult {
    public String id;

    /**
     * 执行评测的id号码
     */
    public String idNo;
    /**
     * 数据插入时间
     */
    public String time;

    public String version;

}
