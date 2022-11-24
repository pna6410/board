package egovframework.example.sample.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class EgovSampleController {
	
	///////////////////////////// 절대 경로 context-common.xml에 선언해준 것 가져오기
	@Resource(name="uploadPath")
	String uploadPath;
	/////////////////////////////////////////////

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	//////////////////////////////////////////////////////// 메인화면
	@RequestMapping(value = "/listUp.do")
	public String listUp(@ModelAttribute("sampleVO") SampleVO sampleVO, ModelMap model) throws Exception {

		sampleVO.setPageUnit(propertiesService.getInt("pageUnit"));
		sampleVO.setPageSize(propertiesService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sampleVO.getPageIndex());// 1
		paginationInfo.setRecordCountPerPage(sampleVO.getPageUnit());// 10
		paginationInfo.setPageSize(sampleVO.getPageSize());// 10

		sampleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sampleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		sampleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 목록 나오게 하기
		List<SampleVO> testList = sampleService.listUp(sampleVO);
		model.addAttribute("resultList", testList);
		System.out.println(testList);

		// 총 개수 구하기 testListCnt
		int totCnt = sampleService.listCnt(sampleVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totCnt", totCnt);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map result = objectMapper.convertValue(sampleVO, Map.class);
		model.addAttribute("searchFormData", result);		

		return "sample/listUp";
	}

	//////////////////////////////////////////////////////// 상세 화면
	@RequestMapping(value = "/listDetail.do")
	public String listDetail(@ModelAttribute("sampleVO") SampleVO sampleVO, ModelMap model) throws Exception {
		System.out.println("listDetail call");
					
		// 조회수 증가
		sampleService.updateCountVisit(sampleVO);
		
		// 목록 나오게 하기		
		List<SampleVO> listDetail = sampleService.listDetail(sampleVO);
		model.addAttribute("resultList", listDetail);
		// 첨부파일 리스트		
		List<Map<String, Object>> fileList = sampleService.fileInfoByCode(sampleVO);
		model.addAttribute("fileList", fileList);
		// 댓글 리스트
		List<Map<String, Object>> replyList = sampleService.replyList(sampleVO);
		model.addAttribute("replyList", replyList);
		
		Map result = new ObjectMapper().convertValue(sampleVO, Map.class);
		model.addAttribute("inputFormData", result);
		
		return "sample/listDetail";
	}

	//////////////////////////////////////////////////////// 등록 화면 (파일 등록)
	@RequestMapping(value = "listInsert.do")
	public String listInsert(@ModelAttribute("sampleVO") SampleVO sampleVO, Model model) throws Exception {
		System.out.println("listInsert call");
		model.addAttribute("sampleVO", new SampleVO());
		
		Map result = new ObjectMapper().convertValue(sampleVO, Map.class);
		model.addAttribute("inputFormData", result);
		
		return "sample/listInsertUpdate";
	}

	@RequestMapping(value = "insert_data.do")
	public String insert_data(@ModelAttribute("sampleVO") SampleVO sampleVO, SessionStatus status,
			@RequestParam("multiFile") MultipartFile[] multiFile, HttpServletRequest request,
			ModelMap model) throws Exception {
		System.out.println("insert_data call");

		sampleService.insertData(sampleVO);

		// 저장할 경로 가져오기
		String path = uploadPath;
		//String path = request.getSession().getServletContext().getRealPath("resources");
		System.out.println("path : " + path);		
		//String root = path + "\\uploadFiles";

		File file = new File(path);

		// 경로에 해당하는 폴더가 없으면 생성해라 라는 뜻
		if (!file.exists())
			file.mkdirs();

		for (int i = 0; i < multiFile.length; i++) {
			// 업로드할 폴더 설정
			String originFileName = multiFile[i].getOriginalFilename();
			String ext = originFileName.substring(originFileName.lastIndexOf("."));
			String ranFileName = UUID.randomUUID().toString() + ext;

			File changeFile = new File(path + File.separator + ranFileName);
			
			// 파일업로드
			try {
				multiFile[i].transferTo(changeFile);
				System.out.println("파일 업로드 성공");
			} catch (IllegalStateException | IOException e) {
				System.out.println("파일 업로드 실패");
				e.printStackTrace();
			}

			long bytes = changeFile.length();			
			sampleVO.setOriginFileName(originFileName);
			sampleVO.setStoredFileName(ranFileName);
			sampleVO.setPath(path);
			sampleVO.setFileSize(bytes);

			sampleService.insertFile(sampleVO);
		}

		status.setComplete();

		return "redirect:/listUp.do";
		//return listUp(sampleVO, model);
	}

	//////////////////////////////////////////////////////// 수정 화면
	@RequestMapping("/listUpdate.do")
	public String listUpdate(@ModelAttribute("sampleVO") SampleVO sampleVO, 
			ModelMap model) throws Exception {
		System.out.println("listUpdate call");
		System.out.println(sampleVO.getCode());
		
		model.addAttribute(selectList(sampleVO));  // sampleVO에 값이 들어가서 path로 값을 가져다 쓴다.
		/* model.addAttribute("resultList", selectList(sampleVO, searchVO)); */  // 코드 값을 토대로 글의 정보를 가져옴
        
		List<Map<String, Object>> fileList = sampleService.fileInfoByCode(sampleVO);
		model.addAttribute("fileList", fileList);
		
		Map result = new ObjectMapper().convertValue(sampleVO, Map.class);
		model.addAttribute("inputFormData", result);
		return "sample/listInsertUpdate";
	}

	public SampleVO selectList(SampleVO sampleVO) throws Exception {
		return sampleService.selectList(sampleVO);
	}

	@RequestMapping("/update_data.do")
	public String updateData(@ModelAttribute("sampleVO") SampleVO sampleVO, SessionStatus status,
			@RequestParam("multiFile") MultipartFile[] multiFile, HttpServletRequest request, 
			ModelMap model) throws Exception {
		System.out.println("update_data call");

		System.out.println(sampleVO);		

		sampleService.updateData(sampleVO);// 글 정보를 수정(코드값 필요)

		// 저장할 경로 가져오기
		String path = uploadPath;		
		System.out.println("path : " + path);

		File file = new File(path);

		// 만약 uploadFiles 폴더가 없으면 생성해라 라는뜻
		if (!file.exists())
			file.mkdirs();

		for (int i = 0; i < multiFile.length; i++) {
			// 업로드할 폴더 설정
			String originFileName = multiFile[i].getOriginalFilename();
			String ext = originFileName.substring(originFileName.lastIndexOf("."));
			String ranFileName = UUID.randomUUID().toString() + ext;

			File changeFile = new File(path + File.separator + ranFileName);
			System.out.println("changeFile : " + changeFile);

			// 파일업로드
			try {
				multiFile[i].transferTo(changeFile);
				System.out.println("파일 업로드 성공");
			} catch (IllegalStateException | IOException e) {
				System.out.println("파일 업로드 실패");
				e.printStackTrace();
			}

			long bytes = changeFile.length();
			sampleVO.setOriginFileName(originFileName);
			sampleVO.setStoredFileName(ranFileName);
			sampleVO.setPath(path);
			sampleVO.setFileSize(bytes);

			sampleService.updateInsertFile(sampleVO);
		}

		status.setComplete();
		//String back = "redirect:/listDetail.do?code=" + request.getParameter("thisCode");
		return listDetail(sampleVO, model);
	}

	//////////////////////////////////////////////////////// 글 삭제 (파일 다중 삭제) (댓글 다중 삭제)
	@RequestMapping("/delete_data.do")
	public String deleteData(@ModelAttribute("sampleVO") SampleVO sampleVO, SessionStatus status, 
			ModelMap model) throws Exception {
		System.out.println("delete_data call");
		System.out.println(sampleVO); // 코드값만 가지고 있음

		
		List<Map<String, Object>> fileList = sampleService.fileInfoByCode(sampleVO);

		for (int i = 0; i < fileList.size(); i++) {
			String path = (String) fileList.get(i).get("path");
			String storedFileName = (String) fileList.get(i).get("storedFileName");
			sampleVO.setFileNo((int) fileList.get(i).get("fileNo"));

			System.out.println("path = " + path);
			System.out.println("storedFileName = " + storedFileName);

			File file = new File(path + File.separator + storedFileName);

			if (file.exists()) { // 파일이 존재하면
				file.delete(); // 파일 삭제
			}
			sampleService.deleteFileByFileNo(sampleVO); // 첨부파일 삭제
		}
		
		List<Map<String, Object>> replyList = sampleService.replyList(sampleVO);
		
		for(int i = 0; i < replyList.size(); i++) {
			sampleVO.setReplyNo((int)(replyList.get(i).get("replyNo")));
			sampleService.deleteReply(sampleVO); // 댓글 삭제
		}

		sampleService.deleteData(sampleVO); // 글 삭제
		status.setComplete();		
				
		return listUp(sampleVO, model);
	}

	//////////////////////////////////////////////////////// 파일 다운로드	
	@RequestMapping("/downloadFile.do")
	public void downloadFile(@ModelAttribute("sampleVO") SampleVO sampleVO, HttpServletResponse response)
			throws Exception {
		System.out.println("downloadFile call");

		// 파일 이름, 코드 받아와야됨
		System.out.println(sampleVO.getFileNo());

		SampleVO fileVO = new SampleVO();
		fileVO = sampleService.fileInfoByFileNo(sampleVO);

		System.out.println("파일 이름 : " + fileVO.getOriginFileName());
		System.out.println("파일 저장된 이름 : " + fileVO.getStoredFileName());
		System.out.println("파일 주소 : " + fileVO.getPath());		

		String fileName = new String(fileVO.getOriginFileName().getBytes("UTF-8"), "ISO-8859-1");

		try {

			//String path = fileVO.getPath() + "\\" + fileVO.getStoredFileName(); // 경로에 접근할 때 역슬래시('\') 사용
			File file = new File(fileVO.getPath(), fileVO.getStoredFileName());			
			
			// 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			
			FileInputStream fileInputStream = new FileInputStream(file.getPath()); // 파일 읽어오기
			OutputStream out = response.getOutputStream();

			int read = 0;
			byte[] buffer = new byte[1024];
			// 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
			while ((read = fileInputStream.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}

		} catch (Exception e) {
			throw new Exception("download error");
		}
	}

	//////////////////////////////////////////////////////// 파일 삭제 (단일 삭제)
	@RequestMapping("/deleteFile.do")
	public String deleteFile(@ModelAttribute("sampleVO") SampleVO sampleVO, 
			ModelMap model,	HttpServletRequest request) throws Exception {
		System.out.println("deleteFile call");
		System.out.println(sampleVO.getFileNo());

		SampleVO fileVO = new SampleVO();
		fileVO = sampleService.fileInfoByFileNo(sampleVO);

		File file = new File(fileVO.getPath(), fileVO.getStoredFileName());

		if (file.exists()) { // 파일이 존재하면
			file.delete(); // 파일 삭제
		}

		sampleService.deleteFileByFileNo(sampleVO); // 파일 테이블에서 해당 파일넘버 튜플 삭제

		//String referer = request.getHeader("referer");
		//return "redirect:" + referer;
		//return listUpdate(sampleVO, model, request);		
		return listUpdate(sampleVO, model);
	}
	
	//////////////////////////////////////////////////////// 엑셀 파일 다운로드	
	@RequestMapping("/excelDownload.do")
	public void excelDownload(@ModelAttribute("sampleVO") SampleVO sampleVO,						
			HttpServletResponse response) throws Exception {
		System.out.println("excelDownload call");
				
		List<Map<String, Object>> excelList = sampleService.excelList(sampleVO);		
		
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("첫번째 시트");
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		// Header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("게시물 번호");
		cell = row.createCell(1);
		cell.setCellValue("코드");
		cell = row.createCell(2);
		cell.setCellValue("제목");
		cell = row.createCell(3);
		cell.setCellValue("등록자");
		cell = row.createCell(4);
		cell.setCellValue("등록일");
        
		// Body
		for (int i = 0; i < excelList.size(); i++) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell = row.createCell(1);
			cell.setCellValue(String.valueOf(excelList.get(i).get("code")));
			cell = row.createCell(2);
			cell.setCellValue(String.valueOf(excelList.get(i).get("title")));
			cell = row.createCell(3);
			cell.setCellValue(String.valueOf(excelList.get(i).get("name")));			
			cell = row.createCell(4);
			cell.setCellValue(String.valueOf(excelList.get(i).get("createDate")));
		}
        
		// 컨텐츠 타입과 파일명 지정 
		response.setContentType("ms-vsd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=boardList.xlsx");
		
		// Excel File Output 
		wb.write(response.getOutputStream()); 
		wb.close();		
	}
	
	//////////////////////////////////////////////////////// 댓글 추가	
	@RequestMapping("/addReply.do")
	public String addReply(@ModelAttribute("sampleVO") SampleVO sampleVO,			
			HttpServletRequest request, ModelMap model) throws Exception{
		System.out.println("addReply call");
		System.out.println(sampleVO.getCode());
		
		
		sampleService.insertReply(sampleVO);
		
		//String referer = request.getHeader("referer");
		//return "redirect:" + referer;
		return listDetail(sampleVO, model);
		//return listDetail(sampleVO, model);
		//return "redirect:/listDetail.do";
		//return "forward:/listDetail.do"; // 새로고침할때마다 문제 생김
	}
	
	//////////////////////////////////////////////////////// 댓글 삭제 (단일 삭제)
	@RequestMapping("/deleteReply.do")
	public String deleteReply(@ModelAttribute("sampleVO") SampleVO sampleVO,			
			HttpServletRequest request, ModelMap model) throws Exception{
		System.out.println("deleteReply call");
		System.out.println(sampleVO.getReplyNo());
		
		sampleService.deleteReply(sampleVO);
		
		//String referer = request.getHeader("referer");
		//return "redirect:" + referer;
		return listDetail(sampleVO, model);
	}
	
	//////////////////////////////////////////////////////// 로그인 화면
	@RequestMapping("/login.do")
	public String login(@ModelAttribute SampleVO sampleVO, ModelMap model) throws Exception {
		System.out.println("login call");
		
		Map result = new ObjectMapper().convertValue(sampleVO, Map.class);
		model.addAttribute("inputFormData", result);
		return "login/login";
	}
	
	@RequestMapping("/loginCheck.do")
	public ModelAndView loginCheck(@ModelAttribute SampleVO sampleVO, HttpSession session) throws Exception {
		System.out.println("loginCheck call");
		
		System.out.println(sampleVO.getUserId());
		System.out.println(sampleVO.getUserPw());
		
		boolean result = sampleService.loginCheck(sampleVO, session);
		ModelAndView mav = new ModelAndView();
		if(result == true) {
			mav.setViewName("redirect:/listUp.do");
			// mav.addObject("msg", "success");
		} else {
			mav.setViewName("login/login");
			mav.addObject("msg", "failure");
		}
		
		System.out.println(result);
		System.out.println(mav);
		
		return mav;
	}
	
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session) {
		System.out.println("logout call");
		
		sampleService.logout(session);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/listUp.do");
		// mav.addObject("msg", "logout");
		
		return mav;
	}
	
	//////////////////////////////////////////////////////// 회원가입 화면	
	@RequestMapping("/signUp.do")
	public String signUp(@ModelAttribute SampleVO sampleVO, ModelMap model) throws Exception {
		System.out.println("signUp call");
		
		Map result = new ObjectMapper().convertValue(sampleVO, Map.class);
		model.addAttribute("inputFormData", result);
		return "login/signUp";
	}
	
	@RequestMapping("/insertMember.do")
	public ModelAndView insertMember(@ModelAttribute SampleVO sampleVO) {
		System.out.println("insertMember call");
				
		boolean result = sampleService.idCheck(sampleVO); // 입력한 id가 이미 사용 중이면 true / 없으면 false
		System.out.println(result);
		
		ModelAndView mav = new ModelAndView();
		if(result == false) { // id가 db에 존재하지 않으면 새로운 회원정보 db에 저장
			sampleService.insertMember(sampleVO);
			mav.setViewName("login/login");
			mav.addObject("msg", "success");
		} else {
			mav.setViewName("login/signUp");
			mav.addObject("msg", "failure");
		}		
		return mav;
	}
	
	////////////////////////////////////////////////////////테스트 페이지
	@RequestMapping("/testPage.do")
	public String testPage(@ModelAttribute("sampleVO") SampleVO sampleVO, ModelMap model) throws Exception {
		
	
		return "sample/testPage";	
	}
	

	
	
	
}

