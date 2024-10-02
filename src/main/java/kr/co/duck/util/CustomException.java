package kr.co.duck.util;

import kr.co.duck.util.StatusCode;

// 기능: 실행 예외에 ErrorCode 필드 추가해 커스텀
public class CustomException extends RuntimeException { // 실행 예외 클래스를 상속받아서 Unchecked Exception으로 활용
	private final StatusCode statusCode;

	public CustomException(StatusCode statusCode) {
		super(statusCode.getStatusMsg());
		this.statusCode = statusCode;
	}

	
	public CustomException(StatusCode statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}
}
