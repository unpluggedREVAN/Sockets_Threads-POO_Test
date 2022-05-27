package Client;

import java.net.*;
import java.io.*;
import Common.*;

public class Server implements Runnable, Constantes{
    ServerSocket server;
    Socket client;
    ObjectInputStream input;
    Dot dot;

    public Server(Dot d){
        dot = d;
        try {
            server = new ServerSocket(4445); // Se cambia el puerto
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void run(){
        try {
            while(true){
                client = server.accept();
                input = new ObjectInputStream(client.getInputStream());
                dot.target = (Target)input.readObject(); // Se recibe el dot.target
                input.close();
                client.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}
