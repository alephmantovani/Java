package relogiodeponto.classes;

import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlephMantovani
 */
public class HorasExtras implements Calculos {

    DefaultTableModel tab;
    HorarioDeTrabalho horario;
    MarcacoesFeitas marcacoes;
    Controlador cont;
    ArrayList<LocalTime> listaH, listaM, listaEntrada, listaSaida, lista;
    LocalTime coluna1, coluna2, extra;

//--Construtor
    public HorasExtras(JTable tab) {
        this.tab = (DefaultTableModel) tab.getModel();
        cont = new Controlador();

        //Elementos que irão nas colunas da tabela
        coluna1 = null;
        coluna2 = null;
    }

//--Verifica o Tamanho das Tabelas e o valor da hora de Entrada em relação a hora de Marcação
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
        }
    }

//------------------------------------------------------------------------------TAMANHO DA TABELA HORÁRIO É **MAIOR** QUE A TABELA MARCAÇÃO
    @Override
    public void populaTabelaMenor(DefaultTableModel tabH, DefaultTableModel tabM) {
        listaH = new ArrayList<>();
        listaM = new ArrayList<>();
        listaEntrada = new ArrayList<>();
        listaSaida = new ArrayList<>();
        cont = new Controlador();

        LocalTime horarioEntrada, horarioSaida, marcEntrada, marcSaida;
        boolean flagE = true;
        boolean flagS = true;
        int par;

        horarioEntrada = (LocalTime) tabH.getValueAt(0, 0);
        marcEntrada = (LocalTime) tabM.getValueAt(0, 0);
        marcSaida = (LocalTime) tabM.getValueAt(tabM.getRowCount() - 1, 1);
        horarioSaida = (LocalTime) tabH.getValueAt(tabH.getRowCount() - 1, 1);

        //PERIODO NOTURNO
        if (cont.periodoNoturno(horarioEntrada)) {
            lista = cont.listaHorarios(tabM);

            //Add num Array
            //ENTRADA - Horarios menores que a hora de Entrada
            for (int i = 0; i <= lista.size() - 1; i++) {
                if (lista.get(i).isBefore(horarioEntrada) || (lista.get(i).equals(horarioEntrada))) {
                    if (lista.get(i).isBefore(marcEntrada)) {
                        break;
                    } else {
                        listaEntrada.add(lista.get(i));
                    }
                }
            }

            lista = cont.listaHorariosOrdenados(tabH, tabM);

            //SAIDA - Horários maiores que a hora de Saida
            for (int j = 0; j < lista.size() - 1; j++) {
                if (lista.get(j).isAfter(horarioSaida) || lista.get(j).equals(horarioSaida)) {
                    listaSaida.add(lista.get(j));
                    if (lista.get(j).equals(marcSaida)) {
                        break;
                    }
                }
            }

        } else {
            lista = cont.listaTodosHorarios(tabH, tabM);
            //Add num Array
            for (int i = 0; i <= lista.size() - 1; i++) {
                //ENTRADA - Horarios menores que a hora de Entrada
                if (lista.get(i).isBefore(horarioEntrada) || (lista.get(i).equals(horarioEntrada))) {
                    listaEntrada.add(lista.get(i));
                }
                //SAIDA - Horários maiores que a hora de Saida
                if (lista.get(i).isAfter(horarioSaida) || (lista.get(i).equals(horarioSaida))) {
                    listaSaida.add(lista.get(i));
                }
            }
        }

        //EXTRAS ENTRADA
        for (int i = 0; i <= listaEntrada.size() - 1; i++) {
            if (flagE == false) {
                extra = cont.subtracaoEntreHorarios(listaEntrada.get(i - 1), listaEntrada.get(i));
                tab.addRow(new Object[]{listaEntrada.get(i - 1), listaEntrada.get(i), extra});
                if (listaEntrada.get(i).equals(horarioEntrada)) {
                    break;
                }
            }
            flagE = !flagE;
        }

        //EXTRAS DENTRO DA CARGA HORARIA
        for (int i = 0; i < tabM.getRowCount() - 1; i++) {
            if (cont.retornaSaida(0, tabH).isBefore(cont.retornaSaida(i, tabM))) {
                if (i <= tabH.getRowCount() - 1) {
                    if (cont.retornaEntrada(i, tabH).isBefore(cont.retornaSaida(i, tabM))) {
                        coluna1 = cont.retornaSaida(0, tabH);
                        coluna2 = cont.retornaEntrada(i, tabH);
                    } else {
                        if (!cont.retornaEntrada(i, tabH).equals(cont.retornaSaida(i, tabM))) {
                            coluna1 = cont.retornaSaida(0, tabH);
                            coluna2 = cont.retornaSaida(i, tabM);
                        }
                    }

                    //Adiciona na Tabela
                    if (coluna1 != null || coluna2 != null) {
                        extra = cont.subtracaoEntreHorarios(coluna1, coluna2);
                        tab.addRow(new Object[]{coluna1, coluna2, extra});
                    }
                }
            }
        }

        par = listaSaida.size() % 2;
        if (par > 0) {
            listaSaida.remove(0);
        }

        //EXTRAS SAIDA
        for (int i = 0; i <= listaSaida.size() - 1; i++) {
            if (flagS == false) {
                extra = cont.subtracaoEntreHorarios(listaSaida.get(i - 1), listaSaida.get(i));
                tab.addRow(new Object[]{listaSaida.get(i - 1), listaSaida.get(i), extra});
            }
            flagS = !flagS;
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
                    if ((cont.retornaSaida(j, tabH).equals(cont.retornaSaida(k, tabM))) && (cont.retornaEntrada(j + 1, tabH).equals(cont.retornaEntrada(k + 1, tabM)))) {
                        tab.removeRow(k);
                    }
                }
            }
        }
    }

