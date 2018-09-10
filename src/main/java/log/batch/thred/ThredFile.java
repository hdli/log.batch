package log.batch.thred;

import java.util.concurrent.CountDownLatch;

import log.batch.api.ReaderFileListener;

public class ThredFile extends Thread {
	private ReaderFileListener processPoiDataListeners;
	private String filePath;
	private long start;
	private long end;
	private CountDownLatch countDownLatch;

	public ThredFile(ReaderFileListener processPoiDataListeners, long start,
			long end, String file, CountDownLatch countDownLatch) {
		this.setName(this.getName() + "-ReadFileThread");
		this.start = start;
		this.end = end;
		this.filePath = file;
		this.processPoiDataListeners = processPoiDataListeners;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		ReadFile readFile = new ReadFile();
		readFile.setReaderListener(processPoiDataListeners);
		readFile.setEncode(processPoiDataListeners.getEncode());
		// readFile.addObserver();
		try {
			readFile.readFileByLine(filePath, start, end + 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			countDownLatch.countDown();
		}
	}
}
