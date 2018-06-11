package com.baidu.ocrqa.sdk.model.param.function;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class FunctionTestResultParam{

    public String caseId;
    public String status;
    public String testTime;
    public String message;
    public String imgResult;
    public String interfaceName;
}
