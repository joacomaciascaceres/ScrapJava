package com.jarroba.scraping.Scraping_jarroba_java;

import java.io.IOException;
import java.sql.*;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraping {	
	
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
                    System.out.println("> ID_CUADERNO: " + rs.getString("IDCUADERNO"));
                    System.out.println("> TIP_CUADERNO: " + rs.getString("TIPCUADERNO"));
                    System.out.println("+----------------CAUSA OT----------------+");
                    correlativoOT = rs.getString("ID_OT");
                    rolEntrada = rs.getString("ROL");
            		letraEntrada = rs.getString("LETRA");
            		anioEntrada = rs.getString("YEAR");
            		codtribunalEntrada = rs.getString("TRIBUNAL");
            		idcuadernoEntrada = rs.getString("IDCUADERNO");
            		tipcuadernoEntrada = rs.getString("TIPCUADERNO");
            		url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno="+tipcuadernoEntrada+"&CRR_IdCuaderno="+idcuadernoEntrada+"&ROL_Causa="+rolEntrada+"&TIP_Causa="+letraEntrada+"&ERA_Causa="+anioEntrada+"&COD_Tribunal="+codtribunalEntrada+"&TIP_Informe=1&";
            		//url = "http://civil.poderjudicial.cl/CIVILPORWEB/ConsultaDetalleAtPublicoAccion.do?TIP_Consulta=1&TIP_Cuaderno=1&CRR_IdCuaderno=18899924&ROL_Causa=24745&TIP_Causa=C&ERA_Causa=2015&CRR_IdCausa=14979001&COD_Tribunal=268&TIP_Informe=1&";	

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
					st.executeUpdate("INSERT INTO caratulas (`ROL`, `FECHA_INGRESO`, `ESTADO_ADMINISTRATIVO`, `PROCEDIMIENTO`, `UBICACION`, `ETAPA`, `TRIBUNAL`, `FK_ID_OT`) "
					      + "VALUES ('"+ROL_INSERT+"', '"+FECHA_INGRESO_INSERT+"', '"+ESTADO_ADMINISTRATIVO_INSERT+"', '"+PROCEDIMIENTO_INSERT+"', '"+UBICACION_INSERT+"', '"+ETAPA_INSERT+"', '"+TRIBUNAL_INSERT+"', '"+correlativoOT+"');");
					ResultSet rst = st.executeQuery("SELECT MAX(ID_CARATULAS) FROM caratulas;");
					if (rst.next()) {
					    auxiliarCaratulasUPDATE_MAX = rst.getInt(1);
					}
					auxiliarInsert=1;
				}
				System.out.println("+----------------CARATULADO----------------+");
				System.out.println("+----------------CONTENIDO----------------+");
				System.out.println(" ETAPA | TRÁMITE | DESC. TRÁMITE | FEC. TRÁMITE | FOJA");
				Elements entradas_contenido = document.select("table > tbody > tr > td").not("td[bgcolor=\"#DBE5EB\"]").not("td[width=\"30\"]");
				int auxiliarEntrada = 0;
				int auxiliarEntradaEncontrado = 0;
				for (Element elem : entradas_contenido) {
					String textohtml_texto = elem.getElementsByClass("texto").text();
					String textohtml_texto_string = elem.getElementsByClass("texto").toString();
					String textohtml_textoC = elem.getElementsByClass("textoC").text();
					String textohtml_numero = elem.getElementsByClass("numero").text();
					
					if(textohtml_texto_string.equals("")) {
						System.out.println("TEXTO VACIO ("+contadorVacio +")");
						contadorVacio++;						
					}else {						
						boolean estadoStringEncontrado = textohtml_texto_string.contains("> <");
						if(estadoStringEncontrado){
							System.out.println("TEXTO NO VACIO (ESPACIO)");
							contadorVACIOInsert = 1;
							textohtml_texto = "-";
						}else {
							contadorVACIOInsert = 0;
						}
					}
					
					if(auxiliarInsert2 > 29) 
					{
						contadorVacio = 0;
						if((textohtml_texto != null) && (!textohtml_texto.equals(""))){							
							
							/*AB.DTE*/
							String litigantes = "AB.DTE";							
							boolean litigantesEncontrado = textohtml_texto.contains(litigantes);
							
							String litigantes2 = "AB.DDO";							
							boolean litigantesEncontrado2 = textohtml_texto.contains(litigantes2);
							
							String litigantes3 = "DDO";							
							boolean litigantesEncontrado3 = textohtml_texto.contains(litigantes3);
							
							String litigantes4 = "DTE.";							
							boolean litigantesEncontrado4 = textohtml_texto.contains(litigantes4);
							if(litigantesEncontrado || litigantesEncontrado2 || litigantesEncontrado3 || litigantesEncontrado4){								
								if(auxiliarEntrada == 0 && auxiliarEntradaEncontrado == 0){
									auxiliarEntrada++;
									auxiliarEntradaEncontrado = 1;
									System.out.println();
									System.out.println(" PARTICIPANTE | RUT | PERSONA | NOMBRE");
									auxiliarInsertLitigantes = 99;
								}								
								//System.out.print(textohtml_texto+"|");
								
							}					
							
							System.out.println(textohtml_texto+"(1)");							
							parte1Tramites = textohtml_texto;	
							
							/* INICIO: GUARDADO DE LITIGANTES DE LA CAUSA */
							if(auxiliarInsertLitigantes == 99 && auxiliarUPDATELitigante == 1) {
								st.executeUpdate("INSERT INTO litigantes (`PARTICIPANTE`, `FK_ID_CARATULAS`) VALUES ('"+parte1Tramites+"', '"+auxiliarCaratulasUPDATE_MAX+"');");
								ResultSet rst2 = st.executeQuery("SELECT MAX(ID_LITIGANTES) FROM litigantes;");
								if (rst2.next()) {
									auxiliarUPDATE_MAXLitigantes = rst2.getInt(1);
								}
								auxiliarUPDATELitigante = 2;	
							}else{
								if(auxiliarUPDATELitigante == 2) {
									st.executeUpdate("UPDATE litigantes SET RUT = '"+parte1Tramites+"' WHERE ID_LITIGANTES = '"+auxiliarUPDATE_MAXLitigantes+"' AND FK_ID_CARATULAS = '"+auxiliarCaratulasUPDATE_MAX+"';");
									auxiliarUPDATELitigante = 3;
								}else {
									if(auxiliarUPDATELitigante == 3) {
										st.executeUpdate("UPDATE litigantes SET PERSONA = '"+parte1Tramites+"' WHERE ID_LITIGANTES = '"+auxiliarUPDATE_MAXLitigantes+"' AND FK_ID_CARATULAS = '"+auxiliarCaratulasUPDATE_MAX+"';");
										auxiliarUPDATELitigante = 4;
									}else {
										if(auxiliarUPDATELitigante == 4) {
											st.executeUpdate("UPDATE litigantes SET NOMBRE = '"+parte1Tramites+"' WHERE ID_LITIGANTES = '"+auxiliarUPDATE_MAXLitigantes+"' AND FK_ID_CARATULAS = '"+auxiliarCaratulasUPDATE_MAX+"';");
											auxiliarUPDATELitigante = 1;
										}
									}
								}
							}
							/* FIN: GUARDADO DE LITIGANTES DE LA CAUSA */
							
							/* INICIO PARTE 1: GUARDADO DE TRAMITES DE LA CAUSA */
							if(auxiliarUPDATE == 1) {
								
								if(contadorVACIOInsert == 1) {
									st.executeUpdate("INSERT INTO tramites (`ETAPA`, `FK_ID_CARATULAS`) "
											  + "VALUES ('-', '"+auxiliarCaratulasUPDATE_MAX+"');");
									
									ResultSet rst = st.executeQuery("SELECT MAX(ID_TRAMITES) FROM tramites;");
									if (rst.next()) {
									    auxiliarUPDATE_MAX = rst.getInt(1);
									}
									auxiliarUPDATE = 2;
								}else {
									st.executeUpdate("INSERT INTO tramites (`ETAPA`, `FK_ID_CARATULAS`) "
											  + "VALUES ('"+parte1Tramites+"', '"+auxiliarCaratulasUPDATE_MAX+"');");
									
									ResultSet rst = st.executeQuery("SELECT MAX(ID_TRAMITES) FROM tramites;");
									if (rst.next()) {
									    auxiliarUPDATE_MAX = rst.getInt(1);
									}
									auxiliarUPDATE = 2;
								}
								
							}else{
								if(auxiliarUPDATE == 2) {
									
									if(contadorVACIOInsert == 1) {
										st.executeUpdate("UPDATE tramites SET TRAMITE = '-' WHERE ID_TRAMITES = '"+auxiliarUPDATE_MAX+"';");
										auxiliarUPDATE = 3;
									}else {
										st.executeUpdate("UPDATE tramites SET TRAMITE = '"+parte1Tramites+"' WHERE ID_TRAMITES = '"+auxiliarUPDATE_MAX+"';");
										auxiliarUPDATE = 3;
									}
									
								}else {
									
									if(auxiliarUPDATE == 3) {
										
										if(contadorVACIOInsert == 1) {
											st.executeUpdate("UPDATE tramites SET DESC_TRAMITE = '"+parte1Tramites+"' WHERE ID_TRAMITES = '"+auxiliarUPDATE_MAX+"';");
											auxiliarUPDATE = 1;
										}else {
											st.executeUpdate("UPDATE tramites SET DESC_TRAMITE = '"+parte1Tramites+"' WHERE ID_TRAMITES = '"+auxiliarUPDATE_MAX+"';");
											auxiliarUPDATE = 1;
										}
										
									}
								}
							}
						}
						/* FIN PARTE 1: GUARDADO DE TRAMITES DE LA CAUSA */
						
						if((textohtml_textoC != null) && (!textohtml_textoC.equals(""))){
							System.out.println(textohtml_textoC+"(2)");
							parte2Tramites = textohtml_textoC;
							
						}
						
						if((textohtml_numero != null) && (!textohtml_numero.equals(""))){
							System.out.println(textohtml_numero+"(3)");
							parte3Tramites = textohtml_numero;
							System.out.println();
							st.executeUpdate("UPDATE tramites SET FECHA_TRAMITE = '"+parte2Tramites+"', FOJA = '"+parte3Tramites+"' WHERE ID_TRAMITES = '"+auxiliarUPDATE_MAX+"';");
							auxiliarContadordeLineasdeTramite++;
						}							
						auxiliarEntradaINSERT++;
					}
					
					//System.out.print("CONTADOR: "+auxiliarInsert2);
					auxiliarInsert2++;
							
					/*st.executeUpdate("INSERT INTO tramites (`ETAPA`, `TRAMITE`, `FECHA_TRAMITE`) "
							  + "VALUES ('"+parte1Tramites+"', '"+parte2Tramites+"', '"+parte3Tramites+"');");*/
						
					//System.out.println("TEXTO: "+textohtml_texto+"|"+textohtml_textoC+"|"+textohtml_numero);
					/*if(auxiliarInsert == 1) {
						st.executeUpdate("INSERT INTO tramites (`ETAPA`, `TRAMITE`, `DESC_TRAMITE`, `FECHA_TRAMITE`, `FOJA`, `FK_ID_CARATULAS`) "
							  + "VALUES ('"+ROL_INSERT+"', '"+FECHA_INGRESO_INSERT+"', '"+ESTADO_ADMINISTRATIVO_INSERT+"', '"+PROCEDIMIENTO_INSERT+"', '"+UBICACION_INSERT+"', '"+ETAPA_INSERT+"', '"+TRIBUNAL_INSERT+"', '"+correlativoOT+"');");
						auxiliarInsert=2;
					}*/
					
				}				
				/*System.out.println("++");
		        System.out.println("TEXTO: "+parte1Tramites+"|"+parte2Tramites+"|"+parte3Tramites);
		        System.out.println("++");*/
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
	        System.out.println();	
	        System.out.println("TRAMITE(S): "+auxiliarEntradaINSERT);
	        
		        try {
		        	Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/webscrap?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		            Statement st = conexion.createStatement();
		            st.executeQuery("call sp_delTramites();"); 
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
