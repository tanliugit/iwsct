package com.letsun.frame.core.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.letsun.frame.core.mapper.BaseMapper;

/**
 * @Desc MybatisPlus配置  
 * @author YY  
 * @date 2018年4月12日
 */
@EnableTransactionManagement
@Configuration
@MapperScan(value="com.letsun.**.mapper*",markerInterface=BaseMapper.class)
public class MybatisPlusConfig {

    /**
     * 本地开发的时候打开
     *  mybatis-plus SQL执行效率插件【生产环境可以关闭】
     * @return
     */
    @Bean
    @Profile("dev")
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLocalPage(true);
        return paginationInterceptor;
    }

}
