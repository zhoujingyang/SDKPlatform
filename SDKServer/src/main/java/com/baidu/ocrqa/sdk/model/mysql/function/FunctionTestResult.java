package com.baidu.ocrqa.sdk.model.mysql.function;

import com.baidu.ocrqa.sdk.model.mysql.BasicResult;
import lombok.Data;
import lombok.ToString;


@Data
public class FunctionTestResult extends BasicResult {

    public String caseId;
    public String status;
    public String testTime;
    public String message;
    public String imgResult;
    public String interfaceName;
}

