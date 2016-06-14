package Main;

import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class EquationSolver {
	// for multiplication of two matrix
	public static void TwoMatrixMultiplication(String var1 , String var2) {
		
		String var11 = null;
		String var22 = null;
		//finding the size of both matrix variable 
		//hence we first find the index of that var1 in the matrixvariable and crop it and stor in the var11 
		int index = (Changing.matrixVariable.indexOf("$"+var1.replaceAll("[()]", "")+"["))+1;
		var11 = Changing.matrixVariable.substring(index, Changing.matrixVariable.indexOf("$",index)) ;
		//if the array are in var1 then it stor like (a[2]={5 5} ) but we required in matrix form (a[1][2]={5 5} )
		if (!Pattern.compile(var1.replaceAll("[()]", "")+"(\\[)(\\d*)(\\])"+"(\\[)(\\d*)(\\])").matcher(var11).find()) {
			var11=var11.split("[\\=\\[\\]]")[0]+"[1]"+"["+var11.split("[\\=\\[\\]]")[1]+"]"+"="+var11.split("[\\=\\[\\]]")[3];
		}
	    //then find the index of that var2 in the matrixvariable and crop it and stor in the var22 
	    index=(Changing.matrixVariable.indexOf("$"+var2.replaceAll("[()]", "")+"["))+1;
	    var22 = Changing.matrixVariable.substring(index, Changing.matrixVariable.indexOf("$",index)) ;
	    //if the array are in var2 then it stor like (a[2]={5 5} ) but we required in matrix form (a[1][2]={5 5} )
	    if (!Pattern.compile(var2.replaceAll("[()]", "")+"(\\[)(\\d*)(\\])"+"(\\[)(\\d*)(\\])").matcher(var22).find()) {
	    	var22=var22.split("[\\=\\[\\]]")[0]+"[1]"+"["+var22.split("[\\=\\[\\]]")[1]+"]"+"="+var22.split("[\\=\\[\\]]")[3];
		}
	    //size of matrix which is in var1 and var2 are stored in mANDn1 and mANDn2
	    String[] mANDn1 = (var11).split("[\\[\\]]"); //in mANDn1 a[1][2] are split and at mANDn1[1]  mANDn1[3] are created
	    String[] mANDn2 = (var22).split("[\\[\\]]");  //in mANDn2 a[1][2] are split and at mANDn2[1]  mANDn2[3] are created
	    
	    //if matrix size are not match
	    if(Integer.parseInt(mANDn1[3]) != Integer.parseInt(mANDn2[1])) {
	    	//TODO error 
	    }
	    	//create a temp variable for storing the ans of two matrix mulyiplication 
		String ans = Find.CreateVariable("ans", "matrixVariable");
		Find.FindInitializevariable(ans+"["+mANDn1[1]+"]"+"["+mANDn2[3]+"]", "Int16"); //define the size of temp variable
		
		Changing.matrixVariable=Changing.matrixVariable+ans+"["+mANDn1[1]+"]"+"["+mANDn2[3]+"]"+"$"; //stor the temp veriable in Changing.matrixVariable with its size
		Equation.AnsTemp=ans; // after two matrix multiplication it is stored into a variable which is stored to AnsTemp of equation class.
		String i = Find.CreateVariable("i", "variablevalue"); //create a local variable i for matrix multiplication (for creation of for loop)
		String j = Find.CreateVariable("j", "variablevalue"); //create a local variable j for matrix multiplication (for creation of for loop)
		String k = Find.CreateVariable("k", "variablevalue"); //create a local variable k for matrix multiplication (for creation of for loop)
		
		var1=var1.replaceAll("[()]", ""); //remove the bracket from var1
		var2=var2.replaceAll("[()]", ""); //remove the bracket from var2
		//change the equation representation according to c and stored into the Equation.TempEquation
		Equation.TempEquation=Equation.TempEquation + "for ("+i+" = 0; "+i+" < "+ mANDn1[1] +"; "+i+"++)\n{\n" +
				"for ("+j+" = 0; "+j+" < "+ mANDn2[3] +"; "+j+"++)\n{\n" +
						ans+"["+i+"]["+j+"] = 0;\n" +
								"for ("+k+" = 0; "+k+" < " + mANDn2[1] + "; "+k+"++)\n{\n" +
										ans+"["+i+"]["+j+"] = "+ans+"["+i+"]["+j+"]+"+ var1+"["+i+"]["+k+"] *"+var2+"["+k+"]["+j+"];\n}\n}\n}";
	}
	
	
	public static void TWOMatrixAddMulSubDiv(String var1 , String var2, String Operator) {
		//TODO two matrix add .mul .div sub
	}
	
	
	public static void OneMatrixAddMulVAR1SubVAR1Div(String var1 , String var2 , String Operator) {
		
		String var11 = null;
		String var22 = null;
		int index;
		String bracket1="";
		String bracket2="";
		//removing bracket from variable and store in another temp variable like bracket1 and bracket2
		if(var1.contains("(")) {
			var1=var1.replaceAll("[()]", "");
			bracket1="(";
		}
		if(var2.contains("(")) {
			var2=var2.replaceAll("[()]", "");
			bracket2="(";
		}
		if(var1.contains(")")) {
			var1=var1.replaceAll("[()]", "");
			bracket1=")";
		}
		if(var2.contains(")")) {
			var2=var2.replaceAll("[()]", "");
			bracket2=")";
		}
		
		if(Pattern.compile("\\$"+Equation.AnsTemp+"(\\[)(\\d+)(\\])").matcher(Changing.matrixVariable).find()
				&& (Pattern.compile(Equation.AnsTemp).matcher(var1.replaceAll(" ","").replaceAll("[()]", "")).matches()
						||Pattern.compile(Equation.AnsTemp).matcher(var2.replaceAll(" ","").replaceAll("[()]", "")).matches())) {
			
			index=Equation.TempEquation.lastIndexOf("{");  //find the last for loop index which present in to the Equation.TempEquation
			//find size of the temp ans variable which created for other calculation 
			String[] mANDn = ((Equation.TempEquation.substring(index, Equation.TempEquation.indexOf(";",index))).split("=")[0]).split("[\\[\\]]") ; 
			
			//Variable 1 are matrix variable
			if(Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
				//in Equation.TempEquation split all lne seperately  and stored into the mul
				String[] mul=Equation.TempEquation.split("[\\n]");
				int length=mul.length; //find no of line 
				//ex. a=array1+array2
				// code in TempEquation
				//for (i = 0; i < m; i++)
				//{
				//for (j = 0; j < n; j++)
				//{
				//ans[i][j] = array1[i][j] + array2[i][j];
				//}
				//}
				
				//ans[i][j] = array1[i][j] + array2[i][j] is not available at 
				if(Pattern.compile("(\\W*)").matcher(mul[length-3]).matches()){
					
					mul[length-3]=mul[length-3]+"\n"+var1+"["+mANDn[1]+"]"+"["+mANDn[3]+"]"+"="+bracket1+var1+"["+mANDn[1]+"]"+"["+mANDn[3]+"]"+Operator+var2+bracket2+";";
				}
				//array0[i][j]=array0[j][i] are available
				else{
					
					mul[length-3]=mul[length-3].split(";")[0];
					mul[length-3]=mul[length-3]+Operator+var2+bracket2+";";
				}
				
				Equation.TempEquation="";
				
				for (String temp:mul) {
					Equation.TempEquation=Equation.TempEquation+temp+"\n";
				}
			}
			
			//Variable 2 are matrix variable
			if(Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
				
				String[] mul=Equation.TempEquation.split("[\\n]");
				int length=mul.length;
				
				if(Pattern.compile("(\\W*)").matcher(mul[length-3]).matches()){
					mul[length-3]=mul[length-3]+"\n"+var1+"["+mANDn[1]+"]"+"["+mANDn[3]+"]"+"="+bracket1+var1+"["+mANDn[1]+"]"+"["+mANDn[3]+"]"+Operator+var2+bracket2+";";
				}
				
				else{
					mul[length-3]=mul[length-3].split("=")[0]+"="+bracket1+var1+Operator+mul[length-3].split("=")[1];
				}
				
				Equation.TempEquation="";
				for (String temp:mul) {
					Equation.TempEquation=Equation.TempEquation+temp+"\n";
				}
			}
		}
		
		//for loop are not created in Equation.TempEquation
		else {
			
			if(Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])").matcher(Changing.matrixVariable).find()) {
				index = (Changing.matrixVariable.indexOf("$"+var1.replaceAll("[()]", "")+"["))+1;
				var11 = Changing.matrixVariable.substring(index, Changing.matrixVariable.indexOf("$",index)) ;
				
				if (!Pattern.compile(var1.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])"+"(\\[)(\\d*)(\\])").matcher(var11).find()) {
					
					var11=var11.split("[\\=\\[\\]]")[0]+"[1]"+"["+var11.split("[\\=\\[\\]]")[1]+"]"+"="+var11.split("[\\=\\[\\]]")[3];
				}
				
				String[] mANDn1 = (var11).split("[\\[\\]]");
				
				String ans = Find.CreateVariable("ans", "matrixVariable");
				Find.FindInitializevariable(ans+"["+mANDn1[1]+"]"+"["+mANDn1[3]+"]", "Int16");
				
				Changing.matrixVariable=Changing.matrixVariable+ans+"["+mANDn1[1]+"]"+"["+mANDn1[3]+"]"+"$";
				Equation.AnsTemp=ans;
				String i = Find.CreateVariable("i", "variablevalue");
				String j = Find.CreateVariable("j", "variablevalue");
				
				Equation.TempEquation=Equation.TempEquation+"\n" + "for ("+i+" = 0; "+i+" < "+ mANDn1[1] +"; "+i+"++)\n{\n" +
						"for ("+j+" = 0; "+j+" < "+ mANDn1[3] +"; "+j+"++)\n{\n" +
									ans+"["+i+"]["+j+"] = "+bracket1+var1+"["+i+"]["+j+"]"+Operator+var2+bracket2+";\n}\n}";
			}
			
			else {
				index = (Changing.matrixVariable.indexOf("$"+var2.replaceAll("[()]", "")+"["))+1;
				var22 = Changing.matrixVariable.substring(index, Changing.matrixVariable.indexOf("$",index)) ;
				
				if (!Pattern.compile(var2.replaceAll(" ","").replaceAll("[()]", "")+"(\\[)(\\d*)(\\])"+"(\\[)(\\d*)(\\])").matcher(var22).find()) {
					var22=var22.split("[\\=\\[\\]]")[0]+"[1]"+"["+var22.split("[\\=\\[\\]]")[1]+"]"+"="+var22.split("[\\=\\[\\]]")[3];
				}
				
				String[] mANDn2 = (var22).split("[\\[\\]]");
				String ans = Find.CreateVariable("ans", "matrixVariable");
				Find.FindInitializevariable(ans+"["+mANDn2[1]+"]"+"["+mANDn2[3]+"]", "Int16");
				
				Changing.matrixVariable=Changing.matrixVariable+ans+"["+mANDn2[1]+"]"+"["+mANDn2[3]+"]"+"$";
				Equation.AnsTemp=ans;
				String i = Find.CreateVariable("i", "variablevalue");
				String j = Find.CreateVariable("j", "variablevalue");
				
				Equation.TempEquation=Equation.TempEquation +"\n"+ "for ("+i+" = 0; "+i+" < "+ mANDn2[1] +"; "+i+"++)\n{\n" +
						"for ("+j+" = 0; "+j+" < "+ mANDn2[3] +"; "+j+"++)\n{\n" +
									ans+"["+i+"]["+j+"] = "+bracket1+var1+Operator+var2+"["+i+"]["+j+"]"+bracket2+";\n}\n}";
			}
			
		}
	}
	
	
	public static Object TwoVarORNumber(String var1 , String var2 , String Operator) {
		String var11 = null;
		String var22 = null;
		int index;
		ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
		
		if(!Pattern.compile("(\\d*)").matcher(var1.replaceAll("[\\.\\()\\{}\\[\\]]", "").replaceAll(" ", "")).matches()){
			
			index=(Changing.variablevalue.indexOf("$"+var1.replaceAll("[()]", "")))+1;
		    var11 = (Changing.variablevalue.substring(index, Changing.variablevalue.indexOf("$",index))).split("=")[1] ;
		}
		
		else{
			var11=var1.replaceAll("[()]", "");
		}
		
		if(!Pattern.compile("(\\d*)").matcher(var2.replaceAll("[\\.\\()\\{}\\[\\]]", "").replaceAll(" ", "")).matches()){
				index=(Changing.variablevalue.indexOf("$"+var2.replaceAll("[()]", "")))+1;
			    var22 = (Changing.variablevalue.substring(index, Changing.variablevalue.indexOf("$",index))).split("=")[1] ;
			}
		
		else{
			var22=var2.replaceAll("[()]", "");
		}
		
		try {
			Equation.AnsTemp = (engine.eval(var11+Operator+var22)).toString();
			
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
}
