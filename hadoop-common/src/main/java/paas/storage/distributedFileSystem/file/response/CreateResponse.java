package paas.storage.distributedFileSystem.file.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import paas.storage.enums.ResponseEnum;
import paas.storage.connection.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateResponse extends Response {

    private String filePath;
}
