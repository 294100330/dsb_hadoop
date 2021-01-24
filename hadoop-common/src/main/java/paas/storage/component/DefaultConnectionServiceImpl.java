package paas.storage.component;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认文件系统连接创建 实现层
 *
 * @author luowei
 * Creation time 2021/1/24 16:38
 */
public class DefaultConnectionServiceImpl implements ConnectionService {

    private final Map<String, FileSystemData> map = new ConcurrentHashMap<>();

    @Autowired
    private FileSystem fileSystem;

    /**
     * 创建
     *
     * @param serviceId
     * @return
     */
    @Override
    public String create(String serviceId) {
        String connectionId = null;
        for (Map.Entry<String, FileSystemData> entity : map.entrySet()) {
            if (entity.getValue().getServiceId().equals(serviceId)) {
                connectionId = entity.getKey();
            }
        }
        if (connectionId == null) {
            connectionId = this.putByServiceId(serviceId);
        }
        return connectionId;
    }

    /**
     * 获取
     *
     * @param connectionId
     * @return
     */
    @Override
    public FileSystem get(String connectionId) {
        FileSystemData fileSystemData = this.map.get(connectionId);
        return Optional.of(fileSystemData).orElseGet(null).getFileSystem();
    }

    /**
     * 删除
     *
     * @param connectionId
     */
    @Override
    public void delete(String connectionId) {
        this.map.remove(connectionId);
    }

    /**
     * 获得id
     *
     * @return
     */
    private String getConnectionId() {
        return IdUtil.simpleUUID();
    }

    /**
     * 保存
     *
     * @param serviceId
     * @return
     */
    private String putByServiceId(String serviceId) {
        String connectionId = this.getConnectionId();
        FileSystemData fileSystemData = FileSystemData.builder().serviceId(serviceId).fileSystem(fileSystem).build();
        this.map.put(connectionId, fileSystemData);
        return connectionId;
    }

    @Data
    @Builder
    private static class FileSystemData {

        /**
         * 服务id
         */
        private String serviceId;

        /**
         * hdfs
         */
        private FileSystem fileSystem;
    }

}
