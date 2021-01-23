package com.dsb.hadoop.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2自动配置
 *
 * @author luowei
 * Creation time 2021/1/23 20:12
 */
@EnableSwagger2
@Configuration
public class SwaggerAutoConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }

    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Spring Boot 测试使用 Swagger2 hadoopAPI接口 RESTful API")
                // 创建人信息
                .contact(new Contact("豆沙包", "https://www.cnblogs.com/zs-notes/category/1258467.html", "294100330@qq.com"))
                // 版本号
                .version("1.0.0")
                // 描述
                .description("hadoopAPI接口")
                .build();
    }

}
