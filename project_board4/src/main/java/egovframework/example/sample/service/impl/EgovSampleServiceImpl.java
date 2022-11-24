package egovframework.example.sample.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("sampleService")
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

	/** SampleDAO */
	// TODO ibatis 사용
	@Resource(name = "sampleDAO")
	private SampleDAO sampleDAO;
	// TODO mybatis 사용
	// @Resource(name="sampleMapper")
	// private SampleMapper sampleDAO;

	/////////////////////////////////////////////////////////////////////

	// test list
	@Override
	public List<SampleVO> listUp(SampleVO sampleVO) throws Exception {
		return sampleDAO.listUp(sampleVO);
	}

	// test cnt
	@Override
	public int listCnt(SampleDefaultVO searchVO) throws Exception {
		return sampleDAO.listCnt(searchVO);
	}

	// test detail
	@Override
	public List<SampleVO> listDetail(SampleDefaultVO searchVO) throws Exception {
		return sampleDAO.listDetail(searchVO);
	}

	// 글 등록
	@Override
	public String insertData(SampleVO sampleVO) throws Exception {
		return sampleDAO.insertData(sampleVO);
	}

	// 글 선택(글에 대한 데이터)
	@Override
	public SampleVO selectList(SampleVO sampleVO) throws Exception {
		return sampleDAO.selectList(sampleVO);
	}

	// 글 수정
	@Override
	public void updateData(SampleVO sampleVO) throws Exception {
		sampleDAO.updateData(sampleVO);
	}

	// 글 삭제
	@Override
	public void deleteData(SampleVO sampleVO) throws Exception {
		sampleDAO.deleteData(sampleVO);
	}

	@Override
	public String insertFile(SampleVO sampleVO) throws Exception {
		return sampleDAO.insertFile(sampleVO);
	}

	@Override
	public List<Map<String, Object>> fileInfoByCode(SampleVO sampleVO) throws Exception {
		return sampleDAO.fileInfoByCode(sampleVO);
	}

	@Override
	public SampleVO fileInfoByFileNo(SampleVO sampleVO) throws Exception {
		return sampleDAO.fileInfoByFileNo(sampleVO);
	}

	@Override
	public String updateInsertFile(SampleVO sampleVO) throws Exception {
		return sampleDAO.updateInsertFile(sampleVO);
	}

	@Override
	public void deleteFileByFileNo(SampleVO sampleVO) throws Exception {
		sampleDAO.deleteFileByFileNo(sampleVO);
	}

	@Override
	public List<Map<String, Object>> excelList(SampleVO sampleVO) throws Exception {
		return sampleDAO.excelList(sampleVO);
	}
	
	@Override
	public List<Map<String, Object>> replyList(SampleVO sampleVO) throws Exception {
		return sampleDAO.replyList(sampleVO);
	}

	@Override
	public String insertReply(SampleVO sampleVO) throws Exception {		
		return sampleDAO.insertReply(sampleVO);
	}
	
	@Override
	public void deleteReply(SampleVO sampleVO) throws Exception {
		sampleDAO.deleteReply(sampleVO);
	}

	@Override
	public void updateCountVisit(SampleVO sampleVO) throws Exception {
		sampleDAO.updateCountVisit(sampleVO);
	}
	
	//////////////////////////////////////////////////////////////////////////

	@Override
	public boolean loginCheck(SampleVO sampleVO, HttpSession session) {
		boolean result = sampleDAO.loginCheck(sampleVO);
		if(result) {
			SampleVO sample2 = viewMember(sampleVO);						
			session.setAttribute("userId", sample2.getUserId());
			session.setAttribute("userName", sample2.getUserName());			
		}
		return result;		
	}

	@Override
	public SampleVO viewMember(SampleVO sampleVO) {		
		return sampleDAO.viewMember(sampleVO);
	}

	@Override
	public void logout(HttpSession session) {		
		session.invalidate();
	}

	@Override
	public boolean idCheck(SampleVO sampleVO) {
		boolean result = sampleDAO.idCheck(sampleVO);		
		return result;
	}

	@Override 
	public void insertMember(SampleVO sampleVO) {
		sampleDAO.insertMember(sampleVO);
	}
	 

	
	

}
