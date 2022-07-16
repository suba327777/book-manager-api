package com.book.manager.bookmanager.presentation.config

import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession
class HttpSessionConfig {
    @Bean
    //Redisとのコネクションを生成する際に使用するJedisConnectionFactoryクラスのインスタンスを生成
    fun connectionFactory():JedisConnectionFactory{
        return JedisConnectionFactory()
    }
}