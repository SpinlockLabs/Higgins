package sh.spinlock.higgins.web.models;

import sh.spinlock.higgins.host.agent.Agent;

public class AgentModel {
    public final String name;

    public AgentModel(Agent agent) {
        this.name = agent.getName();
    }
}
