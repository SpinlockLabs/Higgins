package sh.spinlock.higgins.web.agent.connection;

public abstract class RemoteAgentConnection {
    public abstract void receive(byte[] bytes);
    public abstract void send(byte[] bytes);
}
