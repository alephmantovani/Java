package relogiodeponto.classes;

import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlephMantovani
 */
public class Atrasos implements Calculos {

    DefaultTableModel tab;
    HorarioDeTrabalho horario;
    MarcacoesFeitas marcacoes;
    Controlador cont;
    ArrayList<LocalTime> listaH, listaM, listaEntrada, listaSaida, lista;
    LocalTime coluna1, coluna2, atraso, horarioEntrada, horarioSaida;

//--Construtor
    public Atrasos(JTable tab) {
        this.tab = (DefaultTableModel) tab.getModel();
        cont = new Controlador();

        //Elementos que irão nas colunas da tabela
        coluna1 = null;
        coluna2 = null;
    }

    @Override
    public void tamTabelas(DefaultTableModel tabH, DefaultTableModel tabM) {
        String tam = cont.verificaTamanhoTabela(tabH, tabM);
        tab.setRowCount(0);

        switch (tam) {
            case "menor":
                populaTabelaMenor(tabH, tabM);
                break;
            case "maior":
                populaTabelaMaior(tabH, tabM);
                break;
            case "igual":
                populaTabelaIgual(tabH, tabM);
                break;
            default:
                break;
        }
    }

    @Override
//------------------------------------------------------------------------------TAMANHO DA TABELA HORÁRIO É **MENOR** QUE A TABELA MARCAÇÃO
    public void populaTabelaMenor(DefaultTableModel tabH, DefaultTableModel tabM) {

        LocalTime provisoria;
        int x = 0, i = 0;
        int difCargaHoraria = 0;
        cont = new Controlador();

        horarioEntrada = (LocalTime) tabH.getValueAt(0, 0);
        horarioSaida = (LocalTime) tabH.getValueAt(tabH.getRowCount() - 1, 1);
        provisoria = (LocalTime) tabM.getValueAt(0, 1);
        
        while (x <= tabM.getRowCount() - 1) {
            //ENTRADA
            if (cont.retornaEntrada(0, tabH).isBefore(cont.retornaEntrada(x, tabM))) {
                if (cont.retornaEntrada(x, tabM).isBefore(horarioSaida)) {
                    atraso = cont.subtracaoEntreHorarios(cont.retornaEntrada(0, tabH), cont.retornaEntrada(x, tabM));
                    tab.addRow(new Object[]{cont.retornaEntrada(0, tabH), cont.retornaEntrada(x, tabM), atraso});
                    break;
                }
            }
            if (cont.retornaEntrada(0, tabH).equals(cont.retornaEntrada(x, tabM))) {
                break;
            }
            x++;
        }

        //PERIODO NOTURNO
        if (cont.periodoNoturno(horarioEntrada)) {
            //Horarios de Atraso dentro da carga horaria
            while (i <= tabM.getRowCount() - 1) {
                if (provisoria.isBefore(cont.retornaEntrada(i, tabM))) {
                    difCargaHoraria = cont.retornaEntrada(i, tabM).getHour() - cont.cargaHorariaDiaria(5);
                    if (difCargaHoraria >= 0) {
                        if (cont.retornaEntrada(i, tabM).isBefore(cont.retornaEntrada(i, tabH))) {
                            coluna1 = provisoria;
                            coluna2 = cont.retornaEntrada(i, tabM);
                        }
                    } else if (i > 0){
                        if(provisoria.isAfter(cont.retornaSaida(i-1, tabH))){
                            coluna1 = cont.retornaEntrada(i-1, tabH);
                            coluna2 = provisoria;
                        }else if (!cont.retornaSaida(i-1, tabH).equals(cont.retornaSaida(i-1, tabM))){
                            if(cont.retornaSaida(i-1, tabM).isBefore(cont.retornaEntrada(i, tabM))){
                                coluna1 = provisoria;
                                coluna2 = cont.retornaEntrada(i, tabM);
                            }
                        }
                    }else{
                        coluna1 = provisoria;
                        coluna2 = cont.retornaEntrada(i, tabM);
                    }
                        
                    
                } else if (i > 0) {
                    difCargaHoraria = cont.retornaEntrada(i, tabM).getHour() - cont.cargaHorariaDiaria(5);
                    if (difCargaHoraria < 0) {
                        if (horarioEntrada.equals(cont.retornaSaida(0, tabM))) {
                            coluna1 = cont.retornaSaida(0, tabM);
                            coluna2 = cont.retornaEntrada(i, tabM);
                        }
                    }
                }
                
                //Adiciona na Tabela
                if (coluna1 != null || coluna2 != null) {
                    atraso = cont.subtracaoEntreHorarios(coluna1, coluna2);
                    tab.addRow(new Object[]{coluna1, coluna2, atraso});
                    coluna1 = null;
                    coluna2 = null;
                }

                provisoria = cont.retornaSaida(i, tabM);
                i++;
            }
        } else {
            //Horarios de Atraso dentro da carga horaria
            while (i <= tabM.getRowCount() - 1) {
                if (provisoria.isBefore(cont.retornaEntrada(i, tabM))) {
                    if (!provisoria.isBefore(horarioEntrada)) {
                        if (provisoria.isAfter(horarioSaida)) {
                            break;
                        } else {
                            atraso = cont.subtracaoEntreHorarios(provisoria, (LocalTime) tabM.getValueAt(i, 0));
                            tab.addRow(new Object[]{provisoria, (LocalTime) tabM.getValueAt(i, 0), atraso});
                        }
                    }
                }
                provisoria = (LocalTime) tabM.getValueAt(i, 1);
                i++;
            }

            //Remove os horarios que não sao atrasos
            for (int j = 0; j < tabH.getRowCount() - 1; j++) {
                for (int k = 0; k < tab.getRowCount() - 1; k++) {
                    //Entrada
                    if ((cont.retornaEntrada(j, tabH).equals(tab.getValueAt(k, 0)) && (cont.retornaSaida(j, tabH).equals(tab.getValueAt(k, 1))))) {
                        tab.removeRow(k);
                    }
                    //Almoço
                    if (j < tabH.getRowCount() - 1) {
                        if ((cont.retornaSaida(j, tabH).equals(tab.getValueAt(k, 0))) && (cont.retornaEntrada(j + 1, tabH).equals(tab.getValueAt(k, 1)))) {
                            tab.removeRow(k);
                        }
                    }
                    //Saida
                    if (cont.retornaSaida(tabH.getRowCount() - 1, tabH).equals(tab.getValueAt(k, 0))) {
                        tab.removeRow(k);
                    }
                }
            }

            //SAIDA
            if (cont.retornaSaida(tabH.getRowCount() - 1, tabH).isAfter(cont.retornaSaida(tabM.getRowCount() - 1, tabM))) {
                atraso = cont.subtracaoEntreHorarios(cont.retornaSaida(tabM.getRowCount() - 1, tabM), cont.retornaSaida(tabH.getRowCount() - 1, tabH));
                tab.addRow(new Object[]{cont.retornaSaida(tabM.getRowCount() - 1, tabM), cont.retornaSaida(tabH.getRowCount() - 1, tabH), atraso});
            }
        }
    }

