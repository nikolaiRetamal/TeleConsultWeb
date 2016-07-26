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

import cnam.medimage.bean.Usage;
import cnam.medimage.bean.UsageUser;
import cnam.medimage.bean.User;

import cnam.medimage.repository.UsageRepository;
import cnam.medimage.repository.UsageUserRepository;
import cnam.medimage.repository.UserRepository;

@Controller
@Scope("session")
public class MesUsagesContr {
	@RequestMapping(value="/mesUsages")
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
		UsageUserRepository usageUserRepo = new UsageUserRepository();
		List<UsageUser> usagesUser = usageUserRepo.findByUser(user.getId_user());
		List<Usage> usages = new ArrayList<>();
		UsageRepository usageRepo = new UsageRepository();
		for(UsageUser usageUser : usagesUser){
			usages.add(usageRepo.findOne(usageUser.getId_usage()));
		}
		param.put("title", "Mes usages");
		param.put("titrePage", "Mes usages");
		param.put("usages", usages);
		ModelAndView mv = new ModelAndView("mesUsages", param);
		return mv;
	}
}
