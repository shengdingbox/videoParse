package com.dabaotv.vip.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * @author Dabao
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

	/**
	 * 选择只启用制定包下的api
	 *
	 * @return Docket
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.dabaotv.vip.web"))
				.paths(PathSelectors.any()).build();
	}

	/**
	 * 构建文档基本信息
	 *
	 * @return 文档基本信息
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("影视VIP对接API", "/v1/about", "");
		return new ApiInfoBuilder().title("影视VIP对接API文档").description("影视VIP对接API1.0").termsOfServiceUrl("yingshivip")
				.contact(contact).version("1.0").build();
	}
}