//------------------------------------------------------------------------------TAMANHO DA TABELA HORÁRIO É **MAIOR** QUE A TABELA MARCAÇÃO
    @Override
    public void populaTabelaMaior(DefaultTableModel tabH, DefaultTableModel tabM) {
        listaH = new ArrayList<>();
        listaM = new ArrayList<>();
        LocalTime prov, prov2, horarioEntradaH, horarioEntradaM, ultimoHorarioSaidaH, ultimoHorarioSaidaM;
        cont = new Controlador();
        boolean flag = true;
        int i = 0, tamTabH, tamTabM;
        int difCargaHoraria = 0;

        //Primeiro Horário de Entrada da Tabela Horário
        horarioEntradaH = (LocalTime) tabH.getValueAt(0, 0);
        horarioEntradaM = (LocalTime) tabM.getValueAt(0, 0);

        //Pega o ultimo Horario da tabela Horarios
        ultimoHorarioSaidaH = (LocalTime) tabH.getValueAt(tabH.getRowCount() - 1, 1);
        ultimoHorarioSaidaM = (LocalTime) tabM.getValueAt(tabM.getRowCount() - 1, 1);

        //Tamanho das Tabelas
        tamTabH = tabH.getRowCount() - 1;
        tamTabM = tabM.getRowCount() - 1;

        //Provisória que pega o primeiro horario de Saida da tabela Horarios
        prov = cont.retornaSaida(0, tabH);

        //Periodo Noturno
        if (cont.periodoNoturno(horarioEntradaH)) {
            prov = (LocalTime) tabH.getValueAt(0, 1);

            //ENTRADA
            if (cont.retornaEntrada(0, tabM).isBefore(horarioEntradaH)) {
                difCargaHoraria = cont.retornaEntrada(0, tabM).getHour() - cont.cargaHorariaDiaria(5);
                if (difCargaHoraria > 0) {
                    extra = cont.subtracaoEntreHorarios(cont.retornaEntrada(0, tabM), horarioEntradaH);
                    tab.addRow(new Object[]{cont.retornaEntrada(0, tabM), horarioEntradaH, extra});
                }
            }

            //Horarios de Extra dentro da carga horaria
            while (i <= tabH.getRowCount() - 1) {

                if (prov.isBefore(cont.retornaEntrada(0, tabM))) {
                    if (prov.isBefore((LocalTime) tabH.getValueAt(i, 0))) {
                        if (flag == false) {
                            difCargaHoraria = cont.retornaEntrada(0, tabM).getHour() - cont.cargaHorariaDiaria(5);
                            if (difCargaHoraria > 0) {
                                extra = cont.subtracaoEntreHorarios(prov, (LocalTime) tabH.getValueAt(i, 0));
                                tab.addRow(new Object[]{prov, (LocalTime) tabH.getValueAt(i, 0), extra});
                            }
                        }
                    }
                    prov = (LocalTime) tabH.getValueAt(i, 1);
                    flag = !flag;
                    i++;
                } else {
                    prov = (LocalTime) tabH.getValueAt(i, 1);
                    i++;
                }
            }

            //SAIDA
            if (cont.retornaSaida(0, tabM).isAfter(ultimoHorarioSaidaH)) {
                extra = cont.subtracaoEntreHorarios(ultimoHorarioSaidaH, cont.retornaSaida(0, tabM));
                tab.addRow(new Object[]{ultimoHorarioSaidaH, cont.retornaSaida(0, tabM), extra});
            }

        } else {

            //Se o horario de Marcação não entrar dentro da carga Horaria **ENTRADA**
            if (ultimoHorarioSaidaM.isBefore(horarioEntradaH) || ultimoHorarioSaidaM.equals(horarioEntradaH)) {
                for (int j = 0; j < tabH.getRowCount() - 1; j++) {
                    extra = cont.subtracaoEntreHorarios(cont.retornaEntrada(j, tabM), cont.retornaSaida(j, tabM));
                    tab.addRow(new Object[]{cont.retornaEntrada(j, tabM), cont.retornaSaida(j, tabM), extra});
                }
                //Se o horario de Marcação não entrar dentro da carga Horaria **SAIDA**
            } else if (ultimoHorarioSaidaH.isBefore(horarioEntradaM) || ultimoHorarioSaidaH.equals(horarioEntradaM)) {
                for (int j = 0; j < tabH.getRowCount() - 1; j++) {
                    extra = cont.subtracaoEntreHorarios(cont.retornaEntrada(j, tabM), cont.retornaSaida(j, tabM));
                    tab.addRow(new Object[]{cont.retornaEntrada(j, tabM), cont.retornaSaida(j, tabM), extra});
                }
            } else {
                //ENTRADA
                if (cont.retornaEntrada(0, tabH).isAfter(cont.retornaEntrada(0, tabM))) {
                    extra = cont.subtracaoEntreHorarios(cont.retornaEntrada(0, tabM), cont.retornaEntrada(0, tabH));
                    tab.addRow(new Object[]{cont.retornaEntrada(0, tabM), cont.retornaEntrada(0, tabH), extra});
                }

                //HORARIOS EXTRAS ENTRE A ENTRADA E A SAIDA
                while (i <= tamTabH) {
                    if (prov != ultimoHorarioSaidaH && ultimoHorarioSaidaM.isAfter(cont.retornaSaida(0, tabH))) {
                        if (i > 0) {
                            if (ultimoHorarioSaidaM.isAfter(cont.retornaEntrada(i, tabH)) && ultimoHorarioSaidaM.isBefore(ultimoHorarioSaidaH)) {
                                prov2 = ultimoHorarioSaidaM;
                            } else {
                                if (cont.retornaEntrada(i, tabH).isAfter(horarioEntradaH)) {
                                    prov2 = cont.retornaEntrada(i, tabH);
                                } else {
                                    prov2 = cont.retornaSaida(tabH.getRowCount() - 1, tabM);
                                }
                            }
                        } else {
                            prov2 = cont.retornaEntrada(i, tabH);
                        }

                        if (prov.isBefore(prov2)) {
                            if (prov2.isBefore(cont.retornaEntrada(i, tabH))) {
                                extra = cont.subtracaoEntreHorarios(prov, prov2);
                                tab.addRow(new Object[]{prov, prov2, extra});
                            } else {
                                extra = cont.subtracaoEntreHorarios(prov, cont.retornaEntrada(i, tabH));
                                tab.addRow(new Object[]{prov, cont.retornaEntrada(i, tabH), extra});
                            }
                        }
                        prov = cont.retornaSaida(i, tabH);
                        i++;
                    } else {
                        break;
                    }
                }

                //SAIDA
                if (cont.retornaSaida(tamTabH, tabH).isBefore(cont.retornaSaida(tamTabM, tabM))) {
                    extra = cont.subtracaoEntreHorarios(cont.retornaSaida(tamTabH, tabH), cont.retornaSaida(tamTabM, tabM));
                    tab.addRow(new Object[]{cont.retornaSaida(tamTabH, tabH), cont.retornaSaida(tamTabM, tabM), extra});
                }
            }
        }
    }

