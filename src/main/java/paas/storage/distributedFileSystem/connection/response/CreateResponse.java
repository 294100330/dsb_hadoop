package paas.storage.distributedFileSystem.connection.response;

import lombok.Data;
import paas.storage.utils.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 18:53
 */
@Data
public class CreateResponse extends Response {

    private String connectionId;

}
