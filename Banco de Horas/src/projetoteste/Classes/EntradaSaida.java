
package projetoteste.Classes;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author S430
 */
public class EntradaSaida {
    
    private LocalTime entrada1;
    private LocalTime saida1;
    private LocalTime entrada2;
    private LocalTime saida2;
    private LocalDate data;
    private LocalTime horaTrab;
    private LocalTime atraso;
    private LocalTime horaExtra;

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
     * @return the data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * @return the horaTrab
     */
    public LocalTime getHoraTrab() {
        return horaTrab;
    }

    /**
     * @param horaTrab the horaTrab to set
     */
    public void setHoraTrab(LocalTime horaTrab) {
        this.horaTrab = horaTrab;
    }

    /**
     * @return the atraso
     */
    public LocalTime getAtraso() {
        return atraso;
    }

    /**
     * @param atraso the atraso to set
     */
    public void setAtraso(LocalTime atraso) {
        this.atraso = atraso;
    }

    /**
     * @return the horaExtra
     */
    public LocalTime getHoraExtra() {
        return horaExtra;
    }

    /**
     * @param horaExtra the horaExtra to set
     */
    public void setHoraExtra(LocalTime horaExtra) {
        this.horaExtra = horaExtra;
    }

        
}
