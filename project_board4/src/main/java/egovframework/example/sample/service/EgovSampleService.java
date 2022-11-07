/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @Class Name : EgovSampleService.java
 * @Description : EgovSampleService Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public interface EgovSampleService {

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	String insertSample(SampleVO vo) throws Exception;

	/**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
	void updateSample(SampleVO vo) throws Exception;

	/**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
	void deleteSample(SampleVO vo) throws Exception;

	/**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
	SampleVO selectSample(SampleVO vo) throws Exception;

	/**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
	List<?> selectSampleList(SampleDefaultVO searchVO) throws Exception;

	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectSampleListTotCnt(SampleDefaultVO searchVO);
	
	///////////////////////////////////////////////////////////
	
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
