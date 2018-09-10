package log.batch.service;

import log.batch.api.Map;
import log.batch.po.Context;
import log.batch.util.ConnectionDB;

public class MapImpl extends Map<String> {

	public void excute(String key, String value, Context<String> context) {
		// TODO Auto-generated method stub
		System.out.println(value);
		String[] split = value.split("\t");
		String[] split2 = split[2].split(" ");
		ConnectionDB connectionDB=new ConnectionDB();
		String sql="insert into seach_log (user_id,seach,ranking,clinck,url)VALUES(?,?,?,?,?)";
		Object[] params={split[0],split[1],Integer.parseInt(split2[0]),Integer.parseInt(split2[1]),split[3]};
		connectionDB.executeUpdate(sql, params);
//		String name2 = Thread.currentThread().getName();
//		System.out.println(name2);
		context.writer(key, value);
	}

}
