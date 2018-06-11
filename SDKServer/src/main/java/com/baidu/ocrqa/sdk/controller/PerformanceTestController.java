package com.baidu.ocrqa.sdk.controller;


import com.alibaba.fastjson.JSONObject;
import com.baidu.ocrqa.sdk.model.mysql.performance.PerformanceTestResult;
import com.baidu.ocrqa.sdk.model.param.performance.PerformanceTestParam;
import com.baidu.ocrqa.sdk.utils.DatabaseUtils;
import com.baidu.ocrqa.sdk.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@RestController
@RequestMapping("/ocr/sdk/performance")
@Api(value = "/ocr/sdk/performance", description = "OCR SDK 性能测试相关接口")
public class PerformanceTestController {

    @Autowired
    SqlSessionTemplate template;

    @ApiOperation(value = "保存性能测试结果", httpMethod = "POST")
    @RequestMapping(value = "/savePerformanceTestResult", method = RequestMethod.POST)
    public JSONObject savePerformanceTestResult(@RequestBody PerformanceTestParam param) {

        PerformanceTestResult performanceTestResult = new PerformanceTestResult();
        performanceTestResult.setAverageTime(param.getAverageTime());
        performanceTestResult.setFailNumber(param.getFailNumber());
        performanceTestResult.setInterfaceName(param.getInterfaceName());
        performanceTestResult.setSuccessNumber(param.getSuccessNumber());
        performanceTestResult.setTestImages(param.getTestImages());
        performanceTestResult.setTotalNumber(param.getTotalNumber());
        performanceTestResult.setIdNo(param.getIdNo());
        performanceTestResult.setVersion(param.getVersion());
        performanceTestResult.setTime(TimeUtils.getNowTime());
        int count = template.insert("savePerformanceTestResult", performanceTestResult);
        log.info("传入信息为" + performanceTestResult.toString());
        JSONObject object = new JSONObject();
        if (count > 0) {
            object.put("result", "true");
            return object;
        } else {
            object.put("result", "false");
            object.put("detail", "data insert failed");
            return object;
        }
    }

    @ApiOperation(value = "获取到性能测试结果", httpMethod = "GET")
    @RequestMapping(value = "/getPerformanceTestResult/{idNo}", method = RequestMethod.GET)
    public PerformanceTestResult getPerformanceTestResult(@PathVariable(name = "idNo") String idNo) {
        PerformanceTestResult performanceTestResult = template.selectOne("getPerformanceTestResult", idNo);
        log.info("从数据库中查到idNo=" + idNo + "的信息");
        return performanceTestResult;
    }

    @ApiOperation(value = "获取到性能测试结果", httpMethod = "GET")
    @RequestMapping(value = "/getPerformanceTestResultList", method = RequestMethod.GET)
    public List<PerformanceTestResult> getPerformanceTestResultList(@RequestParam int start,
                                                                   @RequestParam int end) {
            Map map = new HashMap();
            map.put("start", start);
            map.put("end", end);
        List<PerformanceTestResult> performanceTestResult = template.selectList("getPerformanceTestResultList",map);
        return performanceTestResult;
    }

}
