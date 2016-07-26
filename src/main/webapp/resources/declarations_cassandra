DROP KEYSPACE medimage;
CREATE KEYSPACE medimage WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };


//DROP COLUMNFAMILY medimage.dicom;
CREATE COLUMNFAMILY medimage.dicom (
    id_dicom uuid,
    date_import timestamp,
    file_path text, 
    nom text,
    public boolean,
    id_user uuid,
    id_examen uuid,
    id_usage uuid,
    nom_examen text,
    nom_usage text,
    usages map<text, text>,
    tags list<text>,
    metadatas map<text,text>,
    PRIMARY KEY (id_dicom)
);

//DROP COLUMNFAMILY medimage.tag;
CREATE COLUMNFAMILY medimage.tag (
	id_tag uuid,
    id_dicom uuid,
    nom text,
    codifie boolean,
    PRIMARY KEY (id_tag)
);
CREATE INDEX ON medimage.tag (nom);
CREATE INDEX ON medimage.tag (id_dicom);
CREATE INDEX ON medimage.tag (codifie);

//DROP COLUMNFAMILY medimage.tag_examen;
CREATE COLUMNFAMILY medimage.tag_examen(
	id uuid,
    id_examen uuid,
    nom text,
    PRIMARY KEY (id)
);
CREATE INDEX ON medimage.tag_examen(id_examen);
CREATE INDEX ON medimage.tag_examen(nom);

//DROP COLUMNFAMILY medimage.metadata;
CREATE COLUMNFAMILY medimage.metadata (
	id_metadata uuid,
    id_dicom uuid,
	key text,
	value text,
    PRIMARY KEY (id_metadata)
);
CREATE INDEX ON medimage.metadata (key);
CREATE INDEX ON medimage.metadata (value);
CREATE INDEX ON medimage.metadata (id_dicom);

//DROP COLUMNFAMILY medimage.metadata_examen;
CREATE COLUMNFAMILY medimage.metadata_examen (
	id uuid,
	id_examen uuid,
	key text,
	value text,
    PRIMARY KEY (id)
);
CREATE INDEX ON medimage.metadata_examen(id_examen);
CREATE INDEX ON medimage.metadata_examen(key);
CREATE INDEX ON medimage.metadata_examen(value);

//DROP TABLE medimage.user;
CREATE TABLE medimage.user (
    id_user uuid,
    nom text,
    prenom text,
    mail   text,
    date_naissance text,
    date_creation timestamp,
    PRIMARY KEY (id_user)
);
CREATE INDEX ON medimage.user(nom);

//DROP TABLE medimage.connexion;
CREATE TABLE medimage.connexion (
	id_user uuid,
	date_connex timestamp,
	PRIMARY KEY (id_user,date_connex)
);

//DROP COLUMNFAMILY medimage.usage;
CREATE COLUMNFAMILY medimage.usage (
    id_usage uuid,
    date_creat timestamp,
    nom text,
    id_user_crea uuid,
    dicoms list<uuid>,
    users  map<uuid, text>,
    PRIMARY KEY (id_usage)
);
CREATE INDEX ON medimage.usage (id_user_crea);

//DROP COLUMNFAMILY medimage.dicom_user;
CREATE COLUMNFAMILY medimage.dicom_user(
    id uuid,
    id_dicom uuid,
    id_user  uuid,
    PRIMARY KEY (id)
);
CREATE INDEX ON medimage.dicom_user (id_user);
CREATE INDEX ON medimage.dicom_user (id_dicom);

//DROP COLUMNFAMILY medimage.usage_user;
CREATE COLUMNFAMILY medimage.usage_user(
    id uuid,
    id_usage uuid,
    id_user  uuid,
    PRIMARY KEY (id)
);
CREATE INDEX ON medimage.usage_user (id_user);
CREATE INDEX ON medimage.usage_user (id_usage);

//DROP COLUMNFAMILY medimage.examen_user;
CREATE COLUMNFAMILY medimage.examen_user(
    id uuid,
    id_examen uuid,
    id_user  uuid,
    PRIMARY KEY (id)
);
CREATE INDEX ON medimage.examen_user (id_user);
CREATE INDEX ON medimage.examen_user (id_examen);

//DROP COLUMNFAMILY medimage.usage_examen;
CREATE COLUMNFAMILY medimage.usage_examen(
    id uuid,
    id_usage uuid,
    id_examen uuid,
    PRIMARY KEY (id)
);
CREATE INDEX ON medimage.usage_examen (id_usage);
CREATE INDEX ON medimage.usage_examen (id_examen);

//DROP TABLE medimage.examen;
CREATE TABLE medimage.examen (
    id_examen uuid,
    date_import timestamp,
    nom_examen text,
    nom_usage text,
    id_user uuid,
    usages map<text, text>,
    tags list<text>,
    metadatas map<text,text>,
    PRIMARY KEY (id_examen)
);

//DROP TABLE medimage.examen_dicom;
CREATE TABLE medimage.examen_dicom (
	id uuid,
    id_examen uuid,
    id_dicom uuid,
    PRIMARY KEY (id)
);
CREATE INDEX ON medimage.examen_dicom (id_examen);
CREATE INDEX ON medimage.examen_dicom (id_dicom);

//drop table medimage.tagmesh;
CREATE COLUMNFAMILY medimage.tagmesh (
    id_tag text,
	nom text,
	synonymes list<text>,
	categories list<text>,
    PRIMARY KEY (id_tag)
);

//drop table medimage.indexmesh;
CREATE COLUMNFAMILY medimage.indexmesh (
    id_tag text,
	nom text,
    PRIMARY KEY (id_tag,nom)
);