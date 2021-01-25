package paas.storage.distributedFileSystem.file.response;

import lombok.Data;
import paas.storage.connection.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:02
 */
@Data
public class RenameResponse extends Response {
    private String dstFile;

}
