package Gradle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    try{
	        String executionPath = System.getProperty("user.dir");
	        System.out.println("Executing at =>"+executionPath.replace("\\", "/"));
	      }catch (Exception e){
	        System.out.println("Exception caught ="+e.getMessage());
	      }
	    
		try {
			Document document = Jsoup.connect("https://vi.wikipedia.org/wiki/Các_cuộc_chiến_tranh_Việt_Nam_tham_gia").get();
			System.out.println("Success");
		}
		// Nếu có sự cố thì trả về null
		catch(Exception e) {
			System.out.println("Error url.");
			System.err.println(e);
		}
	}

}
