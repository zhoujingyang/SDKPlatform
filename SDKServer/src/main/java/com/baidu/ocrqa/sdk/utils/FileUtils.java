package com.baidu.ocrqa.sdk.utils;

import com.baidu.ocrqa.sdk.model.config.FileBean;
import com.baidu.ocrqa.sdk.model.param.BasicRecognitionResult;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Log4j
public class FileUtils {


    /**
     * @param t
     * @param category
     * @param <T>
     * @return String[写入文件夹中的图片数量, 保存评测结果的文件夹绝对路径]
     * @throws IOException
     */
    public static <T extends BasicRecognitionResult> String[] createDirAndWriteFile(T t, String category)
            throws IOException {


        //获取文件夹名称
        String dirName = t.getIdNo() + "/" + t.getEvalName();

        //获取文件名称
        String fileName = t.getFileName();

        //获取存储路径
        String savePath = FileBean.getFilePath();

        //创建文件夹
        File saveFileDir = new File(savePath + "/" + category + "/" + dirName);

        File recognitionResultFile = createDirAndFile(fileName, savePath, saveFileDir);

        Object result = null;
        String[] imgNumAndSaveDir = new String[]{};
        //获取识别结果，就是要存储的内容
        if (category.equals("generalNoPosition") || category.equals("generalWithPosition")) {
            result = t.getResult();
            imgNumAndSaveDir = generalWriteFiles(t, category, saveFileDir, recognitionResultFile, result);
        } else if (category.equals("idCardFront")) {
            result = t.getResult();
            imgNumAndSaveDir = idCardwriteFiles((Map) result, saveFileDir, recognitionResultFile);
        } else if (category.equals("idCardBack")) {
            result = t.getResult();
            imgNumAndSaveDir = idCardwriteFiles((Map) result, saveFileDir, recognitionResultFile);
        }


        return imgNumAndSaveDir;
    }

    private static <T extends BasicRecognitionResult> String[] generalWriteFiles(
            T t, String category, File saveFileDir, File recognitionResultFile, Object result)
            throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(recognitionResultFile));
        log.info("开始写入文件内容" + result.toString());

        writer.write(result.toString());
        writer.flush();
        writer.close();
        log.info("文件内容写入完成,输入流已关闭");

        int dirImgNum = saveFileDir.listFiles().length;

        log.info(category + "方向,评测ID" + t.getIdNo() + ",已写入文件数量:" + dirImgNum);

        return new String[]{String.valueOf(dirImgNum), saveFileDir.getAbsolutePath()};
    }


    public static File createDirAndFile(String fileName, String savePath, File saveFileDir) throws IOException {
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
            log.info("文件夹不存在,创建文件夹" + savePath);
        }

        //创建文件夹及文件
        File recognitionResultFile = new File(saveFileDir + "/" + fileName);
        if (!recognitionResultFile.exists()) {
            recognitionResultFile.createNewFile();
            log.info("文件不存在,创建文件" + recognitionResultFile.toString());

        }
        return recognitionResultFile;
    }


    public static String[] idCardwriteFiles(
            Map result, File saveFileDir, File recognitionResultFile)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(recognitionResultFile));
        log.info("开始写入文件内容" + result);

        StringBuilder recognitionResult = new StringBuilder();
        Set resultKeySet = result.keySet();
        Iterator it = resultKeySet.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = (String) result.get(key);
            recognitionResult.append(key + "\t" + value + System.getProperty("line.separator"));
        }

        writer.write(recognitionResult.toString());
        writer.flush();
        writer.close();
        log.info("idCard方向 文件内容写入完成,输入流已关闭");

        int dirImgNum = saveFileDir.listFiles().length;

        return new String[]{String.valueOf(dirImgNum), saveFileDir.getAbsolutePath()};
    }


    public static void zipFiles(File[] srcfile, File zipfile) {

        byte[] buf = new byte[1024];
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                if (Objects.nonNull(srcfile)) {
                    if (Objects.nonNull(srcfile[i])) {
                        FileInputStream in = new FileInputStream(srcfile[i]);
                        out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        out.closeEntry();
                        in.close();
                    }
                }
            }
            out.close();
            log.info(zipfile + "文件压缩完成");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
