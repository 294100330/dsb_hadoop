package paas.storage.distributedFileSystem.file.response;

import lombok.Data;
import paas.storage.utils.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:06
 */
@Data
public class FileExistResponse extends Response {
    private int result;

}
