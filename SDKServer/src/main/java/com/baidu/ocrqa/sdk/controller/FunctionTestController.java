package com.baidu.ocrqa.sdk.controller;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ocrqa.sdk.model.mysql.function.FunctionTest;
import com.baidu.ocrqa.sdk.model.mysql.function.FunctionTestResult;
import com.baidu.ocrqa.sdk.model.param.function.FunctionTestParam;
import com.baidu.ocrqa.sdk.model.param.function.FunctionTestResultParam;
import com.baidu.ocrqa.sdk.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@RestController
@Api(value = "/ocr/sdk/function", description = "OCR SDK 功能测试结果相关接口")
@RequestMapping("/ocr/sdk/function")
public class FunctionTestController {

    @Autowired
    SqlSessionTemplate template;

    @Transactional
    @ApiOperation(value = "保存功能测试结果", httpMethod = "POST")
    @RequestMapping(value = "/uploadFunctionTestResult", method = RequestMethod.POST)
    public JSONObject uploadFunctionTestResult(@RequestBody FunctionTestParam param) {


        FunctionTestResultParam resultParam = param.getResult();
        log.info("接收参数：" + param.toString());
        JSONObject object = new JSONObject();
        FunctionTestResult result = new FunctionTestResult();
        result.setCaseId(resultParam.getCaseId());
        result.setIdNo(param.getIdNo());
        result.setImgResult(resultParam.getImgResult());
        result.setMessage(resultParam.getMessage());
        result.setStatus(resultParam.getStatus());
        result.setTestTime(resultParam.getTestTime());
        result.setInterfaceName(resultParam.getInterfaceName());
        result.setTime(TimeUtils.getNowTime());
        result.setVersion(param.getVersion());

        /**
         * 先获取一下数据库中的功能结果，如果存在则直接插入结果，不存在则插入功能
         */

        int functionCount = template.selectOne("getFunctionTestCount",param.getIdNo());
        log.info(param.getIdNo() + "在FunctionTest表中存在数据" + functionCount + "条!!!");
        if(functionCount == 0){
            int insertFunctionCount = template.insert("insertFunctionTest", param);
            log.info("插入数据库FunctionTest:" + insertFunctionCount + "条数据");
        }

        int resultCount = template.insert("insertFunctionTestResult", result);
        log.info("插入数据库FunctionTestResult:" + resultCount + "条数据");
        if (resultCount > 0) {
            object.put("result", "true");
            log.info("数据插入成功");
            return object;
        } else {
            object.put("result", "false");
            object.put("detail", "data insert failed");
            log.error("数据插入错误");
            return object;
        }
    }


    @ApiOperation(value = "获取到功能测试结果", httpMethod = "GET")
    @RequestMapping(value = "/getFunctionTestResult/{idNo}", method = RequestMethod.GET)
    public FunctionTest getFunctionTest(@PathVariable(name = "idNo") String idNo) {
        FunctionTest functionTest = template.selectOne("getFunctionTest", idNo);
        List<FunctionTestResult> functionTestResultList = template.selectList("getFunctionTestResult", idNo);
        log.info(functionTestResultList.toString());
        functionTest.setResult(functionTestResultList);
        return functionTest;
    }


    @ApiOperation(value = "获取到功能测试列表", httpMethod = "GET")
    @RequestMapping(value = "/getFunctionTestList", method = RequestMethod.GET)
    public List<FunctionTest> getFunctionTestList(@RequestParam int start, @RequestParam int end) {
        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        List<FunctionTest> functionTest = template.selectList("getFunctionTestList", map);
        return functionTest;
    }

    @ApiOperation(value = "获取到功能测试结果详细信息列表", httpMethod = "GET")
    @RequestMapping(value = "/getFunctionTestResultList", method = RequestMethod.GET)
    public List<FunctionTestResult> getFunctionTestResultList(@RequestParam int start,
                                                        @RequestParam int end,
                                                        @RequestParam String idNo) {
        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        map.put("idNo", idNo);
        List<FunctionTestResult> functionTestResults = template.selectList("getFunctionTestResultList", map);
        return functionTestResults;
    }
}
