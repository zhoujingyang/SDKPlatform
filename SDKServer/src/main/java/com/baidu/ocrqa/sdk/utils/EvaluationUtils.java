package com.baidu.ocrqa.sdk.utils;

import com.baidu.ocrqa.sdk.model.config.GeneralNoPositionBean;
import com.baidu.ocrqa.sdk.model.config.GeneralWithPositionBean;
import com.baidu.ocrqa.sdk.model.config.IDCardBackBean;
import com.baidu.ocrqa.sdk.model.config.IDCardFrontBean;
import com.baidu.ocrqa.sdk.model.evalCategory.GeneralNoPosition;
import com.baidu.ocrqa.sdk.model.evalCategory.GeneralWithPosition;
import com.baidu.ocrqa.sdk.model.evalCategory.IDCardBack;
import com.baidu.ocrqa.sdk.model.evalCategory.IDCardFront;
import com.baidu.ocrqa.sdk.model.mysql.general.GeneralNoPositionEvalResult;
import com.baidu.ocrqa.sdk.model.mysql.general.GeneralWithPostionEvalResult;
import com.baidu.ocrqa.sdk.model.mysql.idcard.IDCardBackEvalResult;
import com.baidu.ocrqa.sdk.model.mysql.idcard.IDCardFrontEvalResult;
import com.baidu.ocrqa.sdk.model.param.CardBasicRecognitionResult;
import com.baidu.ocrqa.sdk.model.param.GeneralBasicRecognitionResult;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.util.*;

@Log4j
public class EvaluationUtils {


    /**
     * 通用OCR执行评测
     *
     * @param generalRecognitionResult
     * @param saveFileDir
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static GeneralNoPositionEvalResult generalOcrNoPositionEval(
            GeneralBasicRecognitionResult generalRecognitionResult, String saveFileDir)
            throws IOException, InterruptedException {


        String script = GeneralNoPositionBean.getScript();

        String gt = "";

        if (generalRecognitionResult.getEvalName().equals(GeneralNoPosition.FENGCHAO)) {
            gt = GeneralNoPositionBean.getGtFengChao();
        } else if (generalRecognitionResult.getEvalName().equals(GeneralNoPosition.YINSHUA)) {
            gt = GeneralNoPositionBean.getGtYinShua();
        } else if (generalRecognitionResult.getEvalName().equals(GeneralNoPosition.TIMU)) {
            gt = GeneralNoPositionBean.getGtTiMu();
        } else if (generalRecognitionResult.getEvalName().equals(GeneralNoPosition.MIX2923GENERAL)) {
            gt = GeneralNoPositionBean.getGtMix2923General();
        } else if (generalRecognitionResult.getEvalName().equals(GeneralNoPosition.KUAISHOU)) {
            gt = GeneralNoPositionBean.getGtKuaiShou();
        } else {
            log.info(generalRecognitionResult.getIdNo() + "评测集名称填写错误");
            throw new RuntimeException(generalRecognitionResult.getIdNo() + "evalName Error");
        }


        String evalFile = saveFileDir + "/eval.txt";
        String detailFile = saveFileDir + "/detail.txt";


        String[] shellAndParam = new String[]{"python", script, gt, saveFileDir, evalFile, detailFile};

        Process ps = Runtime.getRuntime().exec(shellAndParam);
        ps.waitFor();

        //获取shell的执行结果
        String result = getShellExecResult(ps);
        GeneralNoPositionEvalResult general = new GeneralNoPositionEvalResult();
        if (result.equals("")) {
            /**
             * 拿到shell执行的结果，并解析到general的model中
             */
            String lastLine = getFileLastLine(evalFile);

            String[] evalResult = lastLine.split("  ");
            String err = evalResult[0].split(":")[1].split("\\[")[0];
            log.info("err : " + err);
            String subErr = evalResult[1].split(":")[1];
            log.info("subErr : " + subErr);
            String insertErr = evalResult[2].split(":")[1];
            log.info("insertErr : " + insertErr);
            String delErr = evalResult[3].split(":")[1];
            log.info("delErr : " + delErr);
            String lineErr = evalResult[4].split(":")[1].split("\\[")[0];
            log.info("lineErr : " + lineErr);

