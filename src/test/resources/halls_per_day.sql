
insert  into `api_key`(`id`,`value`) values 
(1,'jabuka'),
(2,'kruska'),
(3,'$2a$10$oD.IDPbdz5q5Q6OWf8UyhuSI4V3VKV7UhnkYVWYwsBW/tbI2XOhES'),
(4,'$2a$10$5Y2rUjggECnTdERd3cR4D.bh2C0OBcV/98HvfrUuv5cKCPRx7mD/6');





insert  into `rc_sala`(`id`,`broj_racunara`) values 
(5,40);








insert  into `sala`(`id`,`naziv`,`napomena`,`broj_mesta`,`tip_sale`,`status`) values 
(5,'Sala 09','Drugi racunarski centar u prizemlju',40,'RC',1);



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
(3,'ROLE_KORISNIK',3),
(6,'ROLE_ADMIN',2);



insert  into `svrha_rezervacije`(`id`,`svrha`,`opis`,`organizator`) values 
(3,'Organizovanje ispita','Orhanizovace se ispit iz programskih jezika','Nina Jovanovic'),
(4,'Gostujuce predavanje','Gostujuce predavanje ce odrzati gospodin Miroljub Petrovic','Miroljub Petrovic');


insert  into `rezervacija`(`id`,`sala_id`,`korisnik_id`,`vreme_pocetka`,`vreme_zavrsetka`,`svrha_id`,`status_rezervacije`) values 
(5,5,3,'2024-06-27 15:00:00','2024-06-27 16:00:00',3,'U CEKANJU'),
(6,5,2,'2024-06-27 21:15:00','2024-06-27 23:45:00',4,'U CEKANJU'),
(14,5,2,'2024-06-27 10:30:00','2024-06-27 12:00:00',3,'POTVRDJENA'),
(15,5,2,'2024-06-27 13:30:00','2024-06-27 15:45:00',4,'ODBIJENA');























