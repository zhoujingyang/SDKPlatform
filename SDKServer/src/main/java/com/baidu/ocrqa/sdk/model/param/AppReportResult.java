package com.baidu.ocrqa.sdk.model.param;

import lombok.Data;

@Data
public class AppReportResult extends ParamBasicResult {

    /**
     * 数据分类，目前只有cpu和mem
     */
    public String dataCategory;

}
