package paas.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author luowei
 * Creation time 2021/1/23 20:34
 */
@Data
@ConfigurationProperties("hadoop")
public class HadoopProperties {

    /**
     * 链接
     */
    private String fsUri;

    /**
     * 配置
     */
    private Map<String, String> config;

}
