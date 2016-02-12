package sim.tv2.no.utilities;

public class Util {
	
	public Util() {
		
	}
	
	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		if(os.contains("Windows")) {
			return true;
		} else return false;
	}

}
