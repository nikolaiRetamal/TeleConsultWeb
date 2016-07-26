/**
 * 
 */
package cnam.medimage.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLStreamException;

import cnam.medimage.bean.IndexMesh;
import cnam.medimage.bean.MetaDataDico;
import cnam.medimage.bean.TagMesh;
import cnam.medimage.repository.IndexMeshRepository;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.PilotException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import com.ximpleware.extended.AutoPilotHuge;
import com.ximpleware.extended.NavExceptionHuge;
import com.ximpleware.extended.VTDGenHuge;
import com.ximpleware.extended.VTDNavHuge;
import com.ximpleware.extended.XPathEvalExceptionHuge;
import com.ximpleware.extended.XPathParseExceptionHuge;

/**
 * @author Jullien
 *
 */
public class ServiceDicoDicomCrawler extends Service {
	
	/**
	* Attribut singleton qui permet de n'avoir qu'une seule instance de la classe Service
	* grâce à un appel unique du constructeur
	* 
	*/
	protected static ServiceDicoDicomCrawler singleton;	
	
	static {
			singleton = new ServiceDicoDicomCrawler();		
	}
	

	/**
	 * Dictionnaire de Metadata
	 */
	private static File dico;
	
	
	/**
	 * Chemin du fichier Dictionnaire
	 */
	private static String dicoPath = null;
	
	/**
	 * La liste simple contenant tous les éléments du dictionnaire
	 */
	private static ArrayList<MetaDataDico> listeElementDico = null;
	
	/**
	* Méthode permettant d'accéder à l'unique instance de la classe Service
	* 
	* @return l'instance de la classe Service
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	*/
	public static ServiceDicoDicomCrawler getInstance(HttpServletRequest request) throws FileNotFoundException, XMLStreamException {
		
		init(request,false);
		
		return (ServiceDicoDicomCrawler) singleton;
	}

	/**
	 * 
	 * Crée la Factory et lui donne le dictionnaire à digérer
	 * 
	 */
	public static void init(HttpServletRequest request, boolean force) throws XMLStreamException, FileNotFoundException {
				

		if(!isInit() || force){		

			HttpSession session =  request.getSession(false);
			ServletContext context = session.getServletContext();

			//On va chercher le fichier dictionnaire
			String dicoFilePath = context.getRealPath(context.getInitParameter("DICOM_DICO_FILEPATH"));
			
			//Sauvegarde du chemin et du fichier Dictionnaire
			dicoPath = dicoFilePath;					
			dico = new File(dicoFilePath);
			
		}
		
	
		
	}
	
	
	/**
	 * 
	 * Contrôle l'initialisation de la classe de service avec le fichier Dico
	 * 
	 * @return
	 */
	private static boolean isInit() {
		return dico != null;
	}
	
	
	/**
	 * 
	 * Renvoie le dictionnaire de MetaDataDico, s'il est déjà en statique on ne le régénère pas
	 * 
	 * @return
	 * @throws NavException 
	 * @throws XPathEvalException 
	 * @throws XPathParseException 
	 */
	public ArrayList<MetaDataDico> getDictionnaire() throws XPathParseException, XPathEvalException, NavException {

		System.out.println("appel du dictionnaire");
		
		if(listeElementDico == null){
			listeElementDico = parseThemAll();
		}
		
		return listeElementDico;
		
	}
	
	
	/**
	 * 
	 * Ramène tous les éléments du dictionnaire de metaData
	 * 
	 * @param saisie
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws XPathParseExceptionHuge 
	 * @throws NavExceptionHuge 
	 * @throws XPathEvalExceptionHuge 
	 */
	private ArrayList<MetaDataDico> parseThemAll() throws XPathParseException, XPathEvalException, NavException  {
		
		System.out.println("appel de la génération du dictionnaire");
		
		ArrayList<MetaDataDico> liste = new ArrayList<MetaDataDico>();
		VTDGen vg = new VTDGen();
	    AutoPilot ap = new AutoPilot();
	    int i;
	
	    //Xpath de détection de tous les DescriptorRecord
	    ap.selectXPath("element");

	    if (vg.parseFile(dicoPath, true)  ){
        	
  	      VTDNav vn = vg.getNav();
  	      
  	      ap.bind(vn); // apply XPath to the VTDNav instance, you can associate ap to different vns
  	      // AutoPilot moves the cursor for you, as it returns the index value of the evaluated node
  	      while((i=ap.evalXPath())!=-1){  
  	    		try{
  	          	  liste.add(elementBuilder(vn,i)); 
  				}catch(Exception e){
  					System.out.println("Erreur de parsing...");
  				}  
  	    	}     
        	
        }
		
	    
		System.out.println("sortie de la génération du dictionnaire");
	    
		 return liste;
		
	}
	
	public TreeMap<String, String> findTags(String tagSaisi) throws IOException, XPathParseException, XPathEvalException, NavException {
		
		TreeMap<String, String> indexes = new TreeMap<String, String>(new Comparator<String>() {
		    public int compare(String o1, String o2) {
		        return o1.toLowerCase().compareTo(o2.toLowerCase());
		    }
		});
		this.getDictionnaire();
		for(MetaDataDico metadata :listeElementDico){
			if(metadata.getNom().toLowerCase().contains(tagSaisi.toLowerCase())){
				indexes.put(metadata.getNom(),metadata.getId());
			}
		}
		
		return indexes;
	}
	
	public String findLibelle(String id_tag) throws IOException, XPathParseException, XPathEvalException, NavException {
		this.getDictionnaire();
		for(MetaDataDico metadata :listeElementDico){
			if(metadata.getId().equals(id_tag))
				return metadata.getNom();
		}
		return "";
	}
	
	/**
	 * @throws PilotException 
	 * @throws XPathParseExceptionHuge 
	 * @throws XPathParseException 
	 * @throws XPathEvalExceptionHuge 
	 * @throws NavExceptionHuge 
	 * @throws NavException 
	 * @throws XPathEvalException 
	 * 
	 */
	private MetaDataDico elementBuilder(VTDNav vn,int xPathEval) throws PilotException, NavException, XPathParseException  {
		
		MetaDataDico metaData = new MetaDataDico();
		AutoPilot ap = null;
		int i,j;
		
		
		VTDNav vnClone = vn.cloneNav();		
	
        //On récupère les attributs et la valeur de l'élément
        if("element".equals(vnClone.toString(xPathEval))){         	
       	  
            
        	ap = new AutoPilot(vnClone);
        	ap.selectAttr("*");
        	i=-1;

        	while((i=ap.iterateAttr())!=-1){
        	 // i will be attr name, i+1 will be attribute value
        	//<element tag="00000000" keyword="CommandGroupLength" vr="UL" vm="1">Command Group Length</element>
        		if("tag".equals(vnClone.toString(i))){
            		metaData.setId(vnClone.toString(i+1));        			
        		}        		
        		if("vr".equals(vnClone.toString(i))){
        			metaData.setVr(vnClone.toString(i+1));        			
        		}        		
        		if("keyword".equals(vnClone.toString(i))){
        			metaData.setKeyword(vnClone.toString(i+1));        			
        		}        		
        	}    
            
            //Récupération de la valeur
   		 	j = vnClone.getText();
            if (j != -1) metaData.setNom(vnClone.toString(j));
		        
        }else{
        	
        	throw new XPathParseException("Le ElementBuilder n'est pas dans un Element.");
        	
        }		
        
        
		
		return metaData;
	}
	
	public String formaterTag(String source){
		return '(' + source.substring(0, 4)+ ',' + source.substring(4) +')';
	}
	
}
	

	