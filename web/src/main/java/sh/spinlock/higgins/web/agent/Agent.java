package sh.spinlock.higgins.web.agent;

import lombok.Getter;
import sh.spinlock.higgins.web.agent.connection.AgentConnection;

public class Agent {
    @Getter
    private String name;

    @Getter
    private AgentConnection connection;

    public Agent(String name, AgentConnection connection)
    {
        this.name = name;
        this.connection = connection;
    }
}
