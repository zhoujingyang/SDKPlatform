package com.baidu.ocrqa.sdk.model.config;

import lombok.Data;

@Data
public class FileBean {
    private static String filePath;
    private static String projectFileSavePath;
    private static String projectDownloadPath;
    private static String projectAbsolutePath;

    public static String getProjectAbsolutePath() {
        return projectAbsolutePath;
    }

    public static void setProjectAbsolutePath(String projectAbsolutePath) {
        FileBean.projectAbsolutePath = projectAbsolutePath;
    }

    public static String getProjectDownloadPath() {
        return projectDownloadPath;
    }

    public static void setProjectDownloadPath(String projectDownloadPath) {
        FileBean.projectDownloadPath = projectDownloadPath;
    }

    public static String getProjectFileSavePath() {
        return projectFileSavePath;
    }

    public static void setProjectFileSavePath(String projectFileSavePath) {
        FileBean.projectFileSavePath = projectFileSavePath;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String path) {
        FileBean.filePath = path;
    }

}
