package Server;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import Common.Constantes;
import Common.Dot;
import Common.Mapa;

import java.net.*;
import java.io.*;

public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Dot dot;
    Dot dot2;

    Socket client;
    ObjectOutputStream output;
    
    public GUI(){

        ventana = new JFrame();
        mapa = new Mapa(this);

        ventana.add(mapa.panelTablero);

        next = new JButton("continuar");
        next.addActionListener(this);
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        dot = new Dot();

        Server server = new Server(dot);
        Thread hilo = new Thread(server);
        hilo.start();

        moveDot();
        run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {  
    }

    public void enviaCoor(){ // Método para realizar conexión con el nuevo servidor
        dot2 = dot;
        try {
            client = new Socket("127.0.0.1", 4445);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(dot2.target); // Se envía el target del dot
            output.flush();
            output.close();
            client.close();
        } catch (Exception ex) {
            System.out.println("Aún no se ha realizado la conexión");
        }
    }

    public void moveDot(){
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void run(){
        while (true){
            enviaCoor(); // Se llama el método para enviar la información del objeto
            dot.move();
            moveDot();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

}