package com.mall.thirdparty;

import com.mall.common.minio.service.impl.MinioServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class OssTest {
    @Autowired
    private MinioServiceImpl minioService;

    @Test
    public void testGetPostPolicy() {
        Map<String, String> policy = minioService.generatePolicy("", "a.png");
        System.out.println(policy);
    }
}
