package com.atguigu.springboot;

import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate; //操作k-v都是字符串
    @Autowired
    RedisTemplate redisTemplate; //k-v都是对象的
    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;
    @Autowired
    RedisCacheManager redisCacheManager;
    /**
     * Redisc常见的五大数据类型
     * String(字符串）、List(列表)、Set(集合)、Hash(散列)、ZSet(有序集合)
     * String(字符串） :stringRedisTemplate.opsForValue()
     * List(列表)      :stringRedisTemplate.opsForList()
     * Set(集合)       :stringRedisTemplate.opsForSet()
     * Hash(散列)      :stringRedisTemplate.opsForHash()
     * ZSet(有序集合)  :stringRedisTemplate.opsForZSet()
     * @return
     * @author wangkang
     * 2019/7/14 20:17
     */
    @Test
    public void testRedisString(){
//        stringRedisTemplate.opsForValue().append("msg","hello world");
//        String msg = stringRedisTemplate.opsForValue().get("msg");
//        System.out.println(msg);
        stringRedisTemplate.opsForList().leftPush("list","1");
        stringRedisTemplate.opsForList().leftPush("list","2");
    }

    @Test
    public void testRedisObject(){
        Employee employee = employeeMapper.getEmpById(1);
        //默认如果保存对象，使用jdk序列化机制（implements Serializable），序列化后的数据保存到redis中。
//        redisTemplate.opsForValue().set("emp-1",employee);
        //1、将数据以json的方式保存
        //（1）自己将对象转为json
        //（2）redisTemplate默认的序列化规则,改变默认的序列化器
//        empRedisTemplate.opsForValue().set("emp-2",employee);
        //2、代码的形式操作缓存
        Cache cache = redisCacheManager.getCache("emp");
        cache.put("emp-3",employee);
    }
    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.getEmpById(1);
        System.out.println(employee);
    }
}
