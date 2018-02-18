package sh.spinlock.higgins.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.spinlock.higgins.web.agent.Agent;
import sh.spinlock.higgins.web.agent.connection.JvmAgentConnection;

@SpringBootApplication
public class HigginsApplication {
    public static void main(String[] args) {
        Agent agent = new Agent("local", new JvmAgentConnection());
        agent.getConnection().sendString("Hello!");

        SpringApplication.run(HigginsApplication.class, args);
    }
}
