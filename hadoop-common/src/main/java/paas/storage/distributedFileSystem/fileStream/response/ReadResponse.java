package paas.storage.distributedFileSystem.fileStream.response;

import lombok.Data;
import paas.storage.connection.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:16
 */
@Data
public class ReadResponse extends Response {
    private long length;
}
