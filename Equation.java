package Main;

import java.util.regex.Pattern;

public class Equation {
	
	public static String AnsReal="";
	public static String AnsTemp="";  
	public static String tempvariable="$"; 
	public static String TempEquation= "";

	public static void matrix(String input) {
		// TODO Auto-generated method stub
		Equation.TempEquation="";
		Equation.AnsTemp="";
		String A;
		
		// while variable = variable/number are not remaining
		while(!Pattern.compile("(\\w*)(\\=*)(\\w*)").matcher(input.replaceAll("\\.", "")).matches()){
			int lastindex = 0;
			int index = 0;
			String smallequation = null;
			//Check the bracket in equation if find position of last bracket
			while(input.indexOf('(', lastindex)!=-1){
				index=input.indexOf('(', lastindex);
				lastindex++;
			}
			//Separate the equation which is in bracket and create small equation
			if(input.indexOf(')', lastindex)!=-1) {
				lastindex=input.indexOf(')', lastindex);
				smallequation= input.substring(index, lastindex+1 ) ;
				A = smallequation;
			}
			//if no bracket are present small equation
			else {
				A = input;
				smallequation=(A.split("[\\=]"))[1];
				A = smallequation;
			}
			
			//while small Equation is not equal to variable/number
			while(!Pattern.compile("(\\w*)").matcher(smallequation.replaceAll("[()]", "").replaceAll("\\.", "")).matches()){
				
				// .^ (dot power) are come in small Equation 
				if(smallequation.indexOf(".^")!=-1){
					//Separate two variable in var1 and var2
					String[] var = smallequation.split("[\\/]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var1 = vartemp1[vartemp1.length-1];	
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					smallequation=smallequation.replaceAll(var1+"[\\/]"+var2, "A");
					A=A.replaceAll("[\\.]", "[\\.]");
					A=A.replaceAll("[\\^]", "[\\^]");		
				}
				
				// ^ (power) are come in small Equation 
				else if(smallequation.indexOf("^")!=-1){
					String[] var = smallequation.split("[\\/]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					smallequation=smallequation.replaceAll(var1+"[\\/]"+var2, "A");
					A=A.replaceAll("[\\^]", "[\\^]");
				}
				
				// ./ (Dot Division) are come in small Equation 
				else if(smallequation.indexOf("./")!=-1){
					String[] var = smallequation.split("[\\/]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\.]", "[\\.]");
					A=A.replaceAll("[\\/]", "[\\/]");
					
					// Variable one & variable two both are matrix
					if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
						EquationSolver.TWOMatrixAddMulSubDiv(var1, var2, "/");
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable one (var1) is matrix variable in two variables
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){

						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp.split("=")[1]);
						
					}
					
					// Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){

						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp.split("=")[1]);
						
					}
					
					// No matrix variable in two variables
					else if((Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())){
						
						EquationSolver.TwoVarORNumber(var1, var2, "/");
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp);
					}
					
					// variable two is matrix variable and first is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
					}
					
