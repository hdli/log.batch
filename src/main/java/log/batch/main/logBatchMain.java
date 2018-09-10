package log.batch.main;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import log.batch.po.Context;
import log.batch.service.LogBatchService;
import log.batch.service.MapImpl;
import log.batch.service.ProcessDataByPostgisListeners;
import log.batch.service.ReduceImpl;
import log.batch.service.ReduceService;

public class logBatchMain {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Class map = MapImpl.class;
		Class reduce = ReduceImpl.class;
		Class readerFileListener = ProcessDataByPostgisListeners.class;
		String path = "d:/vm/SogouQ/access_log.20060830.decode.filter";
		String encode = "utf-8";
		int readColNum = 50000;
		int maxThreadNum = Runtime.getRuntime().availableProcessors() < 2 ? 2
				: Runtime.getRuntime().availableProcessors();
		CountDownLatch countDownLatch = new CountDownLatch(maxThreadNum);
		LogBatchService LogBatchService = new LogBatchService(map, reduce,
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
