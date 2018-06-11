package com.baidu.ocrqa.sdk.model.mysql.general;

import com.baidu.ocrqa.sdk.model.mysql.BasicResult;
import lombok.Data;

/**
 * 数据库存储对应的general
 */

@Data
public class GeneralNoPositionEvalResult extends BasicResult {

    public String err;
    public String subErr;
    public String insertErr;
    public String delErr;
    public String lineErr;


}
