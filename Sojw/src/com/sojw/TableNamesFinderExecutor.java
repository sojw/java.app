/*
 * @(#)TableNamesFinderExecutor.java $version 2014. 2. 12.
 *
 * Copyright 2014 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sojw;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.gibello.zql.ZInsert;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZUpdate;
import org.gibello.zql.ZqlParser;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @author NHN
 * @since 2014. 2. 12.
 */
public class TableNamesFinderExecutor {
	private static final String SOURCE_FILE_NAME = "큐브리드 전환_Query_초당실행수_input.csv";
	private static final String FILE_ROOT_DIR = "D:\\BTS Task\\Cubrid전환\\";
	private static final String OUTPUT_FILE_NAME_FORMAT = "큐브리드 전환_Query_초당실행수_output_iteration%s_%s.csv";

	private static Map<String, List<String>> iterMap = new HashMap<String, List<String>>();
	static {
		iterMap.put("1", Arrays.asList("PT_GUEST", "PT_GUEST_COMMENT", "PT_GUEST_DEL_HISTORY", "PT_GUEST_FILTERED",
			"PT_GUEST_MAXNO", "PT_GUEST_SMS_COUNT", "PT_GUEST_WRITEBLOCK", "PT_BUDDY", "PT_BUDDYGROUP",
			"PT_BUDDY_POST_INDEX", "PT_BUDDYGROUP"));

		iterMap.put("2", Arrays.asList("PT_BLOG_OPTIONS", "PT_BLOGPOST_OPTIONS", "PT_PREPOST", "PT_PREPOST_OPTIONS",
			"PT_LEVERAGE_THEME_META", "PT_SCRAP_HISTORY", "PT_SCRAP_INFO", "PT_SOURCE", "PT_CONNECT_WIDGET",
			"PT_EXTERNAL_WIDGET", "PT_MY_TRACE", "PT_MUSIC", "PT_MUSICPLAYER", "PT_NOTICE_LAYER_DISPLAY_STATUS",
			"PT_NOTICE_POST", "PT_PAPER_MOBILE", "PT_PAPERNO_SEQ", "PT_PAPERTALKLOG", "PT_PDF", "PT_PDF_LOG",
			"PT_PERSONACON", "PT_PERSONACONLIST"));

		//		iterMap.put("3", Arrays.asList("PT_ATTACH_MPEG", "PT_ATTACHFILE", "PT_CATEGORY", "PT_ATTACH_MPEG",
		//			"PT_ATTACHFILE", "PT_CATEGORY ", "PT_LOG_TAG", "PT_RELAY", "PT_LOG_DEL_HISTORY", "PT_LOG_LEVERAGE",
		//			"PT_LOG_LOCATION", "PT_ATTACH_PIC", "PT_LOG_TAGS", "PT_OWFS_LAST_MOVE", "PT_OWFS_MOVE_FAIL", "PT_PREPOST",
		//			"PT_ATTACH_FOREIGN_MAP", "PT_LOG_FOREIGN_LOCATION", "PT_RELAY", "PT_RELAY_FILTERED", "PT_WRITING_MATERIAL",
		//			"ros.TEMP_PT_LOG_ONEMONTHPOSTING_PAPERNO", "ros.TEMP_PT_LOG_TODAYPOSTING_PAPERNO", "PT_LOG"));
		//
		//		iterMap.put("6", Arrays.asList("PT_PAPER", "PT_LOG_DECORATION", "PT_LOG_DETAIL", "PT_LOG_DIRECTORY_DETAIL",
		//			"PT_LOG_FOREIGN_LOCATION", "PT_LOG_SMS_COUNT", "PT_LOG_TAGS", "PT_LOG_UPDATE_HISTORY$SUFFIXMONTH",
		//			"PT_LOGMONTH", "PT_ATTACH_CALENDAR", "PT_ATTACH_ETC", "PT_ATTACH_FOREIGN_MAP", "PT_ATTACH_MAP",
		//			"PT_ATTACH_MPEG", "PT_ATTACH_MPEG_DEL_HISTORY", "PT_ATTACH_PIC", "PT_ATTACH_PIC_DEL_HISTORY",
		//			"PT_ATTACH_STORYPHOTO", "PT_ATTACHFILE", "PT_ATTACHFILE_BLOCKINFO", "PT_ATTACHFILE_DEL_HISTORY",
		//			"PT_ATTACHFILE_ID", "PT_COMMENT_WRITE_BLOCK_IP", "PT_CURRENCY_WIDGET", "PT_DOMAIN_HISTORY",
		//			"PT_DOMAINURL_LOCATOR", "PT_EVENT", "PT_EVENT_CATEGORY", "PT_EVENT_COMMENT", "PT_EXTERNAL_WIDGET",
		//			"PT_FILESIZE", "PT_FONT", "PT_STORYPHOTO_DEL_HISTORY"));
		//
		//		iterMap.put("7", Arrays.asList("PT_CATEGORY", "PT_EVENT_CATEGORY", "PT_EVENT_POST_OPTIONS",
		//			"PT_WIDGET_CATEGORY", "PT_WIDGET_POST", "PT_LEVERAGE_THEME_META", "PT_LOG_LEVERAGE", "PT_LOG_MAPPING",
		//			"PT_LOG_CONTENTS_UPDATE_HISTORY", "PT_LOG_FOREIGN_LOCATION", "PT_LOG_LOCATION", "PT_LOG_LOCATION_COUNT",
		//			"PT_LOG_NLOCATION_HISTORY", "PT_MAPVIEW_LOCATION", "PT_ITEMFACTORY_PUNISHMENT_DATE",
		//			"PT_KOREAWEATHER_WIDGET", "PT_LICENSE_BLACKLIST", "PT_LOG_LICENSE_LIST", "PT_MEMBER", "PT_MEMBER_DETAIL",
		//			"PT_MEMBER_INTRO", "PT_MOBILE", "PT_SOCIALAPP_TOPMENU"));
		//
		//		iterMap.put("8", Arrays.asList("PT_PAPER_LOGINFO", "PT_PAPER_LOGINFO_DEL_HISTORY", "SEARCH_LOG_DEL",
		//			"PT_COUNT", "PT_VISIT_BLOCK", "PT_VISITS_WIDGET", "PT_BLACKLIST", "PT_CATEGORY_SEQUENCE",
		//			"PT_CATEGORY_UPDATE_HISTORY", "PT_BUDDY_INVITE", "PT_BUDDY_POST_INDEX", "PT_BUDDY_TIMESTAMP",
		//			"PT_SUBSCRIBER", "PT_ACCESS_IP", "PT_ADDED_INFO", "PT_API_KEY", "PT_BLACKLIST", "PT_BLACKLIST_POST",
		//			"PT_BLOCK", "PT_BLOG_BASICINFO_BACKUP", "PT_BLOG_LOCATOR", "PT_BLOG_QUALITY_GRADE", "PT_BLOG_TAGS",
		//			"PT_BLOGHISTORY", "PT_BOOKMARK", "PT_BUSINESS", "PT_CCL", "PT_CITY", "PT_COUNT", "PT_COUNTRY"));
		//
		//		iterMap.put("9", Arrays.asList("PT_LOG_DEL_HISTORY", "PT_EVENT", "PT_MEMBER", "PT_EVENT_HISTORY",
		//			"PT_EVENTNO_SEQ", "PT_PERSONAL_LAYOUT", "PT_PERSONAL_SKIN", "PT_SKIN_USER_MAKE",
		//			"PT_SKIN_USER_MAKE_LAYOUT", "PT_WIDGET", "PT_PROLOGUE", "PT_SMS_OPTIONS", "PT_SUBSCRIBER", "PT_SUBSCRIBER",
		//			"PT_SYMPATHY_HISTORY", "PT_KOREAWEATHER_WIDGET", "PT_KOREAWEATHER_TOWN", "PT_KOREAWEATHER_CITY",
		//			"PT_WEATHER_GROUP", "PT_WEATHER", "PT_LICENSE_BLACKLIST", "PT_LOG_LICENSE_LIST", "PT_MOBILEPOST_OPTIONS"));
		//
		//		iterMap.put("10", Arrays.asList("PT_RELAY", "PT_RELAY_FILTERED", "PT_RELAY_DEL_HISTORY", "PT_UPDATEPOSTTIME",
		//			"PT_USERFILTER", "PT_VIDEO_BACKUP_CATEGORY_HISTORY", "PT_VISIT", "PT_VISIT_BLOCK", "PT_WISESAYING_WIDGET",
		//			"PT_WRITING_MATERIAL", "SEARCH_STATIC_TRASH", "PT_TEMPLATE_BOOK", "PT_TEMPLATE_BOOK_RECOMMENDED_BOOK",
		//			"PT_TEMPLATE_DRAMA", "PT_TEMPLATE_MOVIE", "PT_TEMPLATE_MUSIC", "PT_TEMPLATE_MUSIC_RECOMMENDED_ALBUM",
		//			"PT_TEMPLATE_MUSIC_RECOMMENDED_SONG", "PT_TEMPLATE_RECIPE", "PT_TEMPLATE_SHOP", "PT_TEMPLATE_TRAVEL",
		//			"PT_TEMPLATE_TRAVEL_PATH_DETAIL", "PT_TEMPLATE_TRAVEL_REGION_DETAIL"));
	}

