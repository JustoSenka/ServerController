package com.justing.servercontroller.client;

import com.justing.servercontroller.utils.Logger;
import com.justing.servercontroller.utils.LoggerTextArea;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author JustInG
 */
public class ClientJFrame extends javax.swing.JFrame {

    Client c = Client.getInstance();
    private final Logger logger;

    public ClientJFrame() {
        initComponents();

        logger = new LoggerTextArea(textArea);
        
        try {
            ipField.setText(InetAddress.getLocalHost().toString().split("/")[1]);
        } catch (UnknownHostException ex) {
            logger.log(ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ipField = new javax.swing.JTextField();
        ipLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        dataField = new javax.swing.JTextField();
        portField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ServerController - Client");
        setName("clientFrame"); // NOI18N
        setSize(new java.awt.Dimension(500, 400));

        ipField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ipField.setToolTipText("Enter IP to connect");
        ipField.setName("portInput"); // NOI18N

        ipLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ipLabel.setText("Ip:");
        ipLabel.setName("portLabel"); // NOI18N

        sendButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        sendButton.setText("Send");
        sendButton.setName("sendButton"); // NOI18N
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        textArea.setName("textAreafon"); // NOI18N
        jScrollPane1.setViewportView(textArea);

        dataField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        dataField.setToolTipText("Enter data to send");
        dataField.setName("portInput"); // NOI18N

        portField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        portField.setToolTipText("Enter port to connect");
        portField.setName("portInput"); // NOI18N

        portLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        portLabel.setText("Port:");
        portLabel.setName("portLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ipLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(portLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(dataField, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sendButton)))
                        .addGap(190, 190, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipLabel)
                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portLabel)
                    .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendButton)
                    .addComponent(dataField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("ServerController - Client");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        
        int port = tryParse(portField.getText());
        if (port != -1) {
            c.setLogger(logger);
            
            if (c.connect(ipField.getText(), port) == 0) {
                c.send(dataField.getText() + "");
                logger.log(c.get());
                c.disconect();
            }
        }
        else {
            logger.log("Invalid port.");
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private int tryParse(String str) {
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            i = -1;
        }
        return i;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dataField;
    private javax.swing.JTextField ipField;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
