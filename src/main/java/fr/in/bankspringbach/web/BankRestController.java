package fr.in.bankspringbach.web;

import fr.in.bankspringbach.BankTransactionAnalyticsItemProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BankRestController {
    private JobLauncher jobLauncher;
    private Job job;
    @Autowired
    private BankTransactionAnalyticsItemProcessor analyticsItemProcessor;

    public BankRestController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @RequestMapping("/loadData")
    public BatchStatus load()throws Exception{
        Map< String, JobParameter> parameters = new HashMap<>();
        parameters.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters= new JobParameters(parameters);
        JobExecution jobExecution= jobLauncher.run(job,jobParameters);
        while (jobExecution.isRunning()){
            System.out.println("....");
        }
        return jobExecution.getStatus();

    }
    @RequestMapping("/analytics")
    public Map< String,Double> analytics(){
Map< String, Double> map = new HashMap<>();
map.put("totalCredit", analyticsItemProcessor.getTotalCredit());
map.put("totalDebit", analyticsItemProcessor.getTotalDebit());
return map;
    }
}
