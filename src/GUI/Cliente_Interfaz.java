/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import musicShareClient.Conexion;
import musicShareClient.Ecualizador;
import com.sun.org.apache.xml.internal.utils.URI;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import musicShareClient.UDPBroadcast;

/**
 *
 * @author Jesus
 */
public class Cliente_Interfaz extends javax.swing.JFrame {

    Conexion con;
    Ecualizador ec;

    /**
     * Creates new form Client_Gui
     */
    public Cliente_Interfaz(Conexion _con) {
        initComponents();

        con = _con;
        Ecualizador_Interfaz aux = new Ecualizador_Interfaz(this, false);
        aux.setVisible(true);
        ec = new Ecualizador(aux);

        initInterface();
        repr.setVisible(false);
        music.setVisible(false);


    }

    private void initInterface() {
        //Centramos la ventana en la pantalla.
        this.setLocationRelativeTo(null);
        ConnectFrame.setLocationRelativeTo(this);

        fileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);

                String rowName;

                //Tratamos el click derecho en la tabla de archivos.
                if (javax.swing.SwingUtilities.isRightMouseButton(me)) {
                    //Por defecto, la pulsación con el botón derecho del ratón
                    //no selecciona ninguna fila de la tabla. Las siguientes lineas
                    //seleccionan la fila sobre la que el ratón hizo click derecho.
                    int rowID = fileList.rowAtPoint(me.getPoint());
                    fileList.getSelectionModel().setSelectionInterval(rowID, rowID);

                    rowName = (fileList.getValueAt(fileList.getSelectedRow(), 0).toString());
                    if (rowName.endsWith("/")) {
                        FMabrir.setEnabled(true);
                        FMplay.setEnabled(false);
                        FMadd.setEnabled(false);
                    } else if (rowName.endsWith(".mp3")) {
                        FMabrir.setEnabled(false);
                        FMplay.setEnabled(true);
                        FMadd.setEnabled(true);
                    } else {
                        FMabrir.setEnabled(false);
                        FMplay.setEnabled(false);
                        FMadd.setEnabled(false);
                    }
                } //Tratamos el doble click en la tabla de archivos..
                else {
                    rowName = (fileList.getValueAt(fileList.getSelectedRow(), 0).toString());

                    if (me.getClickCount() == 2) {
                        if (fileList.getSelectedRow() == 0) {
                            changeDirectory("..");
                        } else if (rowName.endsWith("/")) {
                            //ABRIR LA CARPETA!
                            changeDirectory(rowName);
                        } else if (rowName.endsWith(".mp3")) {
                            //REPRODUCIR MÚSICA!
                            play(rowName);
                        }
                    }
                }
            }
        });

        musicList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);

                //Tratamos el click derecho en la tabla de archivos.
                if (javax.swing.SwingUtilities.isRightMouseButton(me)) {
                    //Por defecto, la pulsación con el botón derecho del ratón
                    //no selecciona ninguna fila de la tabla. Las siguientes lineas
                    //seleccionan la fila sobre la que el ratón hizo click derecho.
                    int rowID = musicList.rowAtPoint(me.getPoint());
                    musicList.getSelectionModel().setSelectionInterval(rowID, rowID);

                } //Tratamos el doble click en la tabla de archivos..
                else {
                    if (me.getClickCount() == 2) {
                        //REPRODUCIR MÚSICA!
                        playFromMusic(musicList.getSelectedRow());
                    }
                }
            }
        });

        reprList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);

                //Tratamos el click derecho en la tabla de archivos.
                if (javax.swing.SwingUtilities.isRightMouseButton(me)) {
                    //Por defecto, la pulsación con el botón derecho del ratón
                    //no selecciona ninguna fila de la tabla. Las siguientes lineas
                    //seleccionan la fila sobre la que el ratón hizo click derecho.
                    int rowID = reprList.rowAtPoint(me.getPoint());
                    reprList.getSelectionModel().setSelectionInterval(rowID, rowID);

                } //Tratamos el doble click en la tabla de archivos..
                else {
                    if (me.getClickCount() == 2) {
                        //REPRODUCIR MÚSICA!
                        playFromRepr(reprList.getSelectedRow());
                    }
                }
            }
        });


        try {
            Image img = ImageIO.read(getClass().getResource("/images/icon.png"));
            this.setIconImage(img);
            ConnectFrame.setIconImage(img);
        } catch (IOException ex) {
        }

        //Ocultamos el frame principal y mostramos el que realizará la conexión.
        this.setVisible(false);
        ConnectFrame.setVisible(true);
        manual.doClick();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        conection_buttons = new javax.swing.ButtonGroup();
        views = new javax.swing.ButtonGroup();
        reprMenu = new javax.swing.JPopupMenu();
        RMplay = new javax.swing.JMenuItem();
        RMdelete = new javax.swing.JMenuItem();
        musicMenu = new javax.swing.JPopupMenu();
        MMadd = new javax.swing.JMenuItem();
        MMplay = new javax.swing.JMenuItem();
        fileMenu = new javax.swing.JPopupMenu();
        FMup = new javax.swing.JMenuItem();
        FMabrir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        FMplay = new javax.swing.JMenuItem();
        FMadd = new javax.swing.JMenuItem();
        ConnectFrame = new javax.swing.JFrame();
        connectionLayered = new javax.swing.JLayeredPane();
        manConnectPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        serverIP = new javax.swing.JTextField();
        conect = new javax.swing.JButton();
        connInfo = new javax.swing.JLabel();
        autConnectPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        findServer = new javax.swing.JButton();
        autInfo = new javax.swing.JLabel();
        manual = new javax.swing.JToggleButton();
        automatic = new javax.swing.JToggleButton();
        showRepr = new javax.swing.JToggleButton();
        showMusic = new javax.swing.JToggleButton();
        showFiles = new javax.swing.JToggleButton();
        mainContainer = new javax.swing.JLayeredPane();
        repr = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reprList = new javax.swing.JTable();
        repeat = new javax.swing.JToggleButton();
        random = new javax.swing.JToggleButton();
        files = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JTable();
        music = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        musicList = new javax.swing.JTable();
        maddMusic = new javax.swing.JButton();
        mplayMusic = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        info = new javax.swing.JLabel();

        RMplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play_16.png"))); // NOI18N
        RMplay.setText("Reproducir canción");
        RMplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RMplayActionPerformed(evt);
            }
        });
        reprMenu.add(RMplay);

        RMdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cross.png"))); // NOI18N
        RMdelete.setText("Eliminar de la lista");
        RMdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RMdeleteActionPerformed(evt);
            }
        });
        reprMenu.add(RMdelete);

        MMadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/repr_16.png"))); // NOI18N
        MMadd.setText("Añadir a la lista de reproducción");
        MMadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MMaddActionPerformed(evt);
            }
        });
        musicMenu.add(MMadd);

        MMplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play_16.png"))); // NOI18N
        MMplay.setText("Reproducir");
        MMplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MMplayActionPerformed(evt);
            }
        });
        musicMenu.add(MMplay);

        FMup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/up.png"))); // NOI18N
        FMup.setText("Subir un nivel");
        FMup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FMupActionPerformed(evt);
            }
        });
        fileMenu.add(FMup);

        FMabrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open.png"))); // NOI18N
        FMabrir.setText("Abir");
        FMabrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FMabrirActionPerformed(evt);
            }
        });
        fileMenu.add(FMabrir);
        fileMenu.add(jSeparator1);

        FMplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play_16.png"))); // NOI18N
        FMplay.setText("Reproducir");
        FMplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FMplayActionPerformed(evt);
            }
        });
        fileMenu.add(FMplay);

        FMadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/repr_16.png"))); // NOI18N
        FMadd.setText("Añadir a la lista de reproducción");
        FMadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FMaddActionPerformed(evt);
            }
        });
        fileMenu.add(FMadd);

        ConnectFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        ConnectFrame.setTitle("Conectar con servidor");
        ConnectFrame.setMinimumSize(new java.awt.Dimension(415, 360));

        manConnectPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 128, 128), 1, true), "Conexión manual", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jLabel1.setText("Dirección IP:");

        serverIP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        serverIP.setText("192.168.38.108");
        serverIP.setToolTipText("Escribe la dirección IP del servidor");
        serverIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverIPActionPerformed(evt);
            }
        });
        serverIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                serverIPKeyTyped(evt);
            }
        });

        conect.setText("Conectar");
        conect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectActionPerformed(evt);
            }
        });

        connInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        connInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout manConnectPaneLayout = new javax.swing.GroupLayout(manConnectPane);
        manConnectPane.setLayout(manConnectPaneLayout);
        manConnectPaneLayout.setHorizontalGroup(
            manConnectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manConnectPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manConnectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverIP, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manConnectPaneLayout.createSequentialGroup()
                        .addGap(0, 273, Short.MAX_VALUE)
                        .addComponent(conect))
                    .addGroup(manConnectPaneLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(connInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        manConnectPaneLayout.setVerticalGroup(
            manConnectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manConnectPaneLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serverIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(connInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conect)
                .addContainerGap())
        );

        manConnectPane.setBounds(0, 0, 380, 271);
        connectionLayered.add(manConnectPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        autConnectPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 128, 128), 1, true), "Conexión automática", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jLabel2.setText("<html><justify>\nLa conexión automática realizará un mensaje masivo a toda la red meidante inundación para detectar servidores que estén disponibles y operativos. Si conoces la dirección IP del servidor, podrías optar por la conexión manual.\nPulsa el botón para comenzar la inundación.\n</justify>\n</html>");

        findServer.setText("Buscar servidor");
        findServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout autConnectPaneLayout = new javax.swing.GroupLayout(autConnectPane);
        autConnectPane.setLayout(autConnectPaneLayout);
        autConnectPaneLayout.setHorizontalGroup(
            autConnectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(autConnectPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(autConnectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, autConnectPaneLayout.createSequentialGroup()
                        .addGap(0, 243, Short.MAX_VALUE)
                        .addComponent(findServer))
                    .addComponent(autInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        autConnectPaneLayout.setVerticalGroup(
            autConnectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(autConnectPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(autInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(findServer)
                .addContainerGap())
        );

        autConnectPane.setBounds(0, 0, 380, 271);
        connectionLayered.add(autConnectPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        conection_buttons.add(manual);
        manual.setText("Conexión manual");
        manual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualActionPerformed(evt);
            }
        });

        conection_buttons.add(automatic);
        automatic.setText("Conexión automática");
        automatic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                automaticActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ConnectFrameLayout = new javax.swing.GroupLayout(ConnectFrame.getContentPane());
        ConnectFrame.getContentPane().setLayout(ConnectFrameLayout);
        ConnectFrameLayout.setHorizontalGroup(
            ConnectFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectFrameLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(connectionLayered)
                .addContainerGap())
            .addGroup(ConnectFrameLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(manual)
                .addGap(0, 0, 0)
                .addComponent(automatic)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        ConnectFrameLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {automatic, manual});

        ConnectFrameLayout.setVerticalGroup(
            ConnectFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConnectFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manual)
                    .addComponent(automatic))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectionLayered, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Música compartida");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        views.add(showRepr);
        showRepr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/repr.png"))); // NOI18N
        showRepr.setToolTipText("Mostrar la lista de reproducción");
        showRepr.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/repr_prssd.png"))); // NOI18N
        showRepr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showReprActionPerformed(evt);
            }
        });

        views.add(showMusic);
        showMusic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/music.png"))); // NOI18N
        showMusic.setToolTipText("");
        showMusic.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/music_prssd.png"))); // NOI18N
        showMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMusicActionPerformed(evt);
            }
        });

        views.add(showFiles);
        showFiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder.png"))); // NOI18N
        showFiles.setSelected(true);
        showFiles.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_prssd.png"))); // NOI18N
        showFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFilesActionPerformed(evt);
            }
        });

        repr.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 128, 128), 1, true), "Lista de reproducción", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        repr.setPreferredSize(new java.awt.Dimension(409, 357));

        reprList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Música"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reprList.setComponentPopupMenu(reprMenu);
        reprList.setRowHeight(22);
        reprList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(reprList);

        repeat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/repeat.png"))); // NOI18N
        repeat.setToolTipText("Repetir todo");
        repeat.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/repeat_prssd.png"))); // NOI18N
        repeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatActionPerformed(evt);
            }
        });

        random.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/random.png"))); // NOI18N
        random.setToolTipText("Reproducir aleatoriamente");
        random.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/random_prssd.png"))); // NOI18N
        random.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout reprLayout = new javax.swing.GroupLayout(repr);
        repr.setLayout(reprLayout);
        reprLayout.setHorizontalGroup(
            reprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reprLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reprLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(repeat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(random)))
                .addContainerGap())
        );
        reprLayout.setVerticalGroup(
            reprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reprLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(reprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(repeat)
                    .addComponent(random)))
        );

        repr.setBounds(0, 0, 409, 357);
        mainContainer.add(repr, javax.swing.JLayeredPane.DEFAULT_LAYER);

        files.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 128, 128), 1, true), "Ficheros remotos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        files.setPreferredSize(new java.awt.Dimension(409, 357));

        fileList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ficheros remotos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fileList.setComponentPopupMenu(fileMenu);
        fileList.setRowHeight(22);
        fileList.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(fileList);
        fileList.getColumnModel().getColumn(0).setResizable(false);
        fileList.getColumnModel().getColumn(0).setPreferredWidth(50);

        javax.swing.GroupLayout filesLayout = new javax.swing.GroupLayout(files);
        files.setLayout(filesLayout);
        filesLayout.setHorizontalGroup(
            filesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addContainerGap())
        );
        filesLayout.setVerticalGroup(
            filesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filesLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        files.setBounds(0, 0, 409, 357);
        mainContainer.add(files, javax.swing.JLayeredPane.DEFAULT_LAYER);

        music.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(java.awt.Color.gray, 1, true), "Música remota", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        music.setPreferredSize(new java.awt.Dimension(409, 357));

        musicList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Música remota"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        musicList.setComponentPopupMenu(musicMenu);
        musicList.setRowHeight(22);
        musicList.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(musicList);
        musicList.getColumnModel().getColumn(0).setResizable(false);
        musicList.getColumnModel().getColumn(0).setPreferredWidth(50);

        maddMusic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/repr_16.png"))); // NOI18N
        maddMusic.setText("Añadir a la lista");
        maddMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maddMusicActionPerformed(evt);
            }
        });

        mplayMusic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play_16.png"))); // NOI18N
        mplayMusic.setText("Reproducir");
        mplayMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mplayMusicActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout musicLayout = new javax.swing.GroupLayout(music);
        music.setLayout(musicLayout);
        musicLayout.setHorizontalGroup(
            musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(musicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, musicLayout.createSequentialGroup()
                        .addGap(0, 143, Short.MAX_VALUE)
                        .addComponent(mplayMusic)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maddMusic)))
                .addContainerGap())
        );
        musicLayout.setVerticalGroup(
            musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(musicLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maddMusic)
                    .addComponent(mplayMusic)))
        );

        music.setBounds(0, 0, 409, 357);
        mainContainer.add(music, javax.swing.JLayeredPane.DEFAULT_LAYER);

        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/next.png"))); // NOI18N
        nextButton.setText("Siguiente");
        nextButton.setToolTipText("Reproduce la siguiente canción");
        nextButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/prev.png"))); // NOI18N
        jButton5.setText("Anterior");
        jButton5.setToolTipText("Reproduce la canción anterior");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play.png"))); // NOI18N
        playButton.setText("Play");
        playButton.setToolTipText("Reproduce la canción seleccionada");
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stop.png"))); // NOI18N
        jButton7.setText("Stop");
        jButton7.setToolTipText("Detiene la reproducción de música");
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addComponent(playButton)
                    .addComponent(jButton5)
                    .addComponent(nextButton))
                .addContainerGap())
        );

        info.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(showFiles)
                        .addGap(0, 0, 0)
                        .addComponent(showMusic)
                        .addGap(0, 0, 0)
                        .addComponent(showRepr))
                    .addComponent(mainContainer)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(showRepr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showMusic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(info)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

////////////////////////////////////////////////////////////////////////////////
////////////////////////  Navegación entre pestañas ////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    private void showFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFilesActionPerformed
        // TODO add your handling code here:
        mainContainer.getComponent(0).setVisible(false);
        mainContainer.moveToFront(files);
        mainContainer.getComponent(0).setVisible(true);
    }//GEN-LAST:event_showFilesActionPerformed

    private void showMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMusicActionPerformed
        // TODO add your handling code here:
        mainContainer.getComponent(0).setVisible(false);
        mainContainer.moveToFront(music);
        mainContainer.getComponent(0).setVisible(true);
    }//GEN-LAST:event_showMusicActionPerformed

    private void showReprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showReprActionPerformed
        // TODO add your handling code here:
        mainContainer.getComponent(0).setVisible(false);
        mainContainer.moveToFront(repr);
        mainContainer.getComponent(0).setVisible(true);
    }//GEN-LAST:event_showReprActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        if (con.desconectar()) {
            ConnectFrame.setVisible(true);
            this.setVisible(false);
            resetDefaultValues();
        } else {
            info.setText("No se ha podido cerrar la conexión con el servidor...");
        }
    }//GEN-LAST:event_formWindowClosing

