/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */

import org.zeromq.ZMQ;

public class listenAgentAddressRequests implements Runnable {
    
    public void run () {
        String sendString = new String();
        String recvString = new String();
        byte [] recByte;
        System.out.println("MngBroker listen for requests from core for agent address..");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket mBroker = context.socket(ZMQ.REP);
        mBroker.bind("tcp://192.168.200.1:5002");
        while(true) {
            recvString = new String(mBroker.recv(0));
            if(globalVariables.agentListIndex < 1) {
                sendString = "Sorry No agents registered.";
                mBroker.send(sendString.getBytes(),0);
            }
            else {
                if (recvString.contains("GetAgentAddress:")) {
                    String agentName = recvString.split(" ")[1];
                    String agentAddress = new String(); int i;
                    for(i=0;i<globalVariables.agentListIndex;i++) {
                        if(globalVariables.agentList[i].contentEquals(agentName)) {
                            agentAddress = globalVariables.agentList[i];
                            break;
                        }
                    }
                    if(agentAddress.isEmpty()) {
                        sendString = "Unable to find agent.";
                    }
                    else {
                        sendString = "AgentFound.Address="+globalVariables.agentConnectList[i];
                    }
                    mBroker.send(sendString.getBytes(), 0);
                }
            }
        }
    }
}
