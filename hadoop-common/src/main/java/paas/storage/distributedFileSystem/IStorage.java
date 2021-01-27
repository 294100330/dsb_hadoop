package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.storage.response.StorageResponse;

/**
 * 存储空间管理 服务层
 *
 * @author 豆沙包
 * Creation time 2021/1/23 19:22
 */
public interface IStorage {

    /**
     * 存储容量及使用量
     *
     * @param connectionId  必填 文件系统连接标识
     * @param directory 必填 目录
     * @return
     */
    StorageResponse getStorageInfo(String connectionId, String directory);
}
