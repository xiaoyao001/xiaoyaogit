package com.boot.org.application.config;

import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * mvc配置类
 * @author xiaoyao
 * @version 
 */
@Configuration
@EnableWebMvc
public class BootMvcConfig implements WebMvcConfigurer{

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
//		 registry.addMapping("/test1/**") //可以跨域访问的URL路规则
//         .allowedOrigins("http://localhost:8080/")  //可以跨域访问的访问者
//         .allowedMethods("GET", "POST", "PUT", "DELETE") //可以跨域访问的方法
//         .allowedHeaders("");    //可以跨域访问的Headers
         
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		 /**
	     * 处理前台传入类型为String且Controller中接入参数也是String的参数转换 
	     * 将前台传入参数值附加[converted]后在调用Controller时使用；  
	     * 如前台通过访问http://localhost/test?a=b调用Controller，在Controller获取到a的值时将会是a[converted] 
	     */
	    registry.addConverter(new Converter<String, String>() {
	        @Null
	        @Override
	        public String convert(String source) {
	            return null == source? null : source;
	        }
	    });

	    /**
	     * 处理当前台传入是String类型而后台Controller接收的参数是Integer类型时的转换规则
	     */
	    registry.addConverter(new Converter<String, Integer>() {
	        @Null
	        @Override
	        public Integer convert(String source) {
	            return null == source? null : Integer.valueOf(source);
	        }
	    });
	}

	@Override
	public void addInterceptors(InterceptorRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer  configurer) {
		// TODO Auto-generated method stub
		 /**
	     * 设置后缀与访问的内容类型的映射关系；
	     * 如以下示例，默认情况下.txt表明是TXT类型，现在将其指定为PDF类型；
	     * 那么在访问http://localhost/test.txt时将会将其当成PDF文件类型使用PDF阅读工具打开。
	     */
	    configurer.mediaType("txt", MediaType.APPLICATION_PDF);

	    /**
	     * 是否忽略请求报文头中的内容类型
	     * 默认情况下是False，即以请求报文头中的类型为准
	     */
	    configurer.ignoreAcceptHeader(true);

	    /**
	     * 是否通过传入的参数（默认名称是format）来决定内容类型
	     * 需要结合mediaType方法一起使用；
	     * 如通过mediaType指定txt为PDF类型；则访问路径http://localhost/test?format=txt时会当成PDF类型处理
	     * 默认为False
	     */
	    configurer.favorParameter(true);

	    /**
	     * 是否优先使用后缀为决定内容类型 
	     * 为False时优先使用Header中的类型
	     * 为True时优先使用.后面指定的类型
	     * 默认为True
	     */
	    configurer.favorPathExtension(true);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.enable();
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// TODO Auto-generated method stub
		System.out.println("Begin to configure pathMatches!");
		 /**
	     * 是否包含.*来映射请求
	     * 假设RequestMapping注解中指定的路径是/test
	     * 如果设置成True，那么对于/test.do,/test.a等任何包含.的请求都会映射到/test上去；
	     * 如果设置成False，那么对于这种请求不会进行映射。
	     *
	     */
	    configurer.setUseSuffixPatternMatch(false);

	    /**
	     * 设置路径后是否包含/
	     * 假设RequestMapping注解中指定的路径是/test
	     * 设置成True时，会同时处理/test/和/test的请求
	     * 设置成False时，只会处理/test的请求
	     * 默认是True
	     */
	    configurer.setUseTrailingSlashMatch(false);
	    /**
	     * 配置后置模式匹配是否仅在配置内容协商中明确指定的路径扩展名称时生效
	     * 举个例子：假设WebMvcConfigurer中覆盖了configureContentNegotiation方法进行以下处理：
	     * @Override
	     * public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	     *     configurer.mediaType("pdf", MediaType.APPLICATION_PDF);
	     *}
	     * 当setUseRegisteredSuffixPatternMatch配置成TRUE时，即使setUseSuffixPatternMatch设置成True，
	     * 在访问/test.do时也不会命中被RequestMapping注解值为/test的Controller； 
	     * 只有在访问/test.pdf时才能正常访问，其它任何的/test.txt或者/test.doc等均会报404； 
	     * 默认情况下该值是False
	     */
	    configurer.setUseRegisteredSuffixPatternMatch(false);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

}
