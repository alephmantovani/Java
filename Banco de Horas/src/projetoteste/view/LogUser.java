package projetoteste.view;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import projetoteste.Classes.DadosPessoais;
import projetoteste.Classes.EntradaSaida;
import projetoteste.Classes.JornadaTrabalho;
import projetoteste.interfaces.JornTrabalho;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author S430
 */
public class LogUser extends javax.swing.JFrame implements JornTrabalho {

    DadosPessoais pess;
    EntradaSaida entSaid;
    JornadaTrabalho jorn;
    digFuncionario dig;
    DefaultTableModel modelo;
    ArrayList<LocalTime> atrasos;
    ArrayList<LocalTime> extras;

    /**
     * Creates new form logUser
     */
    public LogUser(DadosPessoais pess) {
        initComponents();

        this.setTitle("Funcionário");
        this.setVisible(false);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.pess = pess;
        
        //Passando os dados do Funcionário
        alimentarDadosPessoais();
              
        jorn = new JornadaTrabalho();

        jornadaPadrao();

        //Data Atual
        inserirDataAtual();

        //Inicia a Tabela
        modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
       
        lblParcial.setText("");

        this.setVisible(true);
    }

    //Data Atual
    private LocalDate inserirDataAtual() {
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblDataAtual.setText("Data Corrente: " + dt.format(formatoData));
        return dt;
    }

    private void jornadaPadrao() {
        jorn.setEntrada1(LocalTime.of(8, 0, 0));
        jorn.setSaida1(LocalTime.of(12, 0, 0));
        jorn.setEntrada2(LocalTime.of(14, 0, 0));
        jorn.setSaida2(LocalTime.of(18, 0, 0));
        jorn.setJornadaSemanal(40);
        jorn.setJornadaDiaria(8);

        //Setar Jornada de Trabalho
        lbl_JornadaTrabalho.setText("40h Semanais");
        lbl_CargaHoraria.setText("08:00 - 12:00 / 14:00 - 18:00");
    }

    //Popular os dados no frame
    private void alimentarDadosPessoais() {
        lblNome.setText(pess.getNome());
        lblCPF.setText(pess.getCpf());
        lblEmpresa.setText(pess.getEmpresa());
    }

    //Valida o horário
    private void validarHorario() {
        //Captura os horários
        entSaid = new EntradaSaida();

        try{
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        entSaid.setEntrada1(LocalTime.parse(txtEntrada1.getText(), format));
        entSaid.setSaida1(LocalTime.parse(txtSaida1.getText(), format));
        entSaid.setEntrada2(LocalTime.parse(txtEntrada2.getText(), format));
        entSaid.setSaida2(LocalTime.parse(txtSaida2.getText(), format));
        entSaid.setData(inserirDataAtual());
        } catch (Exception e ){
            JOptionPane.showMessageDialog(null, "Digite um horário válido", "ATENÇÃO", 2);
            txtEntrada1.requestFocus();
        }
        
        
        calcularAtraso();
    }