    @Override
//------------------------------------------------------------------------------TAMANHO DA TABELA HORÁRIO É **MAIOR** QUE A TABELA MARCAÇÃO
    public void populaTabelaMaior(DefaultTableModel tabH, DefaultTableModel tabM) {

        listaH = new ArrayList<>();
        listaM = new ArrayList<>();
        LocalTime provisoria;
        cont = new Controlador();
        int x = 0;

        horarioEntrada = (LocalTime) tabH.getValueAt(0, 0);
        horarioSaida = (LocalTime) tabH.getValueAt(tabH.getRowCount() - 1, 1);
        provisoria = cont.retornaSaida(0, tabM);

        //ENTRADA
        if (cont.retornaEntrada(0, tabH).isBefore(cont.retornaEntrada(0, tabM))) {
            coluna1 = cont.retornaEntrada(0, tabH);
            coluna2 = cont.retornaEntrada(0, tabM);
        } else if (cont.periodoNoturno(horarioEntrada)) {
            if (cont.retornaEntrada(0, tabM).isBefore(cont.retornaSaida(0, tabH))) {
                coluna1 = horarioEntrada;
                coluna2 = cont.retornaEntrada(0, tabM);
            } else {
                if (!cont.retornaEntrada(0, tabM).equals(horarioEntrada)) {
                    coluna1 = horarioEntrada;
                    coluna2 = cont.retornaSaida(0, tabH);
                }
            }
        }
        //Adiciona na Tabela
        if (coluna1 != null || coluna2 != null) {
            atraso = cont.subtracaoEntreHorarios(coluna1, coluna2);
            tab.addRow(new Object[]{coluna1, coluna2, atraso});
            coluna1 = null;
            coluna2 = null;
        }

        //Horarios de Atraso dentro da carga horaria
        while (x <= tabM.getRowCount() - 1) {
            if (provisoria.isBefore((LocalTime) tabM.getValueAt(x, 0))) {
                if (!provisoria.isBefore(horarioEntrada)) {
                    if (provisoria.isAfter(horarioSaida)) {
                        break;
                    } else {
                        atraso = cont.subtracaoEntreHorarios(provisoria, (LocalTime) tabM.getValueAt(x, 0));
                        tab.addRow(new Object[]{provisoria, (LocalTime) tabM.getValueAt(x, 0), atraso});
                    }
                }
            }
            provisoria = (LocalTime) tabM.getValueAt(x, 1);
            x++;
        }

        //SAIDA
        for (int i = 0; i <= tabH.getRowCount() - 1; i++) {
            if (cont.retornaSaida((tabM.getRowCount() - 1), tabM).isBefore(cont.retornaEntrada(i, tabH))) {
                if (!cont.periodoNoturno(horarioEntrada)) {
                    coluna1 = cont.retornaEntrada(i, tabH);
                    coluna2 = cont.retornaSaida(i, tabH);
                }
            } else if (cont.retornaSaida(tabM.getRowCount() - 1, tabM).isBefore(cont.retornaSaida(i, tabH))) {
                coluna1 = cont.retornaSaida(tabM.getRowCount() - 1, tabM);
                coluna2 = cont.retornaSaida(i, tabH);
            }

            //Adiciona na Tabela
            if (coluna1 != null || coluna2 != null) {
                atraso = cont.subtracaoEntreHorarios(coluna1, coluna2);
                tab.addRow(new Object[]{coluna1, coluna2, atraso});
                coluna1 = null;
                coluna2 = null;
            }
        }
    }

