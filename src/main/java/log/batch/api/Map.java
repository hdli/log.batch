package log.batch.api;

import java.lang.reflect.ParameterizedType;

import log.batch.po.Context;

public abstract class Map<T> {
	private Class<T> clazz;
	private Context<T> context;

	public Context<T> getContext() {
		return context;
	}

	public void setContext(Context<T> context) {
		this.context = context;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Map() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class) type.getActualTypeArguments()[0];
		context=new Context<T>();
	}

	/**
	 * @param key
	 * @param value
	 * @param context
	 */
	public abstract void excute(String key, String value, Context<T> context);
}
