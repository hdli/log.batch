package log.batch.main;

import java.util.Map;

public class GetSysenvTest {
	
	public static void main(String[] args) {
		Map<String,String> map=System.getenv();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("----key："+entry.getKey()+"-----value:"+entry.getValue());
		}
		System.out.println("res_env_run:"+map.get("res_env_run"));
	}
}
