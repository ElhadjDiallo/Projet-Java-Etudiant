/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ngoyet-ndiaye
 * Created: 12 mai 2016
 */

-- Database: "javaSTRI"

-- DROP DATABASE "javaSTRI";

CREATE DATABASE "javaSTRI"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'fr_FR.UTF-8'
       LC_CTYPE = 'fr_FR.UTF-8'
       CONNECTION LIMIT = -1;



CREATE TABLE chat_message(
        message_id   Int NOT NULL ,
        message_user Varchar (50) ,
        message_time TIMESTAMP,
        message_text Varchar (255) ,
        id_membre    Int ,
        PRIMARY KEY (message_id )
);

-- Table: public.envoyermess

-- DROP TABLE public.envoyermess;

CREATE TABLE public.envoyermess
(
  id_salon integer NOT NULL,
  id_membre integer NOT NULL,
  message_time timestamp without time zone NOT NULL,
  message text,
  CONSTRAINT envoyermess_pkey PRIMARY KEY (id_salon, id_membre, message_time),
  CONSTRAINT fk_envoyermess_id_membre FOREIGN KEY (id_membre)
      REFERENCES public.utilisateur (id_membre) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_envoyermess_id_salon FOREIGN KEY (id_salon)
      REFERENCES public.salon (id_salon) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.envoyermess
  OWNER TO postgres;
-- Table: public.salon

-- DROP TABLE public.salon;

CREATE TABLE public.salon
(
  id_salon integer NOT NULL,
  nom_salon character varying(50),
  CONSTRAINT salon_pkey PRIMARY KEY (id_salon)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.salon
  OWNER TO postgres;
-- Table: public.salonutilisateur

-- DROP TABLE public.salonutilisateur;

CREATE TABLE public.salonutilisateur
(
  id_salon integer NOT NULL,
  id_membre integer NOT NULL,
  message_time text,
  CONSTRAINT salonutilisateur_pkey PRIMARY KEY (id_salon, id_membre),
  CONSTRAINT fk_salonutilisateur_id_membre FOREIGN KEY (id_membre)
      REFERENCES public.utilisateur (id_membre) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_salonutilisateur_id_salon FOREIGN KEY (id_salon)
      REFERENCES public.salon (id_salon) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.salonutilisateur
  OWNER TO postgres;
-- Table: public.utilisateur

-- DROP TABLE public.utilisateur;

CREATE TABLE public.utilisateur
(
  id_membre integer NOT NULL,
  login character varying(50),
  mdp character varying(50),
  CONSTRAINT utilisateur_pkey PRIMARY KEY (id_membre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.utilisateur
  OWNER TO postgres;










CREATE TABLE Salon(
        id_salon   Int NOT NULL ,
        nom_salon  Varchar (50) ,
        type_salon Varchar (25) ,
        text_salon Varchar (255) ,
        PRIMARY KEY (id_salon )
);


CREATE TABLE utilisateur(
        id_membre Int NOT NULL ,
        login     Varchar (50) ,
        mdp       Varchar (50) ,
        PRIMARY KEY (id_membre )
);



CREATE TYPE online AS ENUM ('en ligne','absent ','occupé');

CREATE TABLE EnLigne(
        id_salon      Int NOT NULL ,
        id_membre     Int NOT NULL ,
        Online_status online,
        PRIMARY KEY (id_salon ,id_membre )
);




CREATE TABLE envoyerMess(
        id_salon  Int NOT NULL ,
        id_membre Int NOT NULL ,
        PRIMARY KEY (id_salon ,id_membre )
);

ALTER TABLE chat_message ADD CONSTRAINT FK_chat_message_id_membre FOREIGN KEY (id_membre) REFERENCES utilisateur(id_membre);
ALTER TABLE EnLigne ADD CONSTRAINT FK_online_id_salon FOREIGN KEY (id_salon) REFERENCES Salon(id_salon);
ALTER TABLE Enligne ADD CONSTRAINT FK_online_id_membre FOREIGN KEY (id_membre) REFERENCES utilisateur(id_membre);
ALTER TABLE envoyerMess ADD CONSTRAINT FK_envoyerMess_id_salon FOREIGN KEY (id_salon) REFERENCES Salon(id_salon);
ALTER TABLE envoyerMess ADD CONSTRAINT FK_envoyerMess_id_membre FOREIGN KEY (id_membre) REFERENCES utilisateur(id_membre);
