package paas.storage.component;

import com.qingcloud.sdk.config.EnvContext;
import com.qingcloud.sdk.exception.QCException;
import com.qingcloud.sdk.service.InstanceService;
import com.qingcloud.sdk.service.Types;
import lombok.extern.log4j.Log4j2;
import paas.storage.exception.QCRuntimeException;
import paas.storage.utils.AssertUtils;

/**
 * 默认鉴权 实现层
 *
 * @author luowei
 * Creation time 2021/1/28 10:18
 */
@Log4j2
public class DefaultAuthService implements AuthService {

    /**
     * 鉴权
     *
     * @param accessKey
     * @param accessSecret
     * @return
     */
    @Override
    public InstanceService.DescribeInstancesOutput authentication(String accessKey, String accessSecret) {

        EnvContext context = new EnvContext(accessKey, accessSecret);
        InstanceService service = new InstanceService(context);
        InstanceService.DescribeInstancesInput input = new InstanceService.DescribeInstancesInput();
        input.setLimit(1);
        try {
            InstanceService.DescribeInstancesOutput output = service.describeInstances(input);
            //
            AssertUtils.isTrue(0 == output.getRetCode(), output.getMessage());
            if (log.isInfoEnabled()) {
                for (Types.InstanceModel model : output.getInstanceSet()) {
                    log.info("==================");
                    log.info(model.getInstanceID());
                    log.info(model.getInstanceName());
                    System.out.println(model.getImage().getImageID());
                    for (Types.NICVxNetModel vxNetModel : model.getVxNets()) {
                        log.info("==================");
                        log.info(vxNetModel.getVxNetID());
                        log.info(vxNetModel.getVxNetType());
                    }
                }
            }
            return output;
        } catch (QCException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
            throw new QCRuntimeException(e.getMessage());
        }
    }
}
