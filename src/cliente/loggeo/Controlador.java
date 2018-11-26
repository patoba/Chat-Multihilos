package cliente.loggeo;

import cliente.cliente.Cliente;
import cliente.cliente.ClienteControlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author principal
 */
public class Controlador implements KeyListener, ActionListener{
    
    private final InterfazGraficaLogeo interfazGrafica;
    
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
    }
    
    public Controlador(){
        interfazGrafica = new InterfazGraficaLogeo();
        interfazGrafica.txtIPHost.addKeyListener(this);
        interfazGrafica.txtPuerto.addKeyListener(this);
        interfazGrafica.txtUsername.addKeyListener(this);
        interfazGrafica.btIngresar.addActionListener(this);
        interfazGrafica.setVisible(true);
    }
    
    public void error(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public boolean usernameEnElServidor(Socket s, String username) {
        
        try {
            PrintStream escribir = new PrintStream(s.getOutputStream());
            escribir.println(username);
            escribir.flush();
            
            BufferedReader entrada=new BufferedReader(new InputStreamReader(s.getInputStream()));
            String cadena = entrada.readLine();
            if (cadena.equals("true")) {
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
        return false;
    }
    
    public void analizar() throws IOException{
        int port;
        String host=interfazGrafica.txtIPHost.getText();
        String puerto = interfazGrafica.txtPuerto.getText();
        String nombre=interfazGrafica.txtUsername.getText();
        Socket s;
        if(host.isEmpty() || puerto.isEmpty() || nombre.isEmpty()){
            error("Llena todos los campos");
        }else{
            port=Integer.valueOf(puerto);
            s = new Socket(host,port);
            if(usernameEnElServidor(s, nombre)){
                error("Nombre ya usado");
            }else{
                Cliente c = new Cliente(nombre, s);
                ClienteControlador con = new ClienteControlador(c);
                Thread a = new Thread(con);
                a.start();
                interfazGrafica.setVisible(false);
                interfazGrafica.dispose();
            }
        }
    }
    
    public void accionar() {
        try {
            analizar();
        } catch (IOException e) {
            error("Error en la conexion");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        accionar();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            accionar();
        }    
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
    }

    
}
