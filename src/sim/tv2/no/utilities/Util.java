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
	
	public static String removeWhiteSpace(String string) {
		return string.replace("\u00a0", " ");
	}
	
	public static String padIndex(int index) {
		
		if(index < 10) {
			return "0" + index;
		} else {
			return "" + index;
		}
	}

}
