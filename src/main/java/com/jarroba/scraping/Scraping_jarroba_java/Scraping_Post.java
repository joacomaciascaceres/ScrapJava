package com.jarroba.scraping.Scraping_jarroba_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Scraping_Post {
	public static void send() throws IOException {
        URL url = new URL("http://civil.poderjudicial.cl/CIVILPORWEB/AtPublicoDAction.do?");
        
        //http://civil.poderjudicial.cl/CIVILPORWEB/AtPublicoDAction.do?TIP_Consulta=1&TIP_Lengueta=tdUno&SeleccionL=0&TIP_Causa=C&ROL_Causa=24745&ERA_Causa=2015&RUC_Era=&RUC_Tribunal=3&RUC_Numero=&RUC_Dv=&FEC_Desde=03/05/2016&FEC_Hasta=03/05/2016&SEL_Litigantes=0&RUT_Consulta=&RUT_DvConsulta=&NOM_Consulta=&APE_Paterno=&APE_Materno=&COD_Tribunal=268&irAccionAtPublico=Consulta
        /*Map<String, Object> params = new LinkedHashMap<>();
 
        params.put("parametro", "ProgramaciónExtrema.com");
 
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
                    "UTF-8"));
        }*/
        String data = "TIP_Consulta=1&TIP_Lengueta=tdUno&SeleccionL=0&TIP_Causa=C&ROL_Causa=24745&ERA_Causa=2015&RUC_Era=&RUC_Tribunal=3&RUC_Numero=&RUC_Dv=&FEC_Desde=03/05/2016&FEC_Hasta=03/05/2016&SEL_Litigantes=0&RUT_Consulta=&RUT_DvConsulta=&NOM_Consulta=&APE_Paterno=&APE_Materno=&COD_Tribunal=268&irAccionAtPublico=Consulta";
        byte[] postData = data.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        
        //byte[] postDataBytes = postData.toString().getBytes("UTF-8");
 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length",Integer.toString(postDataLength));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataLength);
 
        Reader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        for (int c = in.read(); c != -1; c = in.read())
            System.out.print((char) c);
    }
 
    public static void main(String[] args) {
        try {
            send();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
