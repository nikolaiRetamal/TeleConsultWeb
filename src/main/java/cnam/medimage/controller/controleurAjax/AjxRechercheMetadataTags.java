package cnam.medimage.controller.controleurAjax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cnam.medimage.repository.UsageRepository;
import cnam.medimage.service.ServiceDicoDicomCrawler;
import cnam.medimage.bean.MetaDataDico;
import cnam.medimage.bean.TagMesh;
import cnam.medimage.bean.Usage;

@Controller
public class AjxRechercheMetadataTags {
	@RequestMapping(value = "/getMetadataTags", method = RequestMethod.GET )
	public @ResponseBody Map<String,String> getUsages(@RequestParam("term") String tagSaisi, 
			HttpServletResponse response,
			HttpServletRequest  request) throws IOException {
		//On renvoie une TreeMap pour classer alphabétiquement les réponses
		TreeMap<String, String> indexes = new TreeMap<String, String>(new Comparator<String>() {
		    public int compare(String o1, String o2) {
		        return o1.toLowerCase().compareTo(o2.toLowerCase());
		    }
		});
		try{
			ServiceDicoDicomCrawler serviceDicoDicom = ServiceDicoDicomCrawler.getInstance(request);
			indexes = serviceDicoDicom.findTags(tagSaisi);
		}catch (Exception e){
			//traiter recherche vide
		}
		UsageRepository usageRepo = new UsageRepository();
		List<Usage> usagesTrouve = usageRepo.findAll();
		
		for(Usage usage : usagesTrouve){
			if(usage.getNom().toLowerCase().contains(tagSaisi.toLowerCase())){
				indexes.put(usage.getNom(),usage.getId_usage().toString());
			}
		}
		
		return indexes;
	}
}
