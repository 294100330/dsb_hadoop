package paas.storage.component;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import paas.storage.config.AutoHadoopConfiguration;
import paas.storage.exception.QCRuntimeException;
import paas.storage.properties.HadoopProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认文件系统连接创建 实现层
 *
 * @author 豆沙包
 * Creation time 2021/1/24 16:38
 */
public class DefaultConnectionServiceImpl implements ConnectionService {

    private static final Map<String, FileSystemData> MAP = new ConcurrentHashMap<>();

    @Autowired
    private HadoopProperties hadoopProperties;

    @Autowired
    private AutoHadoopConfiguration autoHadoopConfiguration;

    /**
     * 创建
     *
     * @param serviceId 服务唯一标识
     * @return
     */
    @Override
    public String create(String serviceId) {
        String connectionId = null;
        try {
            connectionId = this.getConnectionId();
            FileSystem fileSystem = autoHadoopConfiguration.createFileSystem(hadoopProperties);
            FileSystemData fileSystemData = FileSystemData.builder().serviceId(serviceId).fileSystem(fileSystem).build();
            DefaultConnectionServiceImpl.MAP.put(connectionId, fileSystemData);
        } catch (Exception e) {
            throw new QCRuntimeException(e.getMessage(), e);
        }
        return connectionId;
    }

    /**
     * 获取
     *
     * @param connectionId 文件系统连接标识
     * @return
     */
    @Override
    public FileSystem get(String connectionId) {
        FileSystemData fileSystemData = DefaultConnectionServiceImpl.MAP.get(connectionId);
        if (fileSystemData == null) {
            throw new QCRuntimeException("没有服务");
        }
        return fileSystemData.getFileSystem();
    }

    /**
     * 删除
     *
     * @param connectionId 文件系统连接标识
     */
    @Override
    public void delete(String connectionId) {
        FileSystemData fileSystemData = DefaultConnectionServiceImpl.MAP.get(connectionId);
        if (fileSystemData != null) {
            IOUtils.closeStream(fileSystemData.getFileSystem());
        }
        DefaultConnectionServiceImpl.MAP.remove(connectionId);
    }

    /**
     * 获得id
     *
     * @return
     */
    private String getConnectionId() {
        return IdUtil.simpleUUID();
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
