package com.example.hoteltask.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable配置类
 */
@Configuration
public class FlowableConfig {

    /**
     * 配置流程引擎
     */
    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> engineConfigurationConfigurer() {
        return engineConfiguration -> {
            // 设置流程图字体
            engineConfiguration.setActivityFontName("宋体");
            engineConfiguration.setLabelFontName("宋体");
            engineConfiguration.setAnnotationFontName("宋体");
            
            // 设置自动部署流程定义
            engineConfiguration.setDeploymentMode("single-resource");
            
            // 设置流程定义缓存
            engineConfiguration.setProcessDefinitionCacheLimit(100);
        };
    }
} 