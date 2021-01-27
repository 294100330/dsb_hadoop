package paas.storage.distributedFileSystem.fileStream.response;

import lombok.Data;
import paas.storage.connection.Response;

/**
 * @author 豆沙包
 * Creation time 2021/1/23 19:19
 */
@Data
public class WriteResponse extends Response {
    private long length;
}
