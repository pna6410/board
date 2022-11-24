package egovframework.example.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public interface EgovSampleService {

	List<SampleVO> listUp(SampleVO sampleVO) throws Exception;
	
	int listCnt(SampleDefaultVO searchVO) throws Exception;
	
	List<SampleVO> listDetail(SampleDefaultVO searchVO) throws Exception;

	String insertData(SampleVO sampleVO) throws Exception;

	SampleVO selectList(SampleVO sampleVO) throws Exception;

	void updateData(SampleVO sampleVO) throws Exception;

	void deleteData(SampleVO sampleVO) throws Exception;

	String insertFile(SampleVO sampleVO) throws Exception;

	List<Map<String, Object>> fileInfoByCode(SampleVO sampleVO) throws Exception;

	SampleVO fileInfoByFileNo(SampleVO sampleVO) throws Exception;

	String updateInsertFile(SampleVO sampleVO) throws Exception;

	void deleteFileByFileNo(SampleVO sampleVO) throws Exception;

	List<Map<String, Object>> excelList(SampleVO sampleVO) throws Exception;
	
	List<Map<String, Object>> replyList(SampleVO sampleVO) throws Exception;

	String insertReply(SampleVO sampleVO) throws Exception;

	void deleteReply(SampleVO sampleVO) throws Exception;

	void updateCountVisit(SampleVO sampleVO) throws Exception;

	//////////////////////////////////////////////////////////////////////////
	
	boolean loginCheck(SampleVO sampleVO, HttpSession session);

	SampleVO viewMember(SampleVO sampleVO);

	void logout(HttpSession session);

	boolean idCheck(SampleVO sampleVO);

	void insertMember(SampleVO sampleVO);
	

}
