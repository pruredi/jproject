$(document).ready(function(){
		
		// ID중복검사
//		$("#idcheck").click(function(){
//			if($("#id").val()==""){
//				alert("ID를 입력하세요.");
//				$("#id").focus();
//				return false;
//			}else{
//				// open("팝업창에 실행될 문서명","윈도우이름","옵션")
//				var ref="idcheck.jsp?id="+$("#id").val();
//				window.open(ref,"mywin","width=250,height=150");
//			}			
//		});
//	
//		// ajax로 ID중복 검사
//		$("#idcheck").click(function(){	
//			if($("#id").val()==""){
//				alert("ID를 입력하세요.");
//				$("#id").focus();
//				return false;
//			}else{
//				
//				var id = $("#id").val();
//				
//				$.ajax({
//					type : "POST",
//					url : "idcheck1.jsp",
//					data : {"id": id},
//					success : function(data){
////						alert(data);
//						if(data == 1){	// 중복 ID
//							$("#myid").text("중복 ID입니다.");
//							$("#id").val("").focus();
//						}else{			// 사용 가능한 ID
//							$("#myid").text("사용 가능한 ID입니다.");
//							$("#passwd").focus();
//						}
//						
//					}					
//				}); // end ajax				
//			}			
//		});
	
	
	// keyup 이벤트로 ID중복 검사
	/*$("#id").keyup(function(){	
		if($("#id").val()==""){
			alert("ID를 입력하세요.");
			$("#id").focus();
			return false;
		}else{
			
			var id = $("#id").val();
			
			$.ajax({
				type : "POST",
				url : "idcheck1.jsp",
				data : {"id": id},
				success : function(data){
//					alert(data);
					if(data == 1){	// 중복 ID
						$("#myid").text("중복 ID입니다.");
//						$("#id").val("").focus();
					}else{			// 사용 가능한 ID
						$("#myid").text("사용 가능한 ID입니다.");
//						$("#passwd").focus();
					}
					
				}					
			}); // end ajax				
		}			
	});*/
	
	
	
		
		// 주민번호  뒷자리로 포커스 이동
		$("#join_date").keyup(function(){
			
			if($("#join_date").val().length == 6)
				$("#join_num").focus();
		});
		
		
		
		// 도메인 선택
		$("#mail_list").change(function(){
			if($("#mail_list").val()==""){		// 직접 입력 선택
				$("#emaildomain").attr("readOnly", false);
				$("#emaildomain").val("").focus();
			}else{							// 도메인을 선택
				$("#emaildomain").val($("#mail_list").val());
				$("#emaildomain").attr("readOnly","readOnly");				
			}
		});
		
		
		
		
		
		// 유효성 검사
		$("form").submit(function(){
			
			if($("#join_id").val() == ""){
				alert("ID를 입력하세요.");
				$("#join_id").focus();
				return false;
			}
			if($("#passwd1").val()==""){
				alert("비밀번호를 입력하세요.");
				$("#passwd1").focus();
				return false;
			}
			if($("#passwd2").val()==""){
				alert("비밀번호 확인을 입력하세요.");
				$("#passwd2").focus();
				return false;
			}
			//비번 틀릴시
			if($("#passwd1").val()==$("#passwd2").val()){
				alert("비밀번호와 비밀번호 확인을 동일하게 입력하세요.");
				$("#passwd1").focus();
				return false;
			}
			
			if($("#join_name").val()==""){
				alert("이름을 입력하세요.");
				$("#join_name").focus();
				return false;
			}
			if($("#join_date").val()==""){
				alert("주민번호 앞자리를 입력하세요.");
				$("#join_date").focus();
				return false;
			}
			if($("#join_date").val().length != 6){
				alert("주민번호 앞자리를 6자리로 입력하세요.");
				$("#join_date").val("").focus();
				return false;
			}
			// isNaN() : 문자가 포함되면 true를 리턴하는 함수
			if(isNaN($("#join_date").val())){
				alert("숫자만 입력하세요.");
				$("#join_date").val("").focus();
				return false;
		    }
			if($("#join_num").val()==""){
				alert("주민번호 뒷자리를 입력하세요.");
				$("#join_num").focus();
				return false;
			}
			if($("#join_num").val().length != 7){
				alert("주민번호 뒷자리 7자리를 입력하세요.");
				$("#join_num").val("").focus();
				return false;
			}
			// isNaN() : 문자가 포함되면 true를 리턴하는 함수
			if(isNaN($("#join_num").val())){
				alert("숫자만 입력하세요.");
				$("#join_num").val("").focus();
				return false;
		    }
			// 우편번호

			if($("#addr_num").val()==""){
				alert("우편번호를 입력하세요.");
				$("#addr_num").focus();
				return false;
			}
			// 주소
			if($("#addr1").val()==""){
				alert("주소를 입력하세요.");
				$("#addr1").focus();
				return false;
			}
			if($("#addr2").val()==""){
				alert("나머지 주소를 입력하세요.");
				$("#addr2").focus();
				return false;
			}
			
			

			if(isNaN($("#tel1").val())){
				alert("전화번호 앞자리는 숫자만 입력하세요.");
				$("#tel1").val("").focus();
				return false;
			}
			if(isNaN($("#tel2").val())){
				alert("전화번호 중간자리는 숫자만 입력하세요.");
				$("#tel2").val("").focus();
				return false;
			}
			if(isNaN($("#tel3").val())){
				alert("전화번호 끝자리는 숫자만 입력하세요.");
				$("#tel3").val("").focus();
				return false;
			}
			
			if($("#phone1").val()==""){
				alert("핸드폰 앞자리를 선택 하세요.");
				return false;
			}
			if($("#phone2").val()==""){
				alert("핸드폰 중간자리를 입력하세요.");
				$("#phone2").focus();
				return false;
			}
			if(isNaN($("#phone2").val())){
				alert("핸드폰 중간자리는 숫자만 입력하세요.");
				$("#phone2").val("").focus();
				return false;
			}
			if($("#phone3").val()==""){
				alert("핸드폰 끝자리를 입력하세요.");
				$("#phone3").focus();
				return false;
			}
			if(isNaN($("#phone3").val())){
				alert("핸드폰 끝자리는 숫자만 입력하세요.");
				$("#phone3").val("").focus();
				return false;
			}

			if($("#emailid").val()==""){
				alert("EMail주소를 입력하세요.");
				$("#emailid").focus();
				return false;
			}
			if($("#emaildomain").val()==""){
				alert("도메인을 입력하세요.");
				$("#emaildomain").focus();
				return false;
			}

			

			
	});	// submit() end	
		
}); // ready() end