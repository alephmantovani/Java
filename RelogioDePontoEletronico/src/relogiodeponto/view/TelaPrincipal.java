package relogiodeponto.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import relogiodeponto.classes.Atrasos;
import relogiodeponto.classes.Controlador;
import relogiodeponto.classes.HorarioDeTrabalho;
import relogiodeponto.classes.HorasExtras;
import relogiodeponto.classes.MarcacoesFeitas;
import relogiodeponto.classes.TratamentoDados;

/**
 *
 * @author AlephMantovani
 */
public class TelaPrincipal extends javax.swing.JFrame {

    //Modelos das Tabelas
    DefaultTableModel modHT, modMF, modAt, modEx;

    //Objetos de Classes
    TratamentoDados tratDados;
    HorarioDeTrabalho hrTrab;
    MarcacoesFeitas marcF;
    HorasExtras hEx;
    Atrasos aTra;
    Controlador cont;

//--Construtor da Classe
    public TelaPrincipal() {
        initComponents();

        //Criando um Painel com Abas
        abas.addTab("Horário de Trabalho", Horario);
        abas.addTab("Marcações Feitas", Marcacoes);
        abas.addTab("Atrasos", Atrasos);
        abas.addTab("Horas Extras", Extras);
        abas.setSelectedIndex(0);

        //Verifica a aba Ativa
        abas.addChangeListener((ChangeEvent e) -> {
            verificarAbaAtiva();
        });

        configurarPainel();

        //BOTÃO SAIR
        btnSair.addActionListener((ActionEvent e) -> {
            fecharApp();
        });

        //BOTÃO INSERIR HORARIO
        btnInserir_Horario.addActionListener((ActionEvent e) -> {
            prepararTabela(0);
        });

        //BOTÃO INSERIR MARCAÇÃO
        btnInserir_Marcacoes.addActionListener((ActionEvent e) -> {
            prepararTabela(1);
        });

        //EXCLUIR HORARIO
        btnExcluir_Horario.addActionListener((ActionEvent e) -> {
            excluirDados();
        });

        //EXCLUIR MARCAÇÕES
        btnExcluir_Marcacoes.addActionListener((ActionEvent e) -> {
            excluirDados();
        });
        
        //LIMPAR A TABELA HORARIOS
        btnLimpar_Horarios.addActionListener((ActionEvent e) -> {
            limparTabela("hora");
        });
        
        //LIMPAR A TABELA MARCAÇÕES
        btnLimpar_Marcacoes.addActionListener((ActionEvent e) -> {
            limparTabela("marc");
        });

        //CALCULAR ATRASOS (passa 0 como referencia a tabela de Atraso)
        btnCalc_Atraso.addActionListener((ActionEvent e) -> {
            prepararDados(0);
        });

        //CALCULAR HORAS EXTRAS (passa 1 como referencia a tabela de Hora Extra)
        btnCalc_HoraExtra.addActionListener((ActionEvent e) -> {
            prepararDados(1);
        });

        //CONFIGURA O PERIODO NOTURNO
        btnConfig.addActionListener((ActionEvent e) -> {
            configurarPeriodoNoturno();
        });
    }

//--Configuração da Aplicação
    private void configurarPainel() {

        //Config. Título, Posição, Redimensionamento, Fechar
        this.setTitle("..::Relógio de Ponto::..");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);

        //Cor das abas
        abas.setBackground(Color.LIGHT_GRAY);

        //Configura automaticamente a hora do Periodo Noturno e da Jornada de Trabalho
        cont = new Controlador();
        cont.configHorario(22);
        cont.configJornadaTrabalho(40);

        //Configurando o icone da barra de título
        URL icone = getClass().getResource("/relogiodeponto/imagens/relogio.png");
        Image imagem = Toolkit.getDefaultToolkit().getImage(icone);
        this.setIconImage(imagem);

