package paas.storage.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import paas.storage.properties.HadoopProperties;

import java.net.URI;

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
        conf.set("dfs.replication", "1");
        conf.set("fs.defaultFS", hadoopProperties.getFsUri());
        return conf;
    }

}
