package servidor;

import cliente.cliente.Cliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *<h1>Comunicador</h1>
 * 
 * <p>
 * Esta es la Clase permite comunicar un servidor con un cliente
 * </p>
 * 
 * @author Patricio
 * 
 * @version 1.0
 * 
 * @since 27-nov-2018
 * 
 */
public class Comunicador extends Thread{
    private final BufferedReader entrada;
    private final Cliente emisor;
    private final Servidor servidorPrincipal;
    
    /**
   * Este es el Constructor de nuestra clase Comunicador el cual recibe un 
   * Cliente y se hace una entrada para que se pueda leer todo lo que este 
   * escriba, ademas de que también una salida (escribir) para que este
   * pueda mandar mensajes a los demás clientes.
   * @param usuario [Cliente]
   * @throws IOException
   */
    protected Comunicador(Cliente usuario) throws IOException{
        this.emisor = usuario;
        Socket s = usuario.getSocket();
        entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println("Conexion aceptada desde "+s.getRemoteSocketAddress());
        servidorPrincipal = Servidor.obtenerServidor();
        enviarMensajeSinNombre("El usuario "+emisor.getNombre()+" se ha conectado");
    }
    
    /**
     * Añade formato a un mensaje
     * @param mensaje [String] Mensaje que deseamos enviar
     * @return [String] String con formato
     */
    public String mensajeFormatoNormal(String mensaje){
       return  "<p class=texto>" + mensaje + "</p>";
    }
    /**
     * Retornamos un mesaje con el nombre del usuario
     * @param mensaje [String] Mensaje a enviar
     * @return [String] Mensaje con nombre
     */
    public String mensajeNombre(String mensaje){
        return emisor.getNombre()+ ": " +mensaje;
    }
    
    /**
   * Mandamos un mensaje de error.
   * @param mensaje 
   */
  public void error(String mensaje){
      try {
        servidorPrincipal.error(mensaje, emisor);
      } catch(IOException e) {
        System.out.println("Error al mandar un error");
      }
  }
   
    /**
     * Con este metodo regresamos una cadena de los Clientes
     */
    private void listar(){
        try {
          servidorPrincipal.listar(emisor);
        } catch(IOException e) {
          error("Hubo un error en la comunicación");
        }
    }
    
    /**
     * Metodo que envia un mensaje a todos los usuarios sin nombre
     * @param mensaje [String] Mensaje a enviar
     */
    private void enviarMensajeSinNombre(String mensaje){
        try{
            servidorPrincipal.enviarMensaje(mensajeFormatoNormal(mensaje));
        }catch(IOException e){
            error("Hubo un error al enviar el mensaje");
        }
    }
    
    /**
     * Metodo que envia un mensaje a todos los usuarios
     * @param mensaje [String] Mensaje a enviar
     * 
     */
    private void enviarMensaje(String mensaje){
        enviarMensajeSinNombre(mensajeNombre(mensaje));
    }
    
    /**
    * Permite enviar un mensaje a un usuario
    * @param nombreUsuario [Cliente] Nombre de usuario al que le queremos mandaer el mensaje
    * @param mensaje [String] Mensaje que queremos mandar
    * @return [boolean] Si el usuario receptor existe o no
    * @exception IOException Error en la comunicacion con el usuario
    */
    private boolean enviarMensaje(String nombreCliente, String mensaje){
        try{
            mensaje = emisor.getNombre() + "(privado):" + mensaje;
            if(servidorPrincipal.enviarMensaje(nombreCliente, mensajeFormatoNormal(mensaje))){
                servidorPrincipal.enviarMensaje(emisor, mensajeFormatoNormal(mensaje));
                return true;
            }
        }catch(IOException e){
            error("Hubo un error al enviar el mensaje");
        }
        return false;
    }
    
    private void limpiar(){
        try {
            servidorPrincipal.limpiar(emisor);
        } catch (IOException e) {
            error("Hubo un error al limpiar la pantalla");
        }
    }
    
    /**
    * Esta función lee lo que escribio el cliente en el prompt.
    * @return
    * @throws IOException 
    */
   public String recibirDelCliente() throws IOException{
     String str=entrada.readLine();
     return str;
   } 
   
   /**
    * Cierra sesion un cliente
    * @throws IOException Error de comunicacion
    */
   public void cerrar() throws IOException{
       servidorPrincipal.enviarMensaje(emisor, "quit");
       enviarMensajeSinNombre("El usuario " + emisor.getNombre() + " se ha desconectado");
       entrada.close();
       servidorPrincipal.eliminarCliente(emisor);
   }
   
   /**
    * Muestra ayuda en pantalla
    */
   public void ayuda(){
        try {
            servidorPrincipal.ayuda(emisor);
        } catch (IOException ex) {
            error("Error en la comunicacion");
        }
   }
   
   public void envioArchivo(String ubicacion){
        try {
            servidorPrincipal.envioArchivo(emisor, ubicacion);
        } catch (IOException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

   /**
    * Esta es una de ls partes principales de nuetro programa, esta es la que 
    * comprueba que comando esta ingresando el Cliente, por lo tanto tambien
    * es la que maneja muchos de los métodos de nuestra clase.
    * Comandos:
    * AYUDA - Muestra una pequeña ayuda
    * LIST - Obtenemos una lista de las personas conectadas.
    * QUIT -  Cerramos sesión.
    * LIMPIAR - Limpiamos nuestro chat.
    * TEXT [Mensaje]- Manda un mensaje a todos.
    * SEND_FILE [Ubicacion]- Manda un archivo.
    * TEXT_TO [Usuario] [Mensaje]- Manda un mensaje privado
    * @param cadena 
    */
   public void ejecutarAccion(String cadena){
     String cadena2 = cadena.toUpperCase();
     if(cadena2.equals("LIST")){
         listar();
     }else if(cadena2.equals("LIMPIAR")){
         limpiar();
     }else if(cadena2.equals("QUIT")){
         
     }else if(cadena2.equals("AYUDA")){
         ayuda();
     }else{
        if(cadena2.startsWith("TEXT_TO")){
            String opciones[]=cadena.split(" ");
            String mensaje = cadena.substring(8 + opciones[1].length());
            if(!enviarMensaje(opciones[1], mensaje)){
                error("Usuario no existe");
            }
        }else if(cadena2.startsWith("TEXT")){
            String mensaje = cadena.substring(4);
            enviarMensaje(mensaje);
        }else if(cadena2.startsWith("SEND_FILE")){
            String ubicacion = cadena.substring(10);
            envioArchivo(ubicacion);
        }else{
            error("escribe una sentencia valida");
        }
     }
   }
   
   /**
   * Sobreescritura del método run, de la clase Thread, será lo que nuestro
   * Servidor este haciendo a lo largo del programa.
   */
    @Override
    public void run(){
      try {
        String cadena;
        do{
          cadena=recibirDelCliente();
          ejecutarAccion(cadena);
        }while (!cadena.equalsIgnoreCase("QUIT"));
        cerrar();
      } catch(IOException ioe) {
        error("Error Garrafal");
      }
    }
    
}
