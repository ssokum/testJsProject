package com.spring.JspringProject.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.common.ProjectProvide;
import com.spring.JspringProject.dao.PdsDao;
import com.spring.JspringProject.vo.PdsVo;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDao pdsDao;
	
	@Autowired
	PdsService pdsService;
	
	@Autowired
	ProjectProvide projectProvide;

	@Override
	public List<PdsVo> getPdsList(int startIndexNo, int pageSize, String part, String search, String searchString) {
		return pdsDao.getPdsList(startIndexNo, pageSize, part, search, searchString);
	}

	@Override
	public int setPdsInput(MultipartHttpServletRequest mFName, PdsVo vo) {
		// 파일 업로드 처리
		try {
			List<MultipartFile> fileList = mFName.getFiles("file");
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = projectProvide.saveFileName(oFileName);
				projectProvide.writeFile(file, sFileName, "pds");
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			
			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);			
		} catch (IOException e) { e.printStackTrace(); }
		// 데이터베이스에 저장하기
		return pdsDao.setPdsInput(vo);
	}

	@Override
	public int setPdsDelete(int idx, String fSName, HttpServletRequest request) {
	  String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		String[] fSNames = fSName.split("/");
		
		for(int i=0; i<fSNames.length; i++) {
			new File(realPath + fSNames[i]).delete();
		}
		
		return pdsDao.setPdsDelete(idx);
	}

	@Override
	public int setPdsDownNumPlus(int idx) {
		return pdsDao.setPdsDownNumPlus(idx);
	}

	@Override
	public PdsVo getPdsContent(int idx) {
		return pdsDao.getPdsContent(idx);
	}

	@Override
	public String pdsTotalDown(HttpServletRequest request, int idx) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		PdsVo vo = pdsService.getPdsContent(idx);
		
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		String zipPath = realPath + "temp/";
		String zipName = vo.getTitle() + ".zip";
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		ZipOutputStream zout = null;
		
		try {
			zout = new ZipOutputStream(new FileOutputStream(zipPath + zipName));
		} catch (FileNotFoundException e1) { e1.printStackTrace(); }
		
		byte[] bytes = new byte[2048];
		
		for(int i=0; i<fNames.length; i++) {
			try {
				fis = new FileInputStream(realPath + fSNames[i]);
				fos = new FileOutputStream(zipPath + fNames[i]);
				File copyFile = new File(zipPath + fNames[i]);
				
				int data = 0;
				while((data = fis.read(bytes, 0, bytes.length)) != -1) {
					fos.write(bytes, 0, data);
				}
				fos.flush();
				fos.close();
				fis.close();
				
				fis = new FileInputStream(copyFile);
				zout.putNextEntry(new ZipEntry(fNames[i]));
				while((data = fis.read(bytes, 0, bytes.length)) != -1) {
					zout.write(bytes, 0, data);
				}
				zout.flush();
				zout.closeEntry();
				fis.close();
				
			} catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) {e.printStackTrace();}
		}
		try {
			zout.close();
		} catch (IOException e) { e.printStackTrace(); }
		
		// 작업완료후... 
		
		// 서버의 기본파일 삭제처리(temp폴터의 파일 삭제처리, zip파일 제외)
		File folder = new File(zipPath);
		File[] files = folder.listFiles();
		if(files.length != 0) {
			for(File file : files) {
				String fName = file.toString();
				if(!zipName.equals(fName.substring(fName.lastIndexOf("\\")+1))) file.delete();
			}
		}
		
		//클라이언트로 다운로드....
		return zipName;
	}
	
}
