package inno.fisco.bcos.be.configuration;


import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

/**
 * @Description: swagger配置
 * @Author peifeng
 * @Date 2021/7/19 16:10
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("inno.fisco.bcos.be.controller")).build()
                .groupName("Api 接口！")
                .protocols(new HashSet<String>(Lists.newArrayList("http"))).pathMapping("/")
                .apiInfo(apiInfo()).enable(true)
                ;
    }
    

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API SWAGGER").description("SWAGGER DOCUMENT").version("2.0").build();
    }
}
