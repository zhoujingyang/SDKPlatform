package com.baidu.ocrqa.sdk.controller;


import com.alibaba.fastjson.JSONObject;
import com.baidu.ocrqa.sdk.model.evalCategory.GeneralNoPosition;
import com.baidu.ocrqa.sdk.model.evalCategory.GeneralWithPosition;
import com.baidu.ocrqa.sdk.model.mysql.general.GeneralNoPositionEvalResult;
import com.baidu.ocrqa.sdk.model.mysql.general.GeneralWithPostionEvalResult;
import com.baidu.ocrqa.sdk.model.param.GeneralBasicRecognitionResult;
import com.baidu.ocrqa.sdk.utils.DatabaseUtils;
import com.baidu.ocrqa.sdk.utils.EvaluationUtils;
import com.baidu.ocrqa.sdk.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@RestController
@Api(value = "/ocr/sdk/eval", description = "OCR SDK 通用评测相关接口")
@RequestMapping("/ocr/sdk/eval")
public class GeneralController {

    @Autowired
    SqlSessionTemplate template;


    @ApiOperation(value = "保存generalOCR识别结果接口,达到文件数量时则触发评测任务", httpMethod = "POST")
    @RequestMapping(value = "/save/general/noposition", method = RequestMethod.POST)
    public JSONObject saveGeneralRecognitionResult(
            @RequestBody GeneralBasicRecognitionResult generalRecognitionResult) {
        JSONObject object = new JSONObject();
        if (
                !generalRecognitionResult.getEvalName().equals(GeneralNoPosition.KUAISHOU)
                        && !generalRecognitionResult.getEvalName().equals(GeneralNoPosition.MIX2923GENERAL)
                        && !generalRecognitionResult.getEvalName().equals(GeneralNoPosition.TIMU)
                        && !generalRecognitionResult.getEvalName().equals(GeneralNoPosition.YINSHUA)
                        && !generalRecognitionResult.getEvalName().equals(GeneralNoPosition.FENGCHAO)
                ) {
            object.put("result", "false");
            log.error("评测集名称填写错误,填写的是:" + generalRecognitionResult.getEvalName());
            return object;
        }


        /**
         * 执行创建文件夹及写入识别结果到文件中，并且获取目录下的文件数量
         */
        String[] imgNumAndSaveDir = new String[]{};
        try {
            imgNumAndSaveDir = FileUtils.createDirAndWriteFile(generalRecognitionResult,
                    "generalNoPosition");
        } catch (IOException e) {
            e.printStackTrace();
            object.put("result", "false");
            return object;
        }

        /**
         * 判断是否所有文件都写完了，如果写完了则调用评测脚本
         */
        String imgNum = generalRecognitionResult.getEvalImgNum();
        if (imgNum.equals(imgNumAndSaveDir[0])) {
            log.info(generalRecognitionResult.getIdNo() + "general-ocr 开始进行评测");

            GeneralNoPositionEvalResult general = null;
            try {
                general = EvaluationUtils.generalOcrNoPositionEval(
                        generalRecognitionResult, imgNumAndSaveDir[1]);
            } catch (IOException e) {
                e.printStackTrace();
                object.put("result", "true");
                return object;
            } catch (InterruptedException e) {
                e.printStackTrace();
                object.put("result", "true");
                return object;
            }

            DatabaseUtils.saveToMysql("generalInsert", general, template, generalRecognitionResult);
        }

        object.put("result", "true");
        return object;

    }

    @ApiOperation(value = "获取general-OCR评测结果", httpMethod = "POST")
    @RequestMapping(value = "/getGeneralEvalResult", method = RequestMethod.POST)
    public GeneralNoPositionEvalResult getGeneralEvalResult(@RequestParam String idNo) {

        GeneralNoPositionEvalResult result = template.selectOne("getGeneralEvalResult", idNo);

        return result;
    }


