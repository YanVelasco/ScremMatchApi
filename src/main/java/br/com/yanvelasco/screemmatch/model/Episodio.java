package br.com.yanvelasco.screemmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporadas, DTODadosEpisodio dtoDadosEpisodio) {
        this.temporada = numeroTemporadas;
        this.titulo = dtoDadosEpisodio.titulo();
        this.numeroEpisodio = dtoDadosEpisodio.numeroEpisodio();
        try {
            this.avaliacao = Double.valueOf(dtoDadosEpisodio.avaliacao());
        } catch (Exception e) {
            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(dtoDadosEpisodio.dataLancamento());
        } catch (DateTimeParseException e) {
            this.dataLancamento = null;
        }
    }

    /**
     * @return Integer return the temporada
     */
    public Integer getTemporada() {
        return temporada;
    }

    /**
     * @param temporada the temporada to set
     */
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    /**
     * @return String return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return Integer return the numeroEpisodio
     */
    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    /**
     * @param numeroEpisodio the numeroEpisodio to set
     */
    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    /**
     * @return Double return the avaliacao
     */
    public Double getAvaliacao() {
        return avaliacao;
    }

    /**
     * @param avaliacao the avaliacao to set
     */
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    /**
     * @return LocalDate return the dataLancamento
     */
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    /**
     * @param dataLancamento the dataLancamento to set
     */
    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "{" +
                " temporada='" + getTemporada() + "'" +
                ", titulo='" + getTitulo() + "'" +
                ", numeroEpisodio='" + getNumeroEpisodio() + "'" +
                ", avaliacao='" + getAvaliacao() + "'" +
                ", dataLancamento='" + getDataLancamento() + "'" +
                "}";
    }

}
