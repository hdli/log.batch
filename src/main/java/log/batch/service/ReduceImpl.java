package log.batch.service;

import java.util.List;

import log.batch.api.Reduce;
import log.batch.po.Context;

public class ReduceImpl extends Reduce<String> {

	@Override
	public void excute(String key, List<String> value,
			Context<String> context) {
		// TODO Auto-generated method stub
//		System.out.println(value.size());
		for (int i = 0; i < value.size(); i++) {
			String string = value.get(i);
			System.out.println(string);
		}
		context.writer(key, value.toString());
	}
}
