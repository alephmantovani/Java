/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoteste.view;

import projetoteste.interfaces.JornTrabalho;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import projetoteste.Classes.JornadaTrabalho;

/**
 *
 * @author S430
 */
public class digSettings extends javax.swing.JDialog implements JornTrabalho{

    LocalTime entrada1;
    LocalTime saida1;
    LocalTime entrada2;
    LocalTime saida2;
    JornadaTrabalho jorn;
    int jornada = 0;

    public digSettings(java.awt.Frame parent, boolean modal, JornadaTrabalho j) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(this);
        this.setTitle("Configurar Jornada de Trabalho");

        jorn = j;
        
        // Jornada de Trabalho PADRÃO 40h
        entrada1 = LocalTime.of(8, 0);
        saida1 = LocalTime.of(12, 0);
        entrada2 = LocalTime.of(14, 0);
        saida2 = LocalTime.of(18, 0);
        cbxJornada.setSelectedIndex(0);

    }
    
    @Override
    public void jornadaTrabalho(int x) {
        //Formata a data para a tabela
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("HH:mm");
        
        if (x == 40) {
            // Jornada de Trabalho PADRÃO 40h
            entrada1 = LocalTime.of(8, 0);
            saida1 = LocalTime.of(12, 0);
            entrada2 = LocalTime.of(14, 0);
            saida2 = LocalTime.of(18, 0);
            cbxJornada.setSelectedIndex(1);
            jornada = 40;
        } else if (x == 44) {
            // Jornada de Trabalho PADRÃO 44h
            entrada1 = LocalTime.of(7, 0, 0);
            saida1 = LocalTime.of(12, 0, 0);
            entrada2 = LocalTime.of(14, 0, 0);
            saida2 = LocalTime.of(18, 0, 0);
            cbxJornada.setSelectedIndex(2);
            jornada = 44;
        }
        //Popula os campos de horas
        txtEntrada1.setText(entrada1.format(formatoData));
        txtSaida1.setText(saida1.format(formatoData));
        txtEntrada2.setText(entrada2.format(formatoData));
        txtSaida2.setText(saida2.format(formatoData));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtEntrada1 = new javax.swing.JTextField();
        txtSaida1 = new javax.swing.JTextField();
        txtSaida2 = new javax.swing.JTextField();
        txtEntrada2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxJornada = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configurar Jornada de Trabalho", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        txtEntrada1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtSaida1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtSaida2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtEntrada2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Entrada 1");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Saída 1");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Entrada 2");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Saída 2");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Regime:");

        cbxJornada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione uma opção", "40 Horas Semanais", "44 Horas Semanais" }));
        cbxJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxJornadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(txtEntrada2)
                            .addComponent(txtEntrada1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(txtSaida1)
                            .addComponent(txtSaida2)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxJornada, 0, 189, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntrada1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaida1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntrada2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaida2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnSalvar.setText("SALVAR");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalvar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salvarDados(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        jorn.setEntrada1(LocalTime.parse(txtEntrada1.getText(), format));
        jorn.setSaida1(LocalTime.parse(txtSaida1.getText(), format));
        jorn.setEntrada2(LocalTime.parse(txtEntrada2.getText(), format));
        jorn.setSaida2(LocalTime.parse(txtSaida2.getText(), format));
        jorn.setJornadaSemanal(jornada);
    }
    
    //ComboBox de Jornada de Trabalho
    private void cbxJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxJornadaActionPerformed
        if (cbxJornada.getSelectedIndex() == 1) {
            jornadaTrabalho(40);
        } else if(cbxJornada.getSelectedIndex() == 2){
            jornadaTrabalho(44);
        }
    }//GEN-LAST:event_cbxJornadaActionPerformed

    //Botão SALVAR
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarDados();
        this.dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxJornada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtEntrada1;
    private javax.swing.JTextField txtEntrada2;
    private javax.swing.JTextField txtSaida1;
    private javax.swing.JTextField txtSaida2;
    // End of variables declaration//GEN-END:variables
}