	private final static String CSV_SAVE_HEADER = "iteration,groupno,tablename,statement_text	,exPerSec,origin_recordNumber";
	private final static String CSV_SAVE_FORMAT = "%s,%s,%s,\"%s\",%s,%d";

	public static List<String> getFileContents(final String filePath) throws IOException {
		final List<String> fileContentList = FileUtils.readLines(FileUtils.getFile(filePath));
		return fileContentList;
	}

	public static List<CSVRecord> getCSVFileContents(final String filePath) throws IOException {
		final Reader in = new BufferedReader(new FileReader(filePath));
		final CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT);
		List<CSVRecord> fileContentList = parser.getRecords();
		return fileContentList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		List<CSVRecord> fileContentList = null;
		try {
			fileContentList = getCSVFileContents(FILE_ROOT_DIR + SOURCE_FILE_NAME);
		} catch (IOException e) {
			System.out.println("파일 읽기 오류.");
			System.exit(-1);
		}

		if (fileContentList == null || fileContentList.isEmpty()) {
			System.out.println("파일 내용 없음.");
			System.exit(1);
		}

		// 정규식 방법
		final Set<String> iterMapKeys = iterMap.keySet();
		for (final String mapkey : iterMapKeys) {
			final List<String> iterList = iterMap.get(mapkey);
			for (final String tableName : iterList) {
				final List<String> resultList = extractTableNameByRegex(tableName, fileContentList);
				final String outputFileName = FILE_ROOT_DIR + String.format(OUTPUT_FILE_NAME_FORMAT, mapkey, tableName);

				FileUtils.deleteQuietly(new File(outputFileName));
				FileUtils.writeLines(new File(outputFileName), resultList, false);
			}
		}

