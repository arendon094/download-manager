package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LocalInfo {
	File f = new File("images/craters.jpg");

	
	public LocalInfo() {
		 System.out.println(f.getAbsolutePath());
		 System.out.println("this file" + f.exists());

	}

	public String getDirectory() {
		
        // This will reference one line at a time
        String line = null;
        
		try {
			//System.out.println(file.exists());
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
        	ex.printStackTrace();
        }
        catch(IOException ex) {
        	ex.printStackTrace();
               
        }
		
		return line;
	}
}
