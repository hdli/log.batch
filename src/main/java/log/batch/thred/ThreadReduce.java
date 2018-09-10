package log.batch.thred;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import log.batch.api.ReaderFileListener;
import log.batch.api.Reduce;
import log.batch.po.Context;

public class ThreadReduce extends Thread {
	private Reduce reduce;
	private CountDownLatch countDownLatch;

	public ThreadReduce(Reduce reduce, CountDownLatch countDownLatch) {
		this.setName(this.getName() + "-ThreadReduce");
		this.countDownLatch = countDownLatch;
		this.reduce = reduce;
	}

	@Override
	public void run() {
		try {
			Context context = reduce.getContext();
			ConcurrentHashMap warehouse = context.getWarehouse();
			Iterator iterator = warehouse.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, List> next = (Entry<String, List>) iterator
						.next();
				String key = next.getKey();
				List value = next.getValue();
				reduce.excute(key, value, reduce.getResult());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			countDownLatch.countDown();
		}
	}
}
