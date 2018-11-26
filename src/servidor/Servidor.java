/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import cliente.cliente.Cliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *<h1>Servidor</h1>
 * 
 * <p>
 * 
 * Esta es la Clase Servidor, que basicamente será la que controle todo
 * nuestro sistema, como quien se conecta a nuestro chat, lee el comando
 * que esta uilizando el cliente, distribuye los mensajes entre los clientes.
 * </p>
 * 
 * 
 * <b>Notas:</b> Para mandar un mensaje no se deben poner espacios
 * 
 * @author Patricio
 * 
 * @version 1.0
 * 
 * @since 27-nov-2018
 * 
 */
public class Servidor {
    
    private static Servidor servidorPrincipal;
    private final ArrayList<Cliente> clientes;
    private final ServerSocket socketDelServidor;
    
    private Servidor() throws IOException{
        socketDelServidor = new ServerSocket(0);
        servidorPrincipal = this;
        clientes = new ArrayList<>();
    }
    
    private Socket aceptarConexion() throws IOException{
        return socketDelServidor.accept();
    }
    
   
    
    private void agregarClientes() throws IOException{
        Socket socketCliente=aceptarConexion();//capturar datos interface grafica
        
        BufferedReader entrada=new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
        String nombre = entrada.readLine();
        
        boolean determinar = seEncuentraCliente(nombre);
        System.out.println(determinar);
        
        PrintStream escribir=new PrintStream(socketCliente.getOutputStream());
        escribir.println(String.valueOf(determinar).toLowerCase());
        escribir.flush();
        
        if(!determinar){
            Cliente cliente = new Cliente(nombre, socketCliente);
            clientes.add(cliente);
            Comunicador c = new Comunicador(cliente);
            Thread a=new Thread(c);
            a.start();
            /*
            ClienteControlador clienteControlador = new ClienteControlador(cliente);
            Thread a=new Thread(clienteControlador);
            a.start();
            */
        }
    }
    
    private void ejecutarServidor(){
        System.out.println("Servidor aceptando conexiones en el puerto "+socketDelServidor.getLocalPort());
        while(true){
            try{
                agregarClientes();
            }catch(IOException e){
                System.out.println("Error- " + e.getMessage());
            }
        }
    }
    
    
    /**
   * Obtenemos una instancia de la clase servidor
   * @return [Servidor] Servidor que estamos ejecutando
   */
    public static Servidor obtenerServidor(){
        return servidorPrincipal;
    }
    
    /**
   * Obtenemos un Cliene en específico a partir del nombre.
   * @param nombre [String] Nombre del cliente que deseamos buscar
   * @return [Cliente] Cliente con el nombre que deseamos buscar, null si no lo encontro
   */
    private Cliente getCliente(String nombre){
        for(Cliente usuario : clientes){
            if(usuario.getNombre().equals(nombre)){
                return usuario;
            }
        }
        return null;
    }
    
