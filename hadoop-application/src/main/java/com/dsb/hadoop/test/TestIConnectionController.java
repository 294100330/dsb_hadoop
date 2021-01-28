package com.dsb.hadoop.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Configuration;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.IConnection;
import paas.storage.distributedFileSystem.connection.response.CreateResponse;

/**
 * 文件系统连接 接口层
 *
 * @author 豆沙包
 * Creation time 2021/1/23 20:51
 */
@Configuration
public class TestIConnectionController {

    @Autowired
    private IConnection iConnection;

    /**
     * 创建文件系统连接
     *
     * @param serviceId    必填 服务唯一标识 分布式文件系统服务唯一标识。
     * @param accessToken  必填 用户身份令牌  支持OAuth、LDAP等身份认证协议的TOKEN。
     * @param expendParams 可选
     * @return
     */
    public CreateResponse create(String serviceId, String accessToken, String expendParams) {
        System.out.println("====================== 文件流管理 创建流 开始 ============================");
        CreateResponse response = iConnection.create(serviceId, accessToken, expendParams);
        System.out.println(response);
        System.out.println("====================== 文件流管理 创建流 开始 ============================");
        return response;
    }

    /**
     * 关闭文件系统连接
     *
     * @param connectionId 必填 文件系统连接标识
     * @return
     */
    public Response close(String connectionId) {
        System.out.println("====================== 文件流管理 创建流 开始 ============================");
        Response response = iConnection.close(connectionId);
        System.out.println(response);
        System.out.println("====================== 文件流管理 创建流 开始 ============================");
        return response;
    }

}
