/*
 * @(#)FindUnusedCSS.java $version 2013. 11. 14.
 *
 * Copyright 2013 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sojw;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author NHN
 * @since 2013. 11. 14.
 */
public class FindUnusedCSS {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		File input = new File("/tmp/input.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

		Elements links = doc.select("a[href]"); // a with href
		Elements pngs = doc.select("img[src$=.png]");
		// img with src ending .png

		Element masthead = doc.select("div.masthead").first();
		// div with class=masthead

		Elements resultLinks = doc.select("h3.r > a"); // direct a after h3
	}


}
