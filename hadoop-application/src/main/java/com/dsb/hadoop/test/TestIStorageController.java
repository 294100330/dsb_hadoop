package com.dsb.hadoop.test;

import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paas.storage.distributedFileSystem.IStorage;
import paas.storage.distributedFileSystem.storage.response.StorageResponse;

/**
 * 存储空间管理 实现层
 *
 * @author 豆沙包
 * Creation time 2021/1/27 14:57
 */
@Log4j2
@Configuration
public class TestIStorageController {

    @Autowired
    private IStorage iStorage;

    /**
     * 存储容量及使用量
     *
     * @param connectionId 必填 文件系统连接标识
     * @param directory    必填 目录
     * @return
     */
    public StorageResponse getStorageInfo(String connectionId, String directory) {
        log.info("====================== 存储空间管理 存储容量及使用量 开始 ============================");
        StorageResponse storageResponse = iStorage.getStorageInfo(connectionId, directory);
        log.info(storageResponse);
        log.info("====================== 存储空间管理 存储容量及使用量 结束 ============================");
        return storageResponse;
    }
}
