package spittr.data;

import org.springframework.data.jpa.repository.JpaRepository;

import spittr.config.Spitter;

public interface SpitterRepository  {

  Spitter save(Spitter spitter);
  
  Spitter findByUsername(String username);

}