package by.bsu.contactdirectory.dailymailing;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import org.quartz.TriggerBuilder;

import by.bsu.contactdirectory.service.EmailService;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class DailyMailingStarter {

    static class DailyMailJob implements Job {

        private static EmailService emailService = new EmailService();

        public DailyMailJob() { }

        public void execute(JobExecutionContext context) throws JobExecutionException {
            //emailService.sendBirthdayList();
            System.out.println("Hello");
        }
    }

    public static void start() {


        JobDetail job = newJob(DailyMailJob.class)
                .withIdentity("dailyMailing", "dailyEvents")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                //.withSchedule(dailyAtHourAndMinute(19, 23))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
            System.out.println("Started job");
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }
    }
}