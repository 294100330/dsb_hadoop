package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.utils.Response;

/**
 * 文件系统连接 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 18:51
 */
public class ConnectionImpl implements IConnection {

    /**
     * 创建文件系统连接
     *
     * @param serviceId
     * @param accessToken
     * @param expendParams
     * @return
     */
    @Override
    public CreateResponse create(String serviceId, String accessToken, String expendParams) {
        return null;
    }

    /**
     * 关闭文件系统连接
     *
     * @param connectionId
     * @return
     */
    @Override
    public Response close(String connectionId) {
        return null;
    }
}
