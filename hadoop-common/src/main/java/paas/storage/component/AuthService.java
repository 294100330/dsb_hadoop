package paas.storage.component;

import com.qingcloud.sdk.service.InstanceService;

/**
 * 鉴权 服务层
 *
 * @author luowei
 * Creation time 2021/1/28 10:16
 */
public interface AuthService {

    /**
     * 鉴权
     *
     * @param accessKey
     * @param accessSecret
     * @return
     */
    InstanceService.DescribeInstancesOutput authentication(String accessKey, String accessSecret);
}
