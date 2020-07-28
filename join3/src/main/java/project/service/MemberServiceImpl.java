package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dao.MemberDAOImpl;
import project.model.MemberBean;

@Service
public class MemberServiceImpl {
	@Autowired
	private MemberDAOImpl memberDao;
	
	public int checkMemberId(String id) throws Exception{
		System.out.println("service - checkMemberId");
		return memberDao.checkMemberId(id);
	}
	
	public MemberBean findpwd(MemberBean member)throws Exception {
		System.out.println("service - findpwd");
		return memberDao.findpwd(member);
	}
	
	public void insertMember(MemberBean member) throws Exception{
		System.out.println("service - insertMember");
		memberDao.insertMember(member);
	}
	
	public MemberBean userCheck(String id) throws Exception{
		System.out.println("service - userCheck");
		return memberDao.userCheck(id);		
	}
	
	public void updateMember(MemberBean member) throws Exception{
		System.out.println("service - updateMember");
		memberDao.updateMember(member);
	}
	
	public void deleteMember(MemberBean member) throws Exception{
		System.out.println("service - updateMember");
		memberDao.deleteMember(member);
	}
}
