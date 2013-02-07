/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */

import org.zeromq.ZMQ;


public class agentRegistration implements Runnable {
    
    public static String identity;
    public static boolean registered;
    
    agentRegistration (String _identity) {
        identity = _identity;
        registered = false;
    }
    
    public void run ()
    {
        String sendString = new String();
        String recvString = new String();
        byte [] recByte;
        System.out.println("My Agent Identity = "+identity);
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket mAgent = context.socket(ZMQ.REQ);
        mAgent.connect("tcp://192.168.200.1:5000");
        try {
            while(true)
            {
                sendString = "Register this agent.";
                mAgent.send(sendString.getBytes(),0);
                System.out.println("Registration process Started. Sent request.");
                recByte = mAgent.recv(0);
                recvString = new String(recByte,0,recByte.length);
                if(!recvString.isEmpty() && recvString.contentEquals("Your agent name please"))
                {
                    System.out.println("Received acceptance for registration completing");
                    sendString = "MyAgentName " + identity;
                    mAgent.send(sendString.getBytes(),0);
                    recByte = mAgent.recv(0);
                    recvString = new String(recByte,0,recByte.length);
                    if(recvString.contentEquals("OK") || recvString.contentEquals("OKK"))
                    {
                        System.out.println("The agent is now on mngBroker's list.");
                        if(registered == false)
                        {
                            sendString = "AgentConnectAddress: tcp://192.168.200.1:5500";
                            mAgent.send(sendString.getBytes(),0);
                            recByte = mAgent.recv(0);
                            recvString = new String(recByte,0,recByte.length);
                            if(recvString.contentEquals("Got agent address"))
                            {
                                System.out.println("The broker has agents connect address for core.");
                            }
                            else
                            {
                                System.out.println("The broker was unable to receive the agents address for core. This is an issue. Will Exit.");
                                System.exit(1);
                            }
                        }
                        registered = true;
                    }
                    else
                    {
                        System.out.println("The agent was unable to register.");
                        registered = false;
                    }
                }   
                else
                {
                    System.out.println("Unable to complete the registration process. Exit");
                    System.exit(1);
                }
                Thread.sleep(5000);
            }
        }catch (Exception e) {
            System.out.println("Problem in register agent...");
            System.exit(1);
        }
    }
}
