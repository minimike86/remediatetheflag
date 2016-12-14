package com.vulnerableapp.results;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipHelper
{
	List<String> fileList;

	ZipHelper(){
		fileList = new ArrayList<String>();
	}

	/**
	 * Zip it
	 * @param zipFile output ZIP file location
	 */
	public void zipIt(String zipFile){

		byte[] buffer = new byte[1024];

		try{

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for(String file : this.fileList){

				ZipEntry ze= new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in =
						new FileInputStream(file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			//remember close it
			zos.close();

		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Traverse a directory and get all files,
	 * and add the file into fileList
	 * @param node file or directory
	 */
	public void addToFileList(File node){

		//add file only
		if(node.isFile()){
			fileList.add(node.getAbsoluteFile().toString());
		}

		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				addToFileList(new File(node, filename));
			}
		}

	}
}
