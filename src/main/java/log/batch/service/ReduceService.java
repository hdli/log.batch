package log.batch.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import log.batch.api.Reduce;
import log.batch.po.Context;
import log.batch.thred.ThreadReduce;
import log.batch.util.ShardParser;

public class ReduceService extends Thread {
	private Class reduce;
	private List<Context> mapContexts = new ArrayList<Context>();
	private List<Context> mapTemp = new ArrayList<Context>();
	private List<Context> result = new ArrayList<Context>();
	private CountDownLatch countDownLatch;
	private int maxThreadNum;

	public ReduceService(Class reduce, List<Context> mapContexts,
			CountDownLatch countDownLatch, int maxThreadNum) {
		this.reduce = reduce;
		this.mapContexts = mapContexts;
		this.countDownLatch = countDownLatch;
		this.maxThreadNum = maxThreadNum;

	}

	public void reduce() {
		List<Reduce> reduces = new ArrayList<Reduce>();
		/**
		 * 实例化中间结果集
		 */
		for (int i = 0; i < maxThreadNum; i++) {
			try {
				Reduce newInstance = (Reduce) reduce.newInstance();
				mapTemp.add(newInstance.getContext());
				reduces.add(newInstance);
				result.add(newInstance.getResult());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * 对中间结果集进行合并，并分组
		 */
		Map<String, Object> tempMap = new HashMap<String, Object>();
		for (int i = 0; i < maxThreadNum; i++) {
			Context context = mapContexts.get(i);
			ConcurrentHashMap warehouse = context.getWarehouse();
			Iterator iterator = warehouse.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, List> entry = (Entry<String, List>) iterator
						.next();
				if (!tempMap.containsKey(entry.getKey().trim())) {
					writer(entry.getKey(), entry.getValue());
					tempMap.put(entry.getKey().trim(), "");
					for (int j = 0; j < mapContexts.size(); j++) {
						if (i != j) {
							Context contextj = mapContexts.get(j);
							if (contextj.contains(entry.getKey().trim())) {
								String key = entry.getKey();
								List list = contextj.get(entry.getKey());
								writer(key, list);
							}
						}
					}
				}
			}
		}
		mapContexts = null;
		/**
		 * 开始reduce过程
		 */
		for (int i = 0; i < maxThreadNum; i++) {
			ThreadReduce threadReduce = new ThreadReduce(reduces.get(i),
					countDownLatch);
			threadReduce.start();
		}
	}

	public void writer(String key, List value) {
		int id = ShardParser.getId(key, maxThreadNum - 1);
		Context contextTemp = mapTemp.get(id);
		contextTemp.writerList(key, value);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		reduce();
	}

}
