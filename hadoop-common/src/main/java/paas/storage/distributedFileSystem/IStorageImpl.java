package paas.storage.distributedFileSystem;

import org.springframework.context.annotation.Configuration;
import paas.storage.distributedFileSystem.storage.response.StorageResponse;

/**
 * 存储空间管理 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 19:22
 */
@Configuration
public class IStorageImpl implements IStorage {

    /**
     * 存储容量及使用量
     *
     * @param connectionId 必填 文件系统连接标识
     * @param directory    必填 目录
     * @return
     */
    @Override
    public StorageResponse getStorageInfo(String connectionId, String directory) {
        return null;
    }
}
