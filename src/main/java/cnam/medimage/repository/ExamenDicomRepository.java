package cnam.medimage.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;
import org.easycassandra.persistence.cassandra.SelectBuilder;

import cnam.medimage.bean.Dicom;
import cnam.medimage.bean.ExamenDicom;
import cnam.medimage.bean.UsageExamen;

public class ExamenDicomRepository {

	private Persistence persistence;
	
	public List<ExamenDicom> findByDicom(UUID id_dicom) {
		return persistence.findByIndex("id_dicom", id_dicom, ExamenDicom.class);
	}
	public List<Dicom> getListeDicoms(UUID id_examen) {
		DicomRepository dicoRepo = new DicomRepository();
		List<ExamenDicom> examsDicoms = persistence.findByIndex("id_examen", id_examen, ExamenDicom.class);
		List<Dicom> dicoms = new ArrayList<>();
		for(ExamenDicom examDicom : examsDicoms){
			dicoms.add(dicoRepo.findOne(examDicom.getId_dicom()));
		}
		return dicoms;
	}
	public List<ExamenDicom> findByExamen(UUID id_examen) {
		return persistence.findByIndex("id_examen", id_examen, ExamenDicom.class);
	}
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}

	public void save(ExamenDicom examenDicom) {
		persistence.insert(examenDicom);
	}
	public List<ExamenDicom> findAll() {
		SelectBuilder<ExamenDicom> select = persistence.selectBuilder(ExamenDicom.class);
		return select.execute();
	}
}
