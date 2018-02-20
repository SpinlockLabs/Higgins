package sh.spinlock.higgins.host.connection.agent;

import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.host.agent.Agent;

public abstract class AgentConnection {
    @Getter
    @Setter
    private Agent agent;

    public abstract void send(byte[] bytes);

    public void receive(byte[] bytes) {
    }

    public void close() {
    }
}
