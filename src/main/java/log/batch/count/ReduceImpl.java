package log.batch.count;

import java.util.List;

import log.batch.api.Reduce;
import log.batch.po.Context;

public class ReduceImpl extends Reduce<Integer> {

	@Override
	public void excute(String key, List<Integer> value,
			Context<Integer> context) {
//		// TODO Auto-generated method stub
////		System.out.println(value.size());
//		for (int i = 0; i < value.size(); i++) {
//			String string = value.get(i);
//			System.out.println(string);
//		}
		System.out.println(key+":"+value.size());
		context.writer(key, value.size());
	}
//
//	@Override
//	public void excute(String key, List<Integer> value, Context<Integer> context) {
//		// TODO Auto-generated method stub
//		
//	}
}