        //Zera as linhas da tabela
        modHT = (DefaultTableModel) HorarioDeTrabalho.getModel();
        modMF = (DefaultTableModel) MarcacoesFeitas.getModel();
        modAt = (DefaultTableModel) Atraso.getModel();
        modEx = (DefaultTableModel) HoraExtra.getModel();
        modAt.setRowCount(0);
        modEx.setRowCount(0);

        //Focaliza os campos
        focalizaCampos();

        //Iniciando o Frame
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abas = new javax.swing.JTabbedPane();
        Horario = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        HorarioDeTrabalho = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnInserir_Horario = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtEntrada_Horario = new javax.swing.JFormattedTextField();
        txtSaida_Horario = new javax.swing.JFormattedTextField();
        btnExcluir_Horario = new javax.swing.JButton();
        btnLimpar_Horarios = new javax.swing.JButton();
        Marcacoes = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        MarcacoesFeitas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnInserir_Marcacoes = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        txtEntrada_Marcacoes = new javax.swing.JFormattedTextField();
        txtSaida_Marcacoes = new javax.swing.JFormattedTextField();
        btnExcluir_Marcacoes = new javax.swing.JButton();
        btnLimpar_Marcacoes = new javax.swing.JButton();
        Atrasos = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Atraso = new javax.swing.JTable();
        btnCalc_Atraso = new javax.swing.JButton();
        Extras = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        HoraExtra = new javax.swing.JTable();
        btnCalc_HoraExtra = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        abas.setFont(abas.getFont().deriveFont(abas.getFont().getStyle() | java.awt.Font.BOLD, abas.getFont().getSize()+1));

        Horario.setFont(Horario.getFont().deriveFont(Horario.getFont().getStyle() | java.awt.Font.BOLD, Horario.getFont().getSize()+1));

