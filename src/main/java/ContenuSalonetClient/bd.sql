/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ngoyet-ndiaye
 * Created: 12 mai 2016
 */
CREATE TABLE chat_message(
        message_id   Int NOT NULL ,
        message_user Varchar (50) ,
        message_time TIMESTAMP,
        message_text Varchar (255) ,
        id_membre    Int ,
        PRIMARY KEY (message_id )
);



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
