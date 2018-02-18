package sh.spinlock.higgins.agent.connection;

public abstract class BaseHost {
    public abstract boolean connect();
    public abstract void receiveString(String str);
    public abstract void sendString(String str);
}
