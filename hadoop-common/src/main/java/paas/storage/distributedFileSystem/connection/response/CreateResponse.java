package paas.storage.distributedFileSystem.connection.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import paas.storage.connection.Response;

/**
 * @author 豆沙包
 * Creation time 2021/1/23 18:53
 */
@Data
@ToString(callSuper = true,doNotUseGetters = true)
public class CreateResponse extends Response {

    private String connectionId;


}