    /**
   * Determinamos si se encuentra un cleinte a partir del nombre.
   * @param nombre [String] Nombre del cliente que deseamos buscar
   * @return [boolean] Cliente con el nombre que deseamos buscar, null si no lo encontro
   */
    private boolean seEncuentraCliente(String nombre){
        for(Cliente usuario : clientes){
            if(usuario.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    
    /**
   * Obtenemos un cliente en específico a partir del socket.
   * @param cliente
   * @return [Cliente] cliente a buscark, null si no lo encontro
   */
    private Cliente getCliente(Socket socket){
        for(Cliente usuario : clientes){
            if(usuario.getSocket().equals(socket)){
                return usuario;
            }
        }
        return null;
    }
    
    /**
   * Eliminamos un cliente de la lista de clientes
   * @param usuario [Cliente] Cliente que deseamos eliminar
   * @return [boolean] Si pudimos eliminar el cliente de forma correcta
   */
    public boolean eliminarCliente(Cliente usuario){
        if(this.clientes.contains(usuario)){
            this.clientes.remove(usuario);
            return true;
        }
        return false; 
    }
    
    /**
    * Permite enviar un mensaje a un usuario
    * @param usuario [Cliente] Usuario al que le queremos mandaer el mensaje
    * @param mensaje [String] Mensaje que queremos mandar
    * @throws IOException Error en la comunicacion con el usuario
    */
    protected void enviarMensaje(Cliente usuario, String mensaje) throws IOException{
        PrintStream escribir = new PrintStream(usuario.getSocket().getOutputStream());
        escribir.println(mensaje);
        escribir.flush();
    }
    
    /**
    * Permite enviar un mensaje a un usuario
    * @param nombreUsuario [Cliente] Nombre de usuario al que le queremos mandaer el mensaje
    * @param mensaje [String] Mensaje que queremos mandar
    * @return [boolean] Si el usuario receptor existe o no
    * @throws IOException Error en la comunicacion con el usuario
    */
    protected boolean enviarMensaje(String nombreUsuario, String mensaje) throws IOException{
        Cliente receptor = getCliente(nombreUsuario);
        if(receptor != null){
            enviarMensaje(receptor, mensaje);
            return true;
        }
        return false;
    }
    
    /**
     * Metodo que envia la info de cada usuario al usuario que lo pidio
     * @param usuario [Cliente] El usuario que pidio la informacion
     * @throws IOException Error en la comunicación
     */
    protected void listar(Cliente usuario) throws IOException{
        String mensaje="<p class=subtitulo>Todas las personas:</p>";
        for(Cliente temp : clientes) {
            mensaje += temp.toString();
        }
        enviarMensaje(usuario, mensaje);
    }
    
    /**
     * Metodo que envia un mensaje a todos los usuarios
     * @param mensaje [String] Mensaje a enviar
     * @throws IOException Error en la comunicación
     */
    protected void enviarMensaje(String mensaje) throws IOException{
        for(Cliente usuario : clientes){
            enviarMensaje(usuario, mensaje);
        }
    }
    
    /**
     * Metodo que permite limpiar la pantalla de un cliente
     * @param usuario [Cliente] Cliente al cual queremos limpiar la pantalla
     * @throws IOException Error en la comunicación 
    */
    protected void limpiar(Cliente usuario) throws IOException{
        enviarMensaje(usuario, "limpiar");
    }
    
    protected void envioArchivo(Cliente emisor, String ubicacion) throws IOException{
        enviarMensaje(emisor, "leer"+ubicacion);
        BufferedReader entrada=new BufferedReader(new InputStreamReader(emisor.getSocket().getInputStream()));
        String contenido = entrada.readLine();
        System.out.println(contenido);
        if (contenido.contains("\\")) {
            contenido = contenido.replace("\\","/");
        }
        String dividido[] = ubicacion.split("/");
        String nombre = dividido[dividido.length - 1];
        for(Cliente usuario : clientes){
            if(usuario.equals(emisor)){
                continue;
            }
            enviarMensaje(usuario, "escribir¡¡¡¡¡¡¡¡"+nombre+"¡¡¡¡¡¡¡¡"+emisor.getNombre()+"¡¡¡¡¡¡¡¡"+contenido);
        }
    }
    
    /**
     *
     * @param usuario [Cliente] Cliente el cual solicita ayuda
     * @throws IOException Error en la comunicacion
     */
    protected void ayuda(Cliente usuario) throws IOException{
        String informacion[] = {"COMANDOS:",
                                "AYUDA - Muestra una pequeña ayuda",
                               "LIST - Obtenemos una lista de las personas conectadas",
                               "QUIT -  Cerramos sesión",
                                "LIMPIAR - Limpiamos nuestro chat",
                                "TEXT [Mensaje]- Manda un mensaje a todos",
                                "SEND_FILE [Ubicacion]- Manda un archivo",
                                "TEXT_TO [Usuario] [Mensaje]- Manda un mensaje privado"};
        String mensaje = "";
        for(String comando : informacion){
            mensaje = mensaje + "<p class=texto>" + comando + "</p>";
        }
        enviarMensaje(usuario, mensaje);
    }
    
    /**
    * Mandamos un mensaje de error a un cliente
    * @param mensaje [String] Mensaje de error
    * @param usuario [Cliente] Usuario al que le queremos mandar el mensaje
    * @throws IOException Error en la comunicacion con el cliente
    */
    protected void error(String mensaje, Cliente usuario) throws IOException{
        enviarMensaje(usuario, "Error- "+ mensaje);
    }
    
    public static void main(String[] args) {
        try {
            Servidor s = new Servidor();
            s.ejecutarServidor();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
