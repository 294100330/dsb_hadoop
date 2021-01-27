package com.dsb.hadoop.controller;

import com.qingcloud.sdk.config.EnvContext;
import com.qingcloud.sdk.exception.QCException;
import com.qingcloud.sdk.service.InstanceService;
import com.qingcloud.sdk.service.Types;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paas.storage.distributedFileSystem.IConnection;
import paas.storage.connection.Response;

import javax.annotation.PostConstruct;

/**
 * 文件系统连接 接口层
 *
 * @author luowei
 * Creation time 2021/1/23 20:51
 */
@Api(tags = "文件系统连接 API")
@RestController
@RequestMapping("i_connection")
public class IConnectionController implements InitializingBean {

    @Autowired
    private IConnection iConnection;

    @PostConstruct
    public void aa(){
        EnvContext context = new EnvContext("UBMCZZDPMXHOFVXBKYPB", "UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox");
        context.setProtocol("https");
        context.setHost("api.qingcloud.com");
        context.setPort("443");
        context.setZone("pek3b");
        context.setApiLang("zh-cn");
        InstanceService service = new InstanceService(context);

        InstanceService.DescribeInstancesInput input = new InstanceService.DescribeInstancesInput();
        input.setLimit(1);

        try {
            InstanceService.DescribeInstancesOutput output = service.describeInstances(input);
            for (Types.InstanceModel model : output.getInstanceSet()) {
                System.out.println("==================");
                System.out.println(model.getInstanceID());
                System.out.println(model.getInstanceName());
                System.out.println(model.getImage().getImageID());
                for (Types.NICVxNetModel vxNetModel : model.getVxNets()) {
                    System.out.println("==================");
                    System.out.println(vxNetModel.getVxNetID());
                    System.out.println(vxNetModel.getVxNetType());
                }
            }
        } catch (QCException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("create")
    @ApiOperation("创建")
    public Response create(String serviceId, String accessToken, String expendParams) {
        return iConnection.create(serviceId, accessToken, expendParams);
    }

    @PostMapping("close")
    @ApiOperation("关闭")
    public Response close(String connectionId) {
        return iConnection.close(connectionId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
         iConnection.create("1", "1", "1");

    }
}
