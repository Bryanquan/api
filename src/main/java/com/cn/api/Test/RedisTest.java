package com.cn.api.Test;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisTest {
    public static final Log logger = LogFactory.getLog(RedisTest.class);
    public static final Jedis redisClient = new Jedis("localhost");
    @Test
    public  void  testHash(){
        Map map = new HashMap();
        map.put("name","xiaoming");
        map.put("phone","123");
        redisClient.hmset("person", map);
        logger.info(redisClient.hmget("person","phone","name"));
    }

    @Test
    public  void  testString(){
        redisClient.set("test","success");
        logger.info(redisClient.get("test"));
    }

    @Test
    void testList(){
        redisClient.lpush("helloWord","2");
        redisClient.lpush("site-list", "Runoob");
        redisClient.lpush("site-list", "Google");
        redisClient.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = redisClient.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }
        logger.info(redisClient.llen("site-list"));
        redisClient.del("helloWord");
        redisClient.del("site-list");
    }
    @Test
    public void testSet(){
        redisClient.sadd("set","hashset","treeset");
        logger.info(redisClient.smembers("set"));
    }

}
