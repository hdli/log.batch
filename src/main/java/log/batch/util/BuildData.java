package log.batch.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA. User: okey Date: 14-4-1 Time: 下午6:03 To change
 * this template use File | Settings | File Templates.
 */
public class BuildData {
	public static void main(String[] args) throws Exception {
		File file = new File("d:/vm/新建文本文档 (2).txt");
		FileInputStream fis = null;
		try {
			ReadFile readFile = new ReadFile();
			fis = new FileInputStream(file);
			int available = fis.available();
			/**
			 * 获得当前的核心数量，如果小于2则设置为2，大于2 则设置并行度为当前 cpu的数量
			 */
			int maxThreadNum = Runtime.getRuntime().availableProcessors()<2?2:Runtime.getRuntime().availableProcessors();
			// 线程粗略开始位置
			int i = available / maxThreadNum;
			for (int j = 0; j < maxThreadNum; j++) {
				// 计算精确开始位置
				long startNum = j == 0 ? 0 : readFile.getStartNum(file, i * j);
				long endNum = j + 1 < maxThreadNum ? readFile.getStartNum(file,
						i * (j + 1)) : -2;
				// 具体监听实现
				ReaderFileListener listeners = new ProcessDataByPostgisListeners(
						"gbk");
				new ReadFileThread(listeners, startNum, endNum, file.getPath())
						.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
