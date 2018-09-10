package log.batch.service;

import log.batch.api.ReaderFileListener;

public class ProcessDataByPostgisListeners extends ReaderFileListener {

	public ProcessDataByPostgisListeners() {
		super();
	}

	public ProcessDataByPostgisListeners(String encode) {
		// TODO Auto-generated constructor stub
		this.encode = encode;
	}

	public ProcessDataByPostgisListeners(String encode, int readColNum) {
		// TODO Auto-generated constructor stub
		this.encode = encode;
		this.readColNum = readColNum;
	}
}
