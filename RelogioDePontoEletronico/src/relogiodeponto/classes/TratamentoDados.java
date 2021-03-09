package relogiodeponto.classes;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlephMantovani
 */
public class TratamentoDados {

    private final int LIMITE = 3;
    Controlador cont;

//--Construtor da Classe
    public TratamentoDados() {
    }

//--Insere os dados na Tabela de acordo com os parametros passados
    public void inserirDadosTabela(DefaultTableModel tab, HorarioDeTrabalho hrTrab, MarcacoesFeitas marcF, int aba) {

        if (aba == 0) {
            if (tab.getRowCount() < LIMITE) {
                tab.addRow(new Object[]{hrTrab.getEntrada(), hrTrab.getSaida()});
            } else {
                if (aba == 0) {
                    JOptionPane.showMessageDialog(null, "Limite Máximo de Entradas é 3", "ATENÇÃO", 2);
                }
            }
        } else {
            tab.addRow(new Object[]{marcF.getEntrada(), marcF.getSaida()});
        }
    }

//--Exclui a linha selecionada da Tabela de acordo com os parametros passados
    public void excluirDadosTabela(DefaultTableModel tab, int row) {
        try {
            if (row >= 0) {
                tab.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha para a exclusão!", "ATENÇÃO", 2);
            }
        } catch (HeadlessException e) {
            System.err.println("Erro: " + e);
        }
    }
}
