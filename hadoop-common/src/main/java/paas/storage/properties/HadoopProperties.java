package paas.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luowei
 * Creation time 2021/1/23 20:34
 */
@Data
@ConfigurationProperties("hadoop")
public class HadoopProperties {

    private String fsUri;

}
