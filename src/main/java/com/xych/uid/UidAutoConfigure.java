package com.xych.uid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.xych.uid.impl.CachedUidGenerator;
import com.xych.uid.impl.DefaultUidGenerator;
import com.xych.uid.impl.UidProperties;
import com.xych.uid.worker.DisposableWorkerIdAssigner;
import com.xych.uid.worker.WorkerIdAssigner;

/**
 * UID 的自动配置
 *
 * @author wujun
 * @date 2019.02.20 10:57
 */
@Configuration
@ConditionalOnClass({ DefaultUidGenerator.class, CachedUidGenerator.class })
@MapperScan({ "com.github.wujun234.uid.worker.dao" })
@EnableConfigurationProperties(UidProperties.class)
public class UidAutoConfigure {

	@Autowired
	private UidProperties uidProperties;

	@Bean
	@ConditionalOnMissingBean
	@Lazy
	DefaultUidGenerator defaultUidGenerator() {
		return new DefaultUidGenerator(uidProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	@Lazy
	CachedUidGenerator cachedUidGenerator() {
		return new CachedUidGenerator(uidProperties);
	}

	@Bean
	@ConditionalOnMissingBean
    WorkerIdAssigner workerIdAssigner() {
		return new DisposableWorkerIdAssigner();
	}
}
