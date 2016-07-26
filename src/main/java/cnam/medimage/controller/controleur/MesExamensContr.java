package cnam.medimage.controller.controleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cnam.medimage.bean.Examen;
import cnam.medimage.bean.ExamenUser;
import cnam.medimage.bean.User;
import cnam.medimage.repository.ExamenRepository;
import cnam.medimage.repository.ExamenUserRepository;
import cnam.medimage.repository.UserRepository;

@Controller
@Scope("session")
public class MesExamensContr {
	@RequestMapping(value="/mesExamens")
	public ModelAndView handleRequest(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws Exception {
		System.out.println("Je suis dans le contrôleur detail examen");
		Map<String, Object> param = new HashMap<>();
		String id_user = "";
		User user;
		if(session.getAttribute("id_user") != null){
			id_user = session.getAttribute("id_user").toString();
			System.out.println("USER = " + id_user);
			UserRepository userRepo = new UserRepository();
			user = userRepo.findOne(UUID.fromString(id_user));
			param.put("user", user);
		}else{
			user = new User();
		}
		//On trouve l'examen avec l'id_examen reçu en paramètre
		ExamenUserRepository examUserRepo = new ExamenUserRepository();
		List<ExamenUser> examensUser = examUserRepo.findByUser(user.getId_user());
		List<Examen> examens = new ArrayList<>();
		ExamenRepository examRepo = new ExamenRepository();
		for(ExamenUser examenUser : examensUser){
			examens.add(examRepo.findOne(examenUser.getId_examen()));
		}
		param.put("title", "Mes Examens");
		param.put("titrePage", "Mes Examens");
		param.put("examens", examens);
		ModelAndView mv = new ModelAndView("mesExamens", param);
		return mv;
	}
}
