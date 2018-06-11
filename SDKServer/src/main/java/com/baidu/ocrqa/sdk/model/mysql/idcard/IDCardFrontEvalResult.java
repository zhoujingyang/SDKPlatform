package com.baidu.ocrqa.sdk.model.mysql.idcard;


import com.baidu.ocrqa.sdk.model.mysql.BasicResult;
import lombok.Data;

@Data
public class IDCardFrontEvalResult extends BasicResult {


    /**
     * 召回率
     */
    public String birthdayRecall;
    public String nameRecall;
    public String idNoRecall;
    public String sexRecall;
    public String nationRecall;
    public String addressRecall;

    /**
     * 准确率
     */
    public String birthdayAccuracy;
    public String nameAccuracy;
    public String idNoAccuracy;
    public String sexAccuracy;
    public String nationAccuracy;
    public String addressAccuracy;


}
