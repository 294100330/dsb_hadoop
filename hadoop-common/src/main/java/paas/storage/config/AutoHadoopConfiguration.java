package paas.storage.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import paas.storage.component.ConnectionService;
import paas.storage.component.DefaultConnectionServiceImpl;
import paas.storage.properties.HadoopProperties;

import java.net.URI;
import java.util.Map;

/**
 * hadoop
 *
 * @author luowei
 * Creation time 2021/1/23 20:36
 */
@Data
@Log4j2
@Configuration
@EnableConfigurationProperties({HadoopProperties.class})
public class AutoHadoopConfiguration {

    @Bean
    @ConditionalOnMissingBean(ConnectionService.class)
    public ConnectionService connectionService() {
        return new DefaultConnectionServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public FileSystem fileSystem(HadoopProperties hadoopProperties) {
        // 文件系统
        FileSystem fileSystem = null;
        try {
            URI uri = new URI(hadoopProperties.getFsUri().trim());
            fileSystem = FileSystem.get(uri, this.getConfiguration(hadoopProperties));
        } catch (Exception e) {
            log.error("【FileSystem配置初始化失败】", e);
        }
        return fileSystem;
    }

    /**
     * 配置
     */
    private org.apache.hadoop.conf.Configuration getConfiguration(HadoopProperties hadoopProperties) {
        //读取配置文件
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("fs.defaultFS", hadoopProperties.getFsUri());
        Map<String, String> config = hadoopProperties.getConfig();
        config.forEach((key, value) -> {
            conf.set(key, value);
        });
        return conf;
    }

}
