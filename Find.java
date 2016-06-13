
/* 1. In this class the detected variables in scilab code are being initialized at Changing.Initializevariable. If the variables have
already been initialized then no changes are done. The variables are initialized with their data types specified. Data type of a variiable 
is modified if required Ex. int a; may change to float a; 

2. Array length and matrix length is also found out in this class.
 This class is called from other classes as and when a variable is detected. 
 
3. If user has selected real time mode and there are arithmetic operations on those

4. Creating new variables at the time of matrix multiplication operation */

package Main;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Find {
	public static int[] ArrayLength=new int[2];
	
	
	// 1. To initialize the variables detected
	public static void FindInitializevariable(String input ,String DataType) {
		
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
	
	
	// 2. To find the length of array or matrix Ex. a= [1,2 ; 2,1]
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
	
	//3.  If user has selected real time mode and there are arithmetic operation on those then InputVariableCheck is called.
	public static void InputVariableCheck(String var1 , String var2) {
	
		// If var1 is the output varaible specified by user in real time mode. 
		// It is saved in Changing.InputOutputVariable in form 
		// $input$output$&
		if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"\\$\\&").matcher(Changing.InputOutputVariable).find() ) {
			
				// Adding the default code ralated to output in required strings of Changing class
				Changing.MainProgram=Changing.MainProgram+"\n\n"+"while((XmitR & I2S0_IR) == 0);\n" + 
						"I2S0_W0_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"I2S0_W1_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"\n" + 
						"recent_output[j]="+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"j = ((j + 1) % 100);\n}";

				Changing.GlobleVariable=Changing.GlobleVariable+"Int16 recent_output[100];\n";
				Find.FindInitializevariable("j=0", "Int16");// to initialize j
				Changing.Initializevariable=Changing.Initializevariable+"\n"+Directory.Initializevariable;
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var1.replaceAll(" ","").replaceAll("[()]", "")+"$", "");
			
		}
		
		// If var2 is the output varaible specified by user in real time mode. 
		// It is saved in Changing.InputOutputVariable in form 
		// $input$output$&
		else if (Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"\\$\\&").matcher(Changing.InputOutputVariable).find() ) {
				
				// Adding the default code ralated to output in required strings of Changing class
				Changing.MainProgram=Changing.MainProgram+"\n\n"+"while((XmitR & I2S0_IR) == 0);\n" + 
						"I2S0_W0_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"I2S0_W1_MSW_W = "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"\n" + 
						"recent_output[j]="+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n" + 
						"j = ((j + 1) % 100);\n}";

				Changing.GlobleVariable=Changing.GlobleVariable+"Int16 recent_output[100];\n";
				Find.FindInitializevariable("j=0", "Int16");// to initialze j
				Changing.Initializevariable=Changing.Initializevariable+"\n"+Directory.Initializevariable;
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var2.replaceAll(" ","").replaceAll("[()]", "")+"$", "");
			
		}
		
		// If var1 is the input varaible specified by user in real time mode. 
		// It is saved in Changing.InputOutputVariable in form 
		// $input$output$&
		else if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"\\$").matcher(Changing.InputOutputVariable).find() ) {
			
			// Adding the default code ralated to input in required strings of Changing class
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
			
			Changing.HeaderFile=Changing.HeaderFile+"\n"+Directory.HeaderFile;	
			Changing.GlobleVariable = Changing.GlobleVariable +"\n"+Directory.GlobleVariable+ "\n" + "void transmit(Int16 a);";
			Changing.Function=Changing.Function + "\n" +Directory.Function;
			Changing.MainProgram = "AIC3204_config(freq_48);" + "\n" + Changing.MainProgram;
			Changing.GlobleVariable=Changing.GlobleVariable+"\n"+"Int16 "+var1.replaceAll(" ","").replaceAll("[()]", "")+";\n";
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var1.replaceAll(" ","").replaceAll("[()]", "")+"\\$", "");
			
		}
		
		// If var2 is the input varaible specified by user in real time mode. 
		// It is saved in Changing.InputOutputVariable in form 
		// $input$output$&
		else if (Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"\\$").matcher(Changing.InputOutputVariable).find() ) {
			
			// Adding the default code ralated to input in required strings of Changing class
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

			Changing.HeaderFile=Changing.HeaderFile+"\n"+Directory.HeaderFile;
			Changing.GlobleVariable = Changing.GlobleVariable +"\n"+Directory.GlobleVariable+ "\n" + "void transmit(Int16 a);";
			Changing.Function=Changing.Function + "\n" +Directory.Function;
			Changing.MainProgram = "AIC3204_config(freq_48);" + "\n" + Changing.MainProgram;
			Changing.GlobleVariable=Changing.GlobleVariable+"\n"+"Int16 "+var2.replaceAll(" ","").replaceAll("[()]", "")+";\n";
			Changing.InputOutputVariable=Changing.InputOutputVariable.replaceFirst(var2.replaceAll(" ","").replaceAll("[()]", "")+"\\$", "");
			
		}
		
	}
	
	
	// 
	// 4. For the purpose of matrix multiplication we require to create new variables i, j and k 
	// Also the multiplication answer should be saved on a new variable  Ex. Find.CreateVariable("ans", "matrixVariable");
	// It is being called from class EquationSolver Ex. Find.CreateVariable("i", "variablevalue");
	// If "i" is already present in Changing.variablevalue then append number to i and increment number if already used 
	// Ex. i0, i1.... which ever would be available then use the unused variable 
	public static String CreateVariable(String input ,String variable) {
		String out = "";
		if(variable=="variablevalue"){
			int q;
			
			// If input is not present in Changing.variablevalue
			if(!Pattern.compile("\\$"+input).matcher(Changing.variablevalue).find()){
				out=input;
				
			}
			else {
				q=0;
		
				do{
				
					out=input;
					out = out+String.valueOf(q);
					q++;
		
				}while(Pattern.compile("\\$"+input).matcher(Changing.variablevalue).find());
			}
			Find.FindInitializevariable(out, "Int16");
			Changing.variablevalue=Changing.variablevalue+out+"$";
		}
		
		// The multiplication answer should be saved on a new variable  
		// For that purpose we have to create a new un used variable 
		// It is called as Ex. Find.CreateVariable("ans", "matrixVariable");
		// If "ans" is already present in Changing.matrixVariable then append number to ans and increment number if already used 
		// Ex. ans0, ans1.... which ever would be available then use the unused variable 
		if(variable=="matrixVariable"){
			int q;
			
			// If input is not present in Changing.matrixVariable
			if(!Pattern.compile("\\$"+input+"\\[").matcher(Changing.matrixVariable).find()){
				out=input;
				
			}
			else {
				q=0;
		
				do{
					
					out=input;
					out = out+String.valueOf(q);
					q++;
		
				}while(Pattern.compile("\\$"+out+"\\[").matcher(Changing.matrixVariable).find());
			}
			
		}
	
		return out;
	}

}
