
import java.io.BufferedReader;
import java.io.IOException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sakib
 */
public class ReadThread extends Thread{
    Lock lock;
    
    public ReadThread(Lock lock)
    {
        
        //msgFromServer = null;
        this.lock = lock;
        start();
    }
    
    @Override
    public void run()
    {
        while (true) {            
            lock.getStringFromStream();
        }
    }
}
