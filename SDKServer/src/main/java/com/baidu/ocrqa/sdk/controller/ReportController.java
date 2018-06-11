package com.baidu.ocrqa.sdk.controller;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ocrqa.sdk.model.mysql.AppReportModel;
import com.baidu.ocrqa.sdk.model.param.AppReportResult;
import com.baidu.ocrqa.sdk.utils.DatabaseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "/ocr/sdk/report", description = "OCR SDK 报告相关接口")
@RequestMapping("/ocr/sdk/report")
public class ReportController {

    @Autowired
    SqlSessionTemplate template;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传性能测试监控报告数据", httpMethod = "POST")
//    public JSONObject upload(MultipartFile file, @RequestBody AppReportResult appReportResult) {
    public JSONObject upload(MultipartFile file, @RequestParam String dataCategoryParam,
                             @RequestParam String idNo, @RequestParam String version) {
        JSONObject object = new JSONObject();
        if (Objects.isNull(file)) {
            object.put("result", "false");
            object.put("detail", "file is null , upload failed");
            return object;
        }

        AppReportResult appReportResult = new AppReportResult();
        appReportResult.setDataCategory(dataCategoryParam);
        appReportResult.setIdNo(idNo);
        appReportResult.setVersion(version);


        String fileName = file.getOriginalFilename();

        log.info("上传的文件名为：" + fileName);
        /**
         * 数据分类，目前只有cpu和mem两种
         */
        String dataCategory;
        /**
         * 判断数据分类
         */
        if (appReportResult.getDataCategory().equals("cpu")) {
            dataCategory = "cpu";
            log.info("上传的文件是cpu信息,上传的文件名为：" + fileName);
        } else if (appReportResult.getDataCategory().equals("mem")) {
            dataCategory = "mem";
            log.info("上传的文件是mem信息,上传的文件名为：" + fileName);
        } else {
            object.put("result", "false");
            object.put("detail", "dataCategory填写错误，只能是cpu或mem");
            return object;
        }

        if (file.isEmpty()) {
            object.put("result", "false");
            object.put("detail", "file is empty! fileName is " + fileName);
            log.info("上传的文件是空的,文件名是" + fileName + " idNo是:" + appReportResult.getIdNo());
            return object;
        }


        /**
         * 读取出文件内容
         */
        StringBuilder fileContent = new StringBuilder();
        try {
            InputStream in = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            in.close();
            System.out.println(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }


        AppReportModel appReportModel = new AppReportModel();
        if (dataCategory.equals("cpu")) {
            appReportModel.setCpu(fileContent.toString());
        } else if (dataCategory.equals("mem")) {
            appReportModel.setMem(fileContent.toString());
        }


        /**
         * 判断数据库中是否已经存在这条数据
         */
        int count = template.selectOne("getAppReportCount", appReportResult.getIdNo());
        if (count <= 0) {
            if (dataCategory.equals("cpu")) {
                DatabaseUtils.saveToMysql("appReportCpuInsert", appReportModel, template, appReportResult);
                log.info(appReportResult.getIdNo() + "插入cpu数据");
            } else if (dataCategory.equals("mem")) {
                DatabaseUtils.saveToMysql("appReportMemInsert", appReportModel, template, appReportResult);
                log.info(appReportResult.getIdNo() + "插入mem数据");

            }

        } else {
            DatabaseUtils.saveToMysql("updateAppReport", appReportModel, template, appReportResult);
        }


        object.put("result", "true");
        return object;
    }


    @ApiOperation(value = "获取性能测试监控结果", httpMethod = "GET")
    @RequestMapping(value = "/get/performance/result/{idNo}", method = RequestMethod.GET)
    public AppReportModel getPerformanceResult(@PathVariable(value = "idNo") String idNo) {

        return template.selectOne("getAppReportResult", idNo);
    }

    @ApiOperation(value = "获取性能测试监控结果列表", httpMethod = "GET")
    @RequestMapping(value = "/get/performance/result/list", method = RequestMethod.GET)
    public List<AppReportModel> getAllPerformanceResult(
            @RequestParam int start, @RequestParam int end, @RequestParam String target) {
        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        map.put("target", target);
        return template.selectList("getAppReportResultList", map);

    }

}
