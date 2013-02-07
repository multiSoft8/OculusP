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

public class coreRegistrationListener implements Runnable {
    
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
        mBroker.bind("tcp://192.168.200.1:5001");
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
            else if(recvString.contains("MyName "))
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
        }
    } 
}
