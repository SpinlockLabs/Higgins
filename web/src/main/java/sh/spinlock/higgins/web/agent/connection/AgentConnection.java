package sh.spinlock.higgins.web.agent.connection;

public abstract class AgentConnection {
    public abstract void receive(byte[] bytes);
    public abstract void send(byte[] bytes);
}
