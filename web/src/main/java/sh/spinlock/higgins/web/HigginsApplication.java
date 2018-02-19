package sh.spinlock.higgins.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.spinlock.higgins.agent.job.Job;
import sh.spinlock.higgins.agent.task.shell.UnixShellTask;
import sh.spinlock.higgins.web.agent.Agent;
import sh.spinlock.higgins.web.agent.connection.JvmAgentConnection;

import java.io.IOException;

@SpringBootApplication
public class HigginsApplication {
    public static void main(String[] args) {
        Agent agent = new Agent("local", new JvmAgentConnection());
        agent.getConnection().sendString("Hello!");

        Job job = null;
        try {
            job = Job.createJob("Minetweak");
        } catch (IOException e) {
            System.err.println("Failed to create job!");
            e.printStackTrace();
        }

        if (job != null) {
            job.addTask(new UnixShellTask(job));

            job.run();
        }

        //SpringApplication.run(HigginsApplication.class, args);
    }
}
