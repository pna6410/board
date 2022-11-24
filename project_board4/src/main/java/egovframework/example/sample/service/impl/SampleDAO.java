package egovframework.example.sample.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("sampleDAO")
public class SampleDAO extends EgovAbstractDAO {

	// test list	
	public List<SampleVO> listUp(SampleVO sampleVO) throws Exception {
		return (List<SampleVO>) list("sampleDAO.listUp", sampleVO);
	}

	// test cnt
	public int listCnt(SampleDefaultVO searchVO) {
		return (Integer) select("sampleDAO.listCnt", searchVO);
	}

	// test detail
	public List<SampleVO> listDetail(SampleDefaultVO searchVO) throws Exception {
		return (List<SampleVO>) list("sampleDAO.listDetail", searchVO);
	}

	public String insertData(SampleVO sampleVO) {
		return (String) insert("sampleDAO.insertData", sampleVO);
	}

	public SampleVO selectList(SampleVO sampleVO) {
		return (SampleVO) select("sampleDAO.selectList", sampleVO);
	}

	public void updateData(SampleVO sampleVO) {
		update("sampleDAO.updateData", sampleVO);
	}

	public void deleteData(SampleVO sampleVO) {
		delete("sampleDAO.deleteData", sampleVO);
	}

	public String insertFile(SampleVO sampleVO) {
		return (String) insert("sampleDAO.insertFile", sampleVO);
	}

	public List<Map<String, Object>> fileInfoByCode(SampleVO sampleVO) {
		return (List<Map<String, Object>>) list("sampleDAO.fileInfoByCode", sampleVO);
	}

	public SampleVO fileInfoByFileNo(SampleVO sampleVO) {
		return (SampleVO) select("sampleDAO.fileInfoByFileNo", sampleVO);
	}

	public String updateInsertFile(SampleVO sampleVO) {
		return (String) insert("sampleDAO.updateInsertFile", sampleVO);
	}

	public void deleteFileByFileNo(SampleVO sampleVO) {
		delete("sampleDAO.deleteFileByFileNo", sampleVO);
	}

	public List<Map<String, Object>> excelList(SampleVO sampleVO) {
		return (List<Map<String, Object>>) list("sampleDAO.excelList", sampleVO);
	}

	public List<Map<String, Object>> replyList(SampleVO sampleVO) {
		return (List<Map<String, Object>>) list("sampleDAO.replyList", sampleVO);
	}

	public String insertReply(SampleVO sampleVO) {
		
		return (String) insert("sampleDAO.insertReply", sampleVO);
	}

	public void deleteReply(SampleVO sampleVO) {
		delete("sampleDAO.deleteReply", sampleVO);
	}

	public void updateCountVisit(SampleVO sampleVO) {
		update("sampleDAO.updateCountVisit", sampleVO);
	}

	//////////////////////////////////////////////////////////////////////////
	
	
	public boolean loginCheck(SampleVO sampleVO) {
		String name = (String) select("sampleDAO.loginCheck", sampleVO);
		return (name == null) ? false : true;
	}

	public SampleVO viewMember(SampleVO sampleVO) {		
		return (SampleVO) select("sampleDAO.viewMember", sampleVO);
	}

	public boolean idCheck(SampleVO sampleVO) {
		String name = (String) select("sampleDAO.idCheck", sampleVO);
		return (name == null) ? false : true;
	}

	public void insertMember(SampleVO sampleVO) {
		insert("sampleDAO.insertMember", sampleVO);		
	}
	
	

}
