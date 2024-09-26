package kr.co.duck.config;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.duck.service.PlaylistService;
import kr.co.duck.service.ManiaDBService; // 추가한 서비스

import kr.co.duck.beans.MemberBean;
import kr.co.duck.interceptor.CheckLoginInterceptor;
import kr.co.duck.interceptor.TopMenuInterceptor;
import kr.co.duck.mapper.MemberMapper;
import kr.co.duck.mapper.TopMenuMapper;
import kr.co.duck.service.TopMenuService;

@Configuration
@EnableWebMvc


@ComponentScan("kr.co.duck.dao")
@ComponentScan("kr.co.duck.controller")
@ComponentScan("kr.co.duck.service")
@MapperScan("kr.co.duck.mapper")
@PropertySource("/WEB-INF/properties/db.properties") 
public class ServletAppContext implements WebMvcConfigurer {

    @Value("${db.classname}")
    private String db_classname;

    @Value("${db.url}")
    private String db_url;

    @Value("${db.username}")
    private String db_username;

	@Value("${db.password}")
	private String db_password;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@Resource(name = "loginMemberBean")
	private MemberBean loginMemberBean;
	

    // Controller의 메서드가 반환하는 jsp의 이름 앞뒤에 경로와 확장자를 붙혀주도록 설정
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        WebMvcConfigurer.super.configureViewResolvers(registry);
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    // 정적 파일 경로 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/**").addResourceLocations("/resources/");
    }

    // 데이터베이스 접속 정보를 관리하는 Bean
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(db_classname);
        source.setUrl(db_url);
        source.setUsername(db_username);
        source.setPassword(db_password);

        return source;
    }

    // 쿼리문과 접속 정보를 관리하는 객체
    @Bean
    public SqlSessionFactory factory(BasicDataSource source) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(source);
        SqlSessionFactory factory = factoryBean.getObject();
        return factory;
    }


	@Bean
	public MapperFactoryBean<MemberMapper> getMemberMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<MemberMapper> factoryBean = new MapperFactoryBean<MemberMapper>(MemberMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<TopMenuMapper> factoryBean = new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);

		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService, loginMemberBean);

		InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
		reg1.addPathPatterns("/**");
		
		
		 CheckLoginInterceptor checkLoginInterceptor = new
		 CheckLoginInterceptor(loginMemberBean); InterceptorRegistration reg2 =
		 registry.addInterceptor(checkLoginInterceptor);
		 reg2.addPathPatterns("/member/modify", "/member/logout", "/board/*");
		 reg2.excludePathPatterns("/board/main");
		 
		
		
	}
	
	//property 정보를 읽어들이는 Bean 등록
	@Bean
	public static PropertySourcesPlaceholderConfigurer PropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}


    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
        res.setDefaultEncoding("utf-8");
        res.setBasenames("/WEB-INF/properties/error_message");
        return res;
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


    // PlaylistService Bean
    @Bean
    public PlaylistService playlistService() {
        return new PlaylistService();
    }

    // ManiaDBService Bean 추가
    @Bean
    public ManiaDBService maniaDBService() {
        return new ManiaDBService();
    }


}