////////////////////////////////////////////////////////////////////////////////
/////////////////////  Buscar Servidor Automáticamente.  ///////////////////////
////////////////////////////////////////////////////////////////////////////////
    /**
     * Buscaremos un servidor iniciando una inundación UDP por la red local. El
     * proceso de inundación durará, como mucho, un minuto. Si transcurrido este
     * tiempo, no se localiza un servidor disponible, se dará la búsqueda cómo
     * fallida, y se avisará al usuario.
     */
    private void findServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findServerActionPerformed
        // TODO add your handling code here:
        autInfo.setText("Buscando servidor. Esto puede tomar un par de minutos.");
        findServer.setEnabled(false);
        findServer.setText("Buscando...");

        UDPBroadcast udp = new UDPBroadcast(this);
        udp.start();
    }//GEN-LAST:event_findServerActionPerformed

    /**
     * Establecemos la dirección IP encontrada en la inundación UDP para iniciar
     * la conexión. Esta función es, pues, usada solamente por la clase
     * UDPBroadcast para notificar a la interfaz de la dirección IP encontrada
     * en el proceso de búsqueda. Se nos mandará la IP encontrada en caso de que
     * la búsqueda sea satisfactoria, y NULL en caso de que haya sido fallida.
     */
    public void setIP(String ip) {
        if (ip != null) {
            autInfo.setText("Conectando...");
            findServer.setText("Conectando...");
            serverIP.setText(ip);
            System.out.println(ip);
            conect.doClick();
        } else {
            autInfo.setText("<html>Imposible contactar con el servidor.<br>"
                    + "Por favor, prueba dentro de unos momentos.</html>");
            findServer.setEnabled(true);
            findServer.setText("Buscar servidor");
        }
    }

    /**
     * Establece el texto de los campos de información y los botones a sus
     * valores por defecto, quedándose así la aplicación como recién iniciada.
     */
    private void resetDefaultValues() {
        //Vaciamos las tablas
        clearTable(fileList);
        clearTable(reprList);
        clearTable(musicList);

        //Ocultamos la información anterior
        autInfo.setText("");
        connInfo.setText("");

        //Habilitamos los botones y establecemos su texto
        conect.setText("Conectar");
        conect.setEnabled(true);
        findServer.setText("Buscar servidor");
        findServer.setEnabled(true);
    }

    private void automaticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_automaticActionPerformed
        // TODO add your handling code here:
        manConnectPane.setVisible(false);
        autConnectPane.setVisible(true);
        connectionLayered.moveToFront(autConnectPane);
    }//GEN-LAST:event_automaticActionPerformed

    private void manualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualActionPerformed
        // TODO add your handling code here:
        manConnectPane.setVisible(true);
        autConnectPane.setVisible(false);
        connectionLayered.moveToFront(manConnectPane);
    }//GEN-LAST:event_manualActionPerformed

    private void serverIPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serverIPKeyTyped
        // TODO add your handling code here:
        serverIP.setForeground(Color.black);
        connInfo.setText("");
        if ((int) evt.getKeyChar() == 10) {
            conect.doClick();
        }
    }//GEN-LAST:event_serverIPKeyTyped

    private void conectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectActionPerformed
        // TODO add your handling code here:

        conect.setEnabled(false);
        conect.setText("Conectando...");

        findServer.setEnabled(false);
        findServer.setText("Conectando...");

        ConnectFrame.repaint();

        boolean suscess = connectWithServer();

        if (!suscess) {

            conect.setEnabled(true);
            conect.setText("Conectar");

            findServer.setEnabled(true);
            findServer.setText("Buscar servidor");
        }
    }//GEN-LAST:event_conectActionPerformed

    /**
     * Realiza todas las comprobaciones necesarias para realizar una conexión
     * segura con el servidor, e intenta conectar con él. En caso de que ocurra
     * algún error, se devolverá FALSE y se alertará al usuario por la interfaz.
     *
     * @return TRUE si todo va bien, FALSE en caso de que haya algún error.
     */
    private boolean connectWithServer() {
        //Hacemos una comprobacion rápida de la ip.
        JLabel label = autConnectPane.isVisible() ? autInfo : connInfo;
        label.setText("Comprobando...");

        if (URI.isWellFormedAddress(serverIP.getText())) {
            try {
                //Comprobamos que la direccion y el puerto son validos
                Inet4Address.getByName(serverIP.getText());
            } catch (UnknownHostException ex) {
                label.setText("La dirección IP no es correcta.");
                serverIP.setForeground(Color.red);
                return false;
            }
            //Avisamos al usuario de que vamos a intentar conectarnos
            label.setText("Intentando realizar la conexión...");
            //Intentamos conectarnos
            try {
                if (!con.conectar(serverIP.getText(), ec)) {
                    JOptionPane.showMessageDialog(this,
                            "<html>Se ha producido un error al establecer la conexión con el servidor<br>"
                            + "La causa de este error puede haber sido:<br>"
                            + "<ul><li>Ya hay una conexión abierta</li>"
                            + "<li>El servidor no ha podido responder</li>"
                            + "<li>El servidor está escuchando por otro puerto</li></ul>"
                            + "Por favor, intentelo más tarde...</html>", "Error al realizar la conexión.", JOptionPane.OK_OPTION);
                    label.setText("Error al conectar con el servidor.");
                    return false;
                }
                showInterface();
                return true;

            } catch (IOException ex) {
                //Si ocurre alguna excepcion, avisamos al usuario.
                JOptionPane.showMessageDialog(this,
                        "<html>Se ha producido un error al establecer la conexión con el servidor<br>"
                        + "La causa de este error puede haber sido:<br>"
                        + "<ul><li>Ya hay una conexión abierta</li>"
                        + "<li>El servidor no ha podido responder</li>"
                        + "<li>El servidor está escuchando por otro puerto</li></ul>"
                        + "Por favor, intentelo más tarde...</html>", "Error al realizar la conexión.", JOptionPane.OK_OPTION);
                label.setText("Error al conectar con el servidor.");
                return false;
            }

        } else {
            //Si la ip no es valida, avisamos al usuario
            serverIP.setForeground(Color.red);
            label.setText("La dirección IP no es válida.");
            return false;
        }
    }
    
