package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class FileWrite {
	public static void ConviCode(File file) {
		try {
			Changing.code=Changing.HeaderFile + "\n" +Changing.GlobleVariable + "\n" + Changing.Function + "\n" + Changing.Initializevariable + "\n" + Changing.MainProgram + "\n" + Changing.Ending;
			
			
			BufferedReader reader = new BufferedReader(new StringReader(Changing.code));
			String stringLine;
			String output = "";
			int a=0;
			while((stringLine=reader.readLine()) != null){
	//			System.out.println(stringLine);
				
				//If } not {
				if(stringLine.contains("}") && !stringLine.contains("{")) {
					a--;
				}
				
				for(int i=0;i<a;i++) {
					output=output+"\t";
					
				}
				
				//If { not }
				if(stringLine.contains("{") && !stringLine.contains("}")) {
					a++;
					
				}
				output=output+stringLine+"\n";
			}
			
			FileWriter fileWriter = new FileWriter(file);			
			fileWriter.write(output);
			fileWriter.flush();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

