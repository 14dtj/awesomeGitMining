package edu.nju.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Dora on 2016/5/17.
 */
@Component("taskJob")
public class UpdateDBTask {
    private static final Logger LOG = Logger.getLogger(UpdateDBTask.class);

    //每分钟的10秒执行
    @Scheduled(cron = "10 * * * * ?")
    public void job(){

       // LOG.info("hello。。。。");
    }


}
