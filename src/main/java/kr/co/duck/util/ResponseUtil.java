package kr.co.duck.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// 기능 : 최종 응답이 반환
public class ResponseUtil {

	private static final Logger log = LoggerFactory.getLogger(ResponseUtil.class); // Logger 초기화

	// 메세지 응답 (No Data)
	public static ResponseEntity<GlobalResponseDto> response(StatusCode statusCode) {
		if (statusCode.getStatusCode().equals("200")) {
			// 성공응답
			return new ResponseEntity<>(new GlobalResponseDto<>(statusCode), HttpStatus.OK);
		} else {
			// 예외응답
			return new ResponseEntity<>(new GlobalResponseDto<>(statusCode), statusCode.getHttpStatus());
		}
	}

	// 성공 응답 (Data) - 메세지 없이 오로지 결과값만 반환
	public static <T> ResponseEntity<T> response(T data) {
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// 성공 메세지와 응답 (Data)
	public static <T> ResponseEntity<GlobalResponseDto<T>> response(StatusCode statusCode, T data) {
		return new ResponseEntity<>(new GlobalResponseDto<>(statusCode, data), statusCode.getHttpStatus());
	}
}
