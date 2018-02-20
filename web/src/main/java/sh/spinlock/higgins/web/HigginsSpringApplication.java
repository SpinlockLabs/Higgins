package sh.spinlock.higgins.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.spinlock.higgins.host.HigginsHost;

@SpringBootApplication
public class HigginsSpringApplication {
    private static final Logger LOG = LogManager.getLogger(HigginsSpringApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting Higgins Web Application Server");

        HigginsHost host = new HigginsHost();
        HigginsHost.initializeInstance(host);

        try {
            HigginsHost.getInstance().start();
        } catch (Exception e) {
            LOG.fatal("Could not start Higgins server!", e);
            System.exit(1);
        }

        SpringApplication.run(HigginsSpringApplication.class, args);
    }
}
