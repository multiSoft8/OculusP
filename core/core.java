/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristi
 */


public class core {

    /**
     * @param args the command line arguments
     */
    
    public static String identity;
    public static boolean registered;
    
    public static void main(String[] args) {
        // TODO code application logic here
        String configFile = new String();
        int i;
        identity = new String();
        registered = false;
        if(args.length <= 1 || args.length % 2 != 0)             
        {
            System.out.println("Something is not ok with input params");
            System.exit(0);
        }
        for(i=0;i<args.length-1;i+=2)
        {
            if(args[i].contentEquals("-c")) {
                configFile = args[i+1];
            }
            else if(args[i].contentEquals("-i"))
            {
                identity = args[i+1];
            }
            i+=2;
        }
        if(configFile.isEmpty() && identity.isEmpty()) 
        {
            System.out.println("Identity parameter must be provided in a way...");
            System.exit(0);
        }
        if(!configFile.isEmpty())
        {
            /* parse config and get identity and other parameters  */
            /* if both config and identity are present config will be taken into consideration*/
        }
        
        coreRegistration coreReg = new coreRegistration(identity);
        Thread coreRegThread = new Thread(coreReg);
        coreRegThread.start();
        
        /* on a job */
        getAgentConnectAddress agentAddress = new getAgentConnectAddress("agent1");
        Thread agentAddressThread2 = new Thread(agentAddress);
        agentAddressThread2.start();
    }
}
