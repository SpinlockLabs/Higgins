package sh.spinlock.higgins.agent.connection.info;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class AgentInfo {
    @Getter
    private final Map<String, String> systemProperties;

    public AgentInfo() {
        systemProperties = new HashMap<>();
    }
}
