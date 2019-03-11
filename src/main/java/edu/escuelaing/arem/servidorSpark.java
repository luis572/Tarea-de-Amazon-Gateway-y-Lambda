package edu.escuelaing.arem;

import java.io.BufferedReader;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

public class servidorSpark {

    public static void main(String[] args) {
        port(getPort());
        get("/", (req, res) -> lectura(req, res));
        get("/lectura", (req, res) -> lectura(req, res));
        get("/results", (req, res) -> respuesta(req, res));
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)
    }

    private static String lectura(Request req, Response res) {
        String calcdataHTML = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "   \n"
                + "   <head>\n"
                + "      <title>Calculos Estadisticos</title>\n"
                + "   </head>\n"
                + "	\n"
                + "   <body>\n"
                + "      <h2>Bienvenido a la calculadora de cuadrados :</h2>\n"
                + "      <p>*Se debe solo un numero</p>\n"
                + "       <form action=\"/results\">\n"
                + "           Ingrese los dato:<br>\n"
                + "           <input type=\"text\" placeholder=\"Separated by spaces\" name=\"datos1\" ><br>\n"
                + "           <input type=\"submit\" value=\"Calculate!\">\n"
                + "       </form>"
                + //"      <a href = \"http://localhost:9999/results\" target = \"_self\">Ver Resultados</a>\n" +  
                "   </body>\n"
                + "	\n"
                + "</html>";
        return calcdataHTML;
    }

    public static String respuesta(Request req, Response resp) throws IOException, Exception {
        String numeros = req.queryParams("datos1");
        URL url = new URL("https://do4622yfbd.execute-api.us-east-1.amazonaws.com/CalcularCuadrado?value="+numeros);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String linea;
        String respu="";
        while((linea=in.readLine())!=null){
            respu+=linea;
            
        }
        String respuesta = "<!doctype html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\"\n"
                + "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n"
                + "    <title>Document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "El cuadrado es: "+respu+"\n"
                + "</body>\n"
                + "</html>";
        return respuesta;
    }
}