    //Completa a tabela com os dados inseridos
    private void popularTabela() {
        //Formata a data para a tabela
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int cont = modelo.getRowCount();

        modelo.addRow(new Object[]{
            entSaid.getData().format(formatoData),
            entSaid.getEntrada1(),
            entSaid.getSaida1(),
            entSaid.getEntrada2(),
            entSaid.getSaida2(),
            entSaid.getHoraTrab(),
            entSaid.getHoraExtra(),
            entSaid.getAtraso()
        });

        //Formata os campos para nova entrada
        if (modelo.getRowCount() > cont) {
            txtEntrada1.setText("00:00");
            txtEntrada2.setText("00:00");
            txtSaida1.setText("00:00");
            txtSaida2.setText("00:00");
            txtEntrada1.requestFocus();
            calcHoras();
        }
    }

//Calcula o Atraso do Funcionário
private void calcularAtraso() {
        //Horario que entrou e saiu
        LocalTime entrouM = entSaid.getEntrada1();
        LocalTime saiuM = entSaid.getSaida1();
        LocalTime entrouT = entSaid.getEntrada2();
        LocalTime saiuT = entSaid.getSaida2();

        //Calculos dos Periodos
        //Periodo da Manhã
        LocalTime horaTrabPeriodoManha = saiuM.minusHours(entrouM.getHour()).minusMinutes(entrouM.getMinute());
        //Periodo da Tarde
        LocalTime horaTrabPeriodoTarde = saiuT.minusHours(entrouT.getHour()).minusMinutes(entrouT.getMinute());
        //Total Trabalhado
        entSaid.setHoraTrab(LocalTime.of(horaTrabPeriodoManha.getHour() + horaTrabPeriodoTarde.getHour(), 0));

        LocalTime valor1 = entrouM.minusHours(jorn.getEntrada1().getHour()).minusMinutes(jorn.getEntrada1().getMinute());
        if(valor1.getHour() > jorn.getJornadaDiaria()) valor1 = LocalTime.of(0, 0);
        LocalTime valor2 = jorn.getSaida1().minusHours(saiuM.getHour()).minusMinutes(saiuM.getMinute());
        if(valor2.getHour() > jorn.getJornadaDiaria()) valor2 = LocalTime.of(0, 0);
        System.out.println("Atraso 2 = " + valor2);
        LocalTime valor3 = entrouT.minusHours(jorn.getEntrada2().getHour()).minusMinutes(jorn.getEntrada2().getMinute());
        if(valor3.getHour() > jorn.getJornadaDiaria()) valor3 = LocalTime.of(0, 0);
        LocalTime valor4 = jorn.getSaida2().minusHours(saiuT.getHour()).minusMinutes(saiuT.getMinute());
        if(valor4.getHour() > jorn.getJornadaDiaria()) valor4 = LocalTime.of(0, 0);
        
        LocalTime atraso = LocalTime.of(valor1.getHour() + valor2.getHour() + valor3.getHour() + valor4.getHour(), 0);
        
        long min = valor1.getMinute() + valor2.getMinute() + valor3.getMinute() + valor4.getMinute();

        //Se a soma dos minutos passar de 60, adiciona uma hora a mais
        if (min > 60) {
            atraso = (atraso.plusHours(1).plusMinutes(min - 60));
            System.out.println(">> " + atraso);
        } else {
            atraso = (atraso.plusMinutes(min));
            System.out.println("<< " + atraso);
        }
        
        entSaid.setAtraso(atraso);
        
        calcularHoraExtra();
    }

    //Calcula a hora extra do funcionário
    private void calcularHoraExtra(){        
        //Horario que entrou e saiu
        LocalTime entrouM = entSaid.getEntrada1();
        LocalTime saiuM = entSaid.getSaida1();
        LocalTime entrouT = entSaid.getEntrada2();
        LocalTime saiuT = entSaid.getSaida2();
        
        LocalTime valor1 = jorn.getEntrada1().minusHours(entrouM.getHour()).minusMinutes(entrouM.getMinute());
        if(valor1.getHour() > jorn.getJornadaDiaria()) valor1 = LocalTime.of(0, 0);
        LocalTime valor2 = saiuM.minusHours(jorn.getSaida1().getHour()).minusMinutes(jorn.getSaida1().getMinute());
        if(valor2.getHour() > jorn.getJornadaDiaria()) valor2 = LocalTime.of(0, 0);
        LocalTime valor3 = jorn.getEntrada2().minusHours(entrouT.getHour()).minusMinutes(entrouT.getMinute());
        if(valor3.getHour() > jorn.getJornadaDiaria()) valor3 = LocalTime.of(0, 0);
        LocalTime valor4 = saiuT.minusHours(jorn.getSaida2().getHour()).minusMinutes(jorn.getSaida2().getMinute());
        if(valor4.getHour() > jorn.getJornadaDiaria()) valor4 = LocalTime.of(0, 0);
        
        LocalTime extra = LocalTime.of(valor1.getHour() + valor2.getHour() + valor3.getHour() + valor4.getHour(), 0);
        
        long min = valor1.getMinute() + valor2.getMinute() + valor3.getMinute() + valor4.getMinute();

        //Se a soma dos minutos passar de 60, adiciona uma hora a mais
        if (min > 60) {
            extra = (extra.plusHours(1).plusMinutes(min - 60));
        } else {
            extra = (extra.plusMinutes(min));
        }
          entSaid.setHoraExtra(extra);
        
        popularTabela();
    }
    
