package project.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import project.model.MemberBean;
import project.service.MemberServiceImpl;


@Controller
public class MemberAction {

	@Autowired
	private MemberServiceImpl memberService;

	// ID중복검사 ajax함수로 처리부분
	@RequestMapping(value = "/member_idcheck.do", method = RequestMethod.POST)
	public String member_idcheck(@RequestParam("memid") String id, Model model) throws Exception {

		int result = memberService.checkMemberId(id);
		model.addAttribute("result", result);

		return "member/idcheckResult";
	}

	/* 로그인 폼 뷰 */
	@RequestMapping(value = "/member_login.do")
	public String member_login() {
		System.out.println("member_login");
		return "member/member_login";
	}

	/* 비번찾기 폼 */
	@RequestMapping(value = "/pwd_find.do")
	public String pwd_find() {
		return "member/pwd_find";
		// member 폴더의 pwd_find.jsp 뷰 페이지 실행
	}

	/* 회원가입 폼 */
	@RequestMapping(value = "/member_join.do")
	public String member_join() {
		System.out.print("member_join.do");
		return "member/member_join";
		// member 폴더의 member_join.jsp 뷰 페이지 실행
	}


	/* 비번찾기 완료 */
	@RequestMapping(value = "/pwd_find_ok.do", method = RequestMethod.POST)
	public String pwd_find_ok(@ModelAttribute MemberBean mem, HttpServletResponse response, Model model)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		MemberBean member = memberService.findpwd(mem);

		if (member == null) {// 값이 없는 경우

			return "member/pwdResult";

		} else {

			// Mail Server 설정
			String charSet = "utf-8";
			String hostSMTP = "smtp.naver.com";
			String hostSMTPid = "chlchdyd@naver.com";
			String hostSMTPpwd = "Pr84528542@"; // 비밀번호 입력해야함

			// 보내는 사람 EMail, 제목, 내용
			String fromEmail = "chlchdyd@naver.com";
			String fromName = "관리자";
			String subject = "비밀번호 찾기";

			// 받는 사람 E-Mail 주소
			String mail = member.getEmail();

			try {
				HtmlEmail email = new HtmlEmail();
				email.setDebug(true);
				email.setCharset(charSet);
				email.setSSL(true);
				email.setHostName(hostSMTP);
				email.setSmtpPort(587);

				email.setAuthentication(hostSMTPid, hostSMTPpwd);
				email.setTLS(true);
				email.addTo(mail, charSet);
				email.setFrom(fromEmail, fromName, charSet);
				email.setSubject(subject);
				email.setHtmlMsg("<p align = 'center'>비밀번호 찾기</p><br>" + "<div align='center'> 비밀번호 : "
						+ member.getPasswd1() + "</div>");
				//member.getJoin_pwd() - 임시비번 설정
				email.send();
			} catch (Exception e) {
				System.out.println(e);
			}

			model.addAttribute("pwdok", "등록된 email을 확인 하세요~!!");
			return "member/pwd_find";

		}

	}


	
	/* 회원 가입 저장 */
	@RequestMapping(value = "/member_join_ok.do", method = RequestMethod.POST)
	public String member_join_ok(MemberBean member,
								 HttpServletRequest request,
								 Model model) throws Exception {
		
		System.out.println("member_join_ok.do");
		
		int result=0;
		
		String tel1 = request.getParameter("tel1");
		String tel2 = request.getParameter("tel2");
		String tel3 = request.getParameter("tel3");
		String tel = tel1 + "-" + tel2 + "-" + tel3;
		
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		
		String emailid = request.getParameter("emailid");
		String emaildomain = request.getParameter("emaildomain");
		String email = emailid + "@" + emaildomain;
		
		
		member.setTel(tel);
		member.setPhone(phone);
		member.setEmail(email);
		System.out.println("member_join_ok.do - member");
		
		memberService.insertMember(member);
		
		System.out.println("member_join_ok.do - E");
		
		return "redirect:member_login.do";
	}


	
	/* 로그인 인증 */
	@RequestMapping(value = "/member_login_ok.do", method = RequestMethod.POST)
	public String member_login_ok(@RequestParam("id") String id, 
			                      @RequestParam("pwd") String pwd,
			                      HttpSession session, 
			                      Model model) throws Exception {
		System.out.println("/member_login_ok.do");
		int result=0;		
		MemberBean mb = memberService.userCheck(id);

		if (mb == null) {	// 등록되지 않은 회원일때
			
			result = 1;
			model.addAttribute("result", result);
			
			System.out.println("등록되지 않은 회원일때");
			return "member/loginResult";
			
		} else {			// 등록된 회원일때
			if (mb.getPasswd1().equals(pwd)) {// 비번이 같을때
				session.setAttribute("id", id);

				String join_name = mb.getJoin_name();

				model.addAttribute("join_name", join_name);
				
				System.out.println("id = " + id);
				return "member/main";
				
			} else {// 비번이 다를때
				result = 2;
				model.addAttribute("result", result);
				
				System.out.println("비번이 다를때");
				return "member/loginResult";				
			}
		}

	}

	/* 회원정보 수정 폼 */
	@RequestMapping(value = "/member_edit.do")
	public String member_edit(HttpSession session, Model m) throws Exception {
		System.out.println("/member_edit.do");
		
		String id = (String) session.getAttribute("id");

		MemberBean mbbe = memberService.userCheck(id);
		
		
		String tel = mbbe.getTel();
		StringTokenizer st01 = new StringTokenizer(tel, "-");
		// java.util 패키지의 StringTokenizer 클래스는 첫번째 전달인자를
		// 두번째 -를 기준으로 문자열을 파싱해준다.
		String tel1 = st01.nextToken();// 첫번째 전화번호 저장
		String tel2 = st01.nextToken(); // 두번째 전번 저장
		String tel3 = st01.nextToken();// 세번째 전번 저장
		
		
		String phone = mbbe.getPhone();
		StringTokenizer st02 = new StringTokenizer(phone, "-");
		// java.util 패키지의 StringTokenizer 클래스는 첫번째 전달인자를
		// 두번째 -를 기준으로 문자열을 파싱해준다.
		String phone1 = st02.nextToken();// 첫번째 전화번호 저장
		String phone2 = st02.nextToken(); // 두번째 전번 저장
		String phone3 = st02.nextToken();// 세번째 전번 저장
		
		
		String email = mbbe.getEmail();
		email += "daum.net";
		// 임시로 추가한 이메일 도메인
		StringTokenizer st03 = new StringTokenizer(email, "@");
		String emailid = st03.nextToken();// 첫번째 전화번호 저장
		String emaildomain = st03.nextToken(); // 두번째 전번 저장
		
		System.out.println(email + " = " + emailid + emaildomain);
		
		m.addAttribute("mbbe", mbbe);
		m.addAttribute("tel1", tel1);
		m.addAttribute("tel2", tel2);
		m.addAttribute("tel3", tel3);
		m.addAttribute("phone1", phone1);
		m.addAttribute("phone2", phone2);
		m.addAttribute("phone3", phone3);
		m.addAttribute("emailid", emailid);
		m.addAttribute("emaildomain", emaildomain);
		// 따로 따로 해 놓아야 view 페이지에서 출력이 가능하다.
		
		return "member/member_edit";
	}


	
	
