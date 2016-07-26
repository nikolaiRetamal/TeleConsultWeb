package cnam.medimage.controller.controleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import cnam.medimage.bean.User;
import cnam.medimage.repository.UserRepository;

@Controller
@SessionAttributes("id_user")
public class ControlIdentificationContr {
	
	@RequestMapping(value="/controle_identification")
	public ModelAndView controleIdentification(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String nom) throws Exception {
		System.out.println("user = " + nom);	
		UserRepository userRepo = new UserRepository();
		List<User> users = userRepo.findByNom(nom);
		User user;
		String id_user;
		if(!users.isEmpty()){//existe je le récupère
			id_user = users.get(0).getId_user().toString();
			user = users.get(0);
		}else{//n'existe pas je l'enregistre
			user = new User();
			id_user = UUID.randomUUID().toString();
			user.setId_user(UUID.fromString(id_user));
			user.setNom(nom);
			userRepo.save(user);
		}
		Map<String, Object> param = new HashMap<>();
		param.put("title", "Accueil");
		param.put("titrePage", "MEDIMAGE");
		param.put("id_user", id_user);
		param.put("user", user);
		ModelAndView mv = new ModelAndView("accueil");
		mv.addAllObjects(param);
		return mv;
	}
}
