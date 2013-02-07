/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */

import java.sql.Timestamp;
import java.util.Date;


public class checkRegistration implements Runnable {
    
    public void run () {
        int i; 
        System.out.println("Start the check for agents and cores..." + globalVariables.agentListIndex + "agents");
        Timestamp curTime;
        while(true) {
            curTime = new Timestamp(new Date().getTime());
            System.out.println("Nr of agents at "+curTime+" = "+globalVariables.agentListIndex);
            for(i=0;i<globalVariables.agentListIndex;i++) {
                System.out.println("----Time that passed for agent: " + globalVariables.agentList[i] + " Diff="+(curTime.getTime() - globalVariables.agentListTimestamp[i].getTime()));
                if(curTime.getTime() - globalVariables.agentListTimestamp[i].getTime() > 30000) {
                    System.out.println("----Too much time has passed for agent " + globalVariables.agentList[i] + " - will be eliminated");
                }
            }
            curTime = new Timestamp(new Date().getTime());
            System.out.println("Nr of cores at "+curTime+" = "+globalVariables.coreListIndex);
            for(i=0;i<globalVariables.coreListIndex;i++)
            {
                System.out.println("----Time that passed for core: " + globalVariables.coreList[i] + " Diff="+(curTime.getTime() - globalVariables.coreListTimestamp[i].getTime()));
                if(curTime.getTime() - globalVariables.coreListTimestamp[i].getTime() > 30000) {
                    System.out.println("----Too much time has passed for agent " + globalVariables.coreList[i] + " - will be eliminated");
                }
            }
            try {
                Thread.sleep(5000);
            }
            catch (Exception e) {
                System.out.println("...Error...");
            }
        }
    }
}
