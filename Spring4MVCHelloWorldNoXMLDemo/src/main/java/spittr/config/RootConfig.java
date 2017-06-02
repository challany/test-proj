package spittr.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Configuration
@EnableJpaRepositories(basePackages="spittr")
@ComponentScan(basePackages={"spitter"},
excludeFilters={
@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
})
public class RootConfig {
	
	
	@Bean
	public JndiObjectFactoryBean dataSource2()
	{
		
		JndiObjectFactoryBean jndiObjFB = new JndiObjectFactoryBean();
		jndiObjFB.setJndiName("/jbdc/spittr");
		jndiObjFB.setResourceRef(true);
		jndiObjFB.setProxyInterface(javax.sql.DataSource.class);
		return jndiObjFB;
	}
	
	@Bean
	public DataSource dataSource()
	{
	return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:schema.sql")
			.addScript("classpath:script.sql").build();
	
	}

	@Bean
	public DataSource Data()
	{
		
		BasicDataSource bs =  new BasicDataSource();
		bs.setDriverClassName("ds");
		bs.setUrl("");
		bs.setUsername("");
		bs.setPassword("pass");
		bs.setInitialSize(5);
		return bs;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSouce)
	{
		return  new JdbcTemplate(dataSouce);
		
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMappingResources(new String[] {"spitter.hbm.xml"});
		Properties prop = new Properties();
		prop.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
		sessionFactory.setHibernateProperties(prop);
		return sessionFactory;
	}

	@Bean
	public AnnotationSessionFactoryBean annotationSessionFacotoryBean(DataSource dataSource)
	{
		AnnotationSessionFactoryBean asfb = new AnnotationSessionFactoryBean();
		asfb.setDataSource(dataSource);
		asfb.setPackagesToScan(new String[] {"spittr"});
		Properties prop = new Properties();
		prop.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
		asfb.setHibernateProperties(prop);
		return asfb;
		
		
	}
	
	@Bean
	public BeanPostProcessor persistanceTranslation()
	{
		
		return new PersistenceExceptionTranslationPostProcessor();
		
	}
	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactoryBean()
	{
		LocalEntityManagerFactoryBean emfb = new LocalEntityManagerFactoryBean();
		emfb.setPersistenceUnitName("epitterPU");
		return emfb;
		
	}
	@Bean
	public LocalContainerEntityManagerFactoryBean entitymgrfactory(DataSource dataSource, JpaVendorAdapter jpAdapter)
	{
		LocalContainerEntityManagerFactoryBean cmemfb = new LocalContainerEntityManagerFactoryBean();
		cmemfb.setDataSource(dataSource);
		cmemfb.setJpaVendorAdapter(jpAdapter);
		return cmemfb;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
	HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
	//adapter.setDatabase("HSQL");
	adapter.setShowSql(true);
	adapter.setGenerateDdl(false);
	adapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
	return adapter;
	}
	
}
