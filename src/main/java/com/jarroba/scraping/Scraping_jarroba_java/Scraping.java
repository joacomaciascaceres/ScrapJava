package com.jarroba.scraping.Scraping_jarroba_java;

import java.io.IOException;
import java.sql.*;

//import java.util.Scanner;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraping {	
	
	public static String rolEntrada;
	public static String letraEntrada;
	public static String anioEntrada;
	public static String codtribunalEntrada;
	public static String idcuadernoEntrada;
	public static int auxiliarInsert = 0;
	public static String ROL_INSERT;
	public static String FECHA_INGRESO_INSERT;
	public static String ESTADO_ADMINISTRATIVO_INSERT;
	public static String PROCEDIMIENTO_INSERT;
	public static String UBICACION_INSERT;
	public static String ETAPA_INSERT;
	public static String TRIBUNAL_INSERT;
	public static String ENLACE_TXT_DEMANDA_INSERT;
	public static String url;
	
//	public static final String url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=1&CRR_IdCuaderno=18899924&ROL_Causa=24745&TIP_Causa=C&ERA_Causa=2015&CRR_IdCausa=14979001&COD_Tribunal=268&TIP_Informe=1&";	
	
	//private static Scanner scanner;

	public static void main (String args[]) throws IOException {		
		
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/webscrap?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select * from ot LIMIT 1;");
 
            if (rs != null) {
            	System.out.println("+----------------CAUSA OT----------------+");
                while (rs.next()) {
                    System.out.println("> CAUSA: " + rs.getString("LETRA") + "-" + rs.getString("ROL") + "-" +rs.getString("YEAR"));
                    System.out.println("> TRIBUNAL COD: " + rs.getString("TRIBUNAL"));
                    System.out.println("> ID_CUADERNO: " + rs.getString("IDCUADERNO"));
                    System.out.println("+----------------CAUSA OT----------------+");
                    rolEntrada = rs.getString("ROL");
            		letraEntrada = rs.getString("LETRA");
            		anioEntrada = rs.getString("YEAR");
            		codtribunalEntrada = rs.getString("TRIBUNAL");
            		idcuadernoEntrada = rs.getString("IDCUADERNO");
            		url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=1&CRR_IdCuaderno="+idcuadernoEntrada+"&ROL_Causa="+rolEntrada+"&TIP_Causa="+letraEntrada+"&ERA_Causa="+anioEntrada+"&COD_Tribunal="+codtribunalEntrada+"&TIP_Informe=1&";
            		//System.out.println(url);
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
		
		/*String[] rolUno = {"24745","C","2015","268","18899924"};
		for(int i = 0 ; i < rolUno.length ; i++)
		{
		    //System.out.println(rolUno[i]);
		    if(i == 0) {
		    	rolEntrada = rolUno[i];
		    }
		    if(i == 1) {
		    	letraEntrada = rolUno[i];
		    }
		    if(i == 2) {
		    	anioEntrada = rolUno[i];
		    }
		    if(i == 3) {
		    	codtribunalEntrada = rolUno[i];
		    }
		    if(i == 4) {
		    	idcuadernoEntrada = rolUno[i];
		    }
		    
		}*/
				
		/*String rolEntrada = rolUno[0];
		String letraEntrada = rolUno[1];
		String anioEntrada = rolUno[2];
		String codtribunalEntrada = rolUno[3];
		String idcuadernoEntrada = rolUno[4];
		
		scanner = new Scanner(System.in);		
		System.out.print("Ingrese el rol de la causa (EJ:C-[24745]-2015): ");
		String rolEntrada = scanner.nextLine();
		System.out.print("Ingresa la letra de la causa (EJ:[C]-24745-2015): ");
		String letraEntrada = scanner.nextLine();
		System.out.print("Ingresa el año de la causa (EJ: C-24745-[2015]): ");
		String anioEntrada = scanner.nextLine();
		System.out.print("Ingresa el codigo del tribunal (EJ: 268): ");
		String codtribunalEntrada = scanner.nextLine();
		String idcuadernoEntrada = "18899924";*/
		
		//String url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=1&CRR_IdCuaderno="+idcuadernoEntrada+"&ROL_Causa="+rolEntrada+"&TIP_Causa="+letraEntrada+"&ERA_Causa="+anioEntrada+"&COD_Tribunal="+codtribunalEntrada+"&TIP_Informe=1&";	
		           // http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=1&CRR_IdCuaderno=18899924&ROL_Causa=24745&TIP_Causa=C&ERA_Causa=2015&CRR_IdCausa=14979001&COD_Tribunal=268&TIP_Informe=1&"
		//System.out.println(url);
		if (getStatusConnectionCode(url) == 200) {
			
			System.out.println("INICIO DE EJECUCIÓN.");
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/webscrap?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
	            Statement st = conexion.createStatement();        

	            System.out.println("+----------------CARATULADO----------------+");
				//System.out.println("Status Code: " + getStatusConnectionCode(url));		
				Document document = getHtmlDocument(url);				 
				Elements entradas = document.select("table > tbody > tr > td.texto").select("td[bgcolor=\"#DBE5EB\"]");
				for (Element elem : entradas) {
					String textohtml = elem.getElementsByClass("texto").toString();								
					String texto = elem.getElementsByClass("texto").text();
					
					/*ROL*/
					String rolBuscar = "ROL";
					boolean rolEncontrado = texto.contains(rolBuscar);
					if(rolEncontrado){
						System.out.println("> ROL: "+texto.replace("ROL", "").replace(":", "").replace(" ", ""));	
						ROL_INSERT = texto.replace("ROL", "").replace(":", "").replace(" ", "");
					}
					
					/*FECHA INGRESO*/
					String figresoBuscar = "F. Ing";
					boolean fingresoEncontrado = texto.contains(figresoBuscar);
					if(fingresoEncontrado){
						System.out.println("> F. Ing: "+texto.replace("F. Ing", "").replace(":", "").replace(" ", ""));
						FECHA_INGRESO_INSERT = texto.replace("F. Ing", "").replace(":", "").replace(" ", "");
					}
					
					/*ESTADO ADMINISTRATIVO*/
					String estadoadminBuscar = "Est.Adm.";
					boolean estadoadminEncontrado = texto.contains(estadoadminBuscar);
					if(estadoadminEncontrado){
						System.out.println("> Est.Adm.: "+texto.replace("Est.Adm.", "").replace(":", ""));
						ESTADO_ADMINISTRATIVO_INSERT = texto.replace("Est.Adm.", "").replace(":", "");
					}
					
					/*PROCEDIMIENTO*/
					String procedimientoBuscar = "Proc.";
					boolean procedimientoEncontrado = texto.contains(procedimientoBuscar);
					if(procedimientoEncontrado){
						System.out.println("> Proc.: "+texto.replace("Proc.", "").replace(":", ""));
						PROCEDIMIENTO_INSERT = texto.replace("Proc.", "").replace(":", "");
					}
					
					/*UBICACIÓN*/
					String ubicacionBuscar = "Ubicación";
					boolean ubicacionEncontrado = texto.contains(ubicacionBuscar);
					if(ubicacionEncontrado){
						System.out.println("> Ubicación: "+texto.replace("Ubicación", "").replace(":", ""));
						UBICACION_INSERT = texto.replace("Ubicación", "").replace(":", "");
					}
					
					/*ETAPA*/
					String etapaBuscar = "Etapa";
					boolean etapaEncontrado = texto.contains(etapaBuscar);
					if(etapaEncontrado){
						System.out.println("> Etapa: "+texto.replace("Etapa", "").replace(":", ""));
						ETAPA_INSERT = texto.replace("Etapa", "").replace(":", "");
					}
					
					/*TRIBUNAL*/
					String tribunalBuscar = "Tribunal";
					boolean tribunalEncontrado = texto.contains(tribunalBuscar);
					if(tribunalEncontrado){
						System.out.println("> Tribunal: "+texto.replace("Tribunal", "").replace(":", ""));
						TRIBUNAL_INSERT = texto.replace("Tribunal", "").replace(":", "");
					}
					
					/*ENLACETXTDEMANDA*/
					String enlacetxtdemandaBuscar = "ShowPDFCabecera('";
					boolean enlacetxtdemanda = textohtml.contains(enlacetxtdemandaBuscar);
					if(enlacetxtdemanda){
						System.out.println("> TextoDemanda: "+textohtml);
						ENLACE_TXT_DEMANDA_INSERT = textohtml;
					}							
					
					//System.out.println("TEXTO: "+textohtml);
					
				}
				
				if(auxiliarInsert == 0) {
					st.executeUpdate("INSERT INTO caratulas (`ROL`, `FECHA_INGRESO`, `ESTADO_ADMINISTRATIVO`, `PROCEDIMIENTO`, `UBICACION`, `ETAPA`, `TRIBUNAL`) VALUES ('"+ROL_INSERT+"', '"+FECHA_INGRESO_INSERT+"', '"+ESTADO_ADMINISTRATIVO_INSERT+"', '"+PROCEDIMIENTO_INSERT+"', '"+UBICACION_INSERT+"', '"+ETAPA_INSERT+"', '"+TRIBUNAL_INSERT+"');");
					auxiliarInsert++;
				}
				System.out.println("+----------------CARATULADO----------------+");
				System.out.println("+----------------CONTENIDO----------------+");
				System.out.println(" ETAPA | TRÁMITE | DESC. TRÁMITE | FEC. TRÁMITE | FOJA");
				Elements entradas_contenido = document.select("table > tbody > tr > td").not("td[bgcolor=\"#DBE5EB\"]").not("td[width=\"30\"]");
				for (Element elem : entradas_contenido) {
					String textohtml_texto = elem.getElementsByClass("texto").text();
					String textohtml_textoC = elem.getElementsByClass("textoC").text();
					String textohtml_numero = elem.getElementsByClass("numero").text();
					
					if((textohtml_texto != null) && (!textohtml_texto.equals(""))){
						System.out.print(textohtml_texto+"|");
					}
					
					if((textohtml_textoC != null) && (!textohtml_textoC.equals(""))){
						System.out.print(textohtml_textoC+"|");
					}
					
					if((textohtml_numero != null) && (!textohtml_numero.equals(""))){
						System.out.print(textohtml_numero+"|");
						System.out.println();
					}
					
					//System.out.println("TEXTO: "+textohtml_texto+"|"+textohtml_textoC+"|"+textohtml_numero);
					
				}				
				/*System.out.println("++++++ FECHA CONTENIDO  ++++++");
				
				Elements entradas_contenido_fecha = document.select("table > tbody > tr > td.textoC").not("td[bgcolor=\"#DBE5EB\"]");
				for (Element elem : entradas_contenido_fecha) {
					String textohtml = elem.getElementsByClass("textoC").toString();								
					
					System.out.println("TEXTO: "+textohtml);
					
				}
				System.out.println("++++++  NÚMERO  ++++++");
				
				Elements entradas_contenido_numero = document.select("table > tbody > tr > td.numero").not("td[bgcolor=\"#DBE5EB\"]");
				for (Element elem : entradas_contenido_numero) {
					String textohtml = elem.getElementsByClass("numero").toString();								
					
					System.out.println("TEXTO: "+textohtml);
					
				}
				System.out.println("++++++++++++++++++++++");*/  
	 
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
