package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.utils.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 18:52
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
        CreateResponse createResponse = new CreateResponse();
//        createResponse.setConnectionId();
        return null;
    }

    @Override
    public Response close(String connectionId) {
        return null;
    }
}
