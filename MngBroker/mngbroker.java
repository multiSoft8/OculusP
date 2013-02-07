/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */
/*import java.lang.Exception;*/
import java.sql.Timestamp;

public class mngbroker {
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        int i;
        globalVariables.agentList = new String[10]; 
        globalVariables.agentListTimestamp = new Timestamp[10];
        globalVariables.agentConnectList = new String[10];
        globalVariables.agentListIndex=0;
        globalVariables.coreList = new String[10];
        globalVariables.coreListTimestamp = new Timestamp[10];
        globalVariables.coreListIndex=0;
        registrationListener listenAll = new registrationListener();
        Thread listenAllThread1 = new Thread(listenAll);
        listenAllThread1.start();
        checkRegistration allChecker = new checkRegistration();
        Thread checkAllThread2 = new Thread(allChecker);
        checkAllThread2.start();
        listenAgentAddressRequests listenForReqAgentConn = new listenAgentAddressRequests();
        Thread listenCoreReqAgentConnThread5 = new Thread(listenForReqAgentConn);
        listenCoreReqAgentConnThread5.start();
        
        
/*        
        for(i=0;i<1000;i++) {
            System.out.println("--agentList="+globalVariables.agentListIndex);
            try {
                Thread.sleep(1000);
            }
            catch (Exception e) {
                System.out.println("...Error...");
            }
        }
    }
 * 
 */
    }
}