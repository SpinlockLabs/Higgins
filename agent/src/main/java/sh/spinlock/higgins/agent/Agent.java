package sh.spinlock.higgins.agent;

import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.agent.connection.BaseHost;
import sh.spinlock.higgins.agent.file.RootDirectory;

import java.io.IOException;

public class Agent {
    @Getter
    private static Agent instance;

    @Getter
    @Setter
    private BaseHost connection;

    @Getter
    private RootDirectory rootDirectory;

    public void initialize() {
        rootDirectory = new RootDirectory("higgins/");
        try {
            rootDirectory.initialize();
        } catch (IOException e) {
            System.err.println("Initializing root directory failed.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void connect() {
        connection.connect();
    }

    public static void initializeInstance(Agent agent) {
        instance = agent;
        instance.initialize();
    }
}
