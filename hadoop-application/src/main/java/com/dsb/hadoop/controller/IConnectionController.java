package com.dsb.hadoop.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paas.storage.distributedFileSystem.IConnection;
import paas.storage.connection.Response;

/**
 * 文件系统连接 接口层
 *
 * @author luowei
 * Creation time 2021/1/23 20:51
 */
@Api(tags = "文件系统连接 API")
@RestController
@RequestMapping("i_connection")
public class IConnectionController {

    @Autowired
    private IConnection iConnection;

    @PostMapping("create")
    @ApiOperation("创建")
    public Response create(String serviceId, String accessToken, String expendParams) {
        return iConnection.create(serviceId, accessToken, expendParams);
    }

    @PostMapping("close")
    @ApiOperation("关闭")
    public Response close(String connectionId) {
        return iConnection.close(connectionId);
    }
}
