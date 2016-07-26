package cnam.medimage.repository;

import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;

import cnam.medimage.bean.MetaData;

public class MetadataRepository {

	private Persistence persistence;
	
	public List<MetaData> find(String key, String value) {
		return persistence.selectBuilder(MetaData.class).eq("key", key).eq("value", value).execute();
	}
	
	public List<MetaData> findByDicomId(UUID id_dicom) {
		return persistence.findByIndex("id_dicom", id_dicom, MetaData.class);
	}
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}


	public void save(MetaData metadata) {
		persistence.insert(metadata);
	}

}
