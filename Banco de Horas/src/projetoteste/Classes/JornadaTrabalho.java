package projetoteste.Classes;

import java.time.LocalTime;

/**
 *
 * @author S430
 */
public class JornadaTrabalho {
    
    private LocalTime entrada1;
    private LocalTime saida1;
    private LocalTime entrada2;
    private LocalTime saida2;
    private int jornadaSemanal;
    private int jornadaDiaria;

    /**
     * @return the entrada1
     */
    public LocalTime getEntrada1() {
        return entrada1;
    }

    /**
     * @param entrada1 the entrada1 to set
     */
    public void setEntrada1(LocalTime entrada1) {
        this.entrada1 = entrada1;
    }

    /**
     * @return the saida1
     */
    public LocalTime getSaida1() {
        return saida1;
    }

    /**
     * @param saida1 the saida1 to set
     */
    public void setSaida1(LocalTime saida1) {
        this.saida1 = saida1;
    }

    /**
     * @return the entrada2
     */
    public LocalTime getEntrada2() {
        return entrada2;
    }

    /**
     * @param entrada2 the entrada2 to set
     */
    public void setEntrada2(LocalTime entrada2) {
        this.entrada2 = entrada2;
    }

    /**
     * @return the saida2
     */
    public LocalTime getSaida2() {
        return saida2;
    }

    /**
     * @param saida2 the saida2 to set
     */
    public void setSaida2(LocalTime saida2) {
        this.saida2 = saida2;
    }

    
    /**
     * @return the jornadaSemanal
     */
    public int getJornadaSemanal() {
        return jornadaSemanal;
    }

    /**
     * @param jornadaSemanal the jornadaSemanal to set
     */
    public void setJornadaSemanal(int jornadaSemanal) {
        this.jornadaSemanal = jornadaSemanal;
    }

    /**
     * @return the jornadaDiaria
     */
    public int getJornadaDiaria() {
        return jornadaDiaria;
    }

    /**
     * @param jornadaDiaria the jornadaDiaria to set
     */
    public void setJornadaDiaria(int jornadaDiaria) {
        this.jornadaDiaria = jornadaDiaria;
    }
    
    
}
