package com.szwujie.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MyConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             *  拦截
             */
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry
//                        .addInterceptor(new TestInterceptor())
//                        .addPathPatterns("/**")
//                        .excludePathPatterns("/test/login", "/aaa/login");
//            }

            /**
             *  页面跳转
             */
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry
//                        .addViewController("/toLogin")
//                        .setViewName("login");
//            }

            /**
             * 配置访问静态资源
             */
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry
//                        .addResourceHandler("/my/**")
//                        .addResourceLocations("classpath:/my/");
//            }

            /**
             * 默认静态资源处理
             */
//            @Override
//            public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//                configurer.enable();
//                configurer.enable("defaultServletName");
//            }

            /**
             * 配置请求视图映射
             */
//            @Bean
//            public InternalResourceViewResolver resourceViewResolver() {
//                InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
//                //请求视图文件的前缀地址
//                internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
//                //请求视图文件的后缀
//                internalResourceViewResolver.setSuffix(".jsp");
//                return internalResourceViewResolver;
//            }

            /**
             * 视图配置
             */
//            @Override
//            public void configureViewResolvers(ViewResolverRegistry registry) {
//                registry.viewResolver(resourceViewResolver());
//                /*registry.jsp("/WEB-INF/jsp/",".jsp");*/
//            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        //.allowedHeaders("x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client")
                        .allowedHeaders("*")
                        //设置允许跨域请求的域名
                        .allowedOrigins("*")
                        //是否允许证书 不再默认开启
                        .allowCredentials(true)
                        //设置允许的方法
                        .allowedMethods("*")
                        //跨域允许时间
                        .maxAge(3600)
                ;
            }
        };
    }

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurer() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                        // 鉴权
                    .authorizeRequests()
                    .antMatchers("/resources/**", "/signup", "/about").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        // 自定义参数名称，与login.html中的参数对应
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureForwardUrl("/login?error")
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/index")
                    .permitAll()
                    .and()
                    .httpBasic()
                    .disable();
            }

            /**
             * 认证信息
             * @param auth
             * @throws Exception
             */
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth
                    .inMemoryAuthentication()
                    .withUser("admin").password("admin").roles("USER");
            }


            @Override
            public void configure(WebSecurity web) throws Exception {
                // 设置请求权限验证忽略接口
                web.ignoring().antMatchers("/v2/api-docs",
                        "/swagger-ui.html",
                        "/webjars/springfox-swagger-ui/**",
                        "/swagger-resources/**",
                        //以上为swagger相关内容
                        "/error/**",
                        "/user/**",
                        // 异常展示
                        "/errorShow/**")
                        .antMatchers(HttpMethod.POST,
                                // 登录相关接口
                                "/login"
                        )
                        .antMatchers(HttpMethod.PUT,
                                // 密码重置
                                "/user/resetPassword")
                        .antMatchers(HttpMethod.GET,
                                "/sendMessage/resetPassword"
                        )
                ;
            }

        };
    }

}
