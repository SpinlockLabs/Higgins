package sh.spinlock.higgins.host;

import lombok.Getter;
import sh.spinlock.higgins.host.agent.AgentManager;
import sh.spinlock.higgins.host.connection.AgentTcpServer;

public final class HigginsHost {
    @Getter
    private static HigginsHost instance;

    @Getter
    private final AgentManager agentManager;

    @Getter
    private final AgentTcpServer agentTcpServer;

    public HigginsHost() {
        agentManager = new AgentManager();
        // TODO: Configurable agent port
        agentTcpServer = new AgentTcpServer(6081);
    }

    public void start() throws Exception {
        agentTcpServer.start();
    }

    public void shutdown() {
        agentManager.disconnectAll();
        agentTcpServer.stop();
    }

    public static void initializeInstance(HigginsHost host) {
        instance = host;
    }
}

