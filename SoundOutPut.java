package Main;


import java.util.regex.Pattern;

public class SoundOutPut {

	public static Object sound(String input) {
		// TODO Auto-generated constructor stub
		String removingvariable = null;
		
		input = input.replaceAll("(sound)()[(](.*)([)])", "$3").trim();
		String[] result = (input).split(",");
		
		//sound(a,freq,bit)
		
		// If sampling frequency are present
		if (result.length>=2) {
			
			result[1]=result[1].trim();
			
			// Check the value of frequency defined in sound
			if(Pattern.compile("(\\d*)").matcher(result[1]).matches()) {
				
				 String resultTemp = result[1].substring(0, 2);
				 //*************
				 // Get the nearest value of frequency for dsp kit.
				 //***************
				Changing.MainProgram = "AIC3204_config(freq_" + resultTemp + ");" + "\n" + Changing.MainProgram;
			}
			//if any variable are present that time finf ariable value and then add as a sampling frequency
			else if(Pattern.compile("(\\w*)").matcher(result[1]).matches()) {
				
			}
		}
		
		//If all three parameter are there
		if (result.length==3) {
			//TODO according bit parameter create a program
		}
		
		// to check value in result [0] ex. sound(a,freq,bit) in that seperate 'a' 
		if (Pattern.compile("(\\w*)").matcher(result[0]).matches() || Pattern.compile("(\\w*)(')").matcher(result[0]).matches() || Pattern.compile("(-)(\\w*)").matcher(result[0]).matches()  ) { 
    		int lastindex = 0;
    		result = (result[0]).split("'"); // if transpose of 'a' is present then it not required for transmistion of bit 		
    		int index = 0;
    		//Remove the Initialization of recursion variable like (a[1]={};) i.s a is remove from Initializevariable
    		if(Pattern.compile("\\$"+result[0]+"\\[1\\]").matcher(Changing.matrixVariable).find()) {
    			Changing.Initializevariable= Changing.Initializevariable.replaceAll("int "+result[0]+"\\[1\\]=\\{\\};", "");
    		}
			
			//Checking further occurrence of a=[] i.e cheack in main program the occurrence of transmitating variable and it representated according to DSP
    		while ((Changing.MainProgram.indexOf(result[0]+"=" , lastindex))!=-1 || (Changing.MainProgram.indexOf(result[0]+"[" , lastindex))!=-1) {
					//cheack in main program the position of the variable which is transmiting 
    					if((Changing.MainProgram.indexOf(result[0]+"[" , lastindex))!=-1){ 
    						//find the index of that variable
    						index = Changing.MainProgram.indexOf(result[0]+"[" , lastindex);
    						removingvariable = Changing.MainProgram.substring(index+result[0].length()+1, Changing.MainProgram.indexOf("]",index) );
    					}
    					else{
    						index = Changing.MainProgram.indexOf(result[0]+"=" , lastindex);
    					}
    					 
    					lastindex= Changing.MainProgram.indexOf("\n",index);
    					String temp = Changing.MainProgram.substring(index, lastindex );
    					String[] string2 = (temp).split(";");
    					//checking whether a=[a s]
    					if (string2[0].trim().matches(result[0]+"\\["+"(\\w*)"+"(\\])"+"( = )"+"(.*)")) {
    						
    						string2[0]=string2[0].replaceAll(result[0]+"(=)(\\{)"+result[0]+"(.*)(\\})", "$3").trim();
    						string2[0]=string2[0].split("=")[1];
    						
    						if(removingvariable!=""){
    							
    							Changing.MainProgram= Changing.MainProgram.substring(0, lastindex )
    								+ Changing.MainProgram.substring(lastindex+removingvariable.length()+4, Changing.MainProgram.length() ) ;
    						}
    						
    						if (string2.length!=1){
    							
    						}
    						
    						else {
    							string2[0]=string2[0].replaceAll(",", "");
    							Changing.MainProgram= Changing.MainProgram.substring(0, index ) + "transmit(" +string2[0].trim()+")"+";"
    							+ Changing.MainProgram.substring(lastindex, Changing.MainProgram.length() ) ;
    						}
    					}
    					
    					else if (string2[0].matches(result[0]+"(=)(.*)")) {
    						
    						string2[0]=string2[0].replaceAll(",", "");
    						string2=string2[0].split("=");
    						Changing.MainProgram= Changing.MainProgram.substring(0, lastindex )+ "\n" + "transmit(" +string2[0]+");"
    						+Changing.MainProgram.substring(lastindex, Changing.MainProgram.length() ) ;
    					}
    				}
    	}
		
		Changing.HeaderFile=Changing.HeaderFile+"\n"+Directory.HeaderFile;
		Changing.Initializevariable=Changing.Initializevariable+"\n"+Directory.Initializevariable;
		Changing.GlobleVariable = Changing.GlobleVariable +"\n"+Directory.GlobleVariable+ "\n" + "void transmit(Int16 a);";
		Changing.Function=Changing.Function + "\n" +Directory.Function;
		Changing.Function=Changing.Function + "\n" +"void transmit(Int16 a)	 // Transmits the signed integer 'a'.\n" + 
				"{\n" + 
				"while((Xmit & I2S0_IR) == 0);\n" + 
				"I2S0_W0_MSW_W = a;  // 16 bit left channel transmit audio data\n" + 
				"I2S0_W1_MSW_W = a; // 16 bit right channel transmit audio data\n" + 
				"}";
		
		
		return (null);
		
		
		
		
		
	}

}