		// Zql
		//		final Set<String> iterMapKeys = iterMap.keySet();
		//		for (String mapkey : iterMapKeys) {
		//			final List<String> iterList = iterMap.get(mapkey);
		//			for (final String tableName : iterList) {
		//				extractTableNameByZql(fileContentList);
		//			}
		//		}

	}

	/**
	 *
	 */
	private static void __select() {
		List<CSVRecord> fileContentList = null;
		try {
			fileContentList = getCSVFileContents("D:\\BTS Task\\Cubrid전환\\큐브리드 전환_Query_초당실행수_input.csv");
		} catch (IOException e) {
			System.out.println("파일 읽기 오류.");
			System.exit(-1);
		}

		if (fileContentList == null || fileContentList.isEmpty()) {
			System.out.println("파일 내용 없음.");
			System.exit(1);
		}

		System.out.println("fileContents size : " + fileContentList.size());

		CCJSqlParserManager manager = new CCJSqlParserManager();

		int errorCount = 0;
		for (final CSVRecord record : fileContentList) {
			String sqlStatement = record.get(3);
			try {
				Statement statement = manager.parse(new StringReader(sqlStatement));
				if (statement != null) {
					List<String> tableNames = extractTableNameByJsql(statement);
					if (tableNames != null && !tableNames.isEmpty()) {
						System.out.println("tableNames " + tableNames);
					}
				}
			} catch (Exception e) {
				System.out.println(e + ",  sqlStatement = " + sqlStatement);
				errorCount++;
			}
		}

		System.out.println("errorCount : " + errorCount);
	}

	/**
	 * @param statement
	 * @return
	 */
	private static List<String> extractTableNameByJsql(final Statement statement) {
		if (statement == null) {
			return Collections.<String> emptyList();
		}

		final TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		if (statement instanceof Select) {
			Select selectStatement = (Select)statement;
			return tablesNamesFinder.getTableList(selectStatement);
		}

		if (statement instanceof Update) {
			Update updateStatement = (Update)statement;
			return tablesNamesFinder.getTableList(updateStatement);
		}

		if (statement instanceof Delete) {
			Delete deleteStatement = (Delete)statement;
			return tablesNamesFinder.getTableList(deleteStatement);
		}

		return Collections.<String> emptyList();
	}

	/**
	 * @param tableName
	 * @param fileContentList
	 * @return
	 */
	private static List<String> extractTableNameByRegex(final String tableName, final List<CSVRecord> fileContentList) {
		List<String> searchList = new ArrayList<String>();
		searchList.add(CSV_SAVE_HEADER);

		for (CSVRecord record : fileContentList) {
			final String iteration = record.get(0).trim();
			final String groupno = record.get(1).trim();
			final String sqlStatement = record.get(3).trim();
			final String exPerSec = record.get(4).trim();
			final long recordNumber = record.getRecordNumber();

			/*
			final Pattern updatePattern = Pattern.compile("(?i)UPDATE[^\\S]+(?i)(dbo." + tableName + "|" + tableName
				+ ")[^\\S]+(?i)SET");
			final Matcher updateMatcher = updatePattern.matcher(sqlStatement);
			if (updateMatcher.find()) {
				searchList.add(iteration + ", " + groupno + "," + tableName + "," + sqlStatement + "," + exPerSec);
			}

			final Pattern queryPattern = Pattern.compile("(?i)FROM[^\\S]+(?i)(dbo." + tableName + "|" + tableName
				+ ")[^\\S]+");
			final Matcher queryMatcher = queryPattern.matcher(sqlStatement);
			if (queryMatcher.find()) {
				searchList.add(iteration + ", " + groupno + "," + tableName + "," + sqlStatement + "," + exPerSec);
			}
			*/

			final Pattern queryPattern = Pattern.compile("(?i)UPDATE[^\\S]+(?i)(dbo." + tableName + "|" + tableName
				+ ")[^\\S]+(?i)SET" + "|" + "(?i)FROM[^\\S]+(?i)(dbo." + tableName + "|" + tableName + ")[^\\S]+");
			final Matcher queryMatcher = queryPattern.matcher(sqlStatement);
			if (queryMatcher.find()) {
				searchList.add(String.format(CSV_SAVE_FORMAT, iteration, groupno, tableName, sqlStatement, exPerSec,
					recordNumber));
			}
		}

		return searchList;
	}

	private static void extractTableNameByZql(final List<CSVRecord> fileContentList) {
		try {
			for (final CSVRecord record : fileContentList) {
				String sqlStatement = record.get(3);

				ZqlParser p = new ZqlParser(new DataInputStream(
					new ByteArrayInputStream(sqlStatement.getBytes("UTF-8"))));

				// Read all SQL statements from input
				ZStatement st;
				while ((st = p.readStatement()) != null) {
					System.out.println(st.toString()); // Display the statement

					if (st instanceof ZQuery) { // An SQL query: query the DB
						System.out.println("ZQuery");
						System.out.println("getFrom : " + ((ZQuery)st).getFrom());
					} else if (st instanceof ZInsert) { // An SQL insert
						System.out.println("ZInsert");
					} else if (st instanceof ZUpdate) { // An SQL insert
						System.out.println("ZUpdate");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}