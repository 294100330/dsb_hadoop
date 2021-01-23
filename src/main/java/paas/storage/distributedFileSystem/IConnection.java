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
     * @param serviceId
     * @param accessToken
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