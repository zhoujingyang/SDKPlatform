package com.baidu.ocrqa.sdk.model.param.project;

import lombok.Data;

@Data
public class ProjectInfoModel {


    /**
     * 是否上传过，用来确定是否是一次上传
     */
    public String isUpload;
    /**
     * 文件类型
     */
    public String fileType;
    /**
     *文件保存的路径，第一次上传为空时，新建文件夹，第二次之后上传则带参过来
     * 根据时间生成文件夹
     */
    public String saveDir;
}
