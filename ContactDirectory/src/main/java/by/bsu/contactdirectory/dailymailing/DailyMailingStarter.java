package by.bsu.contactdirectory.dailymailing;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import by.bsu.contactdirectory.service.EmailService;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class DailyMailingStarter {

    static class DailyMailJob implements Job {

        private EmailService emailService;

        public DailyMailJob() {
            this.emailService = new EmailService();
        }

        public void execute(JobExecutionContext context) throws JobExecutionException {
            emailService.sendBirthdayList();
        }
    }

    public static void start() {

        JobDetail job = newJob(DailyMailJob.class)
                .withIdentity("dailyMailing", "dailyEvents")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(15, 0))
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}