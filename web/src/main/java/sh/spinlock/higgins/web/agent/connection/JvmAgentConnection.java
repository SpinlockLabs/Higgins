package sh.spinlock.higgins.web.agent.connection;

import sh.spinlock.higgins.agent.Agent;
import sh.spinlock.higgins.agent.connection.JvmHost;

public class JvmAgentConnection extends RemoteAgentConnection {
    private Agent agent;

    public JvmAgentConnection() {
        agent = new Agent();
        agent.setConnection(new JvmHost(this));
        Agent.initializeInstance(agent);
    }

    @Override
    public void receiveString(String str) {
        System.out.println("Received from agent: " + str);
    }

    @Override
    public void sendString(String str) {
        agent.getConnection().receiveString(str);
    }
}
