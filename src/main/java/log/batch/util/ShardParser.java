package log.batch.util;

/**
 * 
 */
public class ShardParser {
	/**
	 * 分区函数提高并行度，设置
	 * @param key 进行分区的键
	 * @param shard 分区的总数
	 * @return 所在的分区
	 */
	public static int getId(String key,int shard) {
		if (null == key || key.length() == 0) {
			throw new IllegalArgumentException("ERROR ## the userId is null");
		}
		int hashid = HashUtil.quickHash(key.getBytes(),shard);
		return hashid;
	}

	public static void main(String[] args) {
		int id = getId("11大法师打发第三方11122vfggdsf",10);
		System.out.println(id);
	}
}
