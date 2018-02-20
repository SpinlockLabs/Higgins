package sh.spinlock.higgins.host;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.host.agent.AgentManager;
import sh.spinlock.higgins.host.connection.AgentTcpServer;

public final class HigginsHost {
    private static final Logger LOG = LogManager.getLogger(HigginsHost.class);

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
        LOG.info("Starting Higgins Host Server");
        agentTcpServer.start();
    }

    public void shutdown() {
        LOG.info("Shutting Down Higgins Host Server");
        agentManager.disconnectAll();
        agentTcpServer.stop();
    }

    public static void initializeInstance(HigginsHost host) {
        instance = host;
    }
}

