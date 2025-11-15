package co.edu.unbosque.utils;

import java.text.Normalizer;

public class CrearCodigos {
	
	public static String generarCodigo(String nombre, String numeroStr, int letras, int ancho) {
		String pref = Normalizer.normalize(nombre, Normalizer.Form.NFD)
		        .replaceAll("\\p{M}+", "")        
		        .replaceAll("[^A-Za-z]", "")      
		        .toUpperCase();
		
		if (pref.isEmpty()) pref = "X";
	    if (pref.length() < letras) pref = (pref + "X".repeat(letras)).substring(0, letras);
	    else pref = pref.substring(0, letras);
	    
	    String digits = numeroStr.replaceAll("\\D+", "");
	    
	    String sufijo = digits.length() < ancho
	            ? "0".repeat(ancho - digits.length()) + digits
	            : digits; 

	    return pref + sufijo;
	}
	
	public static String generarCodigoTemporal() {
		return Long.toString(System.currentTimeMillis(), 36);
	}
	
}
