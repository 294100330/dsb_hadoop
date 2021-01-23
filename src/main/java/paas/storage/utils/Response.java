package paas.storage.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PAAS 平台返回结果基类
 *
 * @author luowei
 * Creation time 2021/1/23 18:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {


    private int taskStatus;
    private int errorCode;

    private String errorMsg;
}
