package sh.spinlock.higgins.host.agent;

import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.host.connection.agent.AgentConnection;

public class Agent {
    @Getter
    @Setter
    private String name;

    @Getter
    private AgentConnection connection;

    public Agent(AgentConnection connection)
    {
        this.connection = connection;
        connection.setAgent(this);
    }
}
