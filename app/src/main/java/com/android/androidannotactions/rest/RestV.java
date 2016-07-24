package com.android.androidannotactions.rest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.ProgressBar;

import com.android.androidannotactions.Util.DataSerial;
import com.android.androidannotactions.Util.HeaderRequestInterceptor;
import com.orhanobut.logger.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinicius on 30/06/2016.
 */
public class RestV {
    private static RestTemplate restTemplate;
    //aqui voce colca sua url base
    private static String URL_BASE = "https://c7q5vyiew7.execute-api.us-east-1.amazonaws.com";
    private static String URL_GEOCODING = "https://maps.googleapis.com/maps/api/geocode/json?";
    private static String URL_GEOCODING_KEY = "&key=AIzaSyBNPznoGvMq2SQC5srbFaHAe0AhFOnaw1Q";
    public static String URL_GEOCODING_ADDRESS = "address=";
    public static String URL_GEOCODING_LATLON = "latlng=";
    private static boolean log = false;
    private Context contextA;
    private static RestV restV;
    private static ProgressBar progressBar;
    public static String CHAVE = "IfXJnQVdjo1fI4z6OQTWB6RPJ8Qs4JbcaDOZ83vt";
    public static String KEY = "x-api-key";

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
            String url = "";
            if (consultaGoogle) {
                url = URL_GEOCODING + parms + URL_GEOCODING_KEY;
            } else {
                url = URL_BASE + parms;
            }
            if (restTemplate != null) {
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                resposta = restTemplate.getForObject(url, String.class);
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
        String url = "";
        try {

            if (consultaGoogle) {
                url = URL_GEOCODING + parms + URL_GEOCODING_KEY;
            } else {
                url = URL_BASE + parms;
            }

            if (restTemplate != null) {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                if (object != null)
                    resposta = restTemplate.getForObject(url, object.getClass());
            }
        } catch (Exception e) {
            resposta = e.getMessage();
        }
        if (log && parms != null && resposta != null)
            logandoRequest(url + parms, DataSerial.convertObjectToString(resposta));
        return resposta;
    }

    //retornacursos?StatusID=1
    public static String postToString(String parms, String rota) {
        return postToString(parms, rota, log);
    }

    //retornacursos?StatusID=1
    public static String postToString(String parms, String rota, boolean log) {
        getInstace();
        String respostaString = null;
        ResponseEntity<String> resposta = null;
        String url = null;
        try {
            if (restTemplate != null) {
                url = URL_BASE + rota;

                List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
                interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
                interceptors.add(new HeaderRequestInterceptor(KEY, CHAVE));
                restTemplate.setInterceptors(interceptors);
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                resposta = restTemplate.postForEntity(url, parms, String.class);
                if (resposta != null && resposta.getBody() != null && resposta.getStatusCode() == HttpStatus.OK)
                    respostaString = resposta.getBody();
            } else {
                getInstace();
            }
        } catch (Exception e) {
            respostaString = e.getMessage();
        }
        if (log)
            logandoRequest(parms + "- url " + url, resposta);
        return respostaString;
    }

    public static Object postToObject(String parms, String rota, Object object) {
        return postToObject(parms, rota, object, log);
    }

    public static Object postToObject(String parms, String rota, Object object, boolean log) {

        getInstace();
        Object respostaObject = null;
        ResponseEntity<?> resposta = null;
        String url;
        try {
            if (restTemplate != null) {
                //colocando paramestro no HEAD
                List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
                interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
                interceptors.add(new HeaderRequestInterceptor(KEY, CHAVE));
                restTemplate.setInterceptors(interceptors);
                url = URL_BASE + rota;
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                resposta = restTemplate.postForEntity(url, parms, object.getClass());
                if (resposta != null && resposta.getBody() != null && resposta.getStatusCode() == HttpStatus.OK)
                    respostaObject = resposta.getBody();
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
