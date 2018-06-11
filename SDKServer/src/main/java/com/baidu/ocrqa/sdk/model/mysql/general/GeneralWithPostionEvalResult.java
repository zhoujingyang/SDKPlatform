package com.baidu.ocrqa.sdk.model.mysql.general;


import com.baidu.ocrqa.sdk.model.mysql.BasicResult;
import lombok.Data;

@Data
public class GeneralWithPostionEvalResult extends BasicResult {

    public String charLevelRecall;
    public String charLevelPrecision;
    public String imageLevelRecall;
    public String imageLevelPrecision;

}
