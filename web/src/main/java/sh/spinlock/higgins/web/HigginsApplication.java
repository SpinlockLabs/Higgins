package sh.spinlock.higgins.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.spinlock.higgins.host.HigginsHost;
import sh.spinlock.higgins.host.connection.AgentTcpServer;

import java.io.IOException;

@SpringBootApplication
public class HigginsApplication {
    public static void main(String[] args) {

        HigginsHost host = new HigginsHost();
        HigginsHost.initializeInstance(host);

        try {
            AgentTcpServer server = new AgentTcpServer();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //SpringApplication.run(HigginsApplication.class, args);
    }
}
