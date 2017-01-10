package com.suman.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.suman.dao.JobDAO;
import com.suman.dao.JobDAOImpl;
import com.suman.dao.UserDAO;
import com.suman.dao.UserDAO1;
import com.suman.dao.UserDAOImpl;
import com.suman.dao.UserDAOImpl1;
import com.suman.model.Job;
import com.suman.model.User;
import com.suman.model.User12;

@Configuration
@ComponentScan("com")
@EnableTransactionManagement
public class ApplicationContextConfig {

	
	@Bean(name = "dataSource")
	public DataSource getOracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");

		dataSource.setUsername("collab");
		dataSource.setPassword("collab");
		return dataSource;
	}

	private Properties getHibernateProperties() {

		Properties connectionProperties = new Properties();

		connectionProperties.setProperty("hibernate.show_sql", "true");
		connectionProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		connectionProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		connectionProperties.setProperty("hibernate.format_sql", "true");
		connectionProperties.setProperty("hibernate.jdbc.use_get_generated_keys", "true");
		// dataSource.setConnectionProperties(connectionProperties);
		return connectionProperties;
	}

	@Autowired // automatically bean is created n injected
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource); 
		sessionBuilder.addProperties(getHibernateProperties());
		sessionBuilder.addAnnotatedClass(User.class);
		sessionBuilder.addAnnotatedClass(Job.class);
		
		sessionBuilder.addAnnotatedClass(User12.class);
		
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory); 
		return transactionManager;
	}
	/*.......USER..........*/

	@Autowired
	@Bean(name = "user")
	public User getUser() {
		return new User();
	}

	@Autowired
	@Bean(name = "userDAO")
	public UserDAO getUserDAO(SessionFactory sessionFactory) {
		return new UserDAOImpl(sessionFactory);
	}
	
	
	/*.......JOB..........*/

	@Autowired
	@Bean(name = "job")
	public Job getJob() {
		return new Job();
	}

	@Autowired
	@Bean(name = "jobDAO")
	public JobDAO getJobDAO(SessionFactory sessionFactory) {
		return new JobDAOImpl(sessionFactory);
	}

	
	/* test*/
	@Autowired
	@Bean(name = "user12")
	public User12 getUser1() {
		return new User12();
	}

	@Autowired
	@Bean(name = "userDAO1")
	public UserDAO1 getUserDAO1(SessionFactory sessionFactory) {
		return new UserDAOImpl1(sessionFactory);
	}


}
