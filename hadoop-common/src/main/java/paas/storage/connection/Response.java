package paas.storage.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PAAS 平台返回结果基类
 *
 * @author 豆沙包
 * Creation time 2021/1/23 18:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * 任务执行状态 必填 1表示成功，0表示失败。
     */
    private Integer taskStatus;

    /**
     * 错误码 可选
     */
    private Integer errorCode;

    /**
     * 失败原因 可选 失败时填写。
     */
    private String errorMsg;
}
