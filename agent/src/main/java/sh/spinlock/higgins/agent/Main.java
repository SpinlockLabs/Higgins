package sh.spinlock.higgins.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.agent.connection.SocketHostConnection;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("Starting Higgins Agent");

        HigginsAgent agent = new HigginsAgent();
        HigginsAgent.initializeInstance(agent);
        HigginsAgent.getInstance().setConnection(new SocketHostConnection("localhost", 6081));
        agent.start();
    }
}
