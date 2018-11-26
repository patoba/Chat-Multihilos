/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author principal
 */
public class ClienteControlador extends Thread implements ActionListener, KeyListener, WindowListener  {
    
    private final Cliente cliente;
    private final ClienteInterfazGrafica interfazGrafica;
    
    public ClienteControlador(Cliente cliente) throws IOException{
        this.cliente = cliente;
        this.interfazGrafica = new ClienteInterfazGrafica();
        interfazGrafica.establecerNombre(cliente.getNombre());
        this.interfazGrafica.btEnviar.addActionListener(this);
        this.interfazGrafica.addWindowListener(this);
        this.interfazGrafica.promt.addKeyListener(this);
        interfazGrafica.setVisible(true);
    }
    
    /**
     * Este metodo sera la acción que realice el botón enviar y tambien
     * el que usara nuestra tecla ENTER, el cual obtendra
     * el texto de nuestro textField y lo enviara dependiendo el comando
     * especificado.
     */
    protected void accionar(){
        try {
          String cadena = interfazGrafica.promt.getText();
          cliente.enviar(cadena);
      } catch (IOException ex) {
          Logger.getLogger(ClienteInterfazGrafica.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    protected void error(String cadena){
        JOptionPane.showMessageDialog(null, cadena, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    protected void cerrar(){
        try {
            cliente.cerrar();
            interfazGrafica.setVisible(false);
            interfazGrafica.dispose();
        } catch (IOException ex) {
             error("Error en la comunicacion");
        }
    }
    
    
    
    /**
   * La clase ClienteInterfazGrafica implementa la interfaz Runnable, por lo que debemos
 sobreesccribir run(), el cual llevara a cabo las tarea de lectura y 
 escritura de los mensajes, además del manejo de archivos de texto; 
 es necesario hacer un hilo de ClienteInterfazGrafica debido
 a que servira para que se pueda manejar la interfaz gráfica.
   */
   @Override
    public void run() {
       String cadena = " ";
       while(!cadena.equalsIgnoreCase("quit")){
            try {
                cadena = cliente.leerInstruccion();
                if(cadena.startsWith("Error-")){
                    cadena=cadena.substring(6);
                    error(cadena);
                }else if(cadena.equals("limpiar")){
                    interfazGrafica.limpiar();
                }else if(cadena.startsWith("leer")){
                    //JOptionPane.showMessageDialog(null, "El usuario ha aceptado tu peticion","Info",JOptionPane.INFORMATION_MESSAGE);
                    cliente.leerArchivo(cadena.substring(4));
                }else if(cadena.startsWith("escribir")){
                    System.out.println(cadena);
                    String opciones[] = cadena.split("¡¡¡¡¡¡¡¡");
                    String archivo = opciones[1];
                    String emisor = opciones[2];
                    String informacion = opciones[3];
                    if(JOptionPane.showConfirmDialog(null, "El usuario "+emisor+ " desea enviarte un archivo llamado \""+archivo+"\", aceptas?","Envio de archivo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                       JFileChooser seleccionador=new JFileChooser();
                       seleccionador.showSaveDialog(interfazGrafica);
                       cliente.copiarArchivo(seleccionador.getSelectedFile(), informacion);
                    }
                }else{
                    interfazGrafica.agregarTexto(cadena);   
                }
            } catch (IOException ex) {
                error("Error garrafal");
            }
          }
       cerrar();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       accionar();
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Entre");
        cliente.forzarSalida();
        cerrar();
    }
    
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            accionar();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
