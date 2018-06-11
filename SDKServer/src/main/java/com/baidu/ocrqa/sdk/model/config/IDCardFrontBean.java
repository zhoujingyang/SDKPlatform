package com.baidu.ocrqa.sdk.model.config;

public class IDCardFrontBean {

    private static String script;
    private static String gtQianBaoIDCardFront;
    private static String gtQianBaoIDCardFrontUpdate;


    public static String getGtQianBaoIDCardFront() {
        return gtQianBaoIDCardFront;
    }

    public static void setGtQianBaoIDCardFront(String gtQianBaoIDCardFront) {
        IDCardFrontBean.gtQianBaoIDCardFront = gtQianBaoIDCardFront;
    }

    public static String getGtQianBaoIDCardFrontUpdate() {
        return gtQianBaoIDCardFrontUpdate;
    }

    public static void setGtQianBaoIDCardFrontUpdate(String gtQianBaoIDCardFrontUpdate) {
        IDCardFrontBean.gtQianBaoIDCardFrontUpdate = gtQianBaoIDCardFrontUpdate;
    }

    public static String getScript() {
        return script;
    }

    public static void setScript(String script) {
        IDCardFrontBean.script = script;
    }
}
