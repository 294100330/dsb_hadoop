package paas.storage.distributedFileSystem.fileStream.response;

import lombok.Data;
import paas.storage.connection.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:13
 */
@Data
public class CreateResponse extends Response {

    private String streamId;
}
