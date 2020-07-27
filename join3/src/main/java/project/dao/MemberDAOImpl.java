package project.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.model.MemberBean;

@Repository
public class MemberDAOImpl {
	
	@Autowired
	private SqlSession sqlSession;	


	/* 아이디 중복 체크 */
	public int checkMemberId(String id) throws Exception {
		int re = -1;	// 사용 가능한 ID
		MemberBean mb = (MemberBean) sqlSession.selectOne("login_check", id);
		if (mb != null)
			re = 1; 	// 중복id
		return re;
	}
	
	/* 회원저장 */
	public void insertMember(MemberBean m) throws Exception {
		System.out.println("dao - insertMember");
		sqlSession.insert("member_join", m);
	}

	/* 비번 검색 */
	public MemberBean findpwd(MemberBean pm) throws Exception {
		return (MemberBean) sqlSession.selectOne("pwd_find", pm);
	}

	/* 로그인 인증 체크 */
	public MemberBean userCheck(String id) throws Exception {
		return (MemberBean) sqlSession.selectOne("login_check", id);
	}

	/* 회원수정 */
	public void updateMember(MemberBean member) throws Exception {
		sqlSession.update("member_edit", member);
	}

	/* 회원삭제 */
	public void deleteMember(MemberBean delm) throws Exception {
		sqlSession.update("member_delete", delm);
	}
}





