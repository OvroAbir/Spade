
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sakib
 */
public class WriteThread extends Thread{
    Window playerWindow;

    public WriteThread(Window playerWindow) {
        this.playerWindow = playerWindow;
        start();
    }

    @Override
    public void run() {
        synchronized(playerWindow)
        {
            while(playerWindow.sentCard != null)
            {
                try {
                    playerWindow.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            playerWindow.notifyAll();
        }
        
    }
    
    
}
