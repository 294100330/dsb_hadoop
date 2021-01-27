package paas.storage.distributedFileSystem;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import paas.storage.component.ConnectionService;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.connection.response.CreateResponse;

/**
 * 文件系统连接 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 18:51
 */
@Log4j2
@Configuration
public class ConnectionImpl implements IConnection {

    @Autowired
    private ConnectionService connectionService;

    /**
     * 创建文件系统连接
     *
     * @param serviceId    必填 服务唯一标识 分布式文件系统服务唯一标识。
     * @param accessToken  必填 用户身份令牌  支持OAuth、LDAP等身份认证协议的TOKEN。
     * @param expendParams 可选
     * @return
     */
    @Override
    public CreateResponse create(String serviceId, String accessToken, String expendParams) {
        CreateResponse createResponse = new CreateResponse();
        try {
            String connectionId = connectionService.create(serviceId);
            createResponse.setConnectionId(connectionId);
            createResponse.setTaskStatus(1);
        } catch (Exception e) {
            createResponse.setTaskStatus(0);
            createResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return createResponse;
    }


    /**
     * 关闭文件系统连接
     *
     * @param connectionId 必填 文件系统连接标识
     * @return
     */
    @Override
    public Response close(String connectionId) {
        Response response = new Response();
        try {
            connectionService.delete(connectionId);
            response.setTaskStatus(1);
        } catch (Exception e) {
            response.setTaskStatus(0);
            response.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return response;
    }
}
