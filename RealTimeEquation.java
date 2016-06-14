
/*This class is called when there is any operation on input variable (real time variable) for the purpose of conversion in c language*/

package Main;

import java.util.regex.Pattern;

public class RealTimeEquation {
	
	public static void VariableOne(String var1 , String var2 , String Operator) {
		String var11 = null;
		String var22 = null;
		
		//Here in the input variable all real time variables are store with its temporary answer like ex. "ans= e+78" and var1 has "ans"
		// hence it will be cropped and stored in var11
		
		// if var1 is present in Changing.InputVariable 
		if(Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"=").matcher(Changing.InputVariable).find()) {
			
			int index = (Changing.InputVariable.indexOf("$"+var1.replaceAll("[()]", "")))+1;
			var11 = Changing.InputVariable.substring(index, Changing.InputVariable.indexOf("$",index)).split("=")[1] ;// var11= e+78
		}
		
		else{   // if this variable not in input variable then var11= var1 
			var11=var1;
		}
		
		//if the variable in var11 is also in temp variable then it is temporary hence it will be removed
		if (Pattern.compile("\\$"+var11.replaceAll(" ","").replaceAll("[()]", "")).matcher(Equation.tempvariable).find()) {
			
			 Changing.InputVariable=Changing.InputVariable.replaceFirst(var11+"$", "");
			 var11=var11.split("=")[1];     // in var11 ans=44+g are stored but we required only 44+g hence it will be //cropped
		}
		
		if(Pattern.compile("\\$"+var2.replaceAll(" ","").replaceAll("[()]", "")+"=").matcher(Changing.InputVariable).find()) {
			
			int index = (Changing.InputVariable.indexOf("$"+var2.replaceAll("[()]", "")))+1;
			var22 = Changing.InputVariable.substring(index, Changing.InputVariable.indexOf("$",index)).split("=")[1] ;
		}
		
		else {
			var22=var2;
		}
		
		if (Pattern.compile("\\$"+var22.replaceAll(" ","").replaceAll("[()]", "")).matcher(Equation.tempvariable).find()) {
			
			 Changing.InputVariable=Changing.InputVariable.replaceFirst(var22+"$", "");
			 var22=var22.split("=")[1];
		}
                  //the equation are represented according to c with maths operator
		
		Equation.AnsTemp=var11+Operator+var22;
		int q = 0;
		 String ans;
		 //ans are stored in temp ans hence generate temp variable
		 do{
			 
			ans = "ans";
				
			ans = ans+String.valueOf(q);
			q++;
		}while(Pattern.compile(ans+"$").matcher(Changing.InputVariable).find());
		 
		 Equation.TempEquation=Equation.AnsTemp;
		 Changing.InputVariable=Changing.InputVariable+ans+"="+Equation.AnsTemp+"$"; // we stored that that temp ans in input variable for further solution
		 Equation.AnsTemp=ans;
		 Equation.tempvariable=Equation.tempvariable+ans+"$";  // for avoiding duplication that will be stored in temp variable
	}
}
