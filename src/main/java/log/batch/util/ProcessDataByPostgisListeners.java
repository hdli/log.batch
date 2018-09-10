package log.batch.util;


import java.util.List;

public class ProcessDataByPostgisListeners extends ReaderFileListener {
	public ProcessDataByPostgisListeners(String encode) {
		// TODO Auto-generated constructor stub
		setEncode(encode);
	}

	@Override
	public void output(List<String> stringList) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(stringList.size());
	}

}
