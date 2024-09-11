

insert  into `ucionica`(`id`) values 
(6),
(7),
(8);

insert  into `amfiteatar`(`id`) values 
(1),
(2),
(3),
(14),
(15),
(17);






insert  into `kombinovana`(`id`,`broj_racunara`) values 
(10,40);



insert  into `rc_sala`(`id`,`broj_racunara`) values 
(4,40),
(5,40),
(16,56);


insert  into `sala_za_sastanke`(`id`) values 
(9);
     


insert  into `sala`(`id`,`naziv`,`napomena`,`broj_mesta`,`tip_sale`,`status`) values 
(1,'Amfiteatar 1','Zidovi amfiteatra su zelene boje',170,'AMF',0),
(2,'Amfiteatar 2','Nalazi se iznad Amfiteatra 2',250,'AMF',1),
(3,'Amfiteatar 3','Nalazi se u visokom prizemlju',250,'AMF',1),
(4,'Sala 08','Prvi racunarski centar u prizemlju',40,'RC',1),
(5,'Sala 09','Drugi racunarski centar u prizemlju',40,'RC',1),
(6,'Sala 11','Prva ucionica u staroj zgradi',50,'U',1),
(7,'Sala 12','Druga ucionica u staroj zgradi',60,'U',1),
(8,'Sala 13','Treca ucionica u staroj zgradi',50,'U',1),
(9,'Sala 62','Sala za sastanke na trecem spratu',50,'SZS',1),
(10,'Sala 60','Kombinovana sala',60,'KOM',1),
(14,'Sala 115','Sala se nalazi na prvom spratu',60,'AMF',0),
(15,'Sala 115','Sala se nalazi na prvom spratu',60,'AMF',0),
(16,'007','Nalazi se u prizemlju',150,'RC',1),
(17,'Amfiteatar 3','Nalazi se u novoj zgradi',150,'AMF',1);


insert  into `student`(`id`,`svrha_pristupa`) values 
(1,'turnir'),
(2,'organizacija'),
(3,'ispit');


insert  into `zaposleni_u_nastavi`(`id`,`zvanje`,`katedra`) values 
(4,'Profa','SI'),
(6,'docent','si');


insert  into `zaposleni_van_nastave`(`id`) values 
(7);




insert  into `zaposleni`(`id`,`titula`) values 
(4,'Redovni profsefor'),
(6,'Redovni profesor'),
(7,'Asistent');


insert  into `osoba`(`id`,`ime`,`prezime`,`email`,`tip_osobe`) values 
(1,'Marko','Srejic','srejic@gmail.com','S'),
(2,'Stefan','Segrt','stefan.segrt.0502@gmail.com','S'),
(3,'Boban','Todic','bobantodic23@gmail.com','S'),
(4,'Dusan','Savic','savic@gmail.com','ZN'),
(5,'Dimitrije','Siljkovic','siljkovic@gmail.com','S'),
(6,'Ilija','Antovic','antovic@gmail.com','ZN'),
(7,'Bogdan','Bogdanovic','bogdanovic@gmail.com','ZNN');

insert  into `korisnik`(`id`,`sifra`,`osoba_id`,`admin`,`privremena_sifra`) values 
(1,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',1,0,0),
(2,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',2,0,0),
(3,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',7,0,0),
(4,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',4,1,0),
(5,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',6,0,0),
(7,'{bcrypt}$2a$10$1dJf4QH9csrTX1g5/QbK4u.7i7zKQ32qW6V7EVzVhDa7GpzMQrJFu',3,0,0);




























