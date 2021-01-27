package paas.storage.distributedFileSystem.file.response;

import lombok.Data;
import paas.storage.connection.Response;

/**
 * @author 豆沙包
 * Creation time 2021/1/23 19:06
 */
@Data
public class FileExistResponse extends Response {
    private int result;

}
