package Main;

import java.util.regex.Pattern;

public class RealTimeEquation {
	
	public static void VariableOne(String var1 , String var2 , String Operator) {
		String var11 = null;
		String var22 = null;
		
		if(Pattern.compile("\\$"+var1.replaceAll(" ","").replaceAll("[()]", "")+"=").matcher(Changing.InputVariable).find()) {
			
			int index = (Changing.InputVariable.indexOf("$"+var1.replaceAll("[()]", "")))+1;
			var11 = Changing.InputVariable.substring(index, Changing.InputVariable.indexOf("$",index)).split("=")[1] ;
		}
		
		else{
			var11=var1;
		}
		
		if (Pattern.compile("\\$"+var11.replaceAll(" ","").replaceAll("[()]", "")).matcher(Equation.tempvariable).find()) {
			
			 Changing.InputVariable=Changing.InputVariable.replaceFirst(var11+"$", "");
			 var11=var11.split("=")[1];
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
		
		Equation.AnsTemp=var11+Operator+var22;
		int q = 0;
		 String ans;
		 
		 do{
			 
			ans = "ans";
				
			ans = ans+String.valueOf(q);
			q++;
		}while(Pattern.compile(ans+"$").matcher(Changing.InputVariable).find());
		 
		 Equation.TempEquation=Equation.AnsTemp;
		 Changing.InputVariable=Changing.InputVariable+ans+"="+Equation.AnsTemp+"$";
		 Equation.AnsTemp=ans;
		 Equation.tempvariable=Equation.tempvariable+ans+"$";
	}
}
