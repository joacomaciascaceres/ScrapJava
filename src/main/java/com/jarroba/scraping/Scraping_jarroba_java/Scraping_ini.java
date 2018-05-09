package com.jarroba.scraping.Scraping_jarroba_java;

import java.io.IOException;
import java.sql.*;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraping_ini {	
	
	public static String correlativoOT;
	public static String rolEntrada;
	public static String letraEntrada;
	public static String anioEntrada;
	public static String codtribunalEntrada;
	public static String idcuadernoEntrada;
	public static String tipcuadernoEntrada;
	
	public static int auxiliarInsert = 0;
	public static int auxiliarInsert2 = 0;
    public static int auxiliarEntradaINSERT = 0;
    public static int auxiliarUPDATE = 1;
    public static int auxiliarUPDATELitigante = 1;
    public static int auxiliarUPDATE_MAX = 0;
    public static int auxiliarUPDATE_MAXLitigantes = 0;
    public static int auxiliarCaratulasUPDATE_MAX = 0;
    public static int auxiliarInsertLitigantes = 0;
    public static int auxiliarContadordeLineasdeTramite = 0;
    public static int marcadorVACIO = 0;
    public static int contadorVacio = 0;
    public static int contadorVACIOInsert = 0;
	
	public static String ROL_INSERT;
	public static String FECHA_INGRESO_INSERT;
	public static String ESTADO_ADMINISTRATIVO_INSERT;
	public static String PROCEDIMIENTO_INSERT;
	public static String UBICACION_INSERT;
	public static String ETAPA_INSERT;
	public static String TRIBUNAL_INSERT;
	public static String ENLACE_TXT_DEMANDA_INSERT;
	
	public static String url;
	
	public static String parte1Tramites;
	public static String parte2Tramites;
	public static String parte3Tramites;
	
//	public static final String url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=1&CRR_IdCuaderno=18899924&ROL_Causa=24745&TIP_Causa=C&ERA_Causa=2015&CRR_IdCausa=14979001&COD_Tribunal=268&TIP_Informe=1&";	

	public static void main (String args[]) throws IOException {		
		
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/webscrap?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select * from ot WHERE ID_OT = 1;");
 
            if (rs != null) {
            	System.out.println("+----------------CAUSA OT----------------+");
                while (rs.next()) {
                    System.out.println("> CAUSA: " + rs.getString("LETRA") + "-" + rs.getString("ROL") + "-" +rs.getString("YEAR"));
                    System.out.println("> TRIBUNAL COD: " + rs.getString("TRIBUNAL"));
                    //System.out.println("> ID_CUADERNO: " + rs.getString("IDCUADERNO"));
                    //System.out.println("> TIP_CUADERNO: " + rs.getString("TIPCUADERNO"));
                    System.out.println("+----------------CAUSA OT----------------+");
                    correlativoOT = rs.getString("ID_OT");
                    rolEntrada = rs.getString("ROL");
            		letraEntrada = rs.getString("LETRA");
            		anioEntrada = rs.getString("YEAR");
            		codtribunalEntrada = rs.getString("TRIBUNAL");
            		//idcuadernoEntrada = rs.getString("IDCUADERNO");
            		//tipcuadernoEntrada = rs.getString("TIPCUADERNO");
            		//url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno="+tipcuadernoEntrada+"&CRR_IdCuaderno="+idcuadernoEntrada+"&ROL_Causa="+rolEntrada+"&TIP_Causa="+letraEntrada+"&ERA_Causa="+anioEntrada+"&COD_Tribunal="+codtribunalEntrada+"&TIP_Informe=1&";
            		url = "http://civil.poderjudicial.cl/CIVILPORWEB/AtPublicoDAction.do?TIP_Consulta=1&TIP_Lengueta=tdUno&SeleccionL=0&TIP_Causa="+letraEntrada+"&ROL_Causa="+rolEntrada+"&ERA_Causa="+anioEntrada+"&RUC_Era=&RUC_Tribunal=3&RUC_Numero=&RUC_Dv=&FEC_Desde=03/05/2016&FEC_Hasta=03/05/2016&SEL_Litigantes=0&RUT_Consulta=&RUT_DvConsulta=&NOM_Consulta=&APE_Paterno=&APE_Materno=&COD_Tribunal="+codtribunalEntrada+"&irAccionAtPublico=Consulta";
            		//url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=1&CRR_IdCuaderno=18899924&ROL_Causa=24745&TIP_Causa=C&ERA_Causa=2015&CRR_IdCausa=14979001&COD_Tribunal=268&TIP_Informe=1&";	
            		//url = "http://civil.poderjudicial.cl/CIVILPORWEB/AtPublicoDAction.do?TIP_Consulta=1&TIP_Lengueta=tdUno&SeleccionL=0&TIP_Causa=C&ROL_Causa=24745&ERA_Causa=2015&RUC_Era=&RUC_Tribunal=3&RUC_Numero=&RUC_Dv=&FEC_Desde=03/05/2016&FEC_Hasta=03/05/2016&SEL_Litigantes=0&RUT_Consulta=&RUT_DvConsulta=&NOM_Consulta=&APE_Paterno=&APE_Materno=&COD_Tribunal=268&irAccionAtPublico=Consulta";
            		// /CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=49&CRR_IdCuaderno=16745951&ROL_Causa=1000&TIP_Causa=C&ERA_Causa=2015&CRR_IdCausa=13259543&COD_Tribunal=345&TIP_Informe=1&
                }
                rs.close();
            }
            st.close();
 
        }
        catch(SQLException s)
        {
            System.out.println("Error: SQL.");
            System.out.println("SQLException: " + s.getMessage());
        }
        catch(Exception s)
        {
            System.out.println("Error: Varios.");
            System.out.println("SQLException: " + s.getMessage());
        }
		
		if (getStatusConnectionCode(url) == 200) {
			
			System.out.println("INICIO DE EJECUCIÓN.");
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/webscrap?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
	            Statement st = conexion.createStatement();        

	            System.out.println("+----------------CAUSA----------------+");
				//System.out.println("Status Code: " + getStatusConnectionCode(url));		
				Document document = getHtmlDocument(url);				 
				Elements entradas = document.select("table > tbody > tr > td.textoC").select("td[bgcolor=\"#DBE5EB\"]");
				for (Element elem : entradas) {
					String textohtml = elem.getElementsByClass("textoC").toString();								
					String texto = elem.getElementsByClass("textoC").text();
					
					/*ROL*/
					String enlaceBuscar = "/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?";
					boolean enlaceEncontrado = texto.contains(enlaceBuscar);
					if(enlaceEncontrado){
						System.out.println("> Enlace: "+texto);	
						System.out.println("> Enlace: "+textohtml);	
					}					
					
				}
	 
	        }
	        catch(SQLException s)
	        {
	            System.out.println("Error: SQL.");
	            System.out.println("SQLException: " + s.getMessage());
	        }
	        catch(Exception s)
	        {
	            System.out.println("Error: Varios.");
	            System.out.println("SQLException: " + s.getMessage());
	        }	       
	        System.out.println();	
	        //System.out.println("TRAMITE(S): "+auxiliarEntradaINSERT);	        
	        System.out.println("FIN DE EJECUCIÓN.");
	        System.out.println("+----------------CONTENIDO----------------+");
				
		}else
			System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(url));	
	}
	
	
	/**
	 * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
	 * EJM:
	 * 		200 OK					300 Multiple Choices
	 * 		301 Moved Permanently	305 Use Proxy
	 * 		400 Bad Request			403 Forbidden
	 * 		404 Not Found			500 Internal Server Error
	 * 		502 Bad Gateway			503 Service Unavailable
	 * @param url
	 * @return Status Code
	 */
	public static int getStatusConnectionCode(String url) {
		
		Response response = null;
		
		try {
			response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
		}
		return response.statusCode();
	}
	
	
	/**
	 * Con este método devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup
	 * @param url
	 * @return Documento con el HTML
	 */
	public static Document getHtmlDocument(String url) {

		Document doc = null;

		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		}

		return doc;

	}
	
	

}
