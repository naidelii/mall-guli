package com.mall.thirdparty.controller;

import cn.hutool.core.io.FileUtil;
import com.mall.common.base.api.Result;
import com.mall.common.minio.entity.OssFile;
import com.mall.common.minio.service.IOssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author naidelii
 */
@Slf4j
@Controller
@RequestMapping("/thirdparty/oss")
@RequiredArgsConstructor
public class OssFileController {

    private final IOssService ossService;


    @ResponseBody
    @PostMapping("/upload")
    public Result<?> upload(@RequestParam("file") MultipartFile file,
                            @RequestParam("dirName") String dirName) throws IOException {
        if (file == null || file.isEmpty()) {
            return Result.fail("文件不能为空！");
        }
        // 文件原来的名称
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            return Result.fail("文件名不存在！");
        }
        // 文件的后缀名
        String suffix = getFileExtension(file.getOriginalFilename());
        // TODO 做一些校验

        // 生成新的文件路径
        String filePath = ossService.generateFileName(dirName, originalFilename);
        InputStream is = file.getInputStream();
        OssFile ossFile = ossService.upLoadFile(filePath, originalFilename, is);
        return Result.success(ossFile);
    }

    /**
     * 获取文件后缀名
     *
     * @param originalFilename 文件全名
     * @return 后缀名
     */
    public String getFileExtension(String originalFilename) {
        return FileUtil.getPrefix(originalFilename);
    }

}
