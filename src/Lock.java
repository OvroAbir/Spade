
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
public class Lock {
    BufferedReader bufferedReader;
    String msgFromServer;
    String toPlayerClient;
    boolean isGotFromStream;

    public Lock(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        this.msgFromServer = null;
        toPlayerClient = null;
        this.isGotFromStream = false;
    }
    
    synchronized void getStringFromStream()
    {
        while(isGotFromStream)
        {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        try {
            msgFromServer = bufferedReader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        isGotFromStream = true;
        notifyAll();
    }
    
    synchronized void waitForInput()
    {
        while(!isGotFromStream)
        {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        toPlayerClient = msgFromServer;
        isGotFromStream = false;
        notifyAll();
    }
}
