-- Table: public.utilisateur

-- DROP TABLE public.utilisateur;

CREATE TABLE public.utilisateur
(
  id_membre integer NOT NULL,
  login character varying(50) NOT NULL,
  mdp character varying(50),
  type character varying(50),
  CONSTRAINT utilisateur_pkey PRIMARY KEY (id_membre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.utilisateur
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


-- Table: public.salon

-- DROP TABLE public.salon;


-- Table: public.enligne

-- DROP TABLE public.enligne;

CREATE TABLE public.enligne
(
  id_salon integer NOT NULL,
  id_membre integer NOT NULL,
  online_status text,
  CONSTRAINT enligne_pkey PRIMARY KEY (id_salon, id_membre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.enligne
  OWNER TO postgres;


-- Table: public.salonutilisateur

-- DROP TABLE public.salonutilisateur;

CREATE TABLE public.salonutilisateur
(
  id_salon integer NOT NULL,
  id_membre integer NOT NULL,
  message_time text,
  CONSTRAINT salonutilisateur_pkey PRIMARY KEY (id_salon, id_membre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.salonutilisateur
  OWNER TO postgres;


-- Table: public.utilisateur

-- DROP TABLE public.utilisateur;

-- Table: public.envoyermess

-- DROP TABLE public.envoyermess;

CREATE TABLE public.envoyermess
(
  id_salon integer NOT NULL,
  id_membre integer NOT NULL,
  message_time timestamp without time zone NOT NULL,
  message text,
  CONSTRAINT envoyermess_pkey PRIMARY KEY (id_salon, id_membre, message_time)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.envoyermess
  OWNER TO postgres;



-- Table: public.messageprive

-- DROP TABLE public.messageprive;

CREATE TABLE public.messageprive
(
  id_membre1 integer NOT NULL,
  id_membre2 integer NOT NULL,
  message_time timestamp without time zone NOT NULL,
  message text,
  CONSTRAINT message_pkey PRIMARY KEY (id_membre1, id_membre2, message_time)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.messageprive
  OWNER TO postgres;