					// variable one is matrix variable and second is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()) {
						
					}
					
					//no matrix variable in two variable only at list one input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
							&&(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())) {
						
						Find.InputVariableCheck(var1,var2);
						RealTimeEquation.VariableOne(var1, var2, "/");
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp);
						
					}
				}
				
				// / (Division) are come in small Equation 
				else if(smallequation.indexOf("/")!=-1){
					String[] var = smallequation.split("[\\/]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\/]", "[\\/]");
					
					// variable one & variable two both are matrix
					if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
						// TODO Add Function for division of two matrix	
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable one (var1) is matrix variable in two variables
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
						
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					// Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
						
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// No matrix variable in two variables
					else if((Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())){
						
						EquationSolver.TwoVarORNumber(var1, var2, "/");
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp);
					}
					
					// variable two is matrix variable and first is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
					}
					
					// variable one is matrix variable and second is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()) {
						
					}
					
					//no matrix variable in two variable only at list one input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
							&&(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())) {
						
						Find.InputVariableCheck(var1,var2);
						RealTimeEquation.VariableOne(var1, var2, "/");
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp);		
					}
				}
				
				// .\ are come in small Equation 
			/*	else if(smallequation.indexOf(".\")!=-1){
					String[] var = smallequation.split("[\\/]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					var2 = vartemp2[0];
					smallequation=smallequation.replaceAll(var1+"[\\/]"+var2, "A");
					A=A.replaceAll("[\\.]", "[\\.]");
					A=A.replaceAll("[\\/]", "[\\/]");	
				}*/
				
				// .* (Dot Multiplication) are come in small Equation 
				else if(smallequation.indexOf(".*")!=-1) {
					String[] var = smallequation.split("[\\*]");
					//find variable one
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var1 = vartemp1[vartemp1.length-1].replaceAll("[\\.]", "");
					//find variable two
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\.]", "[\\.]");
					A=A.replaceAll("[\\*]", "[\\*]");
					
					// variable one & variable two both are matrix
					if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
						EquationSolver.TWOMatrixAddMulSubDiv(var1, var2, "*");
						smallequation=smallequation.replaceFirst((var1+"[\\.\\*]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable one (var1) or Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()
							|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
						
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"*");
						smallequation=smallequation.replaceFirst((var1+"[\\.\\*]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// No matrix variable in two variables
					else if((Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())){
						
						EquationSolver.TwoVarORNumber(var1, var2, "*");
						smallequation=smallequation.replaceFirst((var1+"[\\*\\.]"+var2), Equation.AnsTemp);
					}
					
					// variable two is matrix variable and first is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
					}
					
					// variable one is matrix variable and second is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()) {
						
					}
					
					//no matrix variable in two variable only at list one input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
							&&(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())) {
						
						Find.InputVariableCheck(var1,var2);
						RealTimeEquation.VariableOne(var1, var2, "*");
						smallequation=smallequation.replaceFirst((var1+"[\\*]"+var2), Equation.AnsTemp);
							
					}
				}
				
				// .* (Multiplication) are come in small Equation 
				else if(smallequation.indexOf("*")!=-1) {
					String[] var = smallequation.split("[\\*]");
					//find variable one
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var1 = vartemp1[vartemp1.length-1];
					//find variable two
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\*]", "[\\*]");
					
					// variable one & variable two both are matrix
					if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
						EquationSolver.TwoMatrixMultiplication(var1, var2);
						smallequation=smallequation.replaceFirst((var1.replaceAll("[()]", "")+"[\\*]"+var2.replaceAll("[()]", "")), Equation.AnsTemp);
					}
					
					// Variable one (var1) or Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()
							|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()){
						
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"*");
						smallequation=smallequation.replaceFirst((var1+"[\\*]"+var2), Equation.AnsTemp);
					}
					
					// No matrix variable in two variables
					else if((Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
								&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
							|| (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
								&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).find())
							|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).find()
								&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
							|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
								&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())){
						
						EquationSolver.TwoVarORNumber(var1, var2, "*");
						smallequation=smallequation.replaceFirst((var1+"[\\*]"+var2), Equation.AnsTemp);

					}
					
					// variable two is matrix variable and first is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find() 
						&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
					}
					
					// variable one is matrix variable and second is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()) {
						
					}
					
					//no matrix variable in two variable only at list one input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
							&&(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())) {
						
						Find.InputVariableCheck(var1,var2);
						RealTimeEquation.VariableOne(var1, var2, "*");
						smallequation=smallequation.replaceFirst((var1+"[\\*]"+var2), Equation.AnsTemp);	
					}
				}
				else if(smallequation.indexOf("+")!=-1){
					String[] var = smallequation.split("[\\+]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\+]", "[\\+]");
					
					// variable one & variable two both are matrix
					if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
						EquationSolver.TWOMatrixAddMulSubDiv(var1.replaceAll("[()]", ""), var2.replaceAll("[()]", ""), "+");
						smallequation=smallequation.replaceFirst((var1+"[\\+]"+var2), Equation.AnsTemp);
					}
					
					// Variable one (var1) or Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()
							|| Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()){
						
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"+");
						smallequation=smallequation.replaceFirst((var1+"[\\+]"+var2), Equation.AnsTemp);
					}
					
					// No matrix variable in two variables
					else if((Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).matches())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).matches()
							&& Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).matches()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).matches())){
						
						EquationSolver.TwoVarORNumber(var1, var2, "+");
						smallequation=smallequation.replaceFirst((var1+"[\\+]"+var2), Equation.AnsTemp);
					}
					
					// variable two is matrix variable and first is input variable
					else if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find() 
							&&Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
					}
					
					// variable one is matrix variable and second is input variable
					else if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()) {
						
					}
					
					//no matrix variable in two variable only at list one input variable
					else if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
							&&(Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
									|| Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())) {
						
						Find.InputVariableCheck(var1,var2);
						RealTimeEquation.VariableOne(var1, var2, "+");
						smallequation=smallequation.replaceFirst((var1+"[\\+]"+var2), Equation.AnsTemp);	
					}
				}
				else if(smallequation.indexOf("-")!=-1){
					String[] var = smallequation.split("[\\-]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\-]", "[\\-]");
					
					// variable one & variable two both are matrix
					if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
						EquationSolver.TWOMatrixAddMulSubDiv(var1, var2, "-");
						smallequation=smallequation.replaceFirst((var1+"[\\-]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable one (var1) is matrix variable in two variables
					else if(Pattern.compile("$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d+)(\\])").matcher(Changing.matrixVariable).find()){
						
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"-");
						smallequation=smallequation.replaceFirst((var1+"[\\-]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile("$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d+)(\\])").matcher(Changing.matrixVariable).find()){
						
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"-");
						smallequation=smallequation.replaceFirst((var1+"[\\-]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// No matrix variable in two variables
					else if((Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())
						|| (Pattern.compile("").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())){
						
						EquationSolver.TwoVarORNumber(var1, var2, "-");
						smallequation=smallequation.replaceFirst((var1+"[\\-]"+var2), Equation.AnsTemp);
						if((Pattern.compile("-"+var2).matcher(smallequation)).matches()){
							
							smallequation=smallequation.replaceFirst(("[\\-]"+var2), var2+"_");
						}
					}
					
					// variable two is matrix variable and first is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
					}
					
					// variable one is matrix variable and second is input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()) {
						
					}
					
					//no matrix variable in two variable only at list one input variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
							&&(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find()
									|| Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
									|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())) {
						
						Find.InputVariableCheck(var1,var2);
						RealTimeEquation.VariableOne(var1, var2, "-");
						smallequation=smallequation.replaceFirst((var1+"[\\-]"+var2), Equation.AnsTemp);	
					}
				}
			}
			
			A=A.replaceAll("[\\(]", "[\\(]");
			A=A.replaceAll("[\\)]", "[\\)]");
			input=input.replaceFirst(A, smallequation.replaceAll("[()]", ""));
		}
		
			if(Equation.TempEquation!=""){
				
				if(Pattern.compile(Equation.AnsTemp).matcher(Equation.TempEquation).find()) {
					
					Changing.MainProgram= Changing.MainProgram+"\n"+Equation.TempEquation.replaceAll(Equation.AnsTemp, input.split("=")[0]);
					Changing.Initializevariable=Changing.Initializevariable.replaceAll(Equation.AnsTemp, input.split("=")[0]);
				}
				
				else{
					
					Changing.MainProgram= Changing.MainProgram+"\n"+input.split("=")[0]+"="+Equation.TempEquation+";";
					Changing.InputVariable=Changing.InputVariable+input.split("=")[0]+"$";
					Find.FindInitializevariable(input.split("=")[0], "Int16");
					Find.InputVariableCheck(input.split("=")[0],"varfxgggcb2$$$$$$$");
				}
			}
			
			else{
				
				if(Pattern.compile("\\d+"+"_").matcher((input.split("=")[1]).replaceAll("\\.", "")).matches()){
					
					Changing.MainProgram= Changing.MainProgram+"\n"+input.split("=")[0]+"= -"+(input.split("=")[1]).split("_")[0]+";";
					Find.FindInitializevariable(input.split("=")[0], "Int16");
					Changing.variablevalue=Changing.variablevalue+input+"$";
				}
				else if(Pattern.compile("\\d+").matcher((input.split("=")[1])).matches()){
					
					Changing.MainProgram= Changing.MainProgram+"\n"+input+";";
					Find.FindInitializevariable(input.split("=")[0], "Int16");
					Changing.variablevalue=Changing.variablevalue+input+"$";
				}
				else if(Pattern.compile("\\d+").matcher((input.split("=")[1]).replaceAll("\\.", "")).matches()){
					
					Changing.MainProgram= Changing.MainProgram+"\n"+input+";";
					Find.FindInitializevariable(input.split("=")[0], "float");
					Changing.variablevalue=Changing.variablevalue+input+"$";
				}
			}
	}
	
	
	
	
	
	public static void MatrixWithVariables(String input) {
		// TODO Auto-generated method stub

		int q = 0;
		if(Pattern.compile("(\\w*)"+"(\\[)(\\d*)(\\])"+"(\\[)(\\d*)(\\])").matcher(input).find()){
			
		}
		else if(Pattern.compile("(\\w*)"+"(\\[)(\\d*)(\\])").matcher(input).find()){
			String j;
			
			if(!Pattern.compile("(j,|j;)").matcher(Changing.Initializevariable).find()){
				j="j";
			}
			else {
				
				do{
					j="j";
					j = j+String.valueOf(q);
					q++;
				}while(Pattern.compile(j+"\\,").matcher(Changing.Initializevariable).find());
			}
			Find.FindInitializevariable(j+"=0", "Int16");
			String array = input.split("=")[1].replaceAll("(\\{|\\}|\\[|\\])","").replaceAll(" ", "");
				
			 if(Pattern.compile(input.split("\\[")[0]+"(\\[)(\\d*)(\\])"+"="+"(\\[)"+input.split("\\[")[0]).matcher(input).find()){
				 
				 Changing.MainProgram= Changing.MainProgram+"\n"+input.split("\\[")[0]+"["+j+"] = "+array.split(",")[1]+";\n"+j+"++;";
			 }
		}
	}
}
