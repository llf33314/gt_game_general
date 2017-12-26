package com.gt.game.core.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 上传组件
 * @author hongjiye
 * @time 2017/11/30 0030.
 */
public class UploaderUtil {


	public static boolean uploadFile(MultipartFile file, String filePath){
		boolean flag = true;
		try {
			File path = new File(filePath);
			if (!path.exists()) {
				path.mkdirs();
			}
			file.transferTo(path);
			ContinueFTP myFtp = new ContinueFTP();
			try {
				myFtp.upload(path.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
