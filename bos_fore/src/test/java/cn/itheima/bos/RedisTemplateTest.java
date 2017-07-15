package cn.itheima.bos;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月14日  下午8:33:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTemplateTest {
        
        @Resource(name="redisTemplate")
        private RedisTemplate<String, String> redisTemplate;
        
        @Test
        public void test() {
                String string = redisTemplate.opsForValue().get("activeCode");
                System.out.println(string);
        }
}
