package cliente.loggeo;
/**
 *<h1>PrebeChat</h1>
 * 
 * <p>Este es el Proyecto de Java Intermedio 
 * de los prebecarios No. 1 y No. 10
 * de la generación 36 de PROTECO.
 * 
 * Esta es la Clase Log la cual nos permitira que el usuario tenga
 * una interfaz gráfica para el inicio de sesión, y para que se puedan 
 * obtener los datos como el Nombre, el Host, y el puerto al cual se conectara
 * el cliente.
 * </p>
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
public class InterfazGraficaLogeo extends javax.swing.JFrame {

    
    public InterfazGraficaLogeo() {
        initComponents();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        btIngresar = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        txtPuerto = new javax.swing.JTextField();
        txtIPHost = new javax.swing.JTextField();
        lbHost = new javax.swing.JLabel();
        lbPuerto = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        lbFondo = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crear Usuario");
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btIngresar.setText("Ingresar");
        jPanel1.add(btIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, -1, -1));

        txtUsername.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jPanel1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 95, 170, 30));

        txtPuerto.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jPanel1.add(txtPuerto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 170, 30));

        txtIPHost.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jPanel1.add(txtIPHost, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 150, 30));

        lbHost.setFont(new java.awt.Font("Noto Mono", 1, 16)); // NOI18N
        lbHost.setForeground(java.awt.Color.white);
        lbHost.setText("IP Host: ");
        jPanel1.add(lbHost, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        lbPuerto.setFont(new java.awt.Font("Noto Mono", 1, 16)); // NOI18N
        lbPuerto.setForeground(java.awt.Color.white);
        lbPuerto.setText("Puerto: ");
        jPanel1.add(lbPuerto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        lbUsername.setBackground(java.awt.Color.white);
        lbUsername.setFont(new java.awt.Font("Noto Mono", 1, 16)); // NOI18N
        lbUsername.setForeground(java.awt.Color.white);
        lbUsername.setText("Nombre: ");
        jPanel1.add(lbUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        lbFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondo.jpg"))); // NOI18N
        lbFondo.setText("jLabel2");
        jPanel1.add(lbFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btIngresar;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFondo;
    private javax.swing.JLabel lbHost;
    private javax.swing.JLabel lbPuerto;
    private javax.swing.JLabel lbUsername;
    protected javax.swing.JTextField txtIPHost;
    protected javax.swing.JTextField txtPuerto;
    protected javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    
}
