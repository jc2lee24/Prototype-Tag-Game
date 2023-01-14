
public class Manager {
    
    private DLList<ServerThread> threadList;

    public Manager(){
        //store a list of serverThreads
        threadList = new DLList<ServerThread>();
    }

    //listen for update


    public void add(ServerThread thread){
        if(!threadList.isEmpty()){
            System.out.println("Manager 18: sending thread all other connected UIDs");
            System.out.println("Manager 19: current UID being sent: " + threadList.toString());
            thread.sendMessage("sendingAllUIDs -1 " + threadList.toString());
        }

        boolean goober = false;
        if(threadList.isEmpty() || threadList.size()%8 == 0){
            goober = true;
        }
        thread.sendMessage("sendingRole " + -1 + " " + goober);

        threadList.add(thread);
        System.out.println("Current threadList: " + threadList);
    }

    public void newConnection(int UID){
        for(int i = 0; i < threadList.size(); i++){
            if(threadList.get(i).getUID() != UID){
                threadList.get(i).sendMessage("sendUID " + UID);
            }
        }
    }

    public void sendMessage(String message){
        String UIDString = message.split(" ")[1];
        int UID = Integer.parseInt(UIDString);
        for(int i = 0; i < threadList.size(); i++){
            if(threadList.get(i).getUID() != UID){
                threadList.get(i).sendMessage(message);
            }
        }
    }




    

}
