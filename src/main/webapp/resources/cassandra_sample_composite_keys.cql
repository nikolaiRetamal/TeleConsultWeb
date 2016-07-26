//DROP KEYSPACE medimage;
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
    nom_examen text,
    nom_usage text,
    usages map<text, text>,
    tags list<text>,
    metadatas map<text,text>,
    PRIMARY KEY (id_dicom)
);

INSERT INTO medimage.dicom (id_dicom, date_import, nom, id_user, tags, metadatas)
VALUES(51eea361-2db2-11e4-8c21-0800200c9a66, 1409120421, 'Baggins', 51ee7c50-2db2-11e4-8c21-0800200c9a66,['pied', 'jambe'], {'calcul':'renal', 'tumeur':'sein'});

//DROP COLUMNFAMILY medimage.tag;
CREATE COLUMNFAMILY medimage.tag (
    id_dicom uuid,
    nom text,
    codifie boolean,
    PRIMARY KEY (id_dicom, nom)
);
CREATE INDEX ON medimage.tag (nom);

//DROP COLUMNFAMILY medimage.metadata;
CREATE COLUMNFAMILY medimage.metadata (
    id_dicom uuid,
	key text,
	value text,
    PRIMARY KEY (id_dicom, key, value)
);

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

//DROP COLUMNFAMILY medimage.usage_joint;
CREATE COLUMNFAMILY medimage.usage_joint (
    id_usage uuid,
    id_dicom uuid,
    id_user  uuid,
    PRIMARY KEY (id_usage, id_dicom, id_user)
);

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

//DROP TABLE medimage.examen;
CREATE TABLE medimage.examen_joint (
    id_examen uuid,
    id_dicom uuid,
    PRIMARY KEY (id_examen, id_dicom)
);


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

