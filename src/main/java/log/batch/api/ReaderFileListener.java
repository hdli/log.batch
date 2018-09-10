package log.batch.api;

import java.util.ArrayList;
import java.util.List;

import log.batch.po.Context;
import log.batch.po.LogPo;

/**
 * Created with Okey User: Okey Date: 13-3-14 Time: 下午3:19 NIO逐行读数据回调方法
 */
public abstract class ReaderFileListener {
	// 一次读取行数，默认为500
	protected int readColNum = 500;

	protected String encode;

	protected List<LogPo> list = new ArrayList<LogPo>();

	protected Map map;

	protected Context context;

	public void setContext(Context context) {
		this.context = context;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * 设置一次读取行数
	 * 
	 * @param readColNum
	 */
	protected void setReadColNum(int readColNum) {
		this.readColNum = readColNum;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * 每读取到一行数据，添加到缓存中
	 * 
	 * @param lineStr
	 *            读取到的数据
	 * @param lineNum
	 *            行号
	 * @param over
	 *            是否读取完成
	 * @throws Exception
	 */
	public void outLine(String lineStr, long lineNum, boolean over)
			throws Exception {
		if (null != lineStr&&lineStr.length()!=0) {
			LogPo log = new LogPo();
			log.setKey(lineNum+"");
			log.setValue(lineStr.trim());
			list.add(log);
		}
		if (!over && (lineNum % readColNum == 0)) {
			output(list);
			list.clear();
		} else if (over) {
			output(list);
			list.clear();
		}
	}

	/**
	 * 批量输出
	 *
	 * @param stringList
	 * @throws Exception
	 */
	public void output(List<LogPo> stringList) throws Exception {
		for (LogPo s : stringList) {
			map.excute(s.getKey(), s.getValue(), context);
		}
	}
}