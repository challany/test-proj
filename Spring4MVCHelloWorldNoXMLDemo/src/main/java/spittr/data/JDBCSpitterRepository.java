package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import spittr.config.Spitter;

@Repository
public class JDBCSpitterRepository implements SpitterRepository {
	
	private JdbcOperations jdbcOperations;
	
	@Autowired
	public JDBCSpitterRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Spitter save(Spitter spitter) {
		// TODO Auto-generated method stub
		jdbcOperations.update("update ",spitter.getUsername(),spitter.getPassword());
		return spitter;
	}

	@Override
	public Spitter findByUsername(String username) {
		// TODO Auto-generated method stub
		jdbcOperations.queryForObject("", new SpitterRowMapper());
		return null;
	}

}
