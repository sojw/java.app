/*
 * @(#)UrlEncodeTest.java $version 2012. 9. 7.
 *
 * Copyright 2012 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sojw;

/**
 * @author NHN
 * @since 2012. 9. 7.
 */
public class UrlEncodeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String testStr = "뷁";

		byte[] testStrBArray1 = testStr.getBytes("utf8");
		String testStr1 = new String(testStrBArray1, "ms949");
		System.out.println(testStr1);

		byte[] testStrBArray2 = testStr.getBytes("ms949");
		String testStr2 = new String(testStrBArray2, "ms949");
		System.out.println(testStr2);

		byte[] testStrBArray3 = testStr.getBytes("ms949");
		String testStr3 = new String(testStrBArray3, "utf8");
		System.out.println(testStr3);

		byte[] testStrBArray4 = testStr.getBytes("utf8");
		String testStr4 = new String(testStrBArray4, "utf8");
		System.out.println(testStr4);
	}

}