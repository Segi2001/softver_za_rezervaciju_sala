
insert  into `api_key`(`id`,`value`) values 
(1,'jabuka'),
(2,'kruska'),
(3,'$2a$10$oD.IDPbdz5q5Q6OWf8UyhuSI4V3VKV7UhnkYVWYwsBW/tbI2XOhES'),
(4,'$2a$10$5Y2rUjggECnTdERd3cR4D.bh2C0OBcV/98HvfrUuv5cKCPRx7mD/6');


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
(6,'Redovni profesor');


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
(3,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',5,0,0),
(4,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',4,1,0),
(5,'{bcrypt}$2a$12$VgHMNkL9rIqcOf1vCy8l0ORfgNfsyNDGRABLvIOcOT201Y3aOrOgW',6,0,0),
(7,'{bcrypt}$2a$10$1dJf4QH9csrTX1g5/QbK4u.7i7zKQ32qW6V7EVzVhDa7GpzMQrJFu',3,0,0);



insert  into `roles`(`id`,`role`,`korisnik_id`) values 
(2,'ROLE_KORISNIK',2),
(3,'ROLE_KORISNIK',5),
(6,'ROLE_ADMIN',2),
(7,'ROLE_KORISNIK',4),
(8,'ROLE_ADMIN',5),
(10,'ROLE_KORISNIK',7);


insert  into `svrha_rezervacije`(`id`,`svrha`,`opis`,`organizator`) values 
(3,'Organizovanje ispita','Orhanizovace se ispit iz programskih jezika','Nina Jovanovic'),
(4,'Gostujuce predavanje','Gostujuce predavanje ce odrzati gospodin Miroljub Petrovic','Miroljub Petrovic'),
(5,'Gostujuce predavanje','Prisustvovace profesor ETF na gostujucem predavanju','Stefan Segrt'),
(6,'Predavanje','Predavanje iz predmeta Projektovanje softvera','Sinisa Vlajic'),
(7,'Predavanje','Predavanje iz predmeta Napredno programiranje','Bojan Tomic'),
(8,'Organizacija ispita','Organizacija ispita iz Ekonomije','Milos Parezanin'),
(9,'Predavanje','Predavanje iz matematike','Nebojsa Nikolic'),
(10,'Vezbe','Vezbe iz predmeta Osnove kvaliteta','Sara Dimitrijevic'),
(11,'Organizovanje ispita','Organizacija ispita iz baza podataka','Milica Skembarevic'),
(12,'Organizovanje ispita','Organizacija ispita iz Projektovanja informacionih sistema','Vladimir Belca'),
(13,'Predavanje','Organizacija ispita iz Projektovanja informacionih sistema','Vladimir Belca'),
(14,'Organizovanje ispita','Organizovanje ispita iz Matematike','Milica Stojanovic'),
(15,'Gostujuce predavanje','Gosujuci profesor sa matematickog fakluteta','Boban Todic'),
(16,'Vezbe','Vezbe iz predmeta Modelovanje poslovnih procesa','Sladjan Babarogic'),
(17,'Predavanje','Predavanje iz predmeta Finansijski menadzment','Sladjana Benkovic'),
(18,'Vezbe','Vezbe iz predmeta Racunovodstvo','Zeljko Spasenic'),
(19,'Vezbe','Vezbe iz predmeta Naprednih java tehnologija','Dusan Savic'),
(20,'Predavanja','Predavanja iz predmeta Softverski paterni','Ilija Antovic'),
(21,'Organizovanje ispita','Organizovanje ispita iz Osnova organizacija','Mladen Cudanov'),
(22,'Vezbe','Vezbe iz Teorije sistema','Milica Zukanovic'),
(23,'Predavanje','Predavanje iz predmeta Neuronske mreze','Pavle Milosevic'),
(24,'Organizovanje ispita','Organizavanje ispita iz Marketinga','Velimir Stavljanin'),
(25,'Organizovanje ispita','Organizovanje ispita iz Programiranja 3','Vladan Devidzic'),
(26,'Organizovanje ispita','Organizovanje ispita iz Inteligentih sistema','Jelena Jovanovic'),
(27,'Vezbe','Vezbe iz predmeta Baze podataka','Vladimir Belca'),
(28,'Vezbe','Vezbe iz predmeta Projektovanja informacionih sistema','Olga Jejic'),
(29,'Vezbe','Vezbe iz predmeta Programskih jezika','Filip Furtula'),
(30,'Predavanja','Predavanja iz predmeta Programski jezika','Nina Jovanovic'),
(31,'Organizovanje ispita','Organizovanje ispita iz Osnove informaciono-komunikacionih tehnlogija','Dejan Simic'),
(32,'Predavanje','Predavanja iz predmeta Inteligentnih sistema','Dragan Djuric'),
(33,'Vezbe','Vezbe iz predmeta Operaciona istrazivanja','Marija Kuzmanovic'),
(34,'Organizovanje prijmnog ispita','Organizovanje ispita iz programskih jezika','Bobi Todic'),
(35,'Predavanje','Predavanje iz matematike','Zoran Markovic');
























