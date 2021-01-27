package paas.storage.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import paas.storage.enums.ResponseEnum;

/**
 * PAAS 平台返回结果基类
 *
 * @author luowei
 * Creation time 2021/1/23 18:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * 任务执行状态 必填 1表示成功，0表示失败。
     */
    private int taskStatus;

    /**
     * 错误码 可选
     */
    private int errorCode;

    /**
     * 失败原因 可选 失败时填写。
     */
    private String errorMsg;
}
