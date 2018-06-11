package com.baidu.ocrqa.sdk.model.param;


import lombok.Data;

import java.util.Map;

@Data
public class CardBasicRecognitionResult extends BasicRecognitionResult {


    /**
     * 识别结果
     */
    public Map<String, String> result;



}