    @Override
//------------------------------------------------------------------------------TAMANHO DAS TABELAS IGUAIS
    public void populaTabelaIgual(DefaultTableModel tabH, DefaultTableModel tabM) {

        int difCargaHoraria = 0;
        cont = new Controlador();

        horarioEntrada = (LocalTime) tabH.getValueAt(0, 0);

        difCargaHoraria = cont.retornaEntrada(0, tabM).getHour() - cont.cargaHorariaDiaria(5);

        //ENTRADA
        if (cont.retornaEntrada(0, tabM).isAfter(cont.retornaEntrada(0, tabH))) {
            if (difCargaHoraria >= 0) {
                coluna1 = cont.retornaEntrada(0, tabH);
                coluna2 = cont.retornaEntrada(0, tabM);
            } else {
                coluna1 = cont.retornaEntrada(0, tabH);
                coluna2 = cont.retornaSaida(0, tabH);
            }
        }

        //Adiciona na Tabela
        if (coluna1 != null || coluna2 != null) {
            atraso = cont.subtracaoEntreHorarios(coluna1, coluna2);
            tab.addRow(new Object[]{coluna1, coluna2, atraso});
            coluna1 = null;
            coluna2 = null;
        }

        //Horários dentro da Jornada de Trabalho
        for (int x = 0; x <= tabM.getRowCount() - 1; x++) {
            difCargaHoraria = cont.retornaSaida(x, tabM).getHour() - cont.cargaHorariaDiaria(5);

            //PERIODO NOTURNO
            if (cont.periodoNoturno(horarioEntrada)) {
                if (tabM.getRowCount() > 1) {
                    if ((difCargaHoraria < 0) && cont.retornaSaida(x, tabM).isBefore(horarioEntrada)) {
                        if (cont.retornaSaida(x, tabM).isBefore(cont.retornaSaida(x, tabH))) {
                            if (!cont.retornaSaida(x, tabM).equals(cont.retornaEntrada(x, tabH))) {
                                coluna1 = cont.retornaSaida(x, tabM);
                                coluna2 = cont.retornaSaida(x, tabH);
                            }
                        } else if (cont.retornaEntrada(x, tabM).isAfter(cont.retornaEntrada(x, tabH))) {
                            if (x > 0) {
                                if (cont.retornaEntrada(x, tabH).isBefore(cont.retornaSaida(x - 1, tabM))) {
                                    coluna1 = cont.retornaSaida(x - 1, tabM);
                                    coluna2 = cont.retornaEntrada(x, tabM);
                                } else {
                                    coluna1 = cont.retornaEntrada(x, tabH);
                                    coluna2 = cont.retornaEntrada(x, tabM);
                                }
                            }
                        }
                    } else if (cont.retornaSaida(x, tabM).isAfter(cont.retornaSaida(x, tabH))) {
                        if (cont.retornaEntrada(x, tabM).isAfter(cont.retornaSaida(x, tabH))) {
                            coluna1 = cont.retornaEntrada(x, tabH);
                            coluna2 = cont.retornaSaida(x, tabH);
                        }
                    }
                } else {
                    if (cont.retornaSaida(x, tabM).isBefore(cont.retornaSaida(x, tabH))) {
                        coluna1 = cont.retornaSaida(x, tabM);
                        coluna2 = cont.retornaSaida(x, tabH);
                    }
                }

                //PERIODO DIURNO
            } else {

                //ENTRADAS
                if (x > 0) {
                    if (cont.retornaEntrada(x, tabM).isAfter(cont.retornaEntrada(x, tabH))) {
                        if (difCargaHoraria >= 0) {
                            coluna1 = cont.retornaEntrada(x, tabH);
                            coluna2 = cont.retornaEntrada(x, tabM);
                        } else {
                            coluna1 = cont.retornaEntrada(x, tabH);
                            coluna2 = cont.retornaSaida(x, tabH);
                        }
                    }
                }

                //SAIDAS
                if (cont.retornaSaida(x, tabM).isBefore(cont.retornaSaida(x, tabH))) {
                    if (x < tabM.getRowCount() - 1) {
                        if (cont.retornaSaida(x, tabH).isAfter(cont.retornaEntrada(x + 1, tabM))) {
                            coluna1 = cont.retornaSaida(x, tabM);
                            coluna2 = cont.retornaEntrada(x + 1, tabM);
                        } else {
                            if (cont.retornaEntrada(x, tabH).isAfter(cont.retornaSaida(x, tabM))) {
                                coluna1 = cont.retornaEntrada(x, tabH);
                                coluna2 = cont.retornaSaida(x, tabH);
                            } else {
                                coluna1 = cont.retornaSaida(x, tabM);
                                coluna2 = cont.retornaSaida(x, tabH);
                            }
                        }
                    } else {
                        if (cont.retornaEntrada(x, tabH).isAfter(cont.retornaSaida(x, tabM))) {
                            coluna1 = cont.retornaEntrada(x, tabH);
                            coluna2 = cont.retornaSaida(x, tabH);
                        } else {
                            coluna1 = cont.retornaSaida(x, tabM);
                            coluna2 = cont.retornaSaida(x, tabH);
                        }
                    }
                } else if (x > 0) {
                    if (cont.retornaSaida(x, tabM).isAfter(cont.retornaSaida(x, tabH))) {
                        if (cont.retornaEntrada(x, tabM).isAfter(cont.retornaSaida(x, tabH))) {
                            coluna1 = cont.retornaEntrada(x, tabH);
                            coluna2 = cont.retornaSaida(x, tabH);
                        } else {
                            coluna1 = cont.retornaEntrada(x, tabH);
                            coluna2 = cont.retornaEntrada(x, tabM);
                        }
                    }
                }
            }

            //Adiciona na Tabela
            if (coluna1 != null || coluna2 != null) {
                atraso = cont.subtracaoEntreHorarios(coluna1, coluna2);
                tab.addRow(new Object[]{coluna1, coluna2, atraso});
                coluna1 = null;
                coluna2 = null;
            }
        }
    }
}