//	/* 회원정보 수정 */
//	@RequestMapping(value = "/member_edit_ok.do", method = RequestMethod.POST)
//	public String member_edit_ok(@RequestParam("join_profile1") MultipartFile mf, 
//								 MemberBean member,
//								 HttpServletRequest request, 
//								 HttpSession session, 
//								 Model model) throws Exception {
//
//		String filename = mf.getOriginalFilename();
//		int size = (int) mf.getSize();		
//		
//		String path = request.getRealPath("upload");
//		
//		int result=0;		
//		String file[] = new String[2];
//
//		
//			
//		
//
//		String id = (String) session.getAttribute("id");
//
//		String join_tel1 = request.getParameter("join_tel1").trim();
//		String join_tel2 = request.getParameter("join_tel2").trim();
//		String join_tel3 = request.getParameter("join_tel3").trim();
//		String join_tel = join_tel1 + "-" + join_tel2 + "-" + join_tel3;
//		String join_phone1 = request.getParameter("join_phone1").trim();
//		String join_phone2 = request.getParameter("join_phone2").trim();
//		String join_phone3 = request.getParameter("join_phone3").trim();
//		String join_phone = join_phone1 + "-" + join_phone2 + "-" + join_phone3;
//		String join_mailid = request.getParameter("join_mailid").trim();
//		String join_maildomain = request.getParameter("join_maildomain").trim();
//		String join_email = join_mailid + "@" + join_maildomain;
//		// 전화번호/핸드폰/메일주소를 결합하는 작업
//		
//		
//		MemberBean editm = this.memberService.userCheck(id);		
//		
//		member.setJoin_id(id);
//		member.setTel(join_tel);
//		member.setPhone(join_phone);
//		member.setEmail(join_email);
//
//		memberService.updateMember(member);// 수정 메서드 호출
//
//		model.addAttribute("join_name", member.getJoin_name());
//
//		return "member/main";
//	}
//
	
	
	
	/* 회원정보 삭제 폼 */
	@RequestMapping(value = "/member_del.do")
	public String member_del(HttpSession session, Model dm) throws Exception {

		String id = (String) session.getAttribute("id");
		MemberBean deleteM = memberService.userCheck(id);
		dm.addAttribute("d_id", id);
		dm.addAttribute("d_name", deleteM.getJoin_name());

		return "member/member_del";
	}

//	/* 회원정보 삭제 완료 */
//	@RequestMapping(value = "/member_del_ok.do", method = RequestMethod.POST)
//	public String member_del_ok(@RequestParam("pwd") String pass, 
//							    @RequestParam("del_cont") String del_cont,
//							    HttpSession session) throws Exception {
//
//		String id = (String) session.getAttribute("id");
//		MemberBean member = this.memberService.userCheck(id);
//
//		if (!member.getPasswd1().equals(pass)) {
//
//			return "member/deleteResult";
//			
//		} else {// 비번이 같은 경우
//			
//			String up = session.getServletContext().getRealPath("upload");
//			System.out.println("up:"+up);
//			
//			MemberBean delm = new MemberBean();
//			delm.setJoin_id(id);
//			delm.setJoin_delcont(del_cont);
//
//			memberService.deleteMember(delm);// 삭제 메서드 호출
//
//			session.invalidate();	// 세션만료
//
//			return "redirect:member_login.do";
//		}
//	}

	// 로그아웃
	@RequestMapping("member_logout.do")
	public String logout(HttpSession session) {
		session.invalidate();

		return "member/member_logout";
	}

}
