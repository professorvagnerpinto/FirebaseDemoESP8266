package br.edu.ifsul.firebasedemoesp8266.model;

public class ArCondicionado {

    private Boolean estado;
    private Integer temperatura;
    private Integer umidade;
    private Integer velocidadeVentilador;

    public ArCondicionado() {
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getUmidade() {
        return umidade;
    }

    public void setUmidade(Integer umidade) {
        this.umidade = umidade;
    }

    public Integer getVelocidadeVentilador() {
        return velocidadeVentilador;
    }

    public void setVelocidadeVentilador(Integer velocidadeVentilador) {
        this.velocidadeVentilador = velocidadeVentilador;
    }

    @Override
    public String toString() {
        return "ArCondicionado{" +
                "estado=" + estado +
                ", temperatura=" + temperatura +
                ", umidade=" + umidade +
                ", velocidadeVentilador=" + velocidadeVentilador +
                '}';
    }
}
