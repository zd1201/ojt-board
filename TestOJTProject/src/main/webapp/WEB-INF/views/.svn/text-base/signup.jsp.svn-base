<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<!-- request 객체에 저장된 contextpath를 참조하여 경로 명시 -->
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}" /> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="signupApp">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/scripts/controller/signup_ctrl.js" type="text/javascript"></script>


<script type="text/javascript">
	/* var ctx = "${ctx}"; */
</script>
 <title>:: 게시판 회원 로그인 ::</title>
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  ng-controller="signupCtrl">

<div id="container">

 <!-- cont -->
<div id="cont">

   <!-- sub_area -->
   <div id="sub_area">   
     
     <div class="sig_title"> 회원가입</div>
     
	   <table width="100%"  border="0" cellpadding="1" cellspacing="0" bgcolor="#a6a4b1" class="list_read">
          <tr>
            <th width="20%">아이디 <span style="color:#c92c71">*</span></th>
            <td>
               <p> 
                 <input class="signup_form" id="id" name="id" type="text" autofocus placeholder="아이디" style="vertical-align: middle;" ng-model="id" ng-change="idChange()" maxlength="16"/>
                 <button type="button" class="btn btn-sm" ng-disabled="!id_validation" ng-click="checkId()" >중복확인</button>
               </p>
               <!--  <p class="list_read_ex" ng-style="userData.code == 0 && {'color': 'red'} 
               									|| userData.code == 1 && {'color': 'blue'}">{{id_msg}}</p> -->               
               <p class="list_read_ex" style="color: {{info_id_color}}">{{id_msg}}</p>               
            </td>
          </tr>
          <tr>
            <th>비밀번호 <span style="color:#c92c71">*</span></th>
            <td>
               <p><input class="signup_form" id="password" name="password" type="password" placeholder="비밀번호"  ng-model="pwd" ng-change="pwdChange()" ng-focus="focus_pwd" ng-trim required="true" maxlength="16"/></p>
               <p style="margin-top:5px;"><input class="signup_form" id="password" name="password" type="password" placeholder="비밀번호 확인" ng-model="pwd_check" ng-change="pwdChange()"/></p>               
               <p class="list_read_ex" style="color: {{info_pwd_color}}">{{pwd_msg}}</p>                     
            </td>
          </tr>          
		  <tr>
            <th>이름</th>
            <td><input class="signup_form" id="name" name="name" type="text" placeholder="name" ng-model="name" ng-focus="focus_name"  ng-trim="false"/></td>
          </tr>
          <tr>
            <th>나이</th>
            <td><input class="signup_form" id="age" name="age" type="number" autofocus placeholder="age" ng-model="age" min="1" ng-focus="focus_age"  ng-trim="false"/></td>
          </tr>
          <tr>
            <th>연락처</th>
            <td>
               <input class="signup_form" id="tel" name="tel" type="text" autofocus placeholder="- 포함 입력하세요" ng-model="tel" ng-pattern="/(\d{3}).*(\d{3}).*(\d{4})/" ng-focus="focus_tel" ng-trim="false"/>
            </td>
          </tr>
          <tr>
            <th>이메일 <span style="color:#c92c71">*</span></th>
            <td>
               <input class="signup_form" id="email" name="email" type="text" placeholder="id@ymtech.co.kr" style="width:80%" ng-model="email"   ng-trim="false" required="true"/>
            </td>
          </tr>
          <tr>
            <th>주소</th>
            <td><input class="signup_form" id="addr" name="addr" type="text" autofocus placeholder="address" style="width:80%" ng-model="addr"  ng-trim="false"/></td>
          </tr>
      </table>
		
		<div class="btn_area">
		   <button type="button" class="btn btn-primary" id="submit_button" ng-click="signup()">확인</button>
		  <a href="/"><button type="button" class="btn btn-default" id="cancel_button" >취소</button></a>
		</div>
  </div>
  <!-- end sub_area -->
 </div>
 <!-- end cont-->
</div>


<!--footer-->
<div id="footer">
  <div id="fGroup" class="ptp">
    Copyright ⓒ UBIQUITOUS MEDIA TECHNOLOGY. All Rights Reserved.
  </div>
</div>
<!--END footer-->


</body>
</html>
