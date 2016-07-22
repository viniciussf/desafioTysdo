package com.android.androidannotactions.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vinicius on 7/22/16.
 */
public class DataResponseCadastro {
    /*  {
          "message": "Local salvo com sucesso!"
          "place_id": 1469216054802
      }*/
    @JsonProperty("message")
    private String message;
    @JsonProperty("place_id")
    private String place_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
