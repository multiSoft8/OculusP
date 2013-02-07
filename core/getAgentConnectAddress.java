/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */

import org.zeromq.ZMQ;

public class getAgentConnectAddress implements Runnable {
    
    public static String agentName;
    
    getAgentConnectAddress (String _agentName) {
        agentName = _agentName;
    }
    
    public void run () {
        String sendString = new String();
        String recvString = new String();
        byte [] recByte;
        System.out.println("Core Thread trying to get agent address..");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket mCore = context.socket(ZMQ.REQ);
        mCore.connect("tcp://192.168.200.1:5002");
        while(true) {
            try {
                sendString = "GetAgentAddress: "+agentName;
                mCore.send(sendString.getBytes(),0);
                System.out.println("Core-Thread-getAgentConnectAddress - Sent Request");
                recByte = mCore.recv(0);
                recvString = new String(recByte,0,recByte.length);
                if(!recvString.isEmpty() && recvString.contains("AgentFound.Address=")) {
                    String agentAddress = recvString.split("=")[1];
                    System.out.println("Core-Thread-getAgentConnectAddress - AgentAddress is "+agentAddress);
                }
                else if(recvString.contains("Sorry No agents registered.")) {
                    System.out.println("Core-Thread-getAgentConnectAddress - No Agents are registered do far");
                }
                else if(recvString.contains("Unable to find agent.")) {
                    System.out.println("Core-Thread-getAgentConnectAddress - Agent "+agentName+" not found on broker list");
                }
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println("Catch exception. Problem in getting the agent address");
                System.exit(1);
            }
        }
    }
}
