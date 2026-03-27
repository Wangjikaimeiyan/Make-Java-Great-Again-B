package com.example.Utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
@Slf4j
@Component/* 将此对象交给spring管理 */
//@ConditionalOnBean(AliyunOSSProperties.class)/* 判断是否引入了aliyun-oss-java-sdk */
public class AliyunOSSOperator {
//    @Value("${aliyun.oss.endpoint}")
//    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
//    @Value("${aliyun.oss.bucketName}")
//    private String bucketName = "java-date";
//    @Value("${aliyun.oss.region}")
//    private String region = "cn-beijing";

    private final AliyunOSSProperties aliyunOSSProperties;
    //    构造器
    public AliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties) {
        this.aliyunOSSProperties = aliyunOSSProperties;
    }


    public String upload(byte[] content, String originalFilename) throws Exception {
//        创建OSSClient实例。
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 填写Object完整路径，例如202406/1.png。Object完整路径中不能包含Bucket名称。
        //获取当前系统日期的字符串,格式为 yyyy/MM
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        //生成一个新的不重复的文件名
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(".")/* 获取扩展名*/);/* 新文件名 */
        String objectName = dir + "/" + newFileName;

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();/* 设置签名版本为V4 */
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);/* 设置签名版本为V4 */
        OSS ossClient = OSSClientBuilder.create()/* 创建OSSClient实例 */
                .endpoint(endpoint)/* 设置OSS服务域名 */
                .credentialsProvider(credentialsProvider)/* 设置访问凭证 */
                .clientConfiguration(clientBuilderConfiguration)/* 设置客户端配置 */
                .region(region)/* 设置地域 */
                .build();/* 创建OSSClient实例 */

        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } finally {
            ossClient.shutdown();
        }
//        返回文件访问路径, 格式为 https://java-date.oss-cn-beijing.aliyuncs.com/202406/1.png
        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }
    //      删除阿里云文件(删除单个文件)
    public void delete(String url) throws Exception {
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();


        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();/* 设置签名版本为V4 */
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);/* 设置签名版本为V4 */
        OSS ossClient = OSSClientBuilder.create()/* 创建OSSClient实例 */
                .endpoint(endpoint)/* 设置OSS服务域名 */
                .credentialsProvider(credentialsProvider)/* 设置访问凭证 */
                .clientConfiguration(clientBuilderConfiguration)/* 设置客户端配置 */
                .region(region)/* 设置地域 */
                .build();/* 创建OSSClient实例 */


        try {
            ossClient.deleteObject(bucketName, url.split("//" + bucketName + "." + endpoint.split("//")[1] + "/")[1]);
        }catch (Exception e){
            log.error("OSS文件删除异常，URL：{}", url,e);
        }
        finally {
            ossClient.shutdown();
        }
    }
}














































