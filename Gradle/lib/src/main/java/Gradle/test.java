package Gradle;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    try{
	        String executionPath = System.getProperty("user.dir");
	        System.out.print("Executing at =>"+executionPath.replace("\\", "/"));
	      }catch (Exception e){
	        System.out.println("Exception caught ="+e.getMessage());
	      }
	}

}
