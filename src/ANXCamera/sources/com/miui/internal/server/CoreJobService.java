package com.miui.internal.server;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import java.util.HashSet;

public class CoreJobService extends JobService {
    private static final long INTERVAL_DAY = 86400000;
    private static final int JOB_ID_DAILY_ANY_NETWORK = 1;
    private static final int JOB_ID_DAILY_UNMERTERED_NETWORK = 2;
    private static final String JOB_SCHEDULER_SERVICE = "jobscheduler";
    private static final String TAG = "CoreJobService";

    private static abstract class AbstractJob implements Runnable {
        private JobParameters params;
        private JobService service;

        public AbstractJob(JobService jobService, JobParameters jobParameters) {
            this.service = jobService;
            this.params = jobParameters;
        }

        public void run() {
            this.service.jobFinished(this.params, false);
        }
    }

    private static class AnyNetworkJob extends AbstractJob {
        public AnyNetworkJob(JobService jobService, JobParameters jobParameters) {
            super(jobService, jobParameters);
        }

        @Override // com.miui.internal.server.CoreJobService.AbstractJob
        public void run() {
            DataUpdateManager.getInstance().update();
            super.run();
        }
    }

    private static class UnmerteredNetworkJob extends AbstractJob {
        public UnmerteredNetworkJob(JobService jobService, JobParameters jobParameters) {
            super(jobService, jobParameters);
        }

        @Override // com.miui.internal.server.CoreJobService.AbstractJob
        public void run() {
            DataUpdateManager.getInstance().update();
            super.run();
        }
    }

    private void executeJob(AbstractJob abstractJob) {
        new Thread(abstractJob).start();
    }

    private static JobInfo getDailyAnyNetworkJob(Context context) {
        return getDailyNetworkJob(context, 1, 1);
    }

    private static JobInfo getDailyNetworkJob(Context context, int i, int i2) {
        return new JobInfo.Builder(i, new ComponentName(context, CoreJobService.class)).setPeriodic(86400000).setRequiredNetworkType(i2).build();
    }

    private static JobInfo getDailyUnmerteredNetworkJob(Context context) {
        return getDailyNetworkJob(context, 2, 2);
    }

    public static void scheduleJobs(Context context) {
        HashSet hashSet = new HashSet();
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        if (jobScheduler == null) {
            Log.e(TAG, "scheduleJobs: get JobScheduler error!");
            return;
        }
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            hashSet.add(Integer.valueOf(jobInfo.getId()));
        }
        if (!hashSet.contains(1)) {
            jobScheduler.schedule(getDailyAnyNetworkJob(context));
        }
    }

    public boolean onStartJob(JobParameters jobParameters) {
        if (jobParameters == null) {
            return false;
        }
        int jobId = jobParameters.getJobId();
        if (jobId == 1) {
            executeJob(new AnyNetworkJob(this, jobParameters));
            return true;
        } else if (jobId != 2) {
            Log.e(TAG, "Unknown job id: " + jobParameters.getJobId());
            return false;
        } else {
            executeJob(new UnmerteredNetworkJob(this, jobParameters));
            return true;
        }
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
