package spittr.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spittr.config.Spitter;

public class SpitterRowMapper implements RowMapper<Spitter> {

	@Override
	public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new Spitter(
				rs.getLong("id"),
				rs.getString("username"),
				rs.getString("password"),
				rs.getString("fullName"),
				rs.getString("email"),
				rs.getString("updateByEmail"));
	}

	

}
