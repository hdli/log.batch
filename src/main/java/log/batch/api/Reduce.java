package log.batch.api;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import log.batch.po.Context;

public abstract class Reduce<T> {

	private Class<T> clazz;

	private Context<T> context;
	private Context<T> result;

	public Context<T> getContext() {
		return context;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setContext(Context<T> context) {
		this.context = context;
	}

	public Context<T> getResult() {
		return result;
	}

	public void setResult(Context<T> result) {
		this.result = result;
	}

	public Reduce() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class) type.getActualTypeArguments()[0];
		context = new Context<T>();
		result = new Context<T>();
	}

	/**
	 * 最后的汇总函数
	 * 
	 * @return
	 */
	public abstract void excute(String key, List<T> value, Context<T> context);
}
