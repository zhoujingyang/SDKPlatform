package com.baidu.ocrqa.sdk.model.param;

import lombok.Data;


@Data
public class BasicRecognitionResult extends ParamBasicResult {


    /**
     * 文件名称，传进来的是图片完整名称，返回时要加一个.txt
     */
    public String fileName;

    /**
     * 评测集图片数量
     */
    public String evalImgNum;

    /**
     * 评测集名称
     */
    public String evalName;


    /**
     * 识别结果
     */
    public Object result;


    /**
     * 获取文件的名称，拼接之后的
     *
     * @return
     */
    public String getFileName() {

        return fileName + ".txt";
    }

}
