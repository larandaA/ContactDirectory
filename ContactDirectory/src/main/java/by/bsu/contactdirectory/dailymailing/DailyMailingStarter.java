package by.bsu.contactdirectory.dailymailing;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import org.quartz.TriggerBuilder;

import by.bsu.contactdirectory.service.EmailService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by Alexandra on 15.09.2016.
 */
public class DailyMailingStarter {

    public static class DailyMailJob implements Job {

        private static EmailService emailService = new EmailService();

        public DailyMailJob() { }



        public void execute(JobExecutionContext context) throws JobExecutionException {
            emailService.sendBirthdayList();
        }
    }

    public static void start() {
        Logger logger = LogManager.getLogger(DailyMailingStarter.class);
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = newJob(DailyMailJob.class)
                .withIdentity("dailyMailing", "dailyEvents")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
           //     .withSchedule(dailyAtHourAndMinute(20, 47))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(60 * 60 * 24)
                        .repeatForever())
                .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] argv) {
        start();
    }
}