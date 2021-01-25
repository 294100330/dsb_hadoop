package paas.storage.distributedFileSystem.storage.response;

import lombok.Data;
import paas.storage.distributedFileSystem.connection.response.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:23
 */
@Data
public class StorageResponse extends Response {
    private long totalVolume;
    private long usedVolume;
}
