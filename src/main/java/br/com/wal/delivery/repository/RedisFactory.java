package br.com.wal.delivery.repository;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PreDestroy;

/**
 * Created by marcelotozzi on 03/09/14.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RedisFactory {
    private final JedisPool pool;

    private String host = "pub-redis-19856.us-east-1-4.3.ec2.garantiadata.com";
    private int port = 19856;
    private int timeout = 30000;
    private String pass = "logistica";

    public RedisFactory() {
        this.pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, pass);
    }

    @PreDestroy
    public void preDestroy() {
        this.pool.destroy();
    }

    public Jedis getResource() {
        return pool.getResource();
    }
}
