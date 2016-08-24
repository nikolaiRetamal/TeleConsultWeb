package cnam.teleconsult.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import cnam.teleconsult.modele.bean.Avis;
import cnam.teleconsult.modele.bean.Consultation;
import cnam.teleconsult.modele.bean.Contributeur;
import cnam.teleconsult.modele.bean.ContributeurId;
import cnam.teleconsult.modele.bean.Dmpcadresse;
import cnam.teleconsult.modele.bean.Dmpcpatient;
import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;
import cnam.teleconsult.modele.bean.Dmpcstructuresante;
import cnam.teleconsult.modele.bean.Examen;
import cnam.teleconsult.modele.bean.Resultat;
import cnam.teleconsult.modele.bean.Specialite;


@Configuration
@ComponentScan(basePackages="cnam.teleconsult")
@EnableWebMvc
@EnableTransactionManagement
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/images/**").addResourceLocations("/resources/images");
	}
	
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/teleconsult");
		dataSource.setUsername("teleconsult");
		dataSource.setPassword("teleconsult2016");
		return dataSource;
	}
	
	private Properties getHibernateProperties() {
    	Properties properties = new Properties();
    	properties.put("hibernate.show_sql", "true");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	properties.put("hibernate.connection.CharSet", "utf8");
    	properties.put("hibernate.connection.characterEncoding", "utf8");
    	return properties;
    }
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
	 
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

		sessionBuilder.addAnnotatedClasses(Specialite.class);
		sessionBuilder.addAnnotatedClasses(Dmpcpersonnelsante.class);
		sessionBuilder.addAnnotatedClasses(Examen.class);
		sessionBuilder.addAnnotatedClasses(Dmpcadresse.class);
		sessionBuilder.addAnnotatedClasses(Dmpcpatient.class);
		sessionBuilder.addAnnotatedClasses(Consultation.class);
		sessionBuilder.addAnnotatedClasses(Resultat.class);
		sessionBuilder.addAnnotatedClasses(Avis.class);
		sessionBuilder.addAnnotatedClasses(ContributeurId.class);
		sessionBuilder.addAnnotatedClasses(Contributeur.class);
		sessionBuilder.addAnnotatedClasses(Dmpcstructuresante.class);
	 
		return sessionBuilder.buildSessionFactory();
	}	
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);
		return transactionManager;
	}
	
	

}
