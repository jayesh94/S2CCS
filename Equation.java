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
		
		// while variable = variable or number are not remaining
		while(!Pattern.compile("(\\w*)(\\=*)(\\w*)").matcher(input.replaceAll("\\.", "")).matches()){
			int lastindex = 0;
			int index = 0;
			String smallequation = null;
			
			// Separating out the bracket to be solved first and saving in smallequation
			// Ex. 12+(a-(2+var))
			// smallequation = 2+var
			//Check if bracket "(" is present in equation, if true then find position of last "(" bracket
			while(input.indexOf('(', lastindex)!=-1){
				index=input.indexOf('(', lastindex);
				lastindex++;
			}
			//Find position or index of ")" Separate the equation which is in brackets and create small equation
			if(input.indexOf(')', lastindex)!=-1) {
				lastindex=input.indexOf(')', lastindex);
				smallequation= input.substring(index, lastindex+1 ) ; 
				A = smallequation; //small equation stored in temp variable 'A' 
			}
			//if no bracket are present small equation
			//i.e input= 2+var then small equation = 2+var
			else {
				A = input;
				smallequation=(A.split("[\\=]"))[1];
				A = smallequation;
			}
			//in this loop equation are represented according to c language 
			//while smallequation is not equal to variable or number
			while(!Pattern.compile("(\\w*)").matcher(smallequation.replaceAll("[()]", "").replaceAll("\\.", "")).matches()){
				
				// .^ (dot power) comes in small Equation 
				// Ex. input= c+(b-(a.^2))
				//     small equation= a.^2
				//   In below loop the small equation is split from ".^" and "a" and "2" is obtained 
				//    and sent to the dot power method to convert in c code which is yet to be done
				// TODO
				if(smallequation.indexOf(".^")!=-1){
					
					String[] var = smallequation.split("[\\^]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var1 = vartemp1[vartemp1.length-1];	
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					smallequation=smallequation.replaceAll(var1+"[\\/]"+var2, "A");
					A=A.replaceAll("[\\.]", "[\\.]");
					A=A.replaceAll("[\\^]", "[\\^]");		
				}
				
				// ^ (power) are come in small Equation
				// Ex. input= c+(b-(a^2))
				//     small equation= a^2
				//   In below loop the small equation is split from "^" and "a" and "2" is obtained 
				//    and sent to the dot power method to convert in c code which is yet to be done
				// TODO
				else if(smallequation.indexOf("^")!=-1){
					String[] var = smallequation.split("[\\^]");
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					smallequation=smallequation.replaceAll(var1+"[\\/]"+var2, "A");
					A=A.replaceAll("[\\^]", "[\\^]");
				}
				
				// ./ (Dot Division) are come in small Equation 
				// Ex. input= c+(b-(a./2))
				//     small equation= a./2
				//   In below loop the small equation is split from "./" and "a" and "2" is obtained 
				//    and sent to the dot Division method to convert in c code which is yet to be done
				// TODO
				else if(smallequation.indexOf("./")!=-1){
					String[] var = smallequation.split("[\\/]"); //small equation are split by / and creat two variable
											//ex. if small equation is a+v./55 then creat a+v and 55
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:\\*]");   
					String var1 = vartemp1[vartemp1.length-1];  // stored 'v' in var1
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];  // stored '55' in var2
					A=A.replaceAll("[\\.]", "[\\.]");
					A=A.replaceAll("[\\/]", "[\\/]");
					
					// Variable one & variable two both are matrix
					if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						// var1, var2 and division sign is sent to EquationSolver.TWOMatrixAddMulSubDiv for dividing both matrices in c representation
						EquationSolver.TWOMatrixAddMulSubDiv(var1, var2, "/");
						//when matrix division is representated according to c then ans of that division replaces var1 and var2 arithmetic in small equation
						//Ex. small equation =a+f./55 then it changes to a+ans 
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable one (var1) is matrix variable among two variables var1 & var2
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
						// var1, var2 and division sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for dividing matrices and number or variable in c representation
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						//when matrix division is representated according to c then ans of that division replaces var1 and var2 arithmetic in small equation
						//Ex. small equation =a+f./55 then it changes to a+ans 
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp.split("=")[1]);
						
					}
					
					// Variable two (var2) is matrix variable among two variables var1 & var2
					else if(Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
						// var1, var2 and division sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for dividing matrices and number or variable in c representation
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						//when matrix division is representated according to c then ans of that division replaces var1 and var2 arithmetic in small equation
						//Ex. small equation =a+f./55 then it changes to a+ans 
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp.split("=")[1]);
						
					}
					
					// No matrix variable in both variables var1 and var2
					
					//var1 is present in Changing.variablevalue and var2 is present in Changing.variablevalue
					//or var1 is present in Changing.variablevalue and var2 is any number like '2'
					//or var2 is present in Changing.variablevalue and var1 is any number like '2'
					//or var1 and var2 both are number
					else if((Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())){
						// var1, var2 and division sign is sent to EquationSolver.TwoVarORNumber for dividing  two numbers or variables in c representation
						EquationSolver.TwoVarORNumber(var1, var2, "/");
						//when matrix division is representated according to c then ans of that division replaces var1 and var2 arithmetic in small equation
						//Ex. small equation =a+f./55 then it changes to a+ans 
						smallequation=smallequation.replaceFirst((var1+"[\\.\\/]"+var2), Equation.AnsTemp);
					}
					
					// variable var2 is matrix variable and var1 is input variable i.e real time variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.InputVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						// TODO
					}
					
					// variable var1 is matrix variable and var2 is input variable i.e real time variable
					else if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()) {
						//TODO
					}
					
					//no matrix variable among the two variables, at least one input variable i.e real time variable
				
					// var1 is present in Changing.InputVariable or var2 is present in Changing.InputVariable
					// and var1 is present in Changing.InputVariable or var1 is any number like '2'
					// or var1 is present in Changing.variablevalue or var2 is present in Changing.InputVariable
					// or var2 is any number like '2' or var2 is present in Changing.variablevalue
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
						//when matrix division is representated according to c then ans of that division replaces var1 and var2 arithmetic in small equation
						//Ex. small equation =a+f./55 then it changes to a+ans 
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp);
						
					}
				}
				
				//     "/" (Division) comes in small Equation 
				// 	Ex. input= c+(b-(a/2))
				//     small equation= a./2
				//     In below loop the small equation is split from "/" and "a" and "2" is obtained 
				//    and sent to the Division method to convert in c code which is yet to be done
				else if(smallequation.indexOf("/")!=-1){
					String[] var = smallequation.split("[\\/]");//small equation are split by / and create two variable
											//ex. if small equation is a+v/55 then create a+v and 55
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
					
					// Variable one (var1) is matrix variable among two variables
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
						
						// var1, var2 and division sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for dividing matrices and number or variable in c representation
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						//when division is representated according to c then ans of that division replaces var1 and var2 arithmetic in small equation
						//Ex. small equation =a+f/55 then it changes to a+ans 
						smallequation=smallequation.replaceFirst((var1+"[\\/]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable two (var2) is matrix variable among two variables
					else if(Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
						
						// var1, var2 and division sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for dviding matrices and number or variable in c representation
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"/");
						//when division is representated according to c then ans of that division replaces var1 and var2 arithmetic in small equation
						//Ex. small equation =a+f/55 then it changes to a+ans 
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
					//when divigen are representated according to c then ans of that divigen put into small equation and remove var1 and vae2 
						//small equation =a+f/55 then it changes to a+ans 
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
					
					//no matrix variable among the two variables, at least one input variable i.e real time variable
				
					// var1 is present in Changing.InputVariable or var2 is present in Changing.InputVariable
					// and var1 is present in Changing.InputVariable or var1 is any number like '2'
					// or var1 is present in Changing.variablevalue or var2 is present in Changing.InputVariable
					// or var2 is any number like '2' or var2 is present in Changing.variablevalue
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
					//when divigen are representated according to c then ans of that divigen put into small equation and remove var1 and vae2 
						//small equation =a+f/55 then it changes to a+ans 
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
				// Ex. input= c+(b-(a.*2))
				//     small equation= a./2
				//   In below loop the small equation is split from ".*" and "a" and "2" is obtained 
				//    and sent to the Dot Multiplication method to convert in c code which is yet to be done
				else if(smallequation.indexOf(".*")!=-1) {
					String[] var = smallequation.split("[\\*]"); //small equation are split by .* and creat two variable
											//ex. if small equation is a+v.*55 then creat a+v and 55
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
						// var1, var2 and Multiplication sign is sent to EquationSolver.TWOMatrixAddMulSubDiv for Multiplication of both matrices in c representation
						EquationSolver.TWOMatrixAddMulSubDiv(var1, var2, "*");
						//when both matrix Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
						//small equation =a+f.*55 then it changes to a+ans 
						smallequation=smallequation.replaceFirst((var1+"[\\.\\*]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable one (var1) or Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()
							|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.Initializevariable).find()){
					// var1, var2 and Multiplication sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for Multiplication matrices and number or variable in c representation
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"*");
						//when both matrix Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
						//small equation =a+f.*55 then it changes to a+ans
						smallequation=smallequation.replaceFirst((var1+"[\\.\\*]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// No matrix variable in both variables var1 and var2
					
					//var1 is present in Changing.variablevalue and var2 is present in Changing.variablevalue
					//or var1 is present in Changing.variablevalue and var2 is any number like '2'
					//or var2 is present in Changing.variablevalue and var1 is any number like '2'
					//or var1 and var2 both are number
					else if((Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")).matcher(Changing.variablevalue).find())
						|| (Pattern.compile("(\\d+)").matcher(var1.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find()
							&& Pattern.compile("(\\d+)").matcher(var2.replaceAll("\\.","").replaceAll(" ","").replaceAll("[()]", "")).find())){
						
						EquationSolver.TwoVarORNumber(var1, var2, "*");
					//when both matrix Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
					//small equation =a+f.*55 then it changes to a+ans
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
					//when both matrix Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
					//small equation =a+f.*55 then it changes to a+ans
						smallequation=smallequation.replaceFirst((var1+"[\\*]"+var2), Equation.AnsTemp);
							
					}
				}
				
				// * (Multiplication) are come in small Equation
				// Ex. input= c+(b-(a*2))
				//     small equation= a./2
				//   In below loop the small equation is split from "*" and "a" and "2" is obtained 
				//    and sent to the Multiplication method to convert in c code which is yet to be done
				else if(smallequation.indexOf("*")!=-1) {  
					String[] var = smallequation.split("[\\*]"); //small equation are split by * and creat two variable
											//ex. if small equation is a+v*55 then creat a+v and 55
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
						
						// var1, var2 and Multiplication sign is sent to EquationSolver.TWOMatrixAddMulSubDiv for Multiplication of both matrices in c representation
						EquationSolver.TwoMatrixMultiplication(var1, var2);
						//when both matrix Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
						//small equation =a+f*55 then it changes to a+ans
						smallequation=smallequation.replaceFirst((var1.replaceAll("[()]", "")+"[\\*]"+var2.replaceAll("[()]", "")), Equation.AnsTemp);
					}
					
					// Variable one (var1) or Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()
						|| Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()){
						
						// var1, var2 and Multiplication sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for Multiplication matrices and number or variable in c representation
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"*");
						//when both matrix Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
						//small equation =a+f*55 then it changes to a+ans
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
						//when Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
						//small equation =a+f*55 then it changes to a+ans
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
						//when Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
						//small equation =a+f*55 then it changes to a+ans
						smallequation=smallequation.replaceFirst((var1+"[\\*]"+var2), Equation.AnsTemp);	
					}
				}
				
				// + (Addition) are come in small Equation 
				// Ex. input= c+(b-(a+2))
				//     small equation= a./2
				//   In below loop the small equation is split from "+" and "a" and "2" is obtained 
				//    and sent to the Addition method to convert in c code which is yet to be done
				else if(smallequation.indexOf("+")!=-1){
					String[] var = smallequation.split("[\\+]"); //small equation are split by + and creat two variable
											//ex. if small equation is a+v+55 then creat a+v and 55
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\+]", "[\\+]");
					
					// variable one & variable two both are matrix
					if (Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
					    &&Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
						
						// var1, var2 and Addition sign is sent to EquationSolver.TWOMatrixAddMulSubDiv for Addition of both matrices in c representation
						EquationSolver.TWOMatrixAddMulSubDiv(var1.replaceAll("[()]", ""), var2.replaceAll("[()]", ""), "+");
						//when both matrix Multiplication are representated according to c then ans of that Multiplication put into small equation and remove var1 and vae2 
						//small equation =a+f.*55 then it changes to a+ans
						smallequation=smallequation.replaceFirst((var1+"[\\+]"+var2), Equation.AnsTemp);
					}
					
					// Variable one (var1) or Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()
						|| Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()){
					
						// var1, var2 and Addition sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for Addition matrices and number or variable in c representation
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
				
				// - (subtraction) are come in small Equation 
				// Ex. input= c+(b-(a-2))
				//     small equation= a./2
				//   In below loop the small equation is split from "-" and "a" and "2" is obtained 
				//    and sent to the subtraction method to convert in c code which is yet to be done
				else if(smallequation.indexOf("-")!=-1){
					String[] var = smallequation.split("[\\-]");  //small equation are split by - and creat two variable
											//ex. if small equation is a+v-55 then creat a+v and 55
					String[] vartemp1 = var[0].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var1 = vartemp1[vartemp1.length-1];
					String[] vartemp2 = var[1].split("[\\+\\-\\/\\^\\~\\=\\:]");
					String var2 = vartemp2[0];
					A=A.replaceAll("[\\-]", "[\\-]");
					
					// variable one & variable two both are matrix
					if (Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find() 
							&&Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
					// var1, var2 and subtraction sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for subtraction of both matrices in c representation	
						EquationSolver.TWOMatrixAddMulSubDiv(var1, var2, "-");
						smallequation=smallequation.replaceFirst((var1+"[\\-]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable one (var1) is matrix variable in two variables
					else if(Pattern.compile("$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d+)(\\])").matcher(Changing.matrixVariable).find()){
					
					// var1, var2 and subtraction sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for subtraction  of matrices and number or variable in c representation
						EquationSolver.OneMatrixAddMulVAR1SubVAR1Div(var1, var2,"-");
						smallequation=smallequation.replaceFirst((var1+"[\\-]"+var2), Equation.AnsTemp.split("=")[1]);
					}
					
					// Variable two (var2) is matrix variable in two variables
					else if(Pattern.compile("$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d+)(\\])").matcher(Changing.matrixVariable).find()){
						// var1, var2 and subtraction sign is sent to EquationSolver.OneMatrixAddMulVAR1SubVAR1Div for subtraction  of matrices and number or variable in c representation
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
			// Equation.TempEquation are not null
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
			// in equation real time variable are not present that time Equation.TempEquation are null
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
