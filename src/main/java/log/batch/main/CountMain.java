package log.batch.main;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import log.batch.po.Context;
import log.batch.service.LogBatchService;
import log.batch.count.MapImpl;
import log.batch.service.ProcessDataByPostgisListeners;
import log.batch.count.ReduceImpl;
import log.batch.service.ReduceService;

public class CountMain {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Class map = MapImpl.class;
		Class reduce = ReduceImpl.class;
		Class readerFileListener = ProcessDataByPostgisListeners.class;
		String path = "G:/test.txt";
		String encode = "utf-8";
		int readColNum = 5000;
		int maxThreadNum = Runtime.getRuntime().availableProcessors() < 2 ? 2
				: Runtime.getRuntime().availableProcessors();
		CountDownLatch countDownLatch = new CountDownLatch(maxThreadNum);
		LogBatchService LogBatchService;
		LogBatchService = new LogBatchService(map, reduce,
				readerFileListener, path, encode, readColNum, countDownLatch,
				maxThreadNum);
		LogBatchService.start();
		countDownLatch.await();
		System.out.println("map执行完毕");
		List<Context> mapContexts = LogBatchService.getMapContexts();
		countDownLatch = new CountDownLatch(maxThreadNum);
		ReduceService reduceService = new ReduceService(reduce, mapContexts,
				countDownLatch, maxThreadNum);
		reduceService.start();
		countDownLatch.await();
		System.out.println("reduce执行完毕");
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
