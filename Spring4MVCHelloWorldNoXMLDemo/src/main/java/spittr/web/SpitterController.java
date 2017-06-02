package spittr.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spittr.config.Spitter;
import spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

  private SpitterRepository spitterRepository;

  @Autowired
  public SpitterController(SpitterRepository spitterRepository) {
    this.spitterRepository = spitterRepository;
  }
  
  @RequestMapping(value="/register", method=RequestMethod.GET)
  public String showRegistrationForm() {
  return "registerForm";
  }
  @RequestMapping(value="/register",method=RequestMethod.POST)
  public String processRegistration(@Valid Spitter spitter,Errors errors, RedirectAttributes model) {
  
	if(errors.hasErrors())
	{
		
		return "registerForm";
	}
  spitterRepository.save(spitter);
  model.addAttribute("username",spitter.getUsername());
  model.addAttribute("id",spitter.getId());
  model.addFlashAttribute(spitter);
  return "redirect:/spitter/{username}";
  }
  
  @RequestMapping(value="/{username}", method=RequestMethod.GET,produces="applicaiton/json")
  public @ResponseBody String showSpitterProfile(@PathVariable String username, Model model) {
    if(!model.containsAttribute("spitter"))
    {
    	model.addAttribute(
    			spitterRepository.findByUsername(username));
    }
   
    return "profile";
  }
  
}