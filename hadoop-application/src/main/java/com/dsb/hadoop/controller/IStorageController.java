package com.dsb.hadoop.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paas.storage.distributedFileSystem.IStorage;
import paas.storage.distributedFileSystem.storage.response.StorageResponse;

/**
 * 存储空间管理 实现层
 *
 * @author luowei
 * Creation time 2021/1/27 14:57
 */
@Api(tags = "存储空间管理 API")
@RestController
@RequestMapping("i_storage")
public class IStorageController {

    @Autowired
    private IStorage iStorage;

    @PostMapping("getStorageInfo")
    @ApiOperation("存储容量及使用量")
    public StorageResponse getStorageInfo(String connectionId, String directory) {
        return iStorage.getStorageInfo(connectionId, directory);
    }
}