////////////////////////////////////////////////////////////////////////////////
/////////////////////  Acciones de los meús emergentes.  ///////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    private void RMplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RMplayActionPerformed
        // TODO add your handling code here:
        playFromRepr(reprList.getSelectedRow());
    }//GEN-LAST:event_RMplayActionPerformed

    private void RMdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RMdeleteActionPerformed
        // TODO add your handling code here:
        deleteFomRepr(reprList.getSelectedRow());
    }//GEN-LAST:event_RMdeleteActionPerformed

    private void MMaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MMaddActionPerformed
        try {
            // TODO add your handling code here:
            String song = musicList.getValueAt(musicList.getSelectedRow(), 0).toString();
            if (con.addSongtoListFromMusic(musicList.getSelectedRow())) {
                insertOnTable(reprList, song);
                info.setForeground(Color.green);
                info.setText("Se ha añadido " + song + " a la lista de reproducción.");
            } else {
                info.setForeground(Color.red);
                info.setText("No se ha podido añadir la canción. Intentalo de nuevo en un momento...");
            }
        } catch (IOException ex) {
            info.setForeground(Color.red);
            info.setText("Hubo un problema al realizar la conexión. Si persiste, reinicie el servidor.");
        }
    }//GEN-LAST:event_MMaddActionPerformed

    private void MMplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MMplayActionPerformed
        // TODO add your handling code here:
        playFromMusic(musicList.getSelectedRow());
    }//GEN-LAST:event_MMplayActionPerformed

    private void FMupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FMupActionPerformed
        // TODO add your handling code here:
        changeDirectory("..");
    }//GEN-LAST:event_FMupActionPerformed

    private void FMabrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FMabrirActionPerformed
        // TODO add your handling code here:
        changeDirectory(fileList.getValueAt(fileList.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_FMabrirActionPerformed

    private void FMplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FMplayActionPerformed
        // TODO add your handling code here:
        play(fileList.getValueAt(fileList.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_FMplayActionPerformed

    private void FMaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FMaddActionPerformed
        // TODO add your handling code here:
        try {
            String song = fileList.getValueAt(fileList.getSelectedRow(), 0).toString();
            if (con.addSongtoList(song)) {
                insertOnTable(reprList, song);
                info.setForeground(Color.green);
                info.setText("Se ha añadido " + song + " a la lista de reproducción.");
            } else {
                info.setForeground(Color.red);
                info.setText("No se ha podido añadir la canción. Intentalo de nuevo en un momento...");
            }
        } catch (IOException ex) {
            info.setForeground(Color.red);
            info.setText("Hubo un problema al realizar la conexión. Si persiste, reinicie el servidor.");
        }
    }//GEN-LAST:event_FMaddActionPerformed

    private void mplayMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mplayMusicActionPerformed
        // TODO add your handling code here:
        int index = musicList.getSelectedRow();
        if (index > 0) {
            playFromMusic(index);
        }
    }//GEN-LAST:event_mplayMusicActionPerformed

    private void maddMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maddMusicActionPerformed
        // TODO add your handling code here:
        int index = musicList.getSelectedRow();
        if (index <= 0) {
            return;
        }
        try {
            String song = musicList.getValueAt(index, 0).toString();
            if (con.addSongtoListFromMusic(index)) {
                insertOnTable(reprList, song);
                info.setForeground(Color.green);
                info.setText("Se ha añadido " + song + " a la lista de reproducción.");
            } else {
                info.setForeground(Color.red);
                info.setText("No se ha podido añadir la canción. Intentalo de nuevo en un momento...");
            }
        } catch (IOException ex) {
            info.setForeground(Color.red);
            info.setText("Hubo un problema al realizar la conexión. Si persiste, reinicie el servidor.");
        }
    }//GEN-LAST:event_maddMusicActionPerformed

    private void repeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatActionPerformed
        // TODO add your handling code here:
        con.repeat(repeat.isSelected());
    }//GEN-LAST:event_repeatActionPerformed

    private void randomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomActionPerformed
        // TODO add your handling code here:
        con.shuffle(random.isSelected());
    }//GEN-LAST:event_randomActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        // TODO add your handling code here:
        int song = -1;
        if (repr.isVisible()) {
            song = reprList.getSelectedRow();
            if (song != -1) {
                con.playFromRepr(song);
            } else {
                con.next();
            }
        } else if (music.isVisible()) {
            song = musicList.getSelectedRow();
            if (song != -1) {
                con.playFromMusic(song);
            } else {
                con.next();
            }
        } else if (files.isVisible()) {
            song = fileList.getSelectedRow();
            if (song != -1) {
                con.play(reprList.getValueAt(song, 0).toString());
            } else {
                con.next();
            }
        }
    }//GEN-LAST:event_playButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
        con.next();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        con.prev();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void serverIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverIPActionPerformed

    private void insertOnTable(javax.swing.JTable table, Object o) {
        ((DefaultTableModel) table.getModel()).addRow(new Object[]{o});
    }

    private void clearTable(javax.swing.JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.getDataVector().clear();
        model.fireTableDataChanged();
    }

    public void setSong(String song) {
        info.setForeground(Color.blue);
        info.setText("Reproduciendo " + song);
    }

    private void changeDirectory(String folder) {
        if (con.cd(folder)) {
            dirFiles();
        } else {
            info.setText("Error al moverse de directorio. Intentalo más tarde.");
        }
    }

    private void dirFiles() {
        ArrayList<String> files = con.dir();
        if (files == null) {
            info.setText("Error al cargar los archivos de la carpeta.");
            return;
        }
        clearTable(fileList);
        insertOnTable(fileList, "..");
        for (String s : files) {
            insertOnTable(fileList, s);
        }
        ((DefaultTableModel) fileList.getModel()).fireTableDataChanged();
    }

    private void play(String song) {
        con.play(song);
        info.setText("No se ha podido reproducir la canción.");
    }

    private void playFromMusic(int song) {
        con.playFromMusic(song);
        info.setText("No se ha podido reproducir la canción.");
    }

    private void playFromRepr(int song) {
        con.playFromRepr(song);
        info.setText("No se ha podido reproducir la canción.");
    }

    public void setMusicList(ArrayList<String> _m) {
        for (String s : _m) {
            insertOnTable(musicList, s.substring(s.lastIndexOf("\\") + 1));
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame ConnectFrame;
    private javax.swing.JMenuItem FMabrir;
    private javax.swing.JMenuItem FMadd;
    private javax.swing.JMenuItem FMplay;
    private javax.swing.JMenuItem FMup;
    private javax.swing.JMenuItem MMadd;
    private javax.swing.JMenuItem MMplay;
    private javax.swing.JMenuItem RMdelete;
    private javax.swing.JMenuItem RMplay;
    private javax.swing.JPanel autConnectPane;
    private javax.swing.JLabel autInfo;
    private javax.swing.JToggleButton automatic;
    private javax.swing.JButton conect;
    private javax.swing.ButtonGroup conection_buttons;
    private javax.swing.JLabel connInfo;
    private javax.swing.JLayeredPane connectionLayered;
    private javax.swing.JTable fileList;
    private javax.swing.JPopupMenu fileMenu;
    private javax.swing.JPanel files;
    private javax.swing.JButton findServer;
    private javax.swing.JLabel info;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JButton maddMusic;
    private javax.swing.JLayeredPane mainContainer;
    private javax.swing.JPanel manConnectPane;
    private javax.swing.JToggleButton manual;
    private javax.swing.JButton mplayMusic;
    private javax.swing.JPanel music;
    private javax.swing.JTable musicList;
    private javax.swing.JPopupMenu musicMenu;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton playButton;
    private javax.swing.JToggleButton random;
    private javax.swing.JToggleButton repeat;
    private javax.swing.JPanel repr;
    private javax.swing.JTable reprList;
    private javax.swing.JPopupMenu reprMenu;
    private javax.swing.JTextField serverIP;
    private javax.swing.JToggleButton showFiles;
    private javax.swing.JToggleButton showMusic;
    private javax.swing.JToggleButton showRepr;
    private javax.swing.ButtonGroup views;
    // End of variables declaration//GEN-END:variables

    public void connectError() {
        con.desconectar();
        connInfo.setText("");
        this.setVisible(false);
        ConnectFrame.setVisible(true);
        JOptionPane.showMessageDialog(mainContainer, "La conexión se ha caido. Inténtalo de nuevo más tarde o comprueba el servidor.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
    }

    private void deleteFomRepr(int song) {
        try {
            if (con.deleteFromRepr(song)) {
                ((DefaultTableModel) reprList.getModel()).removeRow(reprList.getSelectedRow());
                info.setText("Canción eliminada.");
            } else {
                info.setText("No se ha podido eliminar la canción de la lista...");

            }
        } catch (IOException ex) {
            info.setText("No se ha podido eliminar la canción de la lista...");
        }
    }

    public Ecualizador getEcualizador() {
        return ec;
    }

    public void showInterface() {
        showFiles.doClick();
        info.setText("");
        ConnectFrame.setVisible(false);
        dirFiles();
        this.setVisible(true);
    }
}
