package com.thj.wxorder;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/6/28 14:18
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {


    @Test
    public void test1() {
        log.debug("debugger...");
        log.info("info.....");
        log.error("error....");
    }
}
