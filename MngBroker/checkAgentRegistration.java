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


public class checkAgentRegistration implements Runnable {
    
    public void run () {
        int i; 
        System.out.println("Start the check agents Thread " + globalVariables.agentListIndex + "agents");
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
            try {
                Thread.sleep(5000);
            }
            catch (Exception e) {
                System.out.println("...Error...");
            }
        }
    }
}
