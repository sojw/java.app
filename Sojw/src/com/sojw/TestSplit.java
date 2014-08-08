/*
 * @(#)TestSplit.java $version 2013. 1. 24.
 *
 * Copyright 2013 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sojw;

import java.util.Calendar;

/**
 * @author NHN
 * @since 2013. 1. 24.
 */
public class TestSplit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//		String testStr = "<a href=\"mediaId:0;type:IMAGE\"><img src=\"http://blogfiles.naver.net/20130121_232/echinoid_13587530147426uNlj_JPEG/Desert.jpg\" /></a><!--seobject--><a href=\"mediaId:2;type:VIDEO\"><img src=\"http://image.nmv.naver.net/blogucc28/2013/01/21/256/701a806ce90b58c17db23d32&nbsp;<br/>br/>inoid_270P_01_16x9_logo.jpg\"/></a><!--seobject--><br/>";
		//		String[] result = testStr.split("<!--seobject-->");
		//
		//		for (int i = 0; i < result.length; i++) {
		//			System.out.println(result[i]);
		//
		//		}

		String text = "";
		text = String.format("%d%d", Calendar.getInstance().getTimeInMillis(), 1213123123);
		System.out.println(text);

		Long longtest = 11L;
		System.out.println(longtest.toString());
	}

}
