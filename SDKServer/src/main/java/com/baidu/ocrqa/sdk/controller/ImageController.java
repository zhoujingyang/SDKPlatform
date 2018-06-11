package com.baidu.ocrqa.sdk.controller;

import com.baidu.ocrqa.sdk.model.config.ImageBean;
import com.baidu.ocrqa.sdk.model.image.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Log4j
@RestController
@Api(value = "/ocr/sdk/ImageBean", description = "OCR SDK 图片操作相关接口")
@RequestMapping("/ocr/sdk/ImageBean")
public class ImageController {

    @RequestMapping(value = "/Download/{packageName}", method = RequestMethod.GET)
    public void Download(HttpServletResponse res, @PathVariable(name = "packageName") String packageName) {
        String fileName = "cpu.png";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("/Users/zhoujingyang01/Desktop/"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }

    @ApiOperation(value = "获取所有图片和压缩包列表", httpMethod = "GET")
    @RequestMapping(value = "/getAllImageList", method = RequestMethod.GET)
    public ImageUrl getAllImageList() {

        String host = ImageBean.getHost();

        ImageUrl imageUrl = new ImageUrl();

        Eval eval = new Eval();
        EvalIfPosition evalIfPosition = new EvalIfPosition();
        EvalIdCard evalIdCard = new EvalIdCard();
        evalIdCard.setFront(host + ImageBean.getEvalIdCardFront());
        evalIdCard.setBack(host + ImageBean.getEvalIdCardBack());
        eval.setEvalIdCard(evalIdCard);

        evalIfPosition.setNoPosition(host + ImageBean.getEvalGeneralNoPosition());
        evalIfPosition.setWithPosition(host + ImageBean.getEvalGeneralWithPosition());
        eval.setEvalIfPosition(evalIfPosition);


        Function function = new Function();
        FunctionIdCard functionIdCard = new FunctionIdCard();
        functionIdCard.setBack(host + ImageBean.getFunctionIdCardBack());
        functionIdCard.setFront(host + ImageBean.getFunctionIdCardFront());
        function.setFunctionIdCard(functionIdCard);
        function.setGeneral(host + ImageBean.getFunctionGeneral());

        Performance performance = new Performance();
        PerformanceIdCard performanceIdCard = new PerformanceIdCard();
        performanceIdCard.setFront(host + ImageBean.getPerformanceIdCardFront());
        performanceIdCard.setBack(host + ImageBean.getPerformanceIdCardBack());
        performance.setPerformanceIdCard(performanceIdCard);
        performance.setGeneral(host + ImageBean.getPerformanceGeneral());


        Stability stability = new Stability();
        StabilityIdCard stabilityIdCard = new StabilityIdCard();
        stabilityIdCard.setBack(host + ImageBean.getStabilityIdCardBack());
        stabilityIdCard.setFront(host + ImageBean.getStabilityIdCardFront());
        stability.setGeneral(host + ImageBean.getStabilityGeneral());
        stability.setStabilityIdCard(stabilityIdCard);

        imageUrl.setEval(eval);
        imageUrl.setFunction(function);
        imageUrl.setPerformance(performance);
        imageUrl.setStability(stability);


        return imageUrl;
    }
}


