package com.sqshine;

import tk.mybatis.spring.annotation.MapperScan;//如果使用通用mapper，需要使用 tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sqshine
 */
@SpringBootApplication
@MapperScan("com.sqshine.readinglist.domain.mapper")
public class ReadinglistApplication implements CommandLineRunner {

    //private static final Logger logger = LoggerFactory.getLogger(ReadinglistApplication.class);

    //@Autowired
    //private StringRedisTemplate template;

    /*@Autowired
    private StateMachine<States, Events> stateMachine;*/

    public static void main(String[] args) {
        SpringApplication.run(ReadinglistApplication.class, args);
    }


    @Override
    public void run(String... args) {

       /*ValueOperations<String, String> ops = template.opsForValue();
        String key = "spring.boot.redis.test";
        if (template.hasKey(key)) {
            ops.getOperations().delete(key);
            logger.debug("删除 redis key [{}] 数据", key);
        }
        ops.set(key, "redis的值");
        //ops.setIfAbsent(key, "redis的值");
        logger.debug("设置 redis key [{}] , value = {} ", key, ops.get(key));*/


        /*stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);*/
    }
}
