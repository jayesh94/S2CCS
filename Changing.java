
/*Here MainProgram is used to store the converted code at the steps of code detection in MainGrammar.jj*/

package Main;

public class Changing {
	public static int index;
	public static String code;
		
	public static void setMainProgram(String mainProgram) {
	//	MainProgram = mainProgram;
	}
	
	public static String HeaderFile="#include \"stdio.h\"\n"+"#include \"usbstk5515.h\"\n" ;
	
	public static String GlobleVariable="";
	
	
	public static String Function="";
	
	public static String Initializevariable="\n main( void )\n" + 
			"{\n" + 
			"//initialization of variables";
	
	public static String MainProgram="\n//Program\n";
	
	public static String Ending ="}";
	public static String variablevalue ="$";
	public static String InputVariable="$"; // used for checking initialization of variables
	public static String matrixVariable="$"; // workspace of variables defining matrix 
	public static String InputOutputVariable="$";
			
}
