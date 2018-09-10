package log.batch.main;

public class TestSplit {
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		String test = ",,1,,,,,,,";
		  String splitRegex=",";
		  String arr[] = test.split(splitRegex);
		  
		  for (String s : arr) {
		   System.out.println(s);// 测试!!!
		  }
	}
}
