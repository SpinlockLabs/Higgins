package sh.spinlock.higgins.agent;

import lombok.Getter;
import sh.spinlock.higgins.agent.connection.BaseHost;

public class HigginsAgent {
    @Getter
    private BaseHost connection;

    public HigginsAgent(BaseHost connection) {
        this.connection = connection;
    }

    public void connect() {
        connection.connect();
    }
}
