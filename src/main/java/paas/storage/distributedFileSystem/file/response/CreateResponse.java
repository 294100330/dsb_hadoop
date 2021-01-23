package paas.storage.distributedFileSystem.file.response;

import lombok.Data;
import paas.storage.utils.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:00
 */
@Data
public class CreateResponse extends Response {

    private String filePath;
}
