
/*In this class the detected variables in scilab code are being initialized at Changing.Initializevariable. If the variables have
already been initialized then no changes are done. The variables are initialized with their data types specified. Data type of a variiable 
is modified if required Ex. int a; may change to float a; 
Array length and matrix length is also found out in this class.
 This class is called from other classes as and when a variable is detected. */

package Main;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Find {
	public static int[] ArrayLength=new int[2];
	
	
	//
	public static void FindInitializevariable(String input ,String DataType) {
		// TODO Auto-generated method stub
		
		// if the input is initialized the true Ex. a, OR a; so true
		if (Changing.Initializevariable.indexOf(input+",")!=-1 || Changing.Initializevariable.indexOf(input+";")!=-1){
		        
		        // if data type .* input is not present  && the data type is present in Changing.Initializevariable 
			if ((!Pattern.compile(DataType+"(\\.*)"+input).matcher(Changing.Initializevariable).find()) && Changing.Initializevariable.indexOf(DataType)!=-1){
				// remove the input variable where ever intialized
				Changing.Initializevariable=Changing.Initializevariable.replaceAll("\\,"+input, "");
				Changing.Initializevariable=Changing.Initializevariable.replaceAll(input+";", ";");
			}
		}
		
		// IF the input is not initialised even not initiaized in matrix form && the data type is present in Changing.Initializevariable 
		if ((Changing.Initializevariable.indexOf(input+",")==-1 && Changing.Initializevariable.indexOf(input+";")==-1 && !input.contains("[")) && Changing.Initializevariable.indexOf(DataType)!=-1){
			// intitialize the variable in front of the data type in Changing.Initializevariable
			int index = Changing.Initializevariable.indexOf(DataType);
			int length=DataType.length();
			Changing.Initializevariable=Changing.Initializevariable.substring(0, index+length+1)+input+","+Changing.Initializevariable.substring(index+length+1, Changing.Initializevariable.length());
		}
		
		else {
			// Initialze variable with it's data type specified
			Changing.Initializevariable=Changing.Initializevariable + "\n" + DataType + " " + input + ";";
		}
		
	}
	
	
	// To find the length of array or matrix Ex. a= [1,2 ; 2,1]
	// Two dimensions of matrix
	public static void arraylength(String input) {
		
		String mi = null;
		int i=0;
		int index=0;
		int lindex=0;
		if (input.contains(";"))
		{
			// To check the number of semicolons present to get the number of rows 
			while (input.indexOf(";", lindex)!=-1){ // While semicolon is present
				index=input.indexOf(";", lindex);
				lindex=index+1;
				i++;
			}
			
			// i= number of semicolons
			ArrayLength[0]=i+1; // Number of rows
			
			// To get the number of columns by obtaining number of splits from "," before first ";"
			String mi1=input.substring(0, input.indexOf(";"));
			 	mi1=mi1.replaceAll(",", " ");
			    String[] aray = mi1.split("\\s+");
			    ArrayLength[1]= aray.length;
			    mi1=mi1.replaceAll("\\s+", ",");

		}
		
		else {  // To find the length of array if no semicolon is there then it is an array
			Matcher m = Pattern.compile("\\[(.*)\\]").matcher(input.trim());
			while(m.find()) {
			    // Number of splits from "," is the length of array 		
			    mi=m.group(1).toString();
			    mi=mi.replaceAll(",", " ");
			    String[] aray = mi.split("\\s+");
			    ArrayLength[0]= aray.length;
			    mi=mi.replaceAll("\\s+", ",");
			}

		}
		
	}
	
	// If 
	public static void InputVariableCheck(String var1 , String var2) {
	
		// 
		if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"\\$\\&").matcher(Changing.InputOutputVariable).find() ) {
			
//			if(Pattern.compile("while\\(1\\) \\{").matcher(Changing.MainProgram).find()){
//				Changing.MainProgram=Changing.MainProgram+"\nwhile((Rcv & I2S0_IR) == 0);\n" +
//						"while((XmitR & I2S0_IR) == 0);\n" + 
//						"I2S0_W0_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
//						"I2S0_W1_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
//						"\n" + 
//						"recent_output[j]=output;\n" + 
//						"j = ((j + 1) % 100);\n";
//			}
//			else{
				Changing.MainProgram=Changing.MainProgram+"\n\n"+"while((XmitR & I2S0_IR) == 0);\n" + 
						"I2S0_W0_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"I2S0_W1_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"\n" + 
						"recent_output[j]="+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"j = ((j + 1) % 100);\n}";
//			}
//				Find.FindInitializevariable("recent_output", "Int16");
				Changing.GlobleVariable=Changing.GlobleVariable+"Int16 recent_output[100];\n";
				Find.FindInitializevariable("j=0", "Int16");
				Changing.Initializevariable=Changing.Initializevariable+"\n"+Directory.Initializevariable;
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var1.replaceAll(" ","").replaceAll("[()]", "")+"$", "");
			
		}
		else if (Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"\\$\\&").matcher(Changing.InputOutputVariable).find() ) {
			
//			if(Pattern.compile("while\\(1\\) \\{").matcher(Changing.MainProgram).find()){
//				Changing.MainProgram=Changing.MainProgram+"\nwhile((Rcv & I2S0_IR) == 0);\n" +
//						"while((XmitR & I2S0_IR) == 0);\n" + 
//						"I2S0_W0_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
//						"I2S0_W1_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
//						"\n" + 
//						"recent_output[j]=output;\n" + 
//						"j = ((j + 1) % 100);\n";
//			}
//			else{
				Changing.MainProgram=Changing.MainProgram+"\n\n"+"while((XmitR & I2S0_IR) == 0);\n" + 
						"I2S0_W0_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"I2S0_W1_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"\n" + 
						"recent_output[j]="+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"j = ((j + 1) % 100);\n}";
//			}
//				Find.FindInitializevariable("recent_output", "Int16");
				Changing.GlobleVariable=Changing.GlobleVariable+"Int16 recent_output[100];\n";
				Find.FindInitializevariable("j=0", "Int16");
				Changing.Initializevariable=Changing.Initializevariable+"\n"+Directory.Initializevariable;
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var2.replaceAll(" ","").replaceAll("[()]", "")+"$", "");
			
		}
		
		else if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"\\$").matcher(Changing.InputOutputVariable).find() ) {
			
			if(Pattern.compile("while\\(1\\) \\{").matcher(Changing.MainProgram).find()){
				Changing.MainProgram=Changing.MainProgram+"while((Rcv & I2S0_IR) == 0);\n" + 
						var1.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W0_MSW_R;\n" + 
						var1.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W1_MSW_R;\n";
			}
			else{
				Changing.MainProgram=Changing.MainProgram+"while(1) {\n" +
						"while((Rcv & I2S0_IR) == 0);\n" + 
						var1.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W0_MSW_R;\n" + 
						var1.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W1_MSW_R;\n";
			}
//			Find.FindInitializevariable(var1.replaceAll(" ","").replaceAll("[()]", ""), "Int16");
			Changing.HeaderFile=Changing.HeaderFile+"\n"+Directory.HeaderFile;	
			Changing.GlobleVariable = Changing.GlobleVariable +"\n"+Directory.GlobleVariable+ "\n" + "void transmit(Int16 a);";
			Changing.Function=Changing.Function + "\n" +Directory.Function;
			Changing.MainProgram = "AIC3204_config(freq_48);" + "\n" + Changing.MainProgram;
			Changing.GlobleVariable=Changing.GlobleVariable+"\n"+"Int16 "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n";
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var1.replaceAll(" ","").replaceAll("[()]", "")+"\\$", "");
			
		}
		else if (Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"\\$").matcher(Changing.InputOutputVariable).find() ) {
			
			if(Pattern.compile("while\\(1\\) \\{").matcher(Changing.MainProgram).find()){
				Changing.MainProgram=Changing.MainProgram+"while((Rcv & I2S0_IR) == 0);\n" + 
						var2.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W0_MSW_R;\n" + 
						var2.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W1_MSW_R;\n";
			}
			else{
				Changing.MainProgram=Changing.MainProgram+"while(1) {\n" +
						"while((Rcv & I2S0_IR) == 0);\n" + 
						var2.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W0_MSW_R;\n" + 
						var2.replaceAll(" ","").replaceAll("[()]", "")+" = I2S0_W1_MSW_R;\n";
			}
//			Find.FindInitializevariable(var2.replaceAll(" ","").replaceAll("[()]", ""), "Int16");
			Changing.HeaderFile=Changing.HeaderFile+"\n"+Directory.HeaderFile;
			Changing.GlobleVariable = Changing.GlobleVariable +"\n"+Directory.GlobleVariable+ "\n" + "void transmit(Int16 a);";
			Changing.Function=Changing.Function + "\n" +Directory.Function;
			Changing.MainProgram = "AIC3204_config(freq_48);" + "\n" + Changing.MainProgram;
			Changing.GlobleVariable=Changing.GlobleVariable+"\n"+"Int16 "+var2.replaceAll(" ","").replaceAll("[()]", "")+";\n";
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var2.replaceAll(" ","").replaceAll("[()]", "")+"\\$", "");
			
		}
		//System.out.println(Changing.InputOutputVariable);
	}
	
	public static String CreateVariable(String input ,String variable) {
		String out = "";
		if(variable=="variablevalue"){
			int q;
			if(!Pattern.compile("("+input+"\\,|"+input+"\\;)").matcher(Changing.variablevalue).find()){
				out=input;
				
			}
			else {
				q=0;
			//	System.out.println(Changing.Initializevariable);
				do{
					
					out=input;
					out = out+String.valueOf(q);
					q++;
				//	System.out.println(i);
				}while(Pattern.compile(input+"\\,").matcher(Changing.variablevalue).find() && Pattern.compile(input+"\\;").matcher(Changing.Initializevariable).find());
			}
			Find.FindInitializevariable(out, "Int16");
			Changing.variablevalue=Changing.variablevalue+out+"$";
		}
		
		if(variable=="matrixVariable"){
			int q;
		//	System.out.println(Changing.matrixVariable);
		//	System.out.println(input);
			if(!Pattern.compile("\\$"+input+"\\[").matcher(Changing.matrixVariable).find()){
				out=input;
				
			}
			else {
				q=0;
		//		System.out.println(Changing.matrixVariable);
				do{
					
					out=input;
					out = out+String.valueOf(q);
					q++;
				//	System.out.println(out);
				}while(Pattern.compile("\\$"+out+"\\[").matcher(Changing.matrixVariable).find());
			}
			
		}
		//System.out.println(out);
		return out;
	}

}
