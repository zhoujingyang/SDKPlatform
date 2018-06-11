package com.baidu.ocrqa.sdk.utils;

import com.baidu.ocrqa.sdk.model.mysql.BasicResult;
import com.baidu.ocrqa.sdk.model.param.BasicRecognitionResult;
import com.baidu.ocrqa.sdk.model.param.ParamBasicResult;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;

@Log4j
public class DatabaseUtils {


    /**
     * 数据入库方法
     *
     * @param sqlId
     * @param model
     * @param
     */
    public static <M extends BasicResult, R extends ParamBasicResult> void saveToMysql(String sqlId,
                                                                                       M model,
                                                                                       SqlSessionTemplate template,
                                                                                       R r) {

        model.setVersion(r.getVersion());
        model.setTime(TimeUtils.getNowTime());
        model.setIdNo(r.getIdNo());

        int count = template.insert(sqlId, model);
        log.info("插入数据 : " + count + "条");
        log.info(model.toString());

    }

}
