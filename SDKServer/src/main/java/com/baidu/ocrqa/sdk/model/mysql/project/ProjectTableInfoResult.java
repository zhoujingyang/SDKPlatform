package com.baidu.ocrqa.sdk.model.mysql.project;

import lombok.Data;

@Data
public class ProjectTableInfoResult {

    public int id;
    public int taskID;
    public String projectType;
    public String saveDir;
    public String model;
    public String libs;
    public String md5;
    public String demo;
    public String license;
    public String so;
    public String jar;
    public String projectName;
    public String projectDesc;
    public String projectVersion;
    public String projectBusiness;
    public String createTime;

}
