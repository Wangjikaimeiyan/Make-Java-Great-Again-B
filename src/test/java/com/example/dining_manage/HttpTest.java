package com.example.dining_manage;

import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HttpTest {
    @Test
//  httpclient  get
    public void testGet() throws Exception {
//        创建HttpClient实例。
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        请求对象,查询本地营业状态
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/Statue/query");
//        发送
        CloseableHttpResponse response = httpClient.execute(httpGet);
//        获取响应
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("响应状态码：" + statusCode);
        String content = EntityUtils.toString(response.getEntity());
        System.out.println("响应内容：" + content);
//        关闭资源
        response.close();
        httpClient.close();
    }
//  httpclient  post
    @Test
    public void testPost() throws Exception {
//        创建HttpClient实例。
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        请求对象post,登录
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/login");
        JsonObject json = new JsonObject();
        json.addProperty("account", "laoliu");
        json.addProperty("password", "123456");
//        设置请求参数
        StringEntity entity = new StringEntity(json.toString(), "UTF-8");
        /*json*/
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
//        发送
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("响应状态码：" + statusCode);
        String content = EntityUtils.toString(response.getEntity());
        System.out.println("响应内容：" + content);
//        关闭资源
        response.close();
        httpClient.close();
    }
}
