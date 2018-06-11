package com.baidu.ocrqa.sdk.model.config;

public class IDCardBackBean {

    private static String script;
    private static String gtQianBaoIDCardBack;

    public static String getGtQianBaoIDCardBack() {
        return gtQianBaoIDCardBack;
    }

    public static void setGtQianBaoIDCardBack(String gtQianBaoIDCardBack) {
        IDCardBackBean.gtQianBaoIDCardBack = gtQianBaoIDCardBack;
    }

    public static String getScript() {
        return script;
    }

    public static void setScript(String script) {
        IDCardBackBean.script = script;
    }
}
