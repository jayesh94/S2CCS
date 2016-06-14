
/*This class is called when there is any arithmetic operation involving input variable (real time variable). Whenever there is 
arithmetic operation involving matrices Ex.b+a*8 where a is matrix then the result is saved as b+ans so as to be used in converted c code.
This causes other arithmetic operations on any variable even input variable to be saved as ans. Thus we hae to revert it back to its 
original form Ex. convert ans back as a*8 if a is input variable. This is the main functioning of this class. */

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
		
		// If the variable in var11 is also in temp variable then it is temporary hence it will be removed
		// The whole equation ans= e+78 is stored in Equation.tempvariable for the purpose of simplifying detection process
		if (Pattern.compile("\\$"+var11.replaceAll(" ","").replaceAll("[()]", "")).matcher(Equation.tempvariable).find()) {
			
			 Changing.InputVariable=Changing.InputVariable.replaceFirst(var11+"$", "");
			 var11=var11.split("=")[1];     // in var11 ans=44+g are stored but we required only 44+g hence it will be cropped
		}
		
		
		// Similarly with var2
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
                
                // The equation is represented according to c with maths operator
		Equation.AnsTemp=var11+Operator+var22;// Saving the original equation to Equation.AnsTemp
		int q = 0;
		 String ans;
		 //following loop is for creation of ans0, ans1,.... which ever is not initially used
		 do{
			 
			ans = "ans";
				
			ans = ans+String.valueOf(q);
			q++;
		}while(Pattern.compile(ans+"$").matcher(Changing.InputVariable).find());
		 
		 Equation.TempEquation=Equation.AnsTemp;// Saving the equation to Equation.TempEquation 
		 Changing.InputVariable=Changing.InputVariable+ans+"="+Equation.AnsTemp+"$"; // we stored this ans in input variable for further solution
		 Equation.AnsTemp=ans;
		 Equation.tempvariable=Equation.tempvariable+ans+"$";  // for avoiding duplication that will be stored in temp variable
	}
}
