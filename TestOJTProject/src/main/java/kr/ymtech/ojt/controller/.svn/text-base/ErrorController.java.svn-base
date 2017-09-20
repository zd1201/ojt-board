package kr.ymtech.ojt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
	@RequestMapping(value = "/errorpages/{status-code}", method = RequestMethod.GET)
	public ModelAndView error(@PathVariable("status-code") int status) {
		
		ModelAndView view = new ModelAndView();

		switch(status){
		
		case 400:
		    view.addObject("code", status);
		    view.addObject("status", "Bad Request");
		    view.addObject("desc", "서버가 요청의 구문을 인식하지 못했다.");
		    view.setViewName("errors");
		    return view;
		case 401:
			view.addObject("code", status);
		    view.addObject("status", "Unauthorized");
		    view.addObject("desc", "이 요청은 인증이 필요하다. 서버는 로그인이 필요한 페이지에 대해 이 요청을 제공할 수 있다.");
		    view.setViewName("errors");
		    return view;
		case 402:
		    view.addObject("code", status);
		    view.addObject("status", "Payment Required");
		    view.addObject("desc", "이 요청은 결제가 필요합니다.");
		    view.setViewName("errors");
		    return view;
		case 403:
		    view.addObject("code", status);
		    view.addObject("status", "Forbidden");
		    view.addObject("desc", "서버가 요청을 거부하고 있다.");
		    view.setViewName("errors");
		    return view;
		case 404:
		    view.addObject("code", status);
		    view.addObject("status", "Not Found");
		    view.addObject("desc", "서버가 요청한 페이지를 찾을 수 없다.");
		    view.setViewName("errors");
		    return view;
		case 405:
			view.addObject("code", status);
		    view.addObject("status", "Method Not Allowed");
		    view.addObject("desc", "요청에 지정된 방법을 사용할 수 없다.");
		    view.setViewName("errors");
		    return view;
		case 406:
		    view.addObject("code", status);
		    view.addObject("status", "Not Acceptable");
		    view.addObject("desc", "요청한 페이지가 요청한 콘텐츠 특성으로 응답할 수 없다.");
		    view.setViewName("errors");
		    return view;
		case 407:
		    view.addObject("code", status);
		    view.addObject("status", "Proxy Authentication Required");
		    view.addObject("desc", "요청자가 프록시를 사용하여 인증해야 한다. ");
		    view.setViewName("errors");
		    return view;
		case 408:
		    view.addObject("code", status);
		    view.addObject("status", "Request Timeout");
		    view.addObject("desc", "서버의 요청 대기가 시간을 초과하였다.");
		    view.setViewName("errors");
		    return view;
		case 409:
			view.addObject("code", status);
		    view.addObject("status", "Conflict");
		    view.addObject("desc", "서버가 요청을 수행하는 중에 충돌이 발생했다.");
		    view.setViewName("errors");
		    return view;
		case 410:
		    view.addObject("code", status);
		    view.addObject("status", "Gone");
		    view.addObject("desc", "서버는 요청한 리소스가 영구적으로 삭제되었을 때 이 응답을 표시한다.");
		    view.setViewName("errors");
		    return view;
		case 411:
		    view.addObject("code", status);
		    view.addObject("status", "Length Required");
		    view.addObject("desc", "서버는 유효한 콘텐츠 길이 헤더 입력란 없이는 요청을 수락하지 않는다.");
		    view.setViewName("errors");
		    return view;
		case 412:
		    view.addObject("code", status);
		    view.addObject("status", "Precondition Failed");
		    view.addObject("desc", "서버가 요청자가 요청 시 부과한 사전조건을 만족하지 않는다.");
		    view.setViewName("errors");
		    return view;
		case 413:
			view.addObject("code", status);
		    view.addObject("status", "Payload Too Large");
		    view.addObject("desc", "요청이 너무 커서 서버가 처리할 수 없다.");
		    view.setViewName("errors");
		    return view;
		case 414:
		    view.addObject("code", status);
		    view.addObject("status", "URI Too Long");
		    view.addObject("desc", "요청 URI(일반적으로 URL)가 너무 길어 서버가 처리할 수 없다.");
		    view.setViewName("errors");
		    return view;
		case 415:
		    view.addObject("code", status);
		    view.addObject("status", "Unsupported Media Type");
		    view.addObject("desc", "요청이 요청한 페이지에서 지원하지 않는 형식으로 되어 있다.");
		    view.setViewName("errors");
		    return view;
		case 416:
		    view.addObject("code", status);
		    view.addObject("status", "Range Not Satisfiable");
		    view.addObject("desc", "요청이 페이지에서 처리할 수 없는 범위에 해당되는 경우 서버는 이 상태 코드를 표시한다.");
		    view.setViewName("errors");
		    return view;
		case 417:
			view.addObject("code", status);
		    view.addObject("status", "Expectation Failed");
		    view.addObject("desc", "서버는 Expect 요청 헤더 입력란의 요구사항을 만족할 수 없다.");
		    view.setViewName("errors");
		    return view;
		case 422:
			view.addObject("code", status);
		    view.addObject("status", "Unprocessable Entity");
		    view.addObject("desc", "처리할 수 없는 엔티티");
		    view.setViewName("errors");
		    return view;
		case 423:
		    view.addObject("code", status);
		    view.addObject("status", "Locked");
		    view.addObject("desc", "잠김");
		    view.setViewName("errors");
		    return view;
		case 424:
		    view.addObject("code", status);
		    view.addObject("status", "Failed Dependency");
		    view.addObject("desc", "실패된 의존성");
		    view.setViewName("errors");
		    return view;
		case 426:
		    view.addObject("code", status);
		    view.addObject("status", "Upgrade Required");
		    view.addObject("desc", "업그레이드 필요");
		    view.setViewName("errors");
		    return view;
		case 428:
			view.addObject("code", status);
		    view.addObject("status", "Precondition Required");
		    view.addObject("desc", "전제조건 필요");
		    view.setViewName("errors");
		    return view;
		case 429:
		    view.addObject("code", status);
		    view.addObject("status", "Too Many Requests");
		    view.addObject("desc", "너무 많은 요청");
		    view.setViewName("errors");
		    return view;
		case 431:
		    view.addObject("code", status);
		    view.addObject("status", "Request Header Fields Too Large");
		    view.addObject("desc", "요청 헤더 필드가 너무 큼");
		    view.setViewName("errors");
		    return view;
		case 451:
		    view.addObject("code", status);
		    view.addObject("status", "Unavailable For Legal Reasons ");
		    view.addObject("desc", "법적인 이유로 이용 불가");
		    view.setViewName("errors");
		    return view;
		case 500:
		    view.addObject("code", status);
		    view.addObject("status", "Internal Server Error");
		    view.addObject("desc", "서버에 오류가 발생하여 요청을 수행할 수 없다.");
		    view.setViewName("errors");
		    return view;
		case 501:
		    view.addObject("code", status);
		    view.addObject("status", "Not Implemented");
		    view.addObject("desc", "서버에 요청을 수행할 수 있는 기능이 없다. 예를 들어 서버가 요청 메소드를 인식하지 못할 때 이 코드를 표시한다.");
		    view.setViewName("errors");
		    return view;
		case 502:
			view.addObject("code", status);
		    view.addObject("status", "Bad Gateway");
		    view.addObject("desc", "서버가 게이트웨이나 프록시 역할을 하고 있거나 또는 업스트림 서버에서 잘못된 응답을 받았다.");
		    view.setViewName("errors");
		    return view;
		case 503:
		    view.addObject("code", status);
		    view.addObject("status", "Service Unavailable");
		    view.addObject("desc", "서버가 오버로드되었거나 유지관리를 위해 다운되었기 때문에 현재 서버를 사용할 수 없다. 이는 대개 일시적인 상태이다.");
		    view.setViewName("errors");
		    return view;
		case 504:
		    view.addObject("code", status);
		    view.addObject("status", "Gateway Timeout");
		    view.addObject("desc", "서버가 게이트웨이나 프록시 역할을 하고 있거나 또는 업스트림 서버에서 제때 요청을 받지 못했다.");
		    view.setViewName("errors");
		    return view;
		case 505:
		    view.addObject("code", status);
		    view.addObject("status", "HTTP Version Not Supported");
		    view.addObject("desc", "서버가 요청에 사용된 HTTP 프로토콜 버전을 지원하지 않는다.");
		    view.setViewName("errors");
		    return view;
		case 506:
			view.addObject("code", status);
		    view.addObject("status", "Variant Also Negotiates");
		    view.addObject("desc", "Variant Also Negotiates");
		    view.setViewName("errors");
		    return view;
		case 507:
		    view.addObject("code", status);
		    view.addObject("status", "Insufficient Storage");
		    view.addObject("desc", "용량 부족");
		    view.setViewName("errors");
		    return view;
		case 508:
		    view.addObject("code", status);
		    view.addObject("status", "Loop Detected");
		    view.addObject("desc", "루프 감지됨");
		    view.setViewName("errors");
		    return view;
		case 510:
			view.addObject("code", status);
		    view.addObject("status", "Not Extended");
		    view.addObject("desc", "확장되지 않음");
		    view.setViewName("errors");
		    return view;
		case 511:
		    view.addObject("code", status);
		    view.addObject("status", "Network Authentication Required");
		    view.addObject("desc", "네트워크 인증 필요");
		    view.setViewName("errors");
		    return view;
		case 598:
		    view.addObject("code", status);
		    view.addObject("status", "Network read timeout");
		    view.addObject("desc", "네트워크 읽기 시간초과 오류, 알 수 없음");
		    view.setViewName("errors");
		    return view;
		case 599:
		    view.addObject("code", status);
		    view.addObject("status", "Network connect timeout");
		    view.addObject("desc", "네트워크 연결 시간초과 오류, 알 수 없음");
		    view.setViewName("errors");
		    return view;
		default:
			view.addObject("code", status);
		    view.addObject("status", "default");
		    view.addObject("desc", "페이지 처리되지 않은 에러입니다.");
		    view.setViewName("errors");
		    return view;
		}
	}
}
