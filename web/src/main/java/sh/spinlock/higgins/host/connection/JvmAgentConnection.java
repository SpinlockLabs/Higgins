package sh.spinlock.higgins.host.connection;

import sh.spinlock.higgins.agent.HigginsAgent;
import sh.spinlock.higgins.agent.connection.JvmHostConnection;
import sh.spinlock.higgins.host.agent.Agent;

public class JvmAgentConnection extends AgentConnection {
    private final HigginsAgent virtualAgent;

    public JvmAgentConnection(Agent agent) {
        super(agent);
        virtualAgent = new HigginsAgent();
        virtualAgent.setConnection(new JvmHostConnection(this));
        HigginsAgent.initializeInstance(virtualAgent);
    }

    @Override
    public void send(byte[] bytes) {
        super.send(bytes);
        virtualAgent.getConnection().receive(bytes);
    }
}
