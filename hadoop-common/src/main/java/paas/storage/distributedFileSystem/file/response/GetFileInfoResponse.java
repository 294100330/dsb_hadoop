package paas.storage.distributedFileSystem.file.response;

import lombok.Data;
import paas.storage.connection.Response;

/**
 * @author 豆沙包
 * Creation time 2021/1/23 19:08
 */
@Data
public class GetFileInfoResponse extends Response {

    private String fileDetails;
}
