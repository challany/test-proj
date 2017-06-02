package spittr.web;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import spittr.config.Spittle;
import spittr.data.SpittleRepository;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	private static final String MAX_LONG_AS_STRING = "9223372036854775807";

	private SpittleRepository spitt{leRepository;

	@Autowired
	public SpittleController(SpittleRepository spittleRepository) {
		this.spittleRepository = spittleRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Spittle> spittles(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
			@RequestParam(value = "count", defaultValue = "20") int count) {
		return spittleRepository.findSpittles(max, count);
	}

	@RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
	public Spittle spittle(@PathVariable("spittleId") long spittleId, Model model) {
		Spittle spittle = spittleRepository.findOne(spittleId);
		if (spittle == null) {
			throw new SpittleNotFoundException(spittleId);

		}
		return spittle;
	}

	@RequestMapping(value = "/save", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody Spittle saveSpittle(@RequestBody Spittle spittle, UriComponentsBuilder uri) {
		HttpHeaders header = new HttpHeaders();
		URI location = uri.path("/spittles/").path("d").build().toUri();
		header.setLocation(location);

		return spittle;

	}

	@ExceptionHandler(SpittleNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error spittleNotFound(SpittleNotFoundException e) {
		long spittleId = e.getSpittleId();
		Error err = new Error(4, "sppit with id not found");
		return err;
	}
	
	public Spittle getSpittle()
	{
		
		RestTemplate rest = new RestTemplate();
		ResponseEntity<Spittle> response = rest.getForEntity("", Spittle.class, "2");
		HttpStatus stat = response.getStatusCode();
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("id", "12");
		return  rest.getForObject("dsf/{sid}", Spittle.class,uriVariables);
	}
}