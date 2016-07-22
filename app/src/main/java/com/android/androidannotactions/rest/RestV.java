package com.android.androidannotactions.rest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vinicius on 30/06/2016.
 */
public class RestV {
    private static RestTemplate restTemplate;
    //aqui voce colca sua url base
    private static String URL_BASE = "http://homol.enemtotvs.mobilus.com.br/servicos/";
    private static String URL_GEOCODING = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static String URL_GEOCODING_KEY = "&key=AIzaSyBNPznoGvMq2SQC5srbFaHAe0AhFOnaw1Q";
    private static boolean log = false;
    private Context contextA;
    private static RestV restV;
    private static ProgressBar progressBar;

    public RestV(Context contextA) {
        this.contextA = contextA;
    }

    public static void getInstace(Context context) {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        if (restV == null && context != null) {
            restV = new RestV(context);
            /*if (progressBar == null) {
                progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
            }*/
        }
    }

    public static void getInstace() {
        getInstace(null);
    }

    // exemplo retornacursos?StatusID=1
    public static String getToString(String parms) {
        return getToString(parms, log, false);
    }

    public static String getToString(String parms, boolean log, boolean consultaGoogle) {
        //  progressBar.setVisibility(View.VISIBLE);

        getInstace();
        String resposta = null;
        try {
            if (consultaGoogle) {
                URL_BASE = URL_GEOCODING + parms + URL_GEOCODING_KEY;
            } else {
                URL_BASE = URL_BASE + parms;
            }
            if (restTemplate != null) {
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                resposta = restTemplate.getForObject(URL_BASE, String.class);
            }
        } catch (Exception e) {
            resposta = e.getMessage();
        }
        if (log)
            logandoRequest(parms, resposta);
        return resposta;
    }

    public static Object getToObject(String parms, Object object) {
        return getToObject(parms, object, log, false);
    }

    public static Object getToObject(String parms, Object object, boolean log, boolean consultaGoogle) {
        getInstace();
        Object resposta = null;
        try {

            if (consultaGoogle) {
                URL_BASE = URL_GEOCODING + parms + URL_GEOCODING_KEY;
            } else {
                URL_BASE = URL_BASE + parms;
            }
            if (restTemplate != null) {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                if (object != null)
                    resposta = restTemplate.getForObject(URL_BASE, object.getClass());
            }
        } catch (Exception e) {
            resposta = e.getMessage();
        }
        if (log)
            logandoRequest(parms, resposta);
        return resposta;
    }

    //retornacursos?StatusID=1
    public static String postToString(String parms) {
        return postToString(parms, log);
    }

    //retornacursos?StatusID=1
    public static String postToString(String parms, boolean log) {
        getInstace();
        String respostaString = null;
        ResponseEntity<String> resposta = null;
        try {
            if (restTemplate != null) {
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                resposta = restTemplate.postForEntity("http://desenv.ipemed.mobilus.com.br/servicos/logar/", parms, String.class);
                if (resposta != null && resposta.getBody() != null && resposta.getStatusCode() == HttpStatus.OK)
                    respostaString = resposta.getBody();
            } else {
                getInstace();
            }
        } catch (Exception e) {
            respostaString = e.getMessage();
        }
        if (log)
            logandoRequest(parms, resposta);
        return respostaString;
    }

    public static Object postToObject(String parms, Object object) {
        return postToObject(parms, object, log);
    }

    public static Object postToObject(String parms, Object object, boolean log) {

        getInstace();
        Object respostaObject = null;
        ResponseEntity<?> resposta = null;
        try {
            if (restTemplate != null) {
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                resposta = restTemplate.postForEntity("http://desenv.ipemed.mobilus.com.br/servicos/logar/", parms, object.getClass());
                if (resposta != null && resposta.getBody() != null && resposta.getStatusCode() == HttpStatus.OK)
                    respostaObject = resposta.getBody();
            } else {
                getInstace();
            }
        } catch (Exception e) {
            respostaObject = e.getMessage();
        }
        if (log)
            logandoRequest(parms, resposta);
        return respostaObject;
    }

    public static boolean isDebug(Context c) {
        boolean isDebuggable = false;
        if (c != null) {
            isDebuggable = (0 != (c.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
        }
        return isDebuggable;
    }

    //logg

    public static void logandoRequest(Object... args) {
        if (args != null)
            for (int i = 0; i < args.length; i++) {
                Logger.v(String.valueOf(args[i]));
            }
    }
}
