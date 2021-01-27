package paas.storage.distributedFileSystem.storage.response;

import lombok.Data;
import paas.storage.connection.Response;

/**
 * @author 豆沙包
 * Creation time 2021/1/23 19:23
 */
@Data
public class StorageResponse extends Response {

    /**
     * 存储容量 默认以GB返回。
     */
    private long totalVolume;

    /**
     * 存储使用量  默认以GB返回。
     */
    private long usedVolume;
}
