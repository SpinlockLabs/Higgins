package sh.spinlock.higgins.host;

import lombok.Getter;
import sh.spinlock.higgins.host.agent.AgentManager;

public class HigginsHost {
    @Getter
    private static HigginsHost instance;

    @Getter
    private final AgentManager agentManager;

    public HigginsHost() {
        agentManager = new AgentManager();
    }

    public static void initializeInstance(HigginsHost host) {
        instance = host;
    }
}

