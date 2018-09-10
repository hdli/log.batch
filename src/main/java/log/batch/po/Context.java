package log.batch.po;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Context<T> {
	ConcurrentHashMap<String, List<T>> warehouse = new ConcurrentHashMap<String, List<T>>();

	/**
	 * 全文保存对象
	 * 
	 * @param key
	 *            全文唯一存在
	 * @param t
	 *            保存的数值
	 */
	public void writer(String key, T t) {
		if (warehouse.containsKey(key)) {
			List<T> list = warehouse.get(key);
			list.add(t);
		} else {
			List<T> list = new LinkedList<T>();
			list.add(t);
			warehouse.put(key, list);
		}
	}
	public void writerList(String key, List<T> t) {
		if (warehouse.containsKey(key)) {
			List<T> list = warehouse.get(key);
			list.addAll(t);
		} else {
			List<T> list = new LinkedList<T>();
			list.addAll(t);
			warehouse.put(key, list);
		}
	}

	public List<T> get(String key) {
		return warehouse.get(key);
	}

	public Boolean contains(String key) {
		return warehouse.containsKey(key);
	}

	public ConcurrentHashMap<String, List<T>> getWarehouse() {
		return warehouse;
	}

}
