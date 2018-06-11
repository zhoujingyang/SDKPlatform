package com.baidu.ocrqa.sdk.model.mysql.performance;

import com.baidu.ocrqa.sdk.model.mysql.BasicResult;
import lombok.Data;

@Data
public class PerformanceTestResult extends BasicResult{


    public String interfaceName; // 接口名称

    public String testImages; // 测试集

    public String averageTime; // 平均响应时间

    public String successNumber; // 成功识别数量

    public String failNumber;   // 成功识别数量

    public String totalNumber; // 总的图片数量



}
