package com.taro.backspring;

public class BackupException extends Exception {

	private static final long serialVersionUID = 1L;

	public BackupException() {
		super();
	}

	public BackupException(Exception e) {
		super(e);
	}

	public BackupException(String message) {
		super(message);
	}
}
