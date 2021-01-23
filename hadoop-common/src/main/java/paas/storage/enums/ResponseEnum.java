package paas.storage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response预制响应值
 *
 * @author luowei
 * Creation time 2021/1/23 18:44
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    Fail(0, 0, "失败"),
    Success(1, 0, "成功");

    /**
     * 状态码
     */
    private final int status;

    /**
     * 错误码
     */
    private final int errorCode;

    /**
     * 基础错误信息
     */
    private final String errorMsg;
}