    //Calcula a diferença entre os atrasos e as horas
    private void calcHoras(){
        
        atrasos = new ArrayList<>();
        extras = new ArrayList<>();
        LocalTime atraso = LocalTime.of(0,0);
        LocalTime extra = LocalTime.of(0,0);
        LocalTime total;
        long minAt = 0;
        long minEx = 0;
        
        for(int i = 0; i < tabela.getRowCount() ; i++){
            atrasos.add((LocalTime) tabela.getValueAt(i, 7));
            extras.add((LocalTime) tabela.getValueAt(i, 6));
            
            atraso = LocalTime.of(atraso.getHour() + atrasos.get(i).getHour(), 0);
            extra = LocalTime.of(extra.getHour() + extras.get(i).getHour(), 0);
            minAt = atrasos.get(i).getMinute() + minAt;
            minEx = extras.get(i).getMinute() + minEx;
        }
       
        //Se a soma dos minutos passar de 60, adiciona uma hora a mais
        if (minAt > 60) {
            atraso = (atraso.plusHours(1).plusMinutes(minAt - 60));
        } else {
            atraso = (atraso.plusMinutes(minAt));
        }
        //Se a soma dos minutos passar de 60, adiciona uma hora a mais
        if (minEx > 60) {
            extra = (extra.plusHours(1).plusMinutes(minEx - 60));
        } else {
            extra = (extra.plusMinutes(minEx));
        }
        
        total = extra.minusHours(atraso.getHour()).minusMinutes(atraso.getMinute());
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        lblParcial.setText("Soma das horas extras: " + format.format(extra) + " e dos atrasos: " + format.format(atraso));
        
        lblTotalResult.setText(format.format(total));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        pnlDadosFuncionario = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblEmpresa = new javax.swing.JLabel();
        pnlEntradaSaida = new javax.swing.JPanel();

        txtEntrada1 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter hora= new javax.swing.text.MaskFormatter("##:##"); 
            txtEntrada1 = new javax.swing.JFormattedTextField(hora); 
        } 
        catch (Exception e){ 
        }
        txtSaida1 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter hora= new javax.swing.text.MaskFormatter("##:##"); 
            txtSaida1 = new javax.swing.JFormattedTextField(hora); 
        } 
        catch (Exception e){ 
        }
        txtEntrada2 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter hora= new javax.swing.text.MaskFormatter("##:##"); 
            txtEntrada2 = new javax.swing.JFormattedTextField(hora); 
        } 
        catch (Exception e){ 
        }
        txtSaida2 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter hora= new javax.swing.text.MaskFormatter("##:##"); 
            txtSaida2 = new javax.swing.JFormattedTextField(hora); 
        } 
        catch (Exception e){ 
        }
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblDataAtual = new javax.swing.JLabel();
        btnInserir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        pnlRodape = new javax.swing.JPanel();
        lblParcial = new javax.swing.JLabel();
        lblTotalResult = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        pnlJornada = new javax.swing.JPanel();
        lbl_JornadaTrabalho = new javax.swing.JLabel();
        lbl_CargaHoraria = new javax.swing.JLabel();
        btn_JornadaTrabalho = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("BANCO DE HORAS");

        pnlDadosFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("NOME:");

        lblNome.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNome.setText("jLabel3");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("CPF:");

        lblCPF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCPF.setText("jLabel3");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("EMPRESA:");

        lblEmpresa.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEmpresa.setText("jLabel3");

        org.jdesktop.layout.GroupLayout pnlDadosFuncionarioLayout = new org.jdesktop.layout.GroupLayout(pnlDadosFuncionario);
        pnlDadosFuncionario.setLayout(pnlDadosFuncionarioLayout);
        pnlDadosFuncionarioLayout.setHorizontalGroup(
            pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlDadosFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 368, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, lblCPF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, lblEmpresa, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDadosFuncionarioLayout.setVerticalGroup(
            pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlDadosFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(lblNome))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(lblCPF))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnlDadosFuncionarioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(lblEmpresa))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlEntradaSaida.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entrada/Saída", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        txtEntrada1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEntrada1.setText("00:00");
        txtEntrada1.setToolTipText("");
        txtEntrada1.setCaretColor(new java.awt.Color(153, 153, 153));
        txtEntrada1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEntrada1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEntrada1FocusLost(evt);
            }
        });
        txtEntrada1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntrada1KeyPressed(evt);
            }
        });

        txtSaida1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSaida1.setText("00:00");
        txtSaida1.setToolTipText("");
        txtSaida1.setCaretColor(new java.awt.Color(102, 102, 102));
        txtSaida1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSaida1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSaida1FocusLost(evt);
            }
        });
        txtSaida1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSaida1KeyPressed(evt);
            }
        });

        txtEntrada2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEntrada2.setText("00:00");
        txtEntrada2.setCaretColor(new java.awt.Color(102, 102, 102));
        txtEntrada2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEntrada2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEntrada2FocusLost(evt);
            }
        });
        txtEntrada2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntrada2KeyPressed(evt);
            }
        });

        txtSaida2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSaida2.setText("00:00");
        txtSaida2.setCaretColor(new java.awt.Color(102, 102, 102));
        txtSaida2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSaida2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSaida2FocusLost(evt);
            }
        });
        txtSaida2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSaida2KeyPressed(evt);
            }
        });

        jLabel5.setText("Entrada 1:");

        jLabel6.setText("Entrada 2:");

        jLabel7.setText("Saída 1:");

        jLabel8.setText("Saída 2:");

        lblDataAtual.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDataAtual.setText("dd/mm/aaaa");

        btnInserir.setText("INSERIR");
        btnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlEntradaSaidaLayout = new org.jdesktop.layout.GroupLayout(pnlEntradaSaida);
        pnlEntradaSaida.setLayout(pnlEntradaSaidaLayout);
        pnlEntradaSaidaLayout.setHorizontalGroup(
            pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlEntradaSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlEntradaSaidaLayout.createSequentialGroup()
                        .add(pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(txtEntrada1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .add(63, 63, 63)
                        .add(pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(txtSaida1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(69, 69, 69)
                        .add(pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(txtEntrada2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(txtSaida2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .add(jLabel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(btnInserir, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(pnlEntradaSaidaLayout.createSequentialGroup()
                        .add(lblDataAtual)
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlEntradaSaidaLayout.setVerticalGroup(
            pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlEntradaSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblDataAtual)
                .add(18, 18, 18)
                .add(pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(jLabel6)
                    .add(jLabel7)
                    .add(jLabel8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlEntradaSaidaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtEntrada1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtSaida1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtEntrada2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtSaida2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(btnInserir)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Banco de Dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N
        jPanel1.setAutoscrolls(true);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Data", "Entrada 1", "Saída 1", "Entrada 2", "Saída 2", "Hora Trab", "Hora Extra", "Atraso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(6).setResizable(false);
            tabela.getColumnModel().getColumn(7).setResizable(false);
        }

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 154, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(284, 284, 284))
        );

        pnlRodape.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lblParcial.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblParcial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblParcial.setText("Extra:");

        lblTotalResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalResult.setToolTipText("");

        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("Total:");
        lblTotal.setToolTipText("");

        org.jdesktop.layout.GroupLayout pnlRodapeLayout = new org.jdesktop.layout.GroupLayout(pnlRodape);
        pnlRodape.setLayout(pnlRodapeLayout);
        pnlRodapeLayout.setHorizontalGroup(
            pnlRodapeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlRodapeLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblParcial, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 56, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblTotalResult, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlRodapeLayout.setVerticalGroup(
            pnlRodapeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlRodapeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(lblParcial, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lblTotal))
            .add(lblTotalResult, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlJornada.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jornada de Trabalho", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        lbl_JornadaTrabalho.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lbl_JornadaTrabalho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_JornadaTrabalho.setText("jLabel3");

        lbl_CargaHoraria.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lbl_CargaHoraria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_CargaHoraria.setText("00:00");

        btn_JornadaTrabalho.setText("Configurações");
        btn_JornadaTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_JornadaTrabalhoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlJornadaLayout = new org.jdesktop.layout.GroupLayout(pnlJornada);
        pnlJornada.setLayout(pnlJornadaLayout);
        pnlJornadaLayout.setHorizontalGroup(
            pnlJornadaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlJornadaLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlJornadaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lbl_CargaHoraria, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lbl_JornadaTrabalho, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlJornadaLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btn_JornadaTrabalho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(31, 31, 31))
        );
        pnlJornadaLayout.setVerticalGroup(
            pnlJornadaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlJornadaLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_JornadaTrabalho)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lbl_CargaHoraria)
                .add(11, 11, 11)
                .add(btn_JornadaTrabalho)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblTitulo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(pnlEntradaSaida, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(pnlDadosFuncionario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 342, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pnlJornada, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlRodape, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lblTitulo)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(pnlDadosFuncionario, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(pnlJornada, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlEntradaSaida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlRodape, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        if (txtEntrada1.getText().equals("") || txtEntrada1.getText().equals("00:00")) {
            txtEntrada1.requestFocus();
        } else {
            validarHorario();
        }
    }//GEN-LAST:event_btnInserirActionPerformed

    private void txtEntrada1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEntrada1FocusGained
        if (txtEntrada1.getText().equals("00:00")) {
            txtEntrada1.setCaretColor(Color.black);
            txtEntrada1.setCaretPosition(0);
        }
    }//GEN-LAST:event_txtEntrada1FocusGained

    private void txtEntrada1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEntrada1FocusLost
        if (txtEntrada1.getText().equals("")) {
            txtEntrada1.setText("00:00");
            txtEntrada1.setCaretColor(Color.gray);
            txtEntrada1.requestFocus();
        }
    }//GEN-LAST:event_txtEntrada1FocusLost

    private void txtEntrada1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntrada1KeyPressed
        if (txtEntrada1.getText().equals("00:00")) {
            txtEntrada1.setText("");
            txtEntrada1.setCaretColor(Color.black);
        }
    }//GEN-LAST:event_txtEntrada1KeyPressed

    private void txtSaida1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaida1FocusGained
        if (txtSaida1.getText().equals("00:00")) {
            txtSaida1.setCaretColor(Color.black);
            txtSaida1.setCaretPosition(0);
        }
    }//GEN-LAST:event_txtSaida1FocusGained

    private void txtSaida1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaida1FocusLost
        if (txtSaida1.getText().equals("")) {
            txtSaida1.setText("00:00");
            txtSaida1.setCaretColor(Color.gray);
            txtSaida1.requestFocus();
        }
    }//GEN-LAST:event_txtSaida1FocusLost

    private void txtSaida1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaida1KeyPressed
        if (txtSaida1.getText().equals("00:00")) {
            txtSaida1.setText("");
            txtSaida1.setCaretColor(Color.black);
        }
    }//GEN-LAST:event_txtSaida1KeyPressed

    private void txtEntrada2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEntrada2FocusGained
        if (txtEntrada2.getText().equals("00:00")) {
            txtEntrada2.setCaretColor(Color.black);
            txtEntrada2.setCaretPosition(0);
        }
    }//GEN-LAST:event_txtEntrada2FocusGained

    private void txtEntrada2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEntrada2FocusLost
        if (txtEntrada2.getText().equals("")) {
            txtEntrada2.setText("00:00");
            txtEntrada2.setCaretColor(Color.gray);
            txtEntrada2.requestFocus();
        }
    }//GEN-LAST:event_txtEntrada2FocusLost

    private void txtEntrada2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntrada2KeyPressed
        if (txtEntrada2.getText().equals("00:00")) {
            txtEntrada2.setText("");
            txtEntrada2.setCaretColor(Color.black);
        }
    }//GEN-LAST:event_txtEntrada2KeyPressed

    private void txtSaida2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaida2FocusGained
        if (txtSaida2.getText().equals("00:00")) {
            txtSaida2.setCaretColor(Color.black);
            txtSaida2.setCaretPosition(0);
        }
    }//GEN-LAST:event_txtSaida2FocusGained

    private void txtSaida2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaida2KeyPressed
        if (txtSaida2.getText().equals("00:00")) {
            txtSaida2.setText("");
            txtSaida2.setCaretColor(Color.black);
        }
    }//GEN-LAST:event_txtSaida2KeyPressed

    private void txtSaida2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaida2FocusLost
        if (txtSaida2.getText().equals("")) {
            txtSaida2.setText("00:00");
            txtSaida2.setCaretColor(Color.gray);
            txtSaida2.requestFocus();
        }
    }//GEN-LAST:event_txtSaida2FocusLost

    //Janela de Configuração da Jornada de Trabalho
    private void btn_JornadaTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_JornadaTrabalhoActionPerformed
        digSettings digS = new digSettings(this, true, jorn);
        digS.setVisible(true);

        jornadaTrabalho(jorn.getJornadaSemanal());
    }//GEN-LAST:event_btn_JornadaTrabalhoActionPerformed

    @Override
        public void jornadaTrabalho(int x) {

        if (x == 40) {
            //Setar Jornada de Trabalho
            lbl_JornadaTrabalho.setText("40h Semanais");
        } else {
            //Setar Jornada de Trabalho
            lbl_JornadaTrabalho.setText("44h Semanais");
        }

        lbl_CargaHoraria.setText(jorn.getEntrada1() + " - " + jorn.getSaida1() + " / " + jorn.getEntrada2() + " - " + jorn.getSaida2());
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                
//
//}
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LogUser.class
//.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        
//
//} catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LogUser.class
//.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        
//
//} catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LogUser.class
//.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        
//
//} catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LogUser.class
//.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LogUser().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btn_JornadaTrabalho;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblDataAtual;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblParcial;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalResult;
    private javax.swing.JLabel lbl_CargaHoraria;
    private javax.swing.JLabel lbl_JornadaTrabalho;
    private javax.swing.JPanel pnlDadosFuncionario;
    private javax.swing.JPanel pnlEntradaSaida;
    private javax.swing.JPanel pnlJornada;
    private javax.swing.JPanel pnlRodape;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtEntrada1;
    private javax.swing.JTextField txtEntrada2;
    private javax.swing.JTextField txtSaida1;
    private javax.swing.JTextField txtSaida2;
    // End of variables declaration//GEN-END:variables

}
