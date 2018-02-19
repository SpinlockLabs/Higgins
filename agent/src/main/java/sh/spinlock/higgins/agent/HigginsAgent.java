package sh.spinlock.higgins.agent;

import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.agent.connection.HostConnection;
import sh.spinlock.higgins.agent.file.RootDirectory;

import java.io.IOException;

public class HigginsAgent {
    @Getter
    private static HigginsAgent instance;

    @Getter
    @Setter
    private HostConnection connection;

    @Getter
    private RootDirectory rootDirectory;

    private void initialize() {
        rootDirectory = new RootDirectory("higgins-agent/");
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

    public static void initializeInstance(HigginsAgent agent) {
        instance = agent;
        instance.initialize();
    }
}
