package com.baidu.ocrqa.sdk.controller;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ocrqa.sdk.model.evalCategory.IDCardBack;
import com.baidu.ocrqa.sdk.model.evalCategory.IDCardFront;
import com.baidu.ocrqa.sdk.model.mysql.idcard.IDCardBackEvalResult;
import com.baidu.ocrqa.sdk.model.mysql.idcard.IDCardFrontEvalResult;
import com.baidu.ocrqa.sdk.model.param.CardBasicRecognitionResult;
import com.baidu.ocrqa.sdk.utils.DatabaseUtils;
import com.baidu.ocrqa.sdk.utils.EvaluationUtils;
import com.baidu.ocrqa.sdk.utils.FileUtils;
import com.baidu.ocrqa.sdk.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@RestController
@Api(value = "/ocr/sdk/eval", description = "OCR SDK 身份证评测相关接口")
@RequestMapping("/ocr/sdk/eval")
public class IDCardController {

    @Autowired
    SqlSessionTemplate template;


    @ApiOperation(value = "保存身份证正面识别结果接口,达到文件数量时则触发评测任务", httpMethod = "POST")
    @RequestMapping(value = "/save/idcard/front", method = RequestMethod.POST)
    public JSONObject saveIDCardFrontRecognitionResult(@RequestBody CardBasicRecognitionResult cardRecognitionResult) {
        JSONObject object = new JSONObject();


        if (!cardRecognitionResult.getEvalName().equals(IDCardFront.QIANBAOIDFRONTUPDATE) &&
                !cardRecognitionResult.getEvalName().equals(IDCardFront.QIANBAOIDFRONT)) {
            object.put("result", "false");
            log.error("评测集名称填写错误,填写的是:" + cardRecognitionResult.getEvalName());
            return object;
        }

        /**
         * 执行创建文件夹及写入识别结果到文件中，并且获取目录下的文件数量
         */
        String[] imgNumAndSaveDir = new String[]{};
        try {
            imgNumAndSaveDir = FileUtils.createDirAndWriteFile(cardRecognitionResult, "idCardFront");
        } catch (IOException e) {
            e.printStackTrace();
            object.put("result", "false");
            return object;
        }


        /**
         * 判断是否所有文件都写完了，如果写完了则调用评测脚本
         */
        String imgNum = cardRecognitionResult.getEvalImgNum();

        if (imgNum.equals(imgNumAndSaveDir[0])) {
            log.info("idcard front 开始进行评测");
            IDCardFrontEvalResult idCardFrontEvalResult = null;
            try {
                idCardFrontEvalResult = EvaluationUtils.idCardFrontEval(
                        cardRecognitionResult,
                        imgNumAndSaveDir[1]);
            } catch (IOException e) {
                e.printStackTrace();
                object.put("result", "false");
                return object;
            } catch (InterruptedException e) {
                e.printStackTrace();
                object.put("result", "false");
                return object;
            }


            idCardFrontEvalResult.setTime(TimeUtils.getNowTime());
            DatabaseUtils.saveToMysql(
                    "idCardFrontInsert",
                    idCardFrontEvalResult,
                    template,
                    cardRecognitionResult);
        }


        object.put("result", "true");

        return object;

    }


    @ApiOperation(value = "获取身份证正面的评测结果", httpMethod = "GET")
    @RequestMapping(value = "/get/idcard/front/eval/{idNo}", method = RequestMethod.GET)
    public IDCardFrontEvalResult getIDCardFrontEvalResult(@PathVariable(value = "idNo") String idNo) {

        return template.selectOne("getCardFrontEvalResult", idNo);
    }


    @ApiOperation(value = "获取身份证正面的评测结果列表", httpMethod = "GET")
    @RequestMapping(value = "/get/idcard/front/eval/list", method = RequestMethod.GET)
    public List<IDCardFrontEvalResult> getIDCardFrontEvalResultList(
            @RequestParam int start, @RequestParam int end) {
        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        return template.selectList("getIDCardFrontEvalResultList", map);
    }

    @ApiOperation(value = "保存身份证反面识别接口,当图片数量达到与评测集数量相同时触发评测", httpMethod = "POST")
    @RequestMapping(value = "/save/idcard/back", method = RequestMethod.POST)
    public JSONObject saveIDCardBackRecognitionResult(
            @RequestBody CardBasicRecognitionResult cardBasicRecognitionResult) {
        JSONObject object = new JSONObject();
        if (!cardBasicRecognitionResult.getEvalName().equals(IDCardBack.QIANBAOIDBACK)) {
            object.put("result", "false");
            log.error("评测集名称填写错误,填写的是:" + cardBasicRecognitionResult.getEvalName());
            return object;
        }


        /**
         * 执行创建文件夹及写入识别结果到文件中，并且获取目录下的文件数量
         */
        String[] imgNumAndSaveDir = new String[]{};
        try {
            imgNumAndSaveDir = FileUtils.createDirAndWriteFile(cardBasicRecognitionResult, "idCardBack");
        } catch (IOException e) {
            e.printStackTrace();
            object.put("result", "false");
            return object;
        }


        /**
         * 判断是否所有文件都写完了，如果写完了则调用评测脚本
         */
        String imgNum = cardBasicRecognitionResult.getEvalImgNum();

        if (imgNum.equals(imgNumAndSaveDir[0])) {
            log.info("idcard back 开始进行评测");
            IDCardBackEvalResult idCardBackEvalResult = null;
            try {
                idCardBackEvalResult = EvaluationUtils.idCardBackEval(
                        cardBasicRecognitionResult,
                        imgNumAndSaveDir[1]);
            } catch (IOException e) {
                e.printStackTrace();
                object.put("result", "false");
                return object;
            } catch (InterruptedException e) {
                e.printStackTrace();
                object.put("result", "false");
                return object;
            }


            idCardBackEvalResult.setTime(TimeUtils.getNowTime());
            DatabaseUtils.saveToMysql(
                    "idCardBackInsert", idCardBackEvalResult, template, cardBasicRecognitionResult);
        }


        object.put("result", "true");
        return object;
    }


    @ApiOperation(value = "获取身份证反面的评测结果", httpMethod = "GET")
    @RequestMapping(value = "/get/idcard/back/eval/{idNo}", method = RequestMethod.GET)
    public IDCardBackEvalResult getIDCardBackEvalResult(@PathVariable(value = "idNo") String idNo) {

        return template.selectOne("getCardBackEvalResult", idNo);
    }

    @ApiOperation(value = "获取身份证正面的评测结果列表", httpMethod = "GET")
    @RequestMapping(value = "/get/idcard/back/eval/list", method = RequestMethod.GET)
    public List<IDCardBackEvalResult> getIDCardBackEvalResultList(
            @RequestParam int start, @RequestParam int end) {
        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        return template.selectList("getIDCardBackEvalResultList", map);
    }
}
