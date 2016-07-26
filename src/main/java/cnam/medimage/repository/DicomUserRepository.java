package cnam.medimage.repository;

import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;
import org.easycassandra.persistence.cassandra.SelectBuilder;

import cnam.medimage.bean.DicomUser;

public class DicomUserRepository {
	private Persistence persistence;
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}
	
	public List<DicomUser> findByDicom(UUID id_dicom) {
		return persistence.findByIndex("id_dicom", id_dicom, DicomUser.class);
	}

	public List<DicomUser> findByUser(UUID id_user) {
		return persistence.findByIndex("id_user", id_user, DicomUser.class);
	}
	

	public List<DicomUser> findAll() {
		SelectBuilder<DicomUser> select = persistence.selectBuilder(DicomUser.class);
		return select.execute();
	}
	
	public DicomUser findOne(UUID id) {
		return persistence.findByKey(id, DicomUser.class);
	}
	
	public void save(DicomUser dicomUser) {
		persistence.insert(dicomUser);
	}
}
