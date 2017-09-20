<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

  <div id="contents_login">
    <div class="login_area">
      <ul>
        <li><span style="font-size: 14px; font-weight: bold;">에러코드: ${code} - ${status}</span></li>
        <li><span style="font-size: 12px;">${desc}</span></li>
<!--         <li><span style="font-size: 12px;"><a href="javascript:history.go(-1)">돌아가기</a></span></li> -->
		<li><span style="font-size: 12px;"><a href="/#/">돌아가기</a></span></li>
      </ul>
    </div>
  </div>

</body>

</html>