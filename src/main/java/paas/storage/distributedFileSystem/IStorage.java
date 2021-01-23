package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.storage.response.StorageResponse;

/**
 * 存储空间管理 服务层
 *
 * @author luowei
 * Creation time 2021/1/23 19:22
 */
public interface IStorage {

    /**
     * 存储容量及使用量
     * @param streamId
     * @param directory
     * @return
     */
     StorageResponse getStorageInfo(String streamId, String directory);
}
