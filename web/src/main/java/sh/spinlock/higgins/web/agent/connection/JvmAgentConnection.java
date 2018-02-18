package sh.spinlock.higgins.web.agent.connection;

import sh.spinlock.higgins.agent.HigginsAgent;
import sh.spinlock.higgins.agent.connection.JvmHost;

public class JvmAgentConnection extends RemoteAgentConnection {
    private HigginsAgent agent;

    public JvmAgentConnection() {
        agent = new HigginsAgent(new JvmHost(this));
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
