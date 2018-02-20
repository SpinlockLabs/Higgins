package sh.spinlock.higgins.host.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AgentManager {
    private final List<Agent> agents;

    public AgentManager() {
        agents = new ArrayList<>();
    }

    public void addAgent(Agent agent) {
        synchronized (agents) {
            agents.add(agent);
        }
    }

    public void removeAgent(Agent agent) {
        synchronized (agents) {
            agents.remove(agent);
        }
    }

    public List<Agent> getAgents() {
        synchronized (agents) {
            return Collections.unmodifiableList(agents);
        }
    }
}
