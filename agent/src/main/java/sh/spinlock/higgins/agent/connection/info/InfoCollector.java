package sh.spinlock.higgins.agent.connection.info;

public final class InfoCollector {
    public static AgentInfo collectAgentInfo() {
        AgentInfo agentInfo = new AgentInfo();

        collectSysProps(agentInfo);

        return agentInfo;
    }

    private static void collectSysProps(AgentInfo agentInfo) {
        for (String name : System.getProperties().stringPropertyNames()) {
            agentInfo.getSystemProperties().put(name, System.getProperty(name));
        }
    }
}
