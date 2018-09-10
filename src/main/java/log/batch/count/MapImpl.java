package log.batch.count;

import log.batch.api.Map;
import log.batch.po.Context;
import log.batch.util.ConnectionDB;

public class MapImpl extends Map<Integer> {

	public void excute(String key, String value, Context<Integer> context) {
		// TODO Auto-generated method stub
		String[] split = value.split(",");
		for (int i = 0; i < split.length; i++) {
			if (split[i].trim()!=null&&split[i].length()!=0) {
				context.writer(split[i].trim().toLowerCase(), 1);
			}
		}
	}

}
