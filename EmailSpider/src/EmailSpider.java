import java.io.*;
import java.util.regex.*;

public class EmailSpider {

	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/作品集福利发放_.html"));
			String s = null;
			Pattern p = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
			Matcher m;
			while((s = br.readLine())!=null){
				m = p.matcher(s);
				while (m.find()){
					System.out.println(m.group());
				}
			}
			br.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();			
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	}
}
