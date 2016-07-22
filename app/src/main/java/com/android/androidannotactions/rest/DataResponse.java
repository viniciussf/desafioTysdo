package com.android.androidannotactions.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vinicius on 30/06/2016.
 */


public class DataResponse {
    @JsonProperty("OcorreuErro")
    public boolean ocorreuErro;
    @JsonProperty("CodigoErro")
    public String codigoErro;
    @JsonProperty("DescricaoErro")
    public String descricaoErro;
    @JsonProperty("OcorreuAlerta")
    public boolean ocorreuAlerta;
    @JsonProperty("CodigoAlerta")
    public String codigoAlerta;
    @JsonProperty("DescricaoAlerta")
    public String descricaoAlerta;
}
