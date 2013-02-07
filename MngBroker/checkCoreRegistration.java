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

public class checkCoreRegistration implements Runnable {
        public void run () {
        int i; 
        System.out.println("Start the check cores Thread " + globalVariables.coreListIndex + "cores");
        Timestamp curTime;
        while(true) {
            curTime = new Timestamp(new Date().getTime());
            System.out.println("Nr of cores at "+curTime+" = "+globalVariables.coreListIndex);
            for(i=0;i<globalVariables.coreListIndex;i++) {
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
