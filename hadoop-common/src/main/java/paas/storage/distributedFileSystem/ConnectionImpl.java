package paas.storage.distributedFileSystem;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.utils.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件系统连接 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 18:51
 */
@Configuration
public class ConnectionImpl implements IConnection {

    private final static Map<String, FileSystem> FILE_SYSTEM_CONCURRENT_MAP = new ConcurrentHashMap<>();

    @Autowired
    private FileSystem fileSystem;

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
        return null;
    }


    /**
     * 关闭文件系统连接
     *
     * @param connectionId 必填 文件系统连接标识
     * @return
     */
    @Override
    public Response close(String connectionId) {
        return null;
    }
}
