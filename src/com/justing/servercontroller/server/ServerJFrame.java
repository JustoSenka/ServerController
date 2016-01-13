package com.justing.servercontroller.server;

import com.justing.servercontroller.utils.Logger;
import com.justing.servercontroller.utils.LoggerTextArea;
import java.io.IOException;

/**
 *
 * @author JustInG
 */
public class ServerJFrame extends javax.swing.JFrame {

    private Server s = Server.getInstance();
    private final Logger logger;

    public ServerJFrame() {
        initComponents();
        logger = new LoggerTextArea(textArea);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        portField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        hostButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        ipField = new javax.swing.JTextField();
        logOnlyMessagesCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ServerController - Server");
        setName("serverFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(500, 400));
        setSize(new java.awt.Dimension(500, 400));

        portField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        portField.setToolTipText("Enter port number");
        portField.setName("portInput"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Port:");
        jLabel1.setName("portLabel"); // NOI18N

        hostButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        hostButton.setText("Host");
        hostButton.setName("hostButton"); // NOI18N
        hostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostButtonActionPerformed(evt);
            }
        });

        stopButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        stopButton.setName("stopButton"); // NOI18N
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        textArea.setName("textAreafon"); // NOI18N
        jScrollPane1.setViewportView(textArea);

        ipField.setEditable(false);
        ipField.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        ipField.setName("ipFIeld"); // NOI18N

        logOnlyMessagesCheck.setText("Log only messages");
        logOnlyMessagesCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOnlyMessagesCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hostButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton)
                .addGap(18, 18, 18)
                .addComponent(logOnlyMessagesCheck)
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(ipField))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(hostButton)
                    .addComponent(stopButton)
                    .addComponent(logOnlyMessagesCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostButtonActionPerformed
        int port;
        if ((port = tryParse(portField.getText())) == -1) {
            logger.log("Invalid port.");
        } else {
            try {
                s.setLogger(logger);
                s.createSocket(port);
                logger.log("Server successfully hosted.");
                ipField.setText("Local server address: " + s.getLocalAddress());
                stopButton.setEnabled(true);
                hostButton.setEnabled(false);
            } catch (IOException ex) {
                logger.log(ex.getMessage());
            }
        }
    }//GEN-LAST:event_hostButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        s.destroySocket();
        stopButton.setEnabled(false);
        hostButton.setEnabled(true);
        ipField.setText("");
        logger.log("Server successfully destroyed.");
    }//GEN-LAST:event_stopButtonActionPerformed

    private void logOnlyMessagesCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOnlyMessagesCheckActionPerformed
        s.setLogOnlyMessages(logOnlyMessagesCheck.isSelected());
    }//GEN-LAST:event_logOnlyMessagesCheckActionPerformed

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
    private javax.swing.JButton hostButton;
    private javax.swing.JTextField ipField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox logOnlyMessagesCheck;
    private javax.swing.JTextField portField;
    private javax.swing.JButton stopButton;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
