<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 메인화면</title>
</head>


<body>

<%-- <c:if test="${sessionScope.id == null }"> 
  <script>
   alert("다시 로그인 해주세요!");
   location.href="<%=request.getContextPath()%>/member_login.do";
  </script>
</c:if> --%>

<c:if test="${sessionScope.id != null }">  
 <div id="main_wrap">
   <h2 class="main_title">사용자 메인화면</h2>  
   <form method="post" action="member_logout.do"> 
   <table id="main_t">
          
    <tr>
     <th>회원명</th>
     <td>${join_name}님 로그인을 환영합니다</td>
    </tr>
    
    <tr>
     <th colspan="2">
     <input type="button" value="정보수정" class="input_button"
     		onclick="location='member_edit.do'" />
     		<!-- type="button" 이기 때문에 이벤트가 나타난다. -->
     <input type="button" value="회원탈퇴" class="input_button"
     		onclick="location='member_del.do'" />
     <input type="submit" value="로그아웃" class="input_button" />     
     </th>
    </tr>
    
   </table>   
   </form>
 </div>

</c:if>

 <div>
 	<!-- url 파라미터로 받은 로그인한 아이디 값이 있을시에는 "name+방문을 환영한다"고 출력이 되고, null값일 때에는 "guest님 방문을 환영합니다" 메시지가 출력되도록 한다.-->
    
    <%
        String kakaonickname = request.getParameter("kakaonickname");        
        session.setAttribute("kakaonickname", kakaonickname);
        
        
        if (kakaonickname == null) {
    %>
        guest님 방문을 환영합니다.
        
    <%    
        } else if (kakaonickname != null){
    %>
    
    <%=" (카카오톡) "+session.getAttribute("kakaonickname")%>님 방문을 환영합니다.
    
    <form action = "kakao_logout.do" method = "post">
    <button type = "submit" name = "submit">로그아웃</button></form>
    
    <%
        };
       
    %>
 
 </div>


</body>
</html>




