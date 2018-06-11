package com.baidu.ocrqa.sdk.model.param.function;

import com.baidu.ocrqa.sdk.model.param.ParamBasicResult;
import lombok.Data;


@Data
public class FunctionTestParam extends ParamBasicResult {

    public String type;
    public String imgName;
    FunctionTestResultParam result;
}
