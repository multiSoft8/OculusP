/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */

import java.sql.Timestamp;
import org.zeromq.ZMQ;
import java.util.Date;

public class registrationListener implements Runnable {
    
    public void displayAgentList () {
        int i;
        String output = new String();
        System.out.println("-----------------");
        System.out.println(output.format("AgentList now contains %d records: ", globalVariables.agentListIndex));
        for(i=0;i!=globalVariables.agentListIndex;i++)
        {
            System.out.println(output.format("--Agent nr. %d = ", i) + globalVariables.agentList[i] + " " + globalVariables.agentListTimestamp[i] + " Addr=" + globalVariables.agentConnectList[i]);
        }
        System.out.println("-----------------");
    }
    
    public void displayCoreList () {
        int i;
        String output = new String();
        System.out.println("-----------------");
        System.out.println(output.format("CoreList now contains %d records: ", globalVariables.coreListIndex));
        for(i=0;i!=globalVariables.coreListIndex;i++)
        {
            System.out.println(output.format("--Core nr. %d = ", i) + globalVariables.coreList[i] + " " + globalVariables.coreListTimestamp[i]);
        }
        System.out.println("-----------------");
    }
    
    public void run () { 
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket mBroker = context.socket(ZMQ.REP);
        mBroker.bind("tcp://192.168.200.1:5000");
        String sendString = new String();
        String recvString = new String();
        int i;
        while(true)
        {
            recvString = new String(mBroker.recv(0));
            if(recvString.contentEquals("Register this core."))
            {
                System.out.println("Received registration request.");
                /* start process to see if ok to continue the registration */
                sendString = "Your name please";
                mBroker.send(sendString.getBytes(),0);
            }
            else if(recvString.contains("MyCoreName "))
            {
                String tempCoreName = new String();
                boolean coreFound = false;
                System.out.println("Received the core name - "+recvString);
                tempCoreName = recvString.split(" ")[1];
                for(i=0;i<globalVariables.coreListIndex;i++)
                {
                    if(globalVariables.coreList[i].contentEquals(tempCoreName)) 
                    {
                        coreFound=true;
                        sendString = "OKK";
                        System.out.println("Core allready present");
                        globalVariables.coreListTimestamp[i] = new Timestamp(new Date().getTime());
                    }
                }
                if(coreFound == false) 
                {
                    sendString = "OK";
                    System.out.println("New core found");
                }
                mBroker.send(sendString.getBytes(),0);
                if(coreFound == false)
                {
                    globalVariables.coreList[globalVariables.coreListIndex] = tempCoreName;
                    globalVariables.coreListTimestamp[globalVariables.coreListIndex] = new Timestamp(new Date().getTime());
                    globalVariables.coreListIndex++;
                }
                displayCoreList();
            }
            else if(recvString.contentEquals("Register this agent."))
            {
                System.out.println("Received registration request.");
                /* start process to see if ok to continue the registration */
                sendString = "Your agent name please";
                mBroker.send(sendString.getBytes(),0);
            }
            else if(recvString.contains("MyAgentName "))
            {
                String tempAgentName = new String();
                boolean agentFound = false;
                System.out.println("Received the agents name - "+recvString);
                tempAgentName = recvString.split(" ")[1];
                for(i=0;i<globalVariables.agentListIndex;i++)
                {
                    if(globalVariables.agentList[i].contentEquals(tempAgentName)) 
                    {
                        agentFound=true;
                        sendString = "OKK";
                        System.out.println("Agent allready present");
                        globalVariables.agentListTimestamp[i] = new Timestamp(new Date().getTime());
                        mBroker.send(sendString.getBytes(),0);
                        break;
                    }
                }
                if(agentFound == false) 
                {
                    sendString = "OK";
                    System.out.println("New agent found");
                    mBroker.send(sendString.getBytes(),0);
                    globalVariables.agentList[globalVariables.agentListIndex] = tempAgentName;
                    globalVariables.agentListTimestamp[globalVariables.agentListIndex] = new Timestamp(new Date().getTime());            
                }
                displayAgentList();
            }
            else if(recvString.contains("AgentConnectAddress:"))
            {
                System.out.println("Got connect addres from agent for core:" + recvString);
                sendString = "Got agent address";
                globalVariables.agentConnectList[globalVariables.agentListIndex]=recvString.split(" ")[1];
                globalVariables.agentListIndex++;
                mBroker.send(sendString.getBytes(),0);
            }
        }
    } 
}
