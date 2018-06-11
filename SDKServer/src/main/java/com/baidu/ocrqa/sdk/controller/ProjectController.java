package com.baidu.ocrqa.sdk.controller;

import com.baidu.ocrqa.sdk.model.config.FileBean;
import com.baidu.ocrqa.sdk.model.config.ImageBean;
import com.baidu.ocrqa.sdk.model.mysql.project.ProjectTableInfoResult;
import com.baidu.ocrqa.sdk.utils.FileUtils;
import com.baidu.ocrqa.sdk.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Log4j
@RestController
@Api(value = "/ocr/sdk/project", description = "OCR SDK 项目提测相关接口")
@RequestMapping("/ocr/sdk/project")
public class ProjectController {

    @Autowired
    SqlSessionTemplate template;

    @ApiOperation(value = "上传提测项目文件", httpMethod = "POST")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map uploadFile(MultipartFile file,
                          @RequestParam String saveDir,
                          @RequestParam String projectType,
                          @RequestParam String fileType,
                          @RequestParam String taskID,
                          @RequestParam String ifEdit) {

        System.out.println("ifEdit ==== " + ifEdit);

        System.out.println("fileType ==== " + fileType);
        System.out.println("saveDir ==== " + saveDir);
        System.out.println("projectType ==== " + projectType);

        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为：" + fileName);
        /**
         * 判断文件夹是否存在。
         */
        String fileDir = "";
        if (saveDir.equals("") || saveDir.equals(null) && taskID.equals(null)) {

            String basicPath = FileBean.getProjectFileSavePath();
            if (projectType.equals("Android")) {
                fileDir = basicPath + "Android";
                saveDir = createDir(fileDir);
                log.info("创建保存文件的文件夹 : " + saveDir);
            } else if (projectType.equals("IOS")) {
                fileDir = basicPath + "IOS";
                saveDir = createDir(fileDir);
                log.info("创建保存文件的文件夹 : " + saveDir);
            } else {
                log.error("项目类别填写错误，只能是Android 或 IOS，大小写需正确");
                throw new RuntimeException("项目类别填写错误，只能是Android 或 IOS，大小写需正确");
            }

            /**
             * 文件信息入库，首先要生成一个taskID
             * 首先获取最后一个taskid ，在这个基础上加1
             * taskid命名为1,2,3,4......
             */
            taskID = template.selectOne("getMaxTaskId");
            log.info("taskID = " + taskID);
            if ("".equals(taskID) || Objects.isNull(taskID)) {
                taskID = "1";
            } else {
                int historyMaxId = Integer.valueOf(taskID);
                int currentMaxId = historyMaxId + 1;
                taskID = String.valueOf(currentMaxId);
            }

            /**
             * 保存信息入库
             */

            insertToDB(saveDir, projectType, fileType, taskID, fileName);
        } else {
            updateToDB(saveDir, projectType, fileType, taskID, fileName);
        }


        Map resultMap = new HashMap();

        try {
            saveFile(saveDir, file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        resultMap.put("saveDir", saveDir);
        resultMap.put("projectType", projectType);
        resultMap.put("taskID", taskID);
        return resultMap;
    }

    private void updateToDB(String saveDir, String projectType, String fileType, String taskID, String fileName) {
        Map updateFileMap = new HashMap();
        updateFileMap.put("taskID", taskID);
        updateFileMap.put("saveDir", saveDir);
        updateFileMap.put("projectType", projectType);
        updateFileMap.put("fileType", fileType);
        updateFileMap.put("fileName", fileName);
        template.update("updateProjectFile", updateFileMap);
        log.info("更新入库信息:" + updateFileMap.toString());
    }

    private void insertToDB(@RequestParam String saveDir, @RequestParam String projectType, @RequestParam String fileType, String taskID, String fileName) {
        Map insertFileMap = new HashMap();
        insertFileMap.put("taskID", taskID);
        insertFileMap.put("saveDir", saveDir);
        insertFileMap.put("projectType", projectType);
        insertFileMap.put("fileType", fileType);
        insertFileMap.put("fileName", fileName);
        template.insert("insertProjectFile", insertFileMap);
        log.info("写入入库信息:" + insertFileMap.toString());
    }

    private void saveFile(String saveDir, MultipartFile file) throws IOException {
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String outFile = saveDir + "/" + file.getOriginalFilename();

        OutputStream out = new FileOutputStream(outFile);
        int len = 0;
        byte[] tempbytes = new byte[2014];
        while ((len = in.read(tempbytes)) != -1) {
            out.write(tempbytes, 0, len);
        }
        out.flush();
        out.close();
    }

    private String createDir(String fileDirString) {
        String saveDir = "";
        File saveFileDir = new File(fileDirString);
        if (!saveFileDir.exists()) {
            try {
                saveFileDir.mkdir();
                saveDir = createTimeDir(saveFileDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                saveDir = createTimeDir(saveFileDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveDir;
    }

    private String createTimeDir(File saveFileDir) throws IOException {

        /**
         * 取当前时间到秒，创建文件夹
         */
        String saveDir = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeDirName = df.format(new Date());
        saveDir = saveFileDir + "/" + timeDirName;
        File file = new File(saveDir);
        if (!file.exists()) {
            file.mkdir();
        }
        return saveDir;
    }

    @ApiOperation(value = "保存项目表单信息", httpMethod = "GET")
    @RequestMapping(value = "/saveProjectFormInfo", method = RequestMethod.GET)
    public void saveProjectFormInfo(@RequestParam String saveDir,
                                    @RequestParam String taskID,
                                    @RequestParam String projectType,
                                    @RequestParam String projectName,
                                    @RequestParam String projectDesc,
                                    @RequestParam String projectVersion,
                                    @RequestParam String projectBusiness,
                                    @RequestParam String ifEdit) {
        System.out.println(ifEdit);
        Map tableMap = new HashMap();
        tableMap.put("saveDir", saveDir);
        tableMap.put("taskID", taskID);
        tableMap.put("projectType", projectType);
        tableMap.put("projectDesc", projectDesc);
        tableMap.put("projectVersion", projectVersion);
        tableMap.put("projectBusiness", projectBusiness);
        tableMap.put("projectName", projectName);
        String createTime = TimeUtils.getNowTime();
        tableMap.put("createTime", createTime);
        template.update("updateProjectTableInfo", tableMap);
        log.info("更新创建项目表信息:" + tableMap.toString());

    }

    @ApiOperation(value = "获取项目表单信息", httpMethod = "GET")
    @RequestMapping(value = "/getProjectFormInfo", method = RequestMethod.GET)
    public List<ProjectTableInfoResult> getProjectFormInfo(@RequestParam int start,
                                    @RequestParam int end) {
        Map tableMap = new HashMap();
        tableMap.put("start", start);
        tableMap.put("end", end);

        List<ProjectTableInfoResult> resultList = template.selectList("getProjectTableInfo", tableMap);
        log.info("获取了项目表信息:" + tableMap.toString());

        return  resultList;
    }

    //文件下载相关代码
    @ApiOperation(value = "下载包", httpMethod = "GET")
    @RequestMapping(value = "/download" , method = RequestMethod.GET)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,@RequestParam int taskID) throws IOException {

        /**
         * 先打包
         */
        File[] srcFile = new File[7];
        ProjectTableInfoResult result = template.selectOne("getFilesForTaskID",taskID);
        String realPath = result.getSaveDir() + "/";
        if(!Objects.isNull(result.getModel())){
            srcFile[0] = new File(realPath + result.getModel());
        }
        if(!Objects.isNull(result.getLibs())){
            srcFile[1] = new File(realPath + result.getLibs());
        }
        if(!Objects.isNull(result.getMd5())){
            srcFile[2] = new File(realPath + result.getMd5());
        }
        if(!Objects.isNull(result.getDemo())){
            srcFile[3] = new File(realPath + result.getDemo());
        }
        if(!Objects.isNull(result.getLicense())){
            srcFile[4] = new File(realPath + result.getLicense());
        }
        if(!Objects.isNull(result.getSo())){
            srcFile[5] = new File(realPath + result.getSo());
        }
        if(!Objects.isNull(result.getJar())){
            srcFile[6] = new File(realPath + result.getJar());
        }




        File zipFile = new File(realPath+result.getTaskID()+".zip");
        FileUtils.zipFiles(srcFile,zipFile);
        /**
         * 下载相关代码
         */

        String fileName = result.getTaskID() + ".zip";// 设置文件名，根据业务需要替换成要下载的文件名

        /**
         * 将压缩好的文件拷贝到服务路径下
         */
        String fileNameWithPath = realPath + fileName;
        String targetPath = FileBean.getProjectAbsolutePath();

        String cpFile = "cp " + fileNameWithPath + " " + targetPath;
        Process ps = Runtime.getRuntime().exec(cpFile);
        String downloadPath = ImageBean.getHost() + FileBean.getProjectDownloadPath() + fileName;
        return downloadPath;

    }


    @ApiOperation(value = "获取一个项目的全部信息", httpMethod = "GET")
    @RequestMapping(value = "/getOneProjectInfo" , method = RequestMethod.GET)
    public ProjectTableInfoResult getOneProjectInfo(@RequestParam int taskID){

        ProjectTableInfoResult projectTableInfoResult = template.selectOne("getFilesForTaskID",taskID);
        log.info("获取taskID为:" + taskID + "的信息==>" + projectTableInfoResult.toString());
        return projectTableInfoResult;
    }

}
