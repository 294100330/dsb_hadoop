package paas.storage.distributedFileSystem.fileStream.response;

import lombok.Data;
import paas.storage.distributedFileSystem.connection.response.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:21
 */
@Data
public class CloseResponse extends Response {
    private String streamId;
}
