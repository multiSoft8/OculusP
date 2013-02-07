/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */

import org.zeromq.ZMQ;

public class coreRegistration implements Runnable {
    public static String identity;
    public static boolean registered;
    
    coreRegistration (String _identity) {
        identity = _identity;
        registered = false;
    }
    
    public void run () {
        String sendString = new String();
        String recvString = new String();
        byte [] recByte;
        System.out.println("My Core Identity = "+identity);
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket mCore = context.socket(ZMQ.REQ);
        mCore.connect("tcp://192.168.200.1:5000");
        try {
            while(true)
            {
                sendString = "Register this core.";
                mCore.send(sendString.getBytes(),0);
                System.out.println("Registration process Started. Sent request.");
                recByte = mCore.recv(0);
                recvString = new String(recByte,0,recByte.length);
                if(!recvString.isEmpty() && recvString.contentEquals("Your name please"))
                {
                    System.out.println("Received acceptance for registration completing");
                    sendString = "MyCoreName " + identity;
                    mCore.send(sendString.getBytes(),0);
                    recByte = mCore.recv(0);
                    recvString = new String(recByte,0,recByte.length);
                    if(recvString.contentEquals("OK") || recvString.contentEquals("OKK"))
                    {
                        System.out.println("The agent is now on mngBroker's list.");
                        registered = true;
                    }
                    else
                    {
                        System.out.println("The core was unable to register.");
                    }
                }
                else
                {
                    System.out.println("Unable to complete the registration process. Exit");
                    System.exit(1);
                }
                Thread.sleep(5000); /*parameter to be put inside a config file*/
            }
        }catch (Exception e) {
            System.out.println("Problem in register core...");
            System.exit(1);
        }
    }
}
