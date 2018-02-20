package sh.spinlock.higgins.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.spinlock.higgins.host.HigginsHost;

@SpringBootApplication
public class HigginsSpringApplication {
    public static void main(String[] args) {
        HigginsHost host = new HigginsHost();
        HigginsHost.initializeInstance(host);

        try {
            HigginsHost.getInstance().start();
        } catch (Exception e) {
            System.err.println("Could not start Higgins server!");
            e.printStackTrace();
            System.exit(1);
        }

        SpringApplication.run(HigginsSpringApplication.class, args);
    }
}
