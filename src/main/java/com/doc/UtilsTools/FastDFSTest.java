package com.doc.UtilsTools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;


/**
 * 
 * @描述: FastDFS测试 .
 * @作者: WuShuicheng .
 * @创建时间: 2015-3-29,下午8:11:36 .
 * @版本号: V1.0 .
 */
public class FastDFSTest {
	
	/**
	 * 上传测试.
	 * @throws Exception
	 */
	public static void upload() throws Exception {
		String filePath = "D:\\Java\\Idea\\edu-demo-fdfs\\TestFile\\DubboVideo.jpg";
		File file = new File(filePath);
		String fileId = FastDFSClientUtils.uploadFile(file, filePath);
		System.out.println("Upload local file " + filePath + " ok, fileid=" + fileId);
		// fileId:	group1/M00/00/00/wKgEfVUYPieAd6a0AAP3btxj__E335.jpg
		// url:	http://192.168.4.125:8888/group1/M00/00/00/wKgEfVUYPieAd6a0AAP3btxj__E335.jpg
	}
	
	/**
	 * 下载测试.
	 * @throws Exception
	 */
	public static void download() throws Exception {
		String fileId = "group1/M00/00/00/wKiWhF3Xx0KAWX65AAP3btxj__E589.jpg";
		InputStream inputStream = FastDFSClientUtils.downloadFile(fileId);
		File destFile = new File("D:\\Java\\Idea\\edu-demo-fdfs\\TestFile\\DownloadTest.jpg");
		FileUtils.copyInputStreamToFile(inputStream, destFile);
	}

	/**
	 * 删除测试
	 * @throws Exception
	 */
	public static void delete() throws Exception {
		String fileId = "group1/M00/00/00/wKiWhF3Xx0KAWX65AAP3btxj__E589.jpg";
		int result = FastDFSClientUtils.deleteFile(fileId);
		System.out.println(result == 0 ? "删除成功" : "删除失败:" + result);
	}


	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		upload();
//		download();
//		delete();

	}

}
