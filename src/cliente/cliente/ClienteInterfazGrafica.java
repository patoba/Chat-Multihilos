package cliente.cliente;

import java.io.File;
import javax.swing.JFileChooser;
/**
 *<h1>PrebeChat</h1>
 * 
 * <p>Este es el Proyecto de Java Intermedio 
 de los prebecarios No. 1 y No. 10
 de la generación 36 de PROTECO.
 
 Esta es la Clase ClienteInterfazGrafica la cual es la que se debe conectar con el 
 servidor, basicamente sera la interfaz del usuario, aquí se controla 
 como este puede interactuar, ya sea mandando mensajes, conectandose,
 iniciando sesión, además de su interfaz gráfica.
 </p>
 * 
 * 
 * <b>Notas:</b> Para mandar un mensaje no se deben poner espacios
 * 
 * @author Emanuel
 * @author Patricio
 * 
 * @version 1.0
 * 
 * @since 23-04-2018
 * 
 */
public class ClienteInterfazGrafica extends javax.swing.JFrame {
  /**
   * Atributos
   */
  private static final String cabecera="<html><style type=\"text/css\">\n" +
"		.titulo{\n" +
"			font-family: 'Work Sans', sans-serif;\n" +
"			color: purple;\n" +
"			font-weight: bold;\n" +
"			font-size: 25px;\n" +
"		}\n" +
"		.subtitulo{\n" +
"			font-family: 'Questrial', sans-serif;\n" +
"			color: blue;\n" +
"			font-weight: bold;\n" +
"			font-size: 20px;\n" +
"		}\n" +
"		.texto{\n" +
"			font-family: 'Play', sans-serif;\n" +
"			color: black;\n" +
"			font-size: 15px;\n" +
"		}\n" +
"		.otro{\n" +
"			font-family: 'IBM Plex Sans Condensed', sans-serif;\n" +
"			font-size: 15px;\n" +
"		}\n" +
"	</style><body><p class=\"titulo\">Chat</p><br>\n";
  
  /**
   * Este método es el constructor el cual recibe un Socket y nombre
   * para que pueda ser intanciado, además modificamos su interfaz mediante
   * los datos ingresados.
   */
  protected ClienteInterfazGrafica(){
        initComponents();
        texto.setText(cabecera);
  }

  protected void establecerNombre(String nombre){
      lbNombre.setText(nombre);
  }  
  
  protected void limpiar(){
       texto.setText(cabecera);
  }
  
  protected void agregarTexto(String cadena){
      texto.setText(texto.getText() + cadena);
  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lbNombre = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        promt = new javax.swing.JTextField();
        btEnviar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        texto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mensaje");
        setResizable(false);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbNombre.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lbNombre.setForeground(new java.awt.Color(1, 1, 1));
        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre.setText("Hola! Cliente");
        jPanel2.add(lbNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 330, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/carpeta.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivos(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 420, 30, 30));

        promt.setFont(new java.awt.Font("Courier 10 Pitch", 0, 18)); // NOI18N
        promt.setText("Mensaje");
        promt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                promtMouseClicked(evt);
            }
        });
        jPanel2.add(promt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 480, -1));

        btEnviar.setFont(new java.awt.Font("DialogInput", 0, 15)); // NOI18N
        btEnviar.setText("Enviar");
        jPanel2.add(btEnviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, -1, -1));

        texto.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jScrollPane1.setViewportView(texto);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 630, 340));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/imgFondo.jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
    
    /**
     * Este metodo lo usamos para que podamos seleccionar un archivo
     * que queramos enviar.
     * @param evt 
     */
    private void archivos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivos
        JFileChooser escogedor=new JFileChooser();
        escogedor.showOpenDialog(this);
        File archivo=escogedor.getSelectedFile();
        promt.setText("SEND_FILE "+archivo.getAbsolutePath());
    }//GEN-LAST:event_archivos

    /**
     * Accion para borrar lo que tiene el prompt.
     * @param evt 
     */
    private void promtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_promtMouseClicked
        if(promt.getText().equals("Mensaje")){
            promt.setText("");
        }
    }//GEN-LAST:event_promtMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JLabel lbNombre;
    protected javax.swing.JTextField promt;
    private javax.swing.JLabel texto;
    // End of variables declaration//GEN-END:variables
   
}
