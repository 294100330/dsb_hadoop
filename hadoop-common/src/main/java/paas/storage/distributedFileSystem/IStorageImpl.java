package paas.storage.distributedFileSystem;

import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import paas.storage.component.ConnectionService;
import paas.storage.distributedFileSystem.storage.response.StorageResponse;

/**
 * 存储空间管理 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 19:22
 */
@Log4j2
@Configuration
public class IStorageImpl implements IStorage {

    @Autowired
    private ConnectionService connectionService;

    /**
     * 存储容量及使用量
     *
     * @param connectionId 必填 文件系统连接标识
     * @param directory    必填 目录
     * @return
     */
    @Override
    public StorageResponse getStorageInfo(String connectionId, String directory) {
        StorageResponse storageResponse = new StorageResponse();
        try {
            FileSystem fileSystem = connectionService.get(connectionId);
            ContentSummary contentSummary = fileSystem.getContentSummary(new Path(directory));
            storageResponse.setTaskStatus(1);
            storageResponse.setTotalVolume(contentSummary.getSpaceConsumed());
            storageResponse.setUsedVolume(contentSummary.getLength());
        } catch (Exception e) {
            storageResponse.setTaskStatus(0);
            storageResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return storageResponse;
    }
}
