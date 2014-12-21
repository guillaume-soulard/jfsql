package fr.ogama.jfsql.query.clause.having.comparators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

public class HavingSubQueryComparatorsTest extends AbstractHavingTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}
	
	@Test
	public void should_execute_sub_query() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory + "') having name in (#{get name in ('" + directory + "') having name like 'with'})";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
		assertThat(results).isNotNull().hasSize(6);
	}
	
	@Test
	public void should_execute_sub_sub_query() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory + "') having name in (#{get name in ('" + directory + "') having name like (#{get 1 name in ('" + directory + "') having size > 0})})";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
		assertThat(results).isNotNull().hasSize(1);
	}
	
	@Test
	public void should_execute_sub_query_size() throws Exception {
		// GIVEN
		String query = "get file or directory in ('/') deep 1 having size = #{get 1 size in ('/') deep 1 sort by size descending}";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
		assertThat(results).isNotEmpty();
	}
}
