package relogiodeponto.classes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlephMantovani
 */
public class Controlador {

    HorarioDeTrabalho horario = new HorarioDeTrabalho();
    MarcacoesFeitas marcacao = new MarcacoesFeitas();
    ArrayList<LocalTime> lista;
    DefaultTableModel tab;

    //Variável do Periodo Noturno
    public static int pn;
    //Variável da Jornada de Trabalho
    public static int jornada;

//--Construtor
    public void Controlador() {
        
    }


//--Retorna as Entradas de Horarios e Marcacaoes
    public LocalTime retornaEntrada(int lin, DefaultTableModel tab) {
        return (LocalTime) tab.getValueAt(lin, 0);
    }

//--Retorna as Entradas de Horarios e Marcacaoes
    public LocalTime retornaSaida(int lin, DefaultTableModel tab) {
        return (LocalTime) tab.getValueAt(lin, 1);
    }

//--Verifica se a lista de Horarios é Maior, Igual ou Menor que a lista de Harcações
    public String verificaTamanhoTabela(DefaultTableModel horario, DefaultTableModel marcacao) {

        if (horario.getRowCount() > marcacao.getRowCount()) {
            return "maior";
        } else if (horario.getRowCount() == marcacao.getRowCount()) {
            return "igual";
        } else if (horario.getRowCount() < marcacao.getRowCount()) {
            return "menor";
        }
        return "";
    }


//--Alimenta uma Lista com todos os dados das duas tabelas e ordena do menor pro maior
    public ArrayList listaHorariosOrdenados(DefaultTableModel tabH, DefaultTableModel tabM) {

        lista = new ArrayList<>();

        for (int i = 0; i < tabH.getRowCount(); i++) {
            lista.add((LocalTime) tabH.getValueAt(i, 0));
            lista.add((LocalTime) tabH.getValueAt(i, 1));
        }

        //Não ADD na LISTA valor repetido
        for (int i = 0; i < tabM.getRowCount(); i++) {
            //VALOR DE ENTRADA
            if (!lista.contains((LocalTime) tabM.getValueAt(i, 0))) {
                lista.add((LocalTime) tabM.getValueAt(i, 0));
            }

            //VALOR DE SAIDA
            if (!lista.contains((LocalTime) tabM.getValueAt(i, 1))) {
                lista.add((LocalTime) tabM.getValueAt(i, 1));
            }
        }

        //Ordena em Ordem Crescente
        Collections.sort(lista);
        return lista;
    }
    
//---Retorna uma lista de todos os Horarios e Marcações ordenados
    public ArrayList listaTodosHorarios(DefaultTableModel tabH, DefaultTableModel tabM) {

        lista = new ArrayList<>();
        
        int tabHor = tabH.getRowCount();
        int tabMar = tabM.getRowCount();
        
        if(tabHor >= tabMar){
            tab = tabH; 
        }else{
            tab = tabM;
        }

        for (int i = 0; i <= tab.getRowCount()-1; i++) {
            if(i <= tabH.getRowCount()-1){
                if(!lista.contains((LocalTime) tabH.getValueAt(i, 0))){
                    lista.add((LocalTime) tabH.getValueAt(i, 0));
                }
                if(!lista.contains((LocalTime) tabH.getValueAt(i, 1))){
                    lista.add((LocalTime) tabH.getValueAt(i, 1));
                }
            }
            
            if(i <= tabM.getRowCount()-1){
                if(!lista.contains((LocalTime) tabM.getValueAt(i, 0))){
                    lista.add((LocalTime) tabM.getValueAt(i, 0));
                }
                if(!lista.contains((LocalTime) tabM.getValueAt(i, 1))){
                    lista.add((LocalTime) tabM.getValueAt(i, 1));
                }
            }
        }
        
        Collections.sort(lista);
        
        return lista;
    }
    
//---Retorna uma lista de todos os Horarios e Marcações não ordenados
    public ArrayList listaTodosHorariosNaoOrdenados(DefaultTableModel tabH, DefaultTableModel tabM) {

        lista = new ArrayList<>();

        for (int i = 0; i < tabH.getRowCount(); i++) {
            lista.add((LocalTime) tabH.getValueAt(i, 0));
            lista.add((LocalTime) tabM.getValueAt(i, 0));
            lista.add((LocalTime) tabH.getValueAt(i, 1));
            lista.add((LocalTime) tabM.getValueAt(i, 1));
        }
        
        return lista;
    }
    
//---Retorna a lista de HORARIOS num Array
    public ArrayList listaHorarios(DefaultTableModel tabH) {
        lista = new ArrayList<>();
        
        for (int i = 0; i < tabH.getRowCount(); i++) {
            lista.add((LocalTime)tabH.getValueAt(i, 0));
            lista.add((LocalTime)tabH.getValueAt(i, 1));
        }
        
        return lista;
    }
    
//---Retorna a lista de MARCAÇÃO num Array
    public ArrayList listaMarcacao(DefaultTableModel tabM) {
        lista = new ArrayList<>();
                     
        for (int i = 0; i < tabM.getRowCount(); i++) {
            lista.add((LocalTime) tabM.getValueAt(i, 0));
            lista.add((LocalTime) tabM.getValueAt(i, 1));
        }
        return lista;
    }
    
//---Retorna a lista de ENTRADAS
    public ArrayList listaEntradas(DefaultTableModel tabH, DefaultTableModel tabM) {
        lista = new ArrayList<>();
                     
        for (int i = 0; i < tabM.getRowCount(); i++) {
            lista.add((LocalTime) tabH.getValueAt(i, 0));
            lista.add((LocalTime) tabM.getValueAt(i, 0));
        }
        return lista;
    }
    
    //---Retorna a lista de SAIDAS
    public ArrayList listaSaidas(DefaultTableModel tabH, DefaultTableModel tabM) {
        lista = new ArrayList<>();
                     
        for (int i = 0; i < tabM.getRowCount(); i++) {
            lista.add((LocalTime) tabH.getValueAt(i, 1));
            lista.add((LocalTime) tabM.getValueAt(i, 1));
        }
        return lista;
    }

//--Compara horários PRIMEIRO HORARIO MENOR QUE A MARCACAO
    public boolean compararHorarios(LocalTime horario, LocalTime marcacao) {
        return horario.isBefore(marcacao);
    }

//--Retorna a diferença entre dois horários
    public LocalTime subtracaoEntreHorarios(LocalTime hora, LocalTime marcacao) {
        return marcacao.minusHours(hora.getHour()).minusMinutes(hora.getMinute());
    }

//--Retorna se a hora é igual ou superior ao Periodo Noturno
    public boolean periodoNoturno(LocalTime hora) {
        return hora.getHour() >= pn;
    }

//--A variavel global recebe a hora do Perido Noturno
    public boolean configHorario(int hora) {
        pn = hora;
        return true;
    }

//--Carga Horária Diária, recebe a qtd de dias trabalhados na semana
    public int cargaHorariaDiaria(int dias){
        return jornada / dias;
    }
    
//--Carga Horária Semanal
    public int cargaHorariaSemanal(){
        return jornada;
    }
    
//--Configura a Jornada de Trabalho Semanal
    public boolean configJornadaTrabalho(int cg){
        jornada = cg;
        return true;
    }
}