        HorarioDeTrabalho.setFont(HorarioDeTrabalho.getFont().deriveFont(HorarioDeTrabalho.getFont().getStyle() | java.awt.Font.BOLD, HorarioDeTrabalho.getFont().getSize()+1));
        HorarioDeTrabalho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ENTRADA", "SAÍDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        HorarioDeTrabalho.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        HorarioDeTrabalho.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(HorarioDeTrabalho);
        if (HorarioDeTrabalho.getColumnModel().getColumnCount() > 0) {
            HorarioDeTrabalho.getColumnModel().getColumn(0).setResizable(false);
            HorarioDeTrabalho.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+1f));
        jLabel1.setText("ENTRADA:");

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setText("SAÍDA:");

        btnInserir_Horario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnInserir_Horario.setText("INSERIR");
        btnInserir_Horario.setToolTipText("Insere os campos na tabela");
        btnInserir_Horario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnInserir_HorarioKeyReleased(evt);
            }
        });

        try {
            txtEntrada_Horario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtEntrada_Horario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEntrada_Horario.setFont(txtEntrada_Horario.getFont().deriveFont(txtEntrada_Horario.getFont().getSize()+1f));
        txtEntrada_Horario.setNextFocusableComponent(txtSaida_Horario);

        try {
            txtSaida_Horario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtSaida_Horario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSaida_Horario.setFont(txtSaida_Horario.getFont().deriveFont(txtSaida_Horario.getFont().getSize()+1f));

        btnExcluir_Horario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExcluir_Horario.setText("EXCLUIR");
        btnExcluir_Horario.setToolTipText("Exclui a linha selecionada");

        btnLimpar_Horarios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLimpar_Horarios.setText("LIMPAR");
        btnLimpar_Horarios.setToolTipText("Limpar toda a tabela");

        javax.swing.GroupLayout HorarioLayout = new javax.swing.GroupLayout(Horario);
        Horario.setLayout(HorarioLayout);
        HorarioLayout.setHorizontalGroup(
            HorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HorarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, HorarioLayout.createSequentialGroup()
                        .addGroup(HorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(txtEntrada_Horario))
                        .addGap(37, 37, 37)
                        .addGroup(HorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HorarioLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(HorarioLayout.createSequentialGroup()
                                .addComponent(txtSaida_Horario, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnInserir_Horario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExcluir_Horario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLimpar_Horarios)))))
                .addContainerGap())
        );
        HorarioLayout.setVerticalGroup(
            HorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HorarioLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(HorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntrada_Horario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaida_Horario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInserir_Horario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir_Horario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar_Horarios, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        btnInserir_Horario.getAccessibleContext().setAccessibleParent(abas);

        abas.addTab("Horário de Trabalho", Horario);

        Marcacoes.setFont(Marcacoes.getFont().deriveFont(Marcacoes.getFont().getStyle() | java.awt.Font.BOLD, Marcacoes.getFont().getSize()+1));

        MarcacoesFeitas.setFont(MarcacoesFeitas.getFont().deriveFont(MarcacoesFeitas.getFont().getStyle() | java.awt.Font.BOLD, MarcacoesFeitas.getFont().getSize()+1));
        MarcacoesFeitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ENTRADA", "SAÍDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MarcacoesFeitas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        MarcacoesFeitas.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(MarcacoesFeitas);
        if (MarcacoesFeitas.getColumnModel().getColumnCount() > 0) {
            MarcacoesFeitas.getColumnModel().getColumn(0).setResizable(false);
            MarcacoesFeitas.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+1f));
        jLabel3.setText("ENTRADA:");

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+1f));
        jLabel4.setText("SAÍDA:");

        btnInserir_Marcacoes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnInserir_Marcacoes.setText("INSERIR");
        btnInserir_Marcacoes.setToolTipText("Insere os campos na tabela");
        btnInserir_Marcacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnInserir_MarcacoesKeyReleased(evt);
            }
        });

        try {
            txtEntrada_Marcacoes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtEntrada_Marcacoes.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEntrada_Marcacoes.setFont(txtEntrada_Marcacoes.getFont().deriveFont(txtEntrada_Marcacoes.getFont().getSize()+1f));

        try {
            txtSaida_Marcacoes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtSaida_Marcacoes.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSaida_Marcacoes.setFont(txtSaida_Marcacoes.getFont().deriveFont(txtSaida_Marcacoes.getFont().getSize()+1f));

        btnExcluir_Marcacoes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExcluir_Marcacoes.setText("EXCLUIR");
        btnExcluir_Marcacoes.setToolTipText("Exclui a linha selecionada");

        btnLimpar_Marcacoes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLimpar_Marcacoes.setText("LIMPAR");
        btnLimpar_Marcacoes.setToolTipText("Limpa toda a tabela");

        javax.swing.GroupLayout MarcacoesLayout = new javax.swing.GroupLayout(Marcacoes);
        Marcacoes.setLayout(MarcacoesLayout);
        MarcacoesLayout.setHorizontalGroup(
            MarcacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MarcacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MarcacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, MarcacoesLayout.createSequentialGroup()
                        .addGroup(MarcacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(txtEntrada_Marcacoes))
                        .addGap(37, 37, 37)
                        .addGroup(MarcacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(MarcacoesLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(MarcacoesLayout.createSequentialGroup()
                                .addComponent(txtSaida_Marcacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnInserir_Marcacoes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExcluir_Marcacoes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLimpar_Marcacoes))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
                .addContainerGap())
        );
        MarcacoesLayout.setVerticalGroup(
            MarcacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcacoesLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(MarcacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MarcacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntrada_Marcacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaida_Marcacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir_Marcacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInserir_Marcacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar_Marcacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        abas.addTab("Marcações Feitas", Marcacoes);

        Atrasos.setFont(Atrasos.getFont().deriveFont(Atrasos.getFont().getStyle() | java.awt.Font.BOLD, Atrasos.getFont().getSize()+1));

        Atraso.setFont(Atraso.getFont().deriveFont(Atraso.getFont().getSize()+1f));
        Atraso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "INÍCIO", "FIM", "ATRASO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Atraso.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(Atraso);
        if (Atraso.getColumnModel().getColumnCount() > 0) {
            Atraso.getColumnModel().getColumn(0).setResizable(false);
            Atraso.getColumnModel().getColumn(1).setResizable(false);
            Atraso.getColumnModel().getColumn(2).setResizable(false);
        }

        btnCalc_Atraso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCalc_Atraso.setText("CALCULAR");

        javax.swing.GroupLayout AtrasosLayout = new javax.swing.GroupLayout(Atrasos);
        Atrasos.setLayout(AtrasosLayout);
        AtrasosLayout.setHorizontalGroup(
            AtrasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AtrasosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AtrasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(AtrasosLayout.createSequentialGroup()
                        .addComponent(btnCalc_Atraso)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        AtrasosLayout.setVerticalGroup(
            AtrasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AtrasosLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(btnCalc_Atraso)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        abas.addTab("Atrasos", Atrasos);

        Extras.setFont(Extras.getFont().deriveFont(Extras.getFont().getStyle() | java.awt.Font.BOLD, Extras.getFont().getSize()+1));

        HoraExtra.setFont(HoraExtra.getFont().deriveFont(HoraExtra.getFont().getSize()+1f));
        HoraExtra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "INÍCIO", "FIM", "HORA EXTRA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        HoraExtra.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(HoraExtra);
        if (HoraExtra.getColumnModel().getColumnCount() > 0) {
            HoraExtra.getColumnModel().getColumn(0).setResizable(false);
            HoraExtra.getColumnModel().getColumn(1).setResizable(false);
            HoraExtra.getColumnModel().getColumn(2).setResizable(false);
        }

        btnCalc_HoraExtra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCalc_HoraExtra.setText("CALCULAR");

        javax.swing.GroupLayout ExtrasLayout = new javax.swing.GroupLayout(Extras);
        Extras.setLayout(ExtrasLayout);
        ExtrasLayout.setHorizontalGroup(
            ExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExtrasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(ExtrasLayout.createSequentialGroup()
                        .addComponent(btnCalc_HoraExtra)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ExtrasLayout.setVerticalGroup(
            ExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExtrasLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(btnCalc_HoraExtra)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        abas.addTab("Horas Extras", Extras);

        btnSair.setText("SAIR");

        btnConfig.setText("CONFIG");
        btnConfig.setToolTipText("Configura a Jornada de Trabalho e o Período Noturno");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(abas)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfig)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair)
                    .addComponent(btnConfig))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//--Evento do ENTER no Botão Inserir da Aba Horarios
    private void btnInserir_HorarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnInserir_HorarioKeyReleased
        if (evt.getKeyCode() == 10) {
            prepararTabela(0);
        }
    }//GEN-LAST:event_btnInserir_HorarioKeyReleased

//--Evento do ENTER no Botão Inserir da Aba Marcações
    private void btnInserir_MarcacoesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnInserir_MarcacoesKeyReleased
        if (evt.getKeyCode() == 10) {
            prepararTabela(1);
        }
    }//GEN-LAST:event_btnInserir_MarcacoesKeyReleased

//--Fechando a Aplicação
    public void fecharApp() {
        System.exit(0);
    }

//--Verifica qual aba está ativa
    public int verificarAbaAtiva() {
        return abas.getSelectedIndex();
    }

//--Focaliza os campos
    public void focalizaCampos() {

        if (verificarAbaAtiva() == 0) {
            txtEntrada_Horario.requestFocus();
            txtEntrada_Horario.setValue(null);
            txtSaida_Horario.setValue(null);
        } else {
            txtEntrada_Marcacoes.requestFocus();
            txtEntrada_Marcacoes.setValue(null);
            txtSaida_Marcacoes.setValue(null);
        }
    }

//--Prepara os dados para popular a tabela
    public void prepararDados(int tab) {

        if (modHT.getRowCount() == 0 || modMF.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não há nada para calcular!", "INFORMAÇÃO", 1);
            modAt.setRowCount(0);
            modEx.setRowCount(0);
        } else {
            if (tab == 0) {
                aTra = new Atrasos(Atraso);
                aTra.tamTabelas(modHT, modMF);
            } else {
                hEx = new HorasExtras(HoraExtra);
                hEx.tamTabelas(modHT, modMF);
            }
        }
    }

//--Pega os dados digitados e popula a Tabela, enviando para outra classe responsável
    public void prepararTabela(int aba) {

        try {
            tratDados = new TratamentoDados();
            hrTrab = new HorarioDeTrabalho();
            marcF = new MarcacoesFeitas();

            if (aba == 0) {

                hrTrab.setEntrada(LocalTime.parse(txtEntrada_Horario.getText()));
                hrTrab.setSaida(LocalTime.parse(txtSaida_Horario.getText()));
                modHT = (DefaultTableModel) HorarioDeTrabalho.getModel();
                tratDados.inserirDadosTabela(modHT, hrTrab, marcF, aba);
            } else {

                marcF.setEntrada(LocalTime.parse(txtEntrada_Marcacoes.getText()));
                marcF.setSaida(LocalTime.parse(txtSaida_Marcacoes.getText()));
                modMF = (DefaultTableModel) MarcacoesFeitas.getModel();
                tratDados.inserirDadosTabela(modMF, hrTrab, marcF, aba);
            }

            focalizaCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por Favor, insira um horário válido!", "Horário Inválido", 0);
            focalizaCampos();
        }
    }

//--Seleciona a linha e exclui
    public void excluirDados() {

        int linha;

        if (verificarAbaAtiva() == 0) {
            linha = HorarioDeTrabalho.getSelectedRow();
            modHT = (DefaultTableModel) HorarioDeTrabalho.getModel();
            tratDados.excluirDadosTabela(modHT, linha);
            txtEntrada_Horario.requestFocus();
        } else {
            linha = MarcacoesFeitas.getSelectedRow();
            modMF = (DefaultTableModel) MarcacoesFeitas.getModel();
            tratDados.excluirDadosTabela(modMF, linha);
            txtEntrada_Marcacoes.requestFocus();
        }
        
        //Limpa as tabelas de Atraso e Extras
        modAt.setRowCount(0);
        modEx.setRowCount(0);
    }
    
    //Limpa os dados da tabela selecionada
    public void limparTabela(String tabela){
        
        if(tabela.equals("hora")){
            modHT.setRowCount(0);
            modMF.setRowCount(0);
            txtEntrada_Horario.requestFocus();
        }else if(tabela.equals("marc")){
            modMF.setRowCount(0);
            txtEntrada_Marcacoes.requestFocus();
        }
        
        //Limpa as tabelas de Atraso e Extras
        modAt.setRowCount(0);
        modEx.setRowCount(0);
    }

//--Configura o Periodo Noturno
    public void configurarPeriodoNoturno() {
        Dig_Config dig = new Dig_Config(this, true);
        dig.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Atraso;
    private javax.swing.JPanel Atrasos;
    private javax.swing.JPanel Extras;
    private javax.swing.JTable HoraExtra;
    private javax.swing.JPanel Horario;
    private javax.swing.JTable HorarioDeTrabalho;
    private javax.swing.JPanel Marcacoes;
    private javax.swing.JTable MarcacoesFeitas;
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btnCalc_Atraso;
    private javax.swing.JButton btnCalc_HoraExtra;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnExcluir_Horario;
    private javax.swing.JButton btnExcluir_Marcacoes;
    private javax.swing.JButton btnInserir_Horario;
    private javax.swing.JButton btnInserir_Marcacoes;
    private javax.swing.JButton btnLimpar_Horarios;
    private javax.swing.JButton btnLimpar_Marcacoes;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JFormattedTextField txtEntrada_Horario;
    private javax.swing.JFormattedTextField txtEntrada_Marcacoes;
    private javax.swing.JFormattedTextField txtSaida_Horario;
    private javax.swing.JFormattedTextField txtSaida_Marcacoes;
    // End of variables declaration//GEN-END:variables
}
