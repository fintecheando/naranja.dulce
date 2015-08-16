package com.floreantpos.util;

public class DinerNotFoundException extends RuntimeException {

	public DinerNotFoundException() {
	}

	public DinerNotFoundException(String message) {
		super(message);
	}

	public DinerNotFoundException(Throwable cause) {
		super(cause);
	}

	public DinerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
