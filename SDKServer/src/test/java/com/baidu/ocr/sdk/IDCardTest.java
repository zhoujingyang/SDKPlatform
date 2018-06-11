package com.baidu.ocr.sdk;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class IDCardTest {


//    @Test
    public void testSaveIDCardFrontRecognitionResult() throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
//        String url = "http://10.94.243.104:8808/ocr/sdk/eval/save/idcard/front";
        String url = "http://localhost:8888/ocr/sdk/eval/save/idcard/front";
        HttpPost post = new HttpPost(url);

        JSONObject subJson = new JSONObject();
        subJson.put("姓名", "王树莉");
        subJson.put("性别", "女");
        subJson.put("民族", "汉");
        subJson.put("出生", "19771130");
        subJson.put("住址", "吉林省德惠市建设街城郊委15组");
        subJson.put("公民身份号码", "220124197711307623");

        JSONObject object = new JSONObject();


        object.put("evalImgNum", "100");
        object.put("evalName", "general");
        object.put("idNo", "c744d1145b2e496691a7826e1ae230a8");
        object.put("fileName", "idcardA1116_0001.jpg");
        object.put("resultMap", subJson);

        System.out.println(object.toJSONString());

        StringEntity entity = new StringEntity(object.toString(), "utf-8");
        post.setEntity(entity);
        post.setHeader("Content-Type", "application/json");

        HttpResponse response = closeableHttpClient.execute(post);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);


    }
}
