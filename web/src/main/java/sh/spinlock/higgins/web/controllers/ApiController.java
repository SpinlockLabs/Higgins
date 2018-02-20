package sh.spinlock.higgins.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sh.spinlock.higgins.host.HigginsHost;
import sh.spinlock.higgins.host.agent.Agent;
import sh.spinlock.higgins.web.models.AgentModel;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class ApiController {
    @RequestMapping("/agents")
    public List<AgentModel> getAgents() {
        List<AgentModel> agentModels = new ArrayList<>();

        for (Agent agent : HigginsHost.getInstance().getAgentManager().getAgents()) {
            agentModels.add(new AgentModel(agent));
        }

        return agentModels;
    }
}
