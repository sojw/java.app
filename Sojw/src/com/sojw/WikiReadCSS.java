/*
 * @(#)WikiReadCSS.java $version 2013. 11. 14.
 *
 * Copyright 2013 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sojw;

import java.io.File;
import java.util.List;

import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;

/**
 * @author NHN
 * @since 2013. 11. 14.
 */
public class WikiReadCSS {

	public static void main(String[] args) {
		WikiReadCSS exe = new WikiReadCSS();

		File f = new File("C:\\dev\\workspace\\nmobile\\src\\main\\webapp\\css\\w.css");
		CascadingStyleSheet result = exe.readCSS30(f);
		List<CSSStyleRule> list = result.getAllStyleRules();
		System.out.println(list.isEmpty());
	}

	/**
	 * Read a CSS 3.0 declaration from a file using UTF-8 encoding.
	 *
	 * @param aFile The file to be read. May not be <code>null</code>.
	 * @return <code>null</code> if the file has syntax errors.
	 */
	public CascadingStyleSheet readCSS30(final File aFile) {
		final CascadingStyleSheet aCSS = CSSReader.readFromFile(aFile, "utf-8", ECSSVersion.CSS30);
		if (aCSS == null) {
			// Most probably a syntax error
			System.out.println("Failed to read CSS - please see previous logging entries!");
			return null;
		}
		return aCSS;
	}
}
