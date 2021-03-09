package relogiodeponto.classes;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlephMantovani
 */
interface Calculos {

    void populaTabelaMenor(DefaultTableModel tabH, DefaultTableModel tabM);
    void populaTabelaMaior(DefaultTableModel tabH, DefaultTableModel tabM);
    void populaTabelaIgual(DefaultTableModel tabH, DefaultTableModel tabM);
    void tamTabelas(DefaultTableModel tabH, DefaultTableModel tabM);

}

