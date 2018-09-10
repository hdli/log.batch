package log.batch.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestAvailableProcessors {

	public static void main(String[] args) throws Exception {
		int counts=Runtime.getRuntime().availableProcessors();
		System.out.println("Number of processors available to the Java Virtual Machine :"+counts);
		
		File file = new File("G:/test.txt");
		FileInputStream fis=new FileInputStream(file);
		int available=fis.available();    //返回的实际可读字节数，也就是总大小
		System.out.println("file 可读字节 数是："+available);
		
		int i=available/counts;
		System.out.println("i="+i);
		
		
		
	}

}
