package relogiodeponto.classes;

import java.time.LocalTime;

/**
 *
 * @author AlephMantovani
 */
public class HorarioDeTrabalho {

    private LocalTime entrada;
    private LocalTime saida;

    public HorarioDeTrabalho() {

    }

    public HorarioDeTrabalho(LocalTime entrada, LocalTime saida) {
        this.entrada = entrada;
        this.saida = saida;
    }


    /**
     * @return the entrada
     */
    public LocalTime getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(LocalTime entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the saida
     */
    public LocalTime getSaida() {
        return saida;
    }

    /**
     * @param saida the saida to set
     */
    public void setSaida(LocalTime saida) {
        this.saida = saida;
    }

    
}
