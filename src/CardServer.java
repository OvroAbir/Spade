// Server Section

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Sakib
 */
public class CardServer{
    
    static final Person persons[] = new Person[4];
    
    
    static int numReq;
    static ServerSocket serverSocket;
    
    public static void main(String[] arg) throws IOException
    {
        
        Misc.createCardAndSetNamePowerImage(Misc.cardArray);
        GameClass game = new GameClass();
        connect(game);
        
        Misc.cardShuffle(Misc.cardArray);
        Misc.distributeCard(Misc.cardArray, persons);
        
        startThreads();         // now program jumps to persons[0,1,2,3].run()
        //serverSocket.close();
    }
    
    public static void connect(GameClass game)
    {
        try {
            serverSocket = new ServerSocket(5000);
            numReq = 0;
            
            persons[0] = new Person(game, serverSocket.accept(), ++ numReq); 
            //System.out.println("Connected");     // -> Show Waiting Msg and playerNo and ask name
            persons[1] = new Person(game, serverSocket.accept(), ++ numReq);      // -> Show Waiting Msg and playerNo and ask name
            persons[2] = new Person(game, serverSocket.accept(), ++ numReq);      // -> Show Waiting Msg and playerNo and ask name
            persons[3] = new Person(game, serverSocket.accept(), ++ numReq);      // -> Show Waiting Msg and playerNo and ask name
            
            
        } catch (Exception ex) {
            System.err.println("Fatal connection error");
            ex.printStackTrace();
            
            //System.exit(1);
        }
    }
    public static void startThreads()
    {
        for (int i = 0; i < 4; i++) {
            persons[i].start();
        }
    }

    
}
