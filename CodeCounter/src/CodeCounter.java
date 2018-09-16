import java.io.*;
import java.util.regex.*;

public class CodeCounter {
	static long regular = 0;
	static long blank = 0;
	static long comment = 0;
	public static void main(String[] args) {
		File dir = new File("/Users/Fred/EclipseWorkSpace/EmailSpider/src");
		File[] files = dir.listFiles();
		BufferedReader br = null;
		String s = null;
		boolean flag = true;
		
		for (int i=0; i<=files.length-1; i++) {
			if (files[i].getName().matches(".+\\.java")) {
				try{
					br = new BufferedReader(new FileReader(files[i]));
					while ((s = br.readLine())!=null){
						s = s.trim();
						if (s.matches("\\s*")) {
							blank++;
						}
						else if (s.startsWith("//")) {
							comment++;
						}
						else if (s.endsWith("*/")){
							comment++;
							flag = true;
						}
						else if (s.startsWith("/*")) {
							comment++;
							flag = false;
						}
						else if (false == flag) {
							comment++;
						}
						else {
							regular++;
						}
					}
				}
				catch(FileNotFoundException e) {
					e.printStackTrace();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				finally {
					if (br!=null){
						try {						
							br.close();
						} 
						catch (IOException e) {
							e.printStackTrace();
						}						
					}	
				}
			}
		}
		System.out.println("# regularlines: " + regular);
		System.out.println("# blanklines: " + blank);
		System.out.println("# commentlines: " + comment);
	}	
}
