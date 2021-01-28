package paas.storage.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import paas.storage.component.*;
import paas.storage.properties.HadoopProperties;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * hadoop
 *
 * @author 豆沙包
 * Creation time 2021/1/23 20:36
 */
@Data
@Log4j2
@Configuration
@ComponentScan("paas.storage")
@EnableConfigurationProperties({HadoopProperties.class})
public class AutoHadoopConfiguration {

    @Bean
    @ConditionalOnMissingBean(ConnectionService.class)
    public ConnectionService connectionService() {
        return new DefaultConnectionServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(IFileInputStreamService.class)
    public IFileInputStreamService iFileInputStreamService() {
        return new DefaultIFileInputStreamServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(IFileOutStreamService.class)
    public IFileOutStreamService iFileOutStreamService() {
        return new DefaultIFileOutStreamServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(AuthService.class)
    public AuthService authService() {
        return new DefaultAuthService();
    }

    /**
     * 创建 fileSystem
     *
     * @param hadoopProperties
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public FileSystem createFileSystem(HadoopProperties hadoopProperties) throws URISyntaxException, IOException, InterruptedException {
        // 文件系统
        URI uri = new URI(hadoopProperties.getFsUri().trim());
        FileSystem fileSystem = FileSystem.get(uri, this.getConfiguration(hadoopProperties), hadoopProperties.getUser());
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
