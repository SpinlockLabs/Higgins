package sh.spinlock.higgins.web.agent.connection;

public abstract class RemoteAgentConnection {
    public abstract void receiveString(String str);
    public abstract void sendString(String str);
}
