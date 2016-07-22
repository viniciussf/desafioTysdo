package com.android.androidannotactions.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vinicius on 30/06/2016.
 */
class Curso {
    @JsonProperty("CursoID")
    public String cursoID;
    @JsonProperty("NomeCurso")
    public String nomeCurso;
    @JsonProperty("DataCadastro")
    public String dataCadastro;
    @JsonProperty("DataInicio")
    public String dataInicio;
    @JsonProperty("DataFim")
    public String dataFim;
    @JsonProperty("CargaHoraria")
    public String cargaHoraria;
    @JsonProperty("Logomarca")
    public String logomarca;
    @JsonProperty("StatusID")
    public String statusID;

    public String getCursoID() {
        return cursoID;
    }

    public void setCursoID(String cursoID) {
        this.cursoID = cursoID;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getLogomarca() {
        return logomarca;
    }

    public void setLogomarca(String logomarca) {
        this.logomarca = logomarca;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }
}