            general.setErr(err);
            general.setSubErr(subErr);
            general.setInsertErr(insertErr);
            general.setDelErr(delErr);
            general.setLineErr(lineErr);

        }
        return general;
    }


    public static GeneralWithPostionEvalResult generalOCRWithPositionEval(
            GeneralBasicRecognitionResult generalBasicRecognitionResult, String saveFileDir)
            throws IOException, InterruptedException {

        GeneralWithPostionEvalResult generalWithPostionEvalResult = new GeneralWithPostionEvalResult();
        String script = GeneralWithPositionBean.getScript();
        String gt = "";

        if (generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.FENGCHAO)) {
            gt = GeneralWithPositionBean.getGtFengChao();
        } else if (generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.YINSHUA)) {
            gt = GeneralWithPositionBean.getGtYinShua();
        } else if (generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.TIMU)) {
            gt = GeneralWithPositionBean.getGtTiMu();
        } else if (generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.MIX2923GENERAL)) {
            gt = GeneralWithPositionBean.getGtMix2923General();
        } else if (generalBasicRecognitionResult.getEvalName().equals(GeneralWithPosition.KUAISHOU)) {
            gt = GeneralWithPositionBean.getGtKuaiShou();
        } else {
            log.info(generalBasicRecognitionResult.getIdNo() + "评测集名称填写错误");
            throw new RuntimeException(generalBasicRecognitionResult.getIdNo() + "evalName Error");
        }

        String[] shellAndParam = new String[]{script, "0", "1", gt, saveFileDir, "result"};


        Process ps = Runtime.getRuntime().exec(shellAndParam);
        ps.waitFor();

        String result = getShellExecResult(ps);

        String evalFilePath = saveFileDir + "/evalResult" + generalBasicRecognitionResult.getIdNo() + ".txt";
        writeToFile(evalFilePath, result);

        if (Objects.nonNull(result)) {
            BufferedReader reader = new BufferedReader(new FileReader(evalFilePath));
            String lineResult;
            while ((lineResult = reader.readLine()) != null) {
                String[] evalString = lineResult.split(" ");
                if (lineResult.contains("char_level") && lineResult.contains("recall")) {
                    generalWithPostionEvalResult.setCharLevelRecall(evalString[2]);
                } else if (lineResult.equals("char_level") && lineResult.contains("precision")) {
                    generalWithPostionEvalResult.setCharLevelPrecision(evalString[2]);
                } else if (lineResult.contains("image_level") && lineResult.contains("recall")) {
                    generalWithPostionEvalResult.setImageLevelRecall(evalString[2]);
                } else if (lineResult.equals("image_level") && lineResult.contains("precision")) {
                    generalWithPostionEvalResult.setImageLevelPrecision(evalString[2]);
                }
            }
        }


        return generalWithPostionEvalResult;
    }


    /**
     * 获取shell执行的结果
     *
     * @param ps
     * @return
     * @throws IOException
     */
    private static String getShellExecResult(Process ps) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        //执行结果
        String result = sb.toString();
        log.info("shell执行结果为:" + result);
        return result;
    }

    /**
     * 获取文件中的最后一行
     *
     * @param detailFile
     * @return
     * @throws IOException
     */
    private static String getFileLastLine(String detailFile) throws IOException {
        File file = new File(detailFile);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineTxt = null;
        String lastLine = null;
        while ((lineTxt = reader.readLine()) != null) {
            lastLine = lineTxt;
        }
        System.out.println(lastLine);
        return lastLine;
    }


    /**
     * 执行身份证正面评测脚本
     *
     * @param cardRecognitionResult
     * @param saveFileDir
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static IDCardFrontEvalResult idCardFrontEval(
            CardBasicRecognitionResult cardRecognitionResult, String saveFileDir)
            throws IOException, InterruptedException {
        IDCardFrontEvalResult idCardFrontEvalResult = new IDCardFrontEvalResult();


        String script = IDCardFrontBean.getScript();
        String gt = "";


        if (cardRecognitionResult.getEvalName().equals(IDCardFront.QIANBAOIDFRONT)) {
            gt = IDCardFrontBean.getGtQianBaoIDCardFront();
        } else if (cardRecognitionResult.getEvalName().equals(IDCardFront.QIANBAOIDFRONTUPDATE)) {
            gt = IDCardFrontBean.getGtQianBaoIDCardFrontUpdate();
        } else {
            log.info(cardRecognitionResult.getIdNo() + "评测集名称填写错误");
            throw new RuntimeException(cardRecognitionResult.getIdNo() + "评测集名称填写错误");
        }


        String idCardEvalResultFile = saveFileDir + "/evalResult" + cardRecognitionResult.getIdNo() + ".txt";
        /**
         * python idcard_evaluator.py --format kv result gt
         */

        String shellAndParam = "python " + script + " --format kv " + saveFileDir + " " + gt;
        log.info(cardRecognitionResult.getIdNo() + " idCardFront 开始评测");
        Process ps = Runtime.getRuntime().exec(shellAndParam);
        String shellResult = getShellExecResult(ps);

        writeToFile(idCardEvalResultFile, shellResult);

        log.info("执行身份证正面评测脚本:"
                + "python "
                + script
                + " --format kv "
                + saveFileDir
                + "   "
                + gt
                + " > "
                + idCardEvalResultFile);
        ps.waitFor();

        String lineResult = null;

        File resultFile = new File(idCardEvalResultFile);
        BufferedReader reader = new BufferedReader(new FileReader(resultFile));
        while ((lineResult = reader.readLine()) != null) {
            if (lineResult.contains("出生")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("出生字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {

                    setIDCardFrontEvalResult("出生", idCardFrontEvalResult, recallAndAcc);
                }
            } else if (lineResult.contains("姓名")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("姓名字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {
                    setIDCardFrontEvalResult("姓名", idCardFrontEvalResult, recallAndAcc);
                }
            } else if (lineResult.contains("公民身份号码")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("公民身份号码字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {
                    setIDCardFrontEvalResult("公民身份号码", idCardFrontEvalResult, recallAndAcc);
                }
            } else if (lineResult.contains("性别")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("性别字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {
                    setIDCardFrontEvalResult("性别", idCardFrontEvalResult, recallAndAcc);
                }
            } else if (lineResult.contains("民族")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("民族字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {
                    setIDCardFrontEvalResult("民族", idCardFrontEvalResult, recallAndAcc);
                }
            } else if (lineResult.contains("住址")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("住址字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {
                    setIDCardFrontEvalResult("住址", idCardFrontEvalResult, recallAndAcc);
                }
            }
        }

        return idCardFrontEvalResult;
    }




    /**
     * 执行身份证反面评测脚本
     *
     * @param cardRecognitionResult
     * @param saveFileDir
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static IDCardBackEvalResult idCardBackEval(
            CardBasicRecognitionResult cardRecognitionResult, String saveFileDir)
            throws IOException, InterruptedException {
        IDCardBackEvalResult idCardBackEvalResult = new IDCardBackEvalResult();


        String script = IDCardBackBean.getScript();
        String gt = "";


        if (cardRecognitionResult.getEvalName().equals(IDCardBack.QIANBAOIDBACK)) {
            gt = IDCardBackBean.getGtQianBaoIDCardBack();
        } else {
            log.info(cardRecognitionResult.getIdNo() + "评测集名称填写错误");
            throw new RuntimeException(cardRecognitionResult.getIdNo() + "评测集名称填写错误");
        }


        String idCardEvalResultFile = saveFileDir + "/evalResult" + cardRecognitionResult.getIdNo() + ".txt";
        /**
         * python idcard_evaluator.py --format kv result gt
         */

        String shellAndParam = "python " + script + " --format kv " + saveFileDir + " " + gt;
        log.info(cardRecognitionResult.getIdNo() + " idCardFront 开始评测");
        Process ps = Runtime.getRuntime().exec(shellAndParam);
        String shellResult = getShellExecResult(ps);

        writeToFile(idCardEvalResultFile, shellResult);

        log.info("执行身份证正面评测脚本:"
                + "python "
                + script
                + " --format kv "
                + saveFileDir
                + "   "
                + gt
                + " > "
                + idCardEvalResultFile);
        ps.waitFor();

        String lineResult = null;

        File resultFile = new File(idCardEvalResultFile);
        BufferedReader reader = new BufferedReader(new FileReader(resultFile));
        while ((lineResult = reader.readLine()) != null) {
            if (lineResult.contains("签发日期")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("签发日期字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {

                    setIDCardBackEvalResult("签发日期", idCardBackEvalResult, recallAndAcc);
                }
            } else if (lineResult.contains("签发机关")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("签发机关字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {
                    setIDCardBackEvalResult("签发机关", idCardBackEvalResult, recallAndAcc);
                }
            } else if (lineResult.contains("失效日期")) {
                Map recallAndAcc = getIDCardRecallAndAccuracy(lineResult);
                if (recallAndAcc.isEmpty()) {
                    log.info("失效日期字段读取失败，在结果文件中没有读取到准确率和召回率");
                } else {
                    setIDCardBackEvalResult("失效日期", idCardBackEvalResult, recallAndAcc);
                }
            }
        }

        return idCardBackEvalResult;
    }

    private static void setIDCardBackEvalResult(
            String keyName, IDCardBackEvalResult idCardBackEvalResult, Map recallAndAcc) {

        Set keySet = recallAndAcc.keySet();
        Iterator it = keySet.iterator();
        String key = (String) it.next();
        String value = (String) recallAndAcc.get(key);
        if (keyName.equals("签发日期")) {
            idCardBackEvalResult.setIssuingDateRecall(key);
            idCardBackEvalResult.setIssuingDateAccuracy(value);
            log.info("签发日期的准确率:" + idCardBackEvalResult.getIssuingDateAccuracy()
                    + " 签发日期的召回率:" + idCardBackEvalResult.getIssuingDateRecall());
        } else if (keyName.equals("签发机关")) {
            idCardBackEvalResult.setIssuingAuthorityAccuracy(value);
            idCardBackEvalResult.setIssuingAuthorityRecall(key);
            log.info("签发机关的准确率:" + idCardBackEvalResult.getIssuingAuthorityAccuracy()
                    + "  签发机关的召回率:" + idCardBackEvalResult.getIssuingAuthorityRecall());
        } else if (keyName.equals("失效日期")) {
            idCardBackEvalResult.setExpiryDateAccuracy(value);
            idCardBackEvalResult.setExpiryDateRecall(key);
            log.info("失效日期的准确率:" + idCardBackEvalResult.getExpiryDateAccuracy()
                    + "  失效日期的召回率:" + idCardBackEvalResult.getExpiryDateRecall());
        }

    }


    private static void writeToFile(String filepath, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    /**
     * 根据不同的字段值，设置IDCardFrontEvalResult对象中的值
     *
     * @param keyName
     * @param idCardFrontEvalResult
     * @param recallAndAcc
     */
    private static void setIDCardFrontEvalResult(String keyName,
                                                 IDCardFrontEvalResult idCardFrontEvalResult,
                                                 Map recallAndAcc) {
        Set keySet = recallAndAcc.keySet();
        Iterator it = keySet.iterator();
        String key = (String) it.next();
        String value = (String) recallAndAcc.get(key);
        if (keyName.equals("出生")) {
            idCardFrontEvalResult.setBirthdayRecall(key);
            idCardFrontEvalResult.setBirthdayAccuracy(value);
            log.info("出生的准确率:" + idCardFrontEvalResult.getBirthdayAccuracy()
                    + " 出生的召回率:" + idCardFrontEvalResult.getBirthdayRecall());
        } else if (keyName.equals("姓名")) {
            idCardFrontEvalResult.setNameAccuracy(value);
            idCardFrontEvalResult.setNameRecall(key);
            log.info("姓名的准确率:" + idCardFrontEvalResult.getNameAccuracy()
                    + "  姓名的召回率:" + idCardFrontEvalResult.getNameRecall());
        } else if (keyName.equals("公民身份号码")) {
            idCardFrontEvalResult.setIdNoAccuracy(value);
            idCardFrontEvalResult.setIdNoRecall(key);
            log.info("公民身份号码的准确率:" + idCardFrontEvalResult.getIdNoAccuracy()
                    + "  公民身份号码的召回率:" + idCardFrontEvalResult.getIdNoRecall());
        } else if (keyName.equals("性别")) {
            idCardFrontEvalResult.setSexAccuracy(value);
            idCardFrontEvalResult.setSexRecall(key);
            log.info("性别的准确率:" + idCardFrontEvalResult.getSexAccuracy()
                    + "  性别的召回率:" + idCardFrontEvalResult.getSexRecall());
        } else if (keyName.equals("民族")) {
            idCardFrontEvalResult.setNationAccuracy(value);
            idCardFrontEvalResult.setNationRecall(key);
            log.info("民族的准确率:" + idCardFrontEvalResult.getNationAccuracy()
                    + "  民族的召回率:" + idCardFrontEvalResult.getNationRecall());
        } else if (keyName.equals("住址")) {
            idCardFrontEvalResult.setAddressAccuracy(value);
            idCardFrontEvalResult.setAddressRecall(key);
            log.info("住址的准确率:" + idCardFrontEvalResult.getAddressAccuracy()
                    + "  住址的召回率:" + idCardFrontEvalResult.getAddressRecall());
        }
    }


    /**
     * 身份证正面评测后，从结果文件读取的行信息中获取到准确率和召回率
     *
     * @param lineResult
     * @return Map key=召回率, value=准确率
     */
    private static Map<String, String> getIDCardRecallAndAccuracy(String lineResult) {
        Map<String, String> map = new HashMap();
        String s[] = lineResult.split("\t");
        String key = null;
        String value = null;
        for (int i = 0; i < s.length; i++) {

            if (i == 3) {
                key = s[i];//匹配召回率
            }
            if (i == 4) {
                value = s[i];//匹配准确率
                map.put(key, value);
            }
        }


        return map;
    }

}
