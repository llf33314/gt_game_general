package com.gt.game.core.util;

import java.io.File;

/**
 * @author hongjiye
 * @time 2017-12-06 11:51
 **/
public class FileUtil {
	/**
	 * 删除文件
	 * @param path 路径(绝对路径)
	 * @return 返回 -1: 删除失败出现异常 0:该路径是管理后台的资源，不能删除，可继续下一步操作 1:删除文件(夹)成功 ,2:目录不存在
	 */
	public static int  delFile(String path){
		int imgIndex=(path==null||path.isEmpty())?-2:path.indexOf("upload/1");
		int result=-1;
		if(imgIndex>0){//管理后台的图片，不能删除
			result=0;
		}else if(imgIndex==-2){//path为空
			result=1;
		}else{
			// String tempPth=getPath(path);
			File file=new File(path);
			if(file.exists()&&file.isDirectory()){//当path是一个目录时,先通过递归的方式删除目录下的所有文件
				result=deleteDir(file)?1:-1;
			}else if(file.exists()&&file.isDirectory()==false) {//当好path是一个文件时，直接删除文件
				result=file.delete()?1:-1;
			}else{
				result=2;
			}
		}
		return result;
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
