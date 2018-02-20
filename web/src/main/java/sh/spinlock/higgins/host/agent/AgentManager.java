package sh.spinlock.higgins.host.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AgentManager {
    private static final Logger LOG = LogManager.getLogger(AgentManager.class);

    private final List<Agent> agents;

    public AgentManager() {
        agents = new ArrayList<>();
    }

    public void addAgent(Agent agent) {
        LOG.info("Registering {}", agent.getName());
        synchronized (agents) {
            agents.add(agent);
        }
    }

    public void removeAgent(Agent agent) {
        LOG.info("Deregistering {}", agent.getName());
        synchronized (agents) {
            agents.remove(agent);
        }
    }

    public List<Agent> getAgents() {
        synchronized (agents) {
            return Collections.unmodifiableList(agents);
        }
    }

    public void disconnectAll() {
        LOG.info("Disconnecting all agents.");
        synchronized (agents) {
            for (Agent agent : agents) {
                agent.disconnect();
            }
        }
    }
}
