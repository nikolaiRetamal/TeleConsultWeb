package cnam.medimage.controller.controleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cnam.medimage.bean.Dicom;
import cnam.medimage.bean.Examen;
import cnam.medimage.bean.User;
import cnam.medimage.repository.ExamenDicomRepository;
import cnam.medimage.repository.ExamenRepository;
import cnam.medimage.repository.UserRepository;

@Controller
public class ResultatImportContr {
	@RequestMapping(value="/resultat_import")
	public ModelAndView handleRequest(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, @RequestParam("id_examen") String id_examen) throws Exception {
		System.out.println("Je suis dans le contrôleur résultat");
		Map<String, Object> param = new HashMap<>();
		String id_user = "";
		User user;
		if(session.getAttribute("id_user") != null){
			id_user = session.getAttribute("id_user").toString();
			System.out.println("USER = " + id_user);
			UserRepository userRepo = new UserRepository();
			user = userRepo.findOne(UUID.fromString(id_user));
			param.put("user", user);
		}
		boolean connexionExiste = request.getSession().getAttribute("user")!= null;
		ExamenRepository examRepo = new ExamenRepository();
		Examen examen = examRepo.findOne(UUID.fromString(id_examen));
		ExamenDicomRepository examDicomRepo = new ExamenDicomRepository();
		List<Dicom> dicoms = examDicomRepo.getListeDicoms(UUID.fromString(id_examen));
		param.put("examen", examen);
		param.put("dicoms", dicoms);
		param.put("nbImages", dicoms.size());
		param.put("title", "Résumé");
		param.put("titrePage", "Résumé de l'import");
		param.put("connexionExiste", connexionExiste);
		ModelAndView mv = new ModelAndView("resultatImport");
		mv.addAllObjects(param);
		return mv;
	}
}
