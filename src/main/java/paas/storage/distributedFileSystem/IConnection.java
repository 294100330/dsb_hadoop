package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.utils.Response;

/**
 * 文件系统连接 服务层
 *
 * @author luowei
 * Creation time 2021/1/23 18:51
 */
public interface IConnection {

    /**
     * 创建文件系统连接
     *
     * @param serviceId 服务唯一标识 分布式文件系统服务唯一标识。
     * @param accessToken 用户身份令牌  支持OAuth、LDAP等身份认证协议的TOKEN。
     * @param expendParams
     * @return
     */
    CreateResponse create(String serviceId, String accessToken, String expendParams);

    /**
     * 关闭文件系统连接
     *
     * @param connectionId
     * @return
     */
    Response close(String connectionId);
}
