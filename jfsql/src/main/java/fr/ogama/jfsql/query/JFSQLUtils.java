package fr.ogama.jfsql.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JFSQLUtils {
	
	public static String datePattern = "yyyy/MM/dd hh:mm:ss";
	
	public static Matcher executeRegexp(Pattern pattern, String string) {
		string = string.replaceAll("\\\\", "/");
		return pattern.matcher(string);
	}
	
	public static String jfsqlHavingClauseToSqlWhereClause(String havingClause) {
		String sql = new String(havingClause.getBytes());
		sql = sql.replaceAll("having ", "where ");
		return "select * from fileSystem where " + sql + ";";
	}
	
	public static Date parseDateFromCurrentLocal(String date) throws ParseException {  
		return new SimpleDateFormat(datePattern).parse(date);		
	}
}
