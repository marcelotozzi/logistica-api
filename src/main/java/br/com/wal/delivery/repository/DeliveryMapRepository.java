package br.com.wal.delivery.repository;

import br.com.wal.delivery.exception.RepositoryException;
import br.com.wal.delivery.model.DeliveryMap;
import br.com.wal.delivery.repository.generator.RedisKeyGenerator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.PreDestroy;

/**
 * Created by marcelotozzi on 01/09/14.
 */
@Repository
public class DeliveryMapRepository {
    private static final Logger LOGGER = Logger.getLogger(DeliveryMapRepository.class);
    private final JedisPool pool;

    private String host = "pub-redis-19856.us-east-1-4.3.ec2.garantiadata.com";
    private int port = 19856;
    private int timeout = 30000;
    private String pass = "logistica";

    public DeliveryMapRepository() {
        this.pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, pass);
    }

    @PreDestroy
    public void preDestroy() {
        this.pool.destroy();
    }

    public String register(DeliveryMap deliveryMap) throws RepositoryException {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();

            ObjectMapper mapper = new ObjectMapper();

            String token = RedisKeyGenerator.generate(20);
            String key = RedisKeyGenerator.mount("deliverymap", token);

            String value = mapper.writeValueAsString(deliveryMap);

            jedis.set(key, value);

            return token;
        } catch (JedisException e) {
            String message = "Erro ao acessar o Redis";
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        } catch (Exception e) {
            String message = "Erro ao register a Malha";
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public DeliveryMap show(String deliveryMapToken) throws RepositoryException {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();

            String key = RedisKeyGenerator.mount("deliverymap", deliveryMapToken);

            String value = jedis.get(key);

            ObjectMapper mapper = new ObjectMapper();

            DeliveryMap deliveryMap = mapper.readValue(value, DeliveryMap.class);

            return deliveryMap;
        } catch (JedisException e) {
            String message = "Erro ao acessar o Redis";
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        } catch (Exception e) {
            String message = "Erro ao buscar o Mapa";
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}