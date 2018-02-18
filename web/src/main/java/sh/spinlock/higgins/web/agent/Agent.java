package sh.spinlock.higgins.web.agent;

import lombok.Getter;
import sh.spinlock.higgins.web.agent.connection.RemoteAgentConnection;

public class Agent {
    @Getter
    private String name;

    @Getter
    private RemoteAgentConnection connection;

    public Agent(String name, RemoteAgentConnection connection)
    {
        this.name = name;
        this.connection = connection;
    }
}
