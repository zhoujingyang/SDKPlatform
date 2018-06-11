package com.baidu.ocrqa.sdk.config;


import com.baidu.ocrqa.sdk.model.config.*;
import com.baidu.ocrqa.sdk.model.evalCategory.IDCardBack;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@Configuration
@EnableTransactionManagement

public class FileConfig implements EnvironmentAware {
    private RelaxedPropertyResolver pathPropertyResolver;

    /**
     * 评测相关配置
     */
    private RelaxedPropertyResolver evalPropertyResolver;

    /**
     * 图片下载配置
     */
    private RelaxedPropertyResolver imgPropertyResolver;

    @Bean(name = "fileBean")
    public FileBean fileBeanFactory() throws IOException {
        FileBean bean = new FileBean();
        bean.setFilePath(pathPropertyResolver.getProperty("path"));
        bean.setProjectFileSavePath(pathPropertyResolver.getProperty("project-file-save-path"));
        bean.setProjectDownloadPath(pathPropertyResolver.getProperty("project-download-path"));
        bean.setProjectAbsolutePath(pathPropertyResolver.getProperty("project-absolute-path"));
        return bean;
    }

    @Bean(name = "generalNoPositionBean")
    public GeneralNoPositionBean generalNoPositionBeanFactory() {
        GeneralNoPositionBean bean = new GeneralNoPositionBean();
        bean.setScript(evalPropertyResolver.getProperty("general.no-position.script"));
        bean.setGtFengChao(evalPropertyResolver.getProperty("general.no-position.gt.fengchao"));
        bean.setGtYinShua(evalPropertyResolver.getProperty("general.no-position.gt.yinshua"));
        bean.setGtTiMu(evalPropertyResolver.getProperty("general.no-position.gt.timu"));
        bean.setGtMix2923General(evalPropertyResolver.getProperty("general.no-position.gt.mix2923general"));
        bean.setGtKuaiShou(evalPropertyResolver.getProperty("general.no-position.gt.kuaishou"));

        return bean;
    }


    @Bean(name = "generalWithPositionBean")
    public GeneralWithPositionBean generalWithPositionBeanFactory() {
        GeneralWithPositionBean bean = new GeneralWithPositionBean();
        bean.setScript(evalPropertyResolver.getProperty("general.with-position.script"));
        bean.setGtFengChao(evalPropertyResolver.getProperty("general.with-position.gt.fengchao"));
        bean.setGtYinShua(evalPropertyResolver.getProperty("general.with-position.gt.yinshua"));
        bean.setGtTiMu(evalPropertyResolver.getProperty("general.with-position.gt.timu"));
        bean.setGtMix2923General(evalPropertyResolver.getProperty("general.with-position.gt.mix2923general"));
        bean.setGtKuaiShou(evalPropertyResolver.getProperty("general.with-position.gt.kuaishou"));

        return bean;
    }


    @Bean(name = "iDCardFrontBean")
    public IDCardFrontBean iDCardFrontBeanFactory() {
        IDCardFrontBean bean = new IDCardFrontBean();
        bean.setScript(evalPropertyResolver.getProperty("idcard.front.script"));
        bean.setGtQianBaoIDCardFront(evalPropertyResolver.getProperty("idcard.front.gt.qianbao-idcard-front"));
        bean.setGtQianBaoIDCardFrontUpdate(evalPropertyResolver
                .getProperty("idcard.front.gt.qianbao-idcard-front-update"));
        return bean;
    }


    @Bean(name = "iDCardBackBean")
    public IDCardBackBean iDCardBackBeanFactory() {
        IDCardBackBean bean = new IDCardBackBean();
        bean.setScript(evalPropertyResolver.getProperty("idcard.back.script"));
        bean.setGtQianBaoIDCardBack(evalPropertyResolver.getProperty("idcard.back.gt.qianbao-idcard-back"));
        return bean;
    }

    @Bean(name = "imageBean")
    public ImageBean imageBeanFactory() {
        ImageBean bean = new ImageBean();
        bean.setEvalGeneralNoPosition(imgPropertyResolver.getProperty("path.eval.general.noPosition"));
        bean.setEvalGeneralWithPosition(imgPropertyResolver.getProperty("path.eval.general.withPosition"));
        bean.setEvalIdCardBack(imgPropertyResolver.getProperty("path.eval.idcard.back"));
        bean.setEvalIdCardFront(imgPropertyResolver.getProperty("path.eval.idcard.front"));
        bean.setFunctionGeneral(imgPropertyResolver.getProperty("path.function.general"));
        bean.setFunctionIdCardBack(imgPropertyResolver.getProperty("path.function.idcard.back"));
        bean.setFunctionIdCardFront(imgPropertyResolver.getProperty("path.function.idcard.front"));
        bean.setPerformanceGeneral(imgPropertyResolver.getProperty("path.performance.general"));
        bean.setPerformanceIdCardBack(imgPropertyResolver.getProperty("path.performance.idcard.back"));
        bean.setPerformanceIdCardFront(imgPropertyResolver.getProperty("path.performance.idcard.front"));
        bean.setStabilityGeneral(imgPropertyResolver.getProperty("path.stability.general"));
        bean.setStabilityIdCardBack(imgPropertyResolver.getProperty("path.stability.idcard.back"));
        bean.setStabilityIdCardFront(imgPropertyResolver.getProperty("path.stability.idcard.front"));
        bean.setHost(imgPropertyResolver.getProperty("host"));
        return bean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        pathPropertyResolver = new RelaxedPropertyResolver(environment, "file.");
        evalPropertyResolver = new RelaxedPropertyResolver(environment, "eval.");
        imgPropertyResolver = new RelaxedPropertyResolver(environment, "image.");
    }
}
