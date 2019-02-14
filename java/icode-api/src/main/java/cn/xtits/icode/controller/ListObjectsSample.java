package cn.xtits.icode.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import java.io.IOException;
import java.util.List;

/**
 * This sample demonstrates how to list objects under specified bucket
 * from Aliyun OSS using the OSS SDK for Java.
 */
public class ListObjectsSample {

    private static String endpoint = "<endpoint>";
    private static String accessKeyId = "<accessKeyId>";
    private static String accessKeySecret = "<accessKeySecret>";
    private static String bucketName = "<bucketName>";

    public static void main(String[] args) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIyyIqIa7tE70x";
        String accessKeySecret = "N1nvCNu78lSZBW0DsQwbMUuCMBjJfc";
        String bucketName = "xtits-icode";
        String KeyPrifex = "<yourKeyPrifex>";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 列举Object。 如KeyPrifex参数为空，则列举Bucket下所有的Object。如KeyPrifex参数不为空，则列举包含指定前缀的Object。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName).withMaxKeys(1000));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
        }
        // 关闭Client。
        ossClient.shutdown();
    }
}
