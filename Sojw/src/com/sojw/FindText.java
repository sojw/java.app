/*
 * @(#)FileRead.java $version 2012. 7. 10.
 *
 * Copyright 2012 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sojw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author NHN
 * @since 2012. 7. 10.
 */
public class FindText {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String filePath = "C:\\dev\\workspace\\nmobile_RB-5.18.1\\src\\main\\webapp\\css\\";
		readFile(filePath);
	}

	/**
	 * @param filePath
	 * @throws Exception
	 */
	private static void readFile(String filePath) throws Exception {
		if (filePath == null || filePath.equals("")) {
			return;
		}

		File f;
		try {
			f = new File(filePath);
			File[] fileList = f.listFiles();
			if (fileList == null && f.isFile()) {
				appendTail(f);
			} else {
				for (File tempFile : fileList) {
					if (tempFile.isDirectory()) {
						if (!tempFile.getAbsolutePath().contains(".svn")) {
							readFile(tempFile.getAbsolutePath());
						}
					} else if (tempFile.isFile()) {
						appendTail(tempFile);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			f = null;
		}

	}

	/**
	 * @param file
	 * @throws IOException
	 */
	private static void appendTail(File file) throws IOException {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			bw.append(" ");
			bw.flush();
		} catch (Exception e) {
		} finally {
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
		System.out.println(file.getAbsolutePath() + " append ok.");
	}
}