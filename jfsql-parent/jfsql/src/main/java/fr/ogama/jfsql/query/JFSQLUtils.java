package fr.ogama.jfsql.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JFSQLUtils {
	
	public static String datePattern = "yyyy/MM/dd HH:mm:ss";
	
	public static Matcher executeRegexp(Pattern pattern, String string) {
		string = string.replaceAll("\\\\", "/");
		return pattern.matcher(string);
	}
	
	public static Pair<String, Map<String, String>> jfsqlHavingClauseToSqlWhereClause(String havingClause) {		
		try {
			Map<String, String> subQueries = new HashMap<String, String>();
			String workingQuery = new String(havingClause.getBytes());

			String subQueryRegex = Properties.getProperty(Properties.QUERY_READ_STATEMENT_SUBQUERY);
			Pattern pattern = Pattern.compile(subQueryRegex);
			
			AtomicInteger integer = new AtomicInteger(1);
			
			boolean hasMoreToReplace = true;
			// on remplace les sous requêtes
			while (hasMoreToReplace) {
				Matcher matcher = executeRegexp(pattern, workingQuery);
				if (matcher.find()) {
					String subQuery = matcher.group();
					String subQueryKey = "subQuery" + integer.getAndAdd(1);
					subQueries.put(subQueryKey, subQuery);
					workingQuery = workingQuery.replace(subQuery, subQueryKey);
				} else {
					hasMoreToReplace = false;
				}
			}
			
			workingQuery = workingQuery.replaceAll("having ", "where ");
			return new Pair<String, Map<String,String>>("select * from fileSystem where " + workingQuery + ";", subQueries);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date parseDateFromCurrentLocal(String date) throws ParseException {  
		return new SimpleDateFormat(datePattern).parse(date);		
	}
	
	public static String dateToString(Date date) {
		return new SimpleDateFormat(datePattern).format(date);
	}
	
	public static <T extends Comparable> List<Comparable> toComparable(List<T> items) {
		if (items != null) {
			List<Comparable> newList = new ArrayList<Comparable>(items.size());
			
			for (T item : items) {
				newList.add(item);
			}
			return newList;
		}
		
		return null;
	}
}
