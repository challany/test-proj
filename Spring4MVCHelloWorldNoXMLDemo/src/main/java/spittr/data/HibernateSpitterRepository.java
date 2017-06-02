package spittr.data;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import spittr.config.Spitter;

public class HibernateSpitterRepository implements SpitterRepository {

	SessionFactory sessionFactory;
	@Autowired
	public HibernateSpitterRepository(SessionFactory sessionFactory) {
		// TODO Auto-generated constructor stub
	this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	@Override
	@PreAuthorize("(hasRole('ROLE_SPITTR') and #spitter.text.length()<140) or hasRole('ROLE_PREMIUM')")
	public Spitter save(Spitter spitter) {
		// TODO Auto-generated method stub
		Serializable id = getCurrentSession().save(spitter);
		return new Spitter((Long) id,
				spitter.getUsername(),
				spitter.getPassword(),
				spitter.getFirstName(),
				spitter.getEmail(),
				spitter.getUsername());
	}

	@Override
	public Spitter findByUsername(String username) {
		// TODO Auto-generated method stub
	return  (Spitter)	getCurrentSession().createCriteria(Spitter.class).add(Restrictions.eq("username", username)).list().get(0);
		
	}
	
	
	public Spitter findOne(long id) {
	return  (Spitter) getCurrentSession().get(Spitter.class, id);
	}
	
	
	public List<Spitter> findAll() {
	
	return (List<Spitter>)	getCurrentSession().createCriteria(Spitter.class).list();
	}

}
