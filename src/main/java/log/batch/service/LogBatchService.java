package log.batch.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import log.batch.api.Map;
import log.batch.api.ReaderFileListener;
import log.batch.api.Reduce;
import log.batch.po.Context;
import log.batch.thred.ReadFile;
import log.batch.thred.ThreadReduce;
import log.batch.thred.ThredFile;
import log.batch.util.ShardParser;

public class LogBatchService extends Thread {
	private Class map;
	private Class readerFileListener;
	private String path;
	private String encode;
	private int readColNum;
	private List<Context> mapContexts = new ArrayList<Context>();
	private CountDownLatch countDownLatch;
	private int maxThreadNum;

	public List<Context> getMapContexts() {
		return mapContexts;
	}

	public void setMapContexts(List<Context> mapContexts) {
		this.mapContexts = mapContexts;
	}

	public LogBatchService(Class map, Class reduce, Class readerFileListener,
			String path, String encode, int readColNum,
			CountDownLatch countDownLatch, int maxThreadNum) throws Exception {
		this.map = map;
		this.readerFileListener = readerFileListener;
		this.path = path;
		this.encode = encode;
		this.readColNum = readColNum;
		this.countDownLatch = countDownLatch;
		this.maxThreadNum = maxThreadNum;
	}

	public void logic() {
		File file = new File(path);
		FileInputStream fis = null;
		try {
			ReadFile readFile = new ReadFile();
			fis = new FileInputStream(file);
			int available = fis.available();   //返回的实际可读字节数，也就是总大小
			/**
			 * 获得当前的核心数量，如果小于2则设置为2，大于2 则设置并行度为当前 cpu的数量
			 */
			// 线程粗略开始位置
			int i = available / maxThreadNum;
			for (int j = 0; j < maxThreadNum; j++) {
				// 计算精确开始位置
				long startNum = j == 0 ? 0 : readFile.getStartNum(file, i * j);
				long endNum = j + 1 < maxThreadNum ? readFile.getStartNum(file,i * (j + 1)) : -2;
				// 具体监听实现
				ReaderFileListener listeners = (ReaderFileListener) readerFileListener.newInstance();
				Map newInstance = (Map) map.newInstance();
				listeners.setMap(newInstance);
				Context context = newInstance.getContext();
				listeners.setContext(context);
				listeners.setEncode(encode);
				mapContexts.add(context);
				new ThredFile(listeners, startNum, endNum, path, countDownLatch)
						.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logic();
	}
}
