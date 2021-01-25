package paas.storage.distributedFileSystem.file.response;

import lombok.Data;
import paas.storage.distributedFileSystem.connection.response.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:08
 */
@Data
public class GetFileInfoResponse extends Response {

    private String fileDetails;
}
