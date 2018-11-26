/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author principal
 */
public class Cliente{
    
    private final BufferedReader br;
    private final Socket socket;
    private final PrintStream ps;
    private final String nombre;
    
    public Cliente(String nombre, Socket socket) throws IOException{
        this.nombre = nombre;
        this.socket = socket;
        ps = new PrintStream(socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
    }
    
    /**
    * Salimos del Chat
    */
   public void cerrar() throws IOException{
     br.close();
     ps.close();
   }
   
   public void forzarSalida(){
      ps.println("quit");
   }
   
   public String leerInstruccion() throws IOException{
       String a = br.readLine();
       return a;
   }
   
   public void leerArchivo(String ubicacion) throws IOException{
        File f=new File(ubicacion);
        String Final="";
        FileReader fr=new FileReader(f);
        BufferedReader br=new BufferedReader(fr);
        String linea;
        while((linea=br.readLine())!= null)
            Final+=linea+"¡";
        br.close();
        fr.close();
        enviar(Final);
   }
   
   protected void copiarArchivo(File archivo, String cadena) throws IOException{
        FileWriter fw;
        BufferedWriter bw;
        String lineas[]=cadena.split("¡");
        if(!archivo.exists()){
           archivo.createNewFile();
        }
        fw=new FileWriter(archivo);//si existe no lo sobrescribe
        bw=new BufferedWriter(fw);
        for(String temp: lineas){
          bw.write(temp+"\n");
        }
        bw.flush();
        bw.close();
        fw.close();
   }
   
    /**
    * Obtenemos una cadena (Mensaje) s y lo mandamos como flujo
    * @param s
    * @throws IOException 
    */
   public void enviar(String s) throws IOException{
       System.out.println("Envio "+ s);
        ps.println(s);
   }
    
    public Socket getSocket(){
        return this.socket;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    /**
    * Sobreescritura del metodo tu String para cada vez que se imprima
    * el cliente
    * @return 
    */
    @Override
    public String toString(){
      return String.format("<p class=texto><span style=\"font-weight: bold;color: red;\">"+"Nombre: %s </span> -  %s"+"</p>",nombre,socket.getRemoteSocketAddress(),socket.getPort());
    }
    
}