    @ApiOperation(value = "通用评测带单字位置信息的", httpMethod = "POST")
    @RequestMapping(value = "/save/general/with/position", method = RequestMethod.POST)
    public JSONObject saveGeneralWithPosition(
            @RequestBody GeneralBasicRecognitionResult generalBasicRecognitionResult) {

        JSONObject object = new JSONObject();
        if (
                !generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.FENGCHAO)
                        && !generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.KUAISHOU)
                        && !generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.MIX2923GENERAL)
                        && !generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.TIMU)
                        && !generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.YINSHUA)
                ) {
            object.put("result", "false");
            log.error("评测集名称填写错误,填写的是:" + generalBasicRecognitionResult.getEvalName());
            return object;
        }


        /**
         * 执行创建文件夹及写入识别结果到文件中，并且获取目录下的文件数量
         */
        String[] imgNumAndSaveDir = new String[]{};
        try {
            imgNumAndSaveDir = FileUtils.createDirAndWriteFile(generalBasicRecognitionResult,
                    "generalWithPosition");
        } catch (IOException e) {
            e.printStackTrace();
            object.put("result", "false");
            return object;
        }

        /**
         * 判断是否所有文件都写完了，如果写完了则调用评测脚本
         */
        String imgNum = generalBasicRecognitionResult.getEvalImgNum();
        if (imgNum.equals(imgNumAndSaveDir[0])) {
            log.info(generalBasicRecognitionResult.getIdNo() + "general-ocr-with-position 开始进行评测");

            GeneralWithPostionEvalResult general = null;
            try {
                general = EvaluationUtils.generalOCRWithPositionEval(
                        generalBasicRecognitionResult, imgNumAndSaveDir[1]);
            } catch (IOException e) {
                e.printStackTrace();
                object.put("result", "true");
                return object;
            } catch (InterruptedException e) {
                e.printStackTrace();
                object.put("result", "true");
                return object;
            }

            DatabaseUtils.saveToMysql(
                    "generalWithPositionInsert",
                    general,
                    template,
                    generalBasicRecognitionResult);
        }

        object.put("result", "true");
        return object;

    }


    @ApiOperation(value = "获取通用带单字位置信息的评测结果", httpMethod = "GET")
    @RequestMapping(value = "/getGeneralWithPositionEvalResult/{idNo}", method = RequestMethod.GET)
    public GeneralWithPostionEvalResult getGeneralWithPositionEvalResult(@PathVariable(value = "idNo") String idNo) {
        GeneralWithPostionEvalResult generalWithPostionEvalResult = template.selectOne(
                "getGeneralWithPositionEvalResult", idNo);
        return generalWithPostionEvalResult;
    }

    @ApiOperation(value = "获取通用带单字位置信息的评测结果列表", httpMethod = "GET")
    @RequestMapping(value = "/getGeneralWithPositionEvalResultList", method = RequestMethod.GET)
    public List<GeneralWithPostionEvalResult> getGeneralWithPositionEvalResultList(
            @RequestParam int start, @RequestParam int end) {
        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        return template.selectList("getGeneralWithPositionEvalResultList", map);
    }


    @ApiOperation(value = "获取通用无位置信息的评测结果", httpMethod = "GET")
    @RequestMapping(value = "/getGeneralNoPositionEvalResult/{idNo}", method = RequestMethod.GET)
    public GeneralNoPositionEvalResult getGeneralNoPositionEvalResult(@PathVariable(value = "idNo") String idNo) {
        GeneralNoPositionEvalResult generalNoPostionEvalResult = template.selectOne(
                "getGeneralNoPositionEvalResult", idNo);
        return generalNoPostionEvalResult;
    }

    @ApiOperation(value = "获取通用无位置信息的评测结果列表", httpMethod = "GET")
    @RequestMapping(value = "/getGeneralNoPositionEvalResultList", method = RequestMethod.GET)
    public List<GeneralNoPositionEvalResult> getGeneralNoPositionEvalResultList(
            @RequestParam int start, @RequestParam int end) {
        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        return template.selectList("getGeneralNoPositionEvalResultList", map);
    }
}