//------------------------------------------------------------------------------TAMANHO DAS TABELAS IGUAIS
    @Override
    public void populaTabelaIgual(DefaultTableModel tabH, DefaultTableModel tabM) {
        listaH = new ArrayList<>();
        listaM = new ArrayList<>();
        LocalTime horarioEntrada, horarioSaida;
        int i = 0;
        int difCargaHoraria;
        cont = new Controlador();

        horarioEntrada = (LocalTime) tabH.getValueAt(0, 0);
        horarioSaida = (LocalTime) tabH.getValueAt(tabH.getRowCount() - 1, 1);

        //ENTRADA
        if (cont.periodoNoturno(horarioEntrada)) {
            if (cont.retornaEntrada(i, tabM).isBefore(horarioEntrada)) {
                if (cont.retornaSaida(i, tabM).isBefore(horarioEntrada)) {
                    difCargaHoraria = cont.retornaSaida(i, tabM).getHour() - cont.cargaHorariaDiaria(5);
                    if (difCargaHoraria >= 0) {
                        coluna1 = cont.retornaEntrada(i, tabM);
                        coluna2 = cont.retornaSaida(i, tabM);
                    } else {
                        coluna1 = cont.retornaEntrada(i, tabM);
                        coluna2 = cont.retornaEntrada(i, tabH);
                    }
                }
            }

            //Adiciona na Tabela
            if (coluna1 != null || coluna2 != null) {
                extra = cont.subtracaoEntreHorarios(coluna1, coluna2);
                tab.addRow(new Object[]{coluna1, coluna2, extra});
                coluna1 = null;
                coluna2 = null;
            }
        } else {
            while (i <= tabM.getRowCount() - 1) {
                if (cont.retornaEntrada(i, tabM).isBefore(horarioEntrada)) {
                    if (cont.retornaSaida(i, tabM).isBefore(horarioEntrada)) {
                        coluna1 = cont.retornaEntrada(i, tabM);
                        coluna2 = cont.retornaSaida(i, tabM);
                    } else {
                        coluna1 = cont.retornaEntrada(i, tabM);
                        coluna2 = horarioEntrada;
                    }
                }
                //Adiciona na Tabela
                if (coluna1 != null || coluna2 != null) {
                    extra = cont.subtracaoEntreHorarios(coluna1, coluna2);
                    tab.addRow(new Object[]{coluna1, coluna2, extra});
                    coluna1 = null;
                    coluna2 = null;
                }
                i++;
            }
        }

        //Extras dentro da Jornada de Trabalho
        for (int j = 0; j <= tabH.getRowCount() - 1; j++) {
            if (cont.retornaEntrada(j, tabM).isBefore(cont.retornaEntrada(j, tabH))) {
                if (tabH.getRowCount() > 1) {
                    if (cont.retornaEntrada(j, tabH).isBefore(cont.retornaSaida(j, tabM))) {
                        if (!cont.retornaEntrada(j, tabH).equals(horarioEntrada)) {
                            coluna1 = cont.retornaEntrada(j, tabM);
                            coluna2 = cont.retornaEntrada(j, tabH);
                        }
                    } else {
                        if (j > 0) {
                            if (cont.retornaSaida(j - 1, tabH).isAfter(cont.retornaEntrada(j, tabM))) {
                                coluna1 = cont.retornaSaida(j - 1, tabH);
                                coluna2 = cont.retornaSaida(j, tabM);
                            } else {
                                coluna1 = cont.retornaEntrada(j, tabM);
                                coluna2 = cont.retornaSaida(j, tabM);
                            }
                        }
                    }
                }
            } else 
                //if (j > 0) { 09-13 -- 15-19
                if (cont.retornaSaida(j, tabH).isBefore(cont.retornaSaida(j, tabM))) {
                    if (j < tabH.getRowCount() - 1) {
                        coluna1 = cont.retornaSaida(j, tabH);
                        coluna2 = cont.retornaSaida(j, tabM);
                    }
//                }
            }

            //Adiciona na Tabela
            if (coluna1 != null || coluna2 != null) {
                extra = cont.subtracaoEntreHorarios(coluna1, coluna2);
                tab.addRow(new Object[]{coluna1, coluna2, extra});
                coluna1 = null;
                coluna2 = null;
            }
        }

        //Reiniciei o contador
        i = 0;

        //Dentro da Jornada de Trabalho
        while (i < tabH.getRowCount() - 1) {
            if (cont.retornaSaida(i, tabH).isBefore(cont.retornaSaida(i, tabM))) {
                
                difCargaHoraria = cont.cargaHorariaDiaria(5) + cont.retornaEntrada(i, tabM).getHour();
                
                if (difCargaHoraria < horarioEntrada.getHour()) {
                    if (cont.retornaEntrada(i, tabM).isBefore(horarioSaida)) {
                        coluna1 = cont.retornaSaida(i, tabH);
                        coluna2 = cont.retornaSaida(i, tabM);
                    } else {
                        if (cont.periodoNoturno(horarioEntrada)) {
                            if (cont.retornaSaida(tabH.getRowCount() - 1, tabH).isBefore(cont.retornaEntrada(tabM.getRowCount() - 1, tabM))) {
                                coluna1 = cont.retornaEntrada(i, tabM);
                                coluna2 = cont.retornaSaida(i, tabM);
                            } else if (cont.retornaSaida(i, tabM).isAfter(cont.retornaSaida(i, tabH))) {
                                coluna1 = cont.retornaSaida(i, tabH);
                                coluna2 = cont.retornaSaida(i, tabM);
                            }
                        } else {
                            coluna1 = cont.retornaEntrada(i, tabM);
                            coluna2 = cont.retornaSaida(i, tabM);
                        }
                    }
                }
            }

            //Adiciona na Tabela
            if (coluna1 != null || coluna2 != null) {
                extra = cont.subtracaoEntreHorarios(coluna1, coluna2);
                tab.addRow(new Object[]{coluna1, coluna2, extra});
                coluna1 = null;
                coluna2 = null;
            }
            i++;
        }

        //SAIDA
        if (tabH.getRowCount() > 1) {
            if (cont.retornaSaida(tabH.getRowCount() - 1, tabH).isBefore(cont.retornaSaida(tabM.getRowCount() - 1, tabM))) {
                if (cont.retornaSaida(tabH.getRowCount() - 1, tabH).isBefore(cont.retornaEntrada(tabM.getRowCount() - 1, tabM))) {
                    coluna1 = cont.retornaEntrada(tabM.getRowCount() - 1, tabM);
                    coluna2 = cont.retornaSaida(tabM.getRowCount() - 1, tabM);
                } else {
                    coluna1 = cont.retornaSaida(tabH.getRowCount() - 1, tabH);
                    coluna2 = cont.retornaSaida(tabM.getRowCount() - 1, tabM);
                }
            }
        } else {
            difCargaHoraria = cont.cargaHorariaDiaria(5) + cont.retornaEntrada(tabM.getRowCount() - 1, tabM).getHour();
            if (difCargaHoraria >= horarioEntrada.getHour() && cont.periodoNoturno(horarioEntrada)) {
                if(cont.retornaSaida(0, tabH).isBefore(cont.retornaSaida(0, tabM))){
                    coluna1 = cont.retornaSaida(tabH.getRowCount() - 1, tabH);
                    coluna2 = cont.retornaSaida(tabM.getRowCount() - 1, tabM);
                }
            }else if (cont.retornaSaida(0, tabM).isAfter(cont.retornaSaida(0, tabH))){
                if(cont.retornaSaida(0, tabH).isBefore(cont.retornaSaida(0, tabM))){
                    coluna1 = cont.retornaSaida(0, tabH);
                    coluna2 = cont.retornaSaida(0, tabM);
                }
            }
        }

        //Adicona na tabela
        if (coluna1 != null || coluna2 != null) {
            extra = cont.subtracaoEntreHorarios(coluna1, coluna2);
            tab.addRow(new Object[]{coluna1, coluna2, extra});
        }
    }
}
