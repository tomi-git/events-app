DROP TABLE IF EXISTS igea_tk.dogadaj;
DROP TABLE IF EXISTS igea_tk.grad;
DROP TABLE IF EXISTS igea_tk.velicina_grada;
DROP TABLE IF EXISTS igea_tk.organizacijska_jedinica;
DROP TABLE IF EXISTS igea_tk.tip_organizacijske_jedinice;
DROP SCHEMA IF EXISTS igea_tk;


CREATE SCHEMA igea_tk;

	
CREATE TABLE igea_tk.tip_organizacijske_jedinice(
    sifra SERIAL PRIMARY KEY,
    naziv VARCHAR(50) NOT NULL,
    aktivan BOOLEAN NOT NULL
);

CREATE TABLE igea_tk.velicina_grada(
    sifra SERIAL PRIMARY KEY,
    naziv VARCHAR(50) NOT NULL,
    aktivan BOOLEAN NOT NULL
    
);

CREATE TABLE igea_tk.organizacijska_jedinica(
    sifra SERIAL PRIMARY KEY,
    naziv VARCHAR(50) NOT NULL,
    opis VARCHAR(200),
    tip_org_jedinice INTEGER NOT NULL,
    org_jedinica INTEGER,    
    FOREIGN KEY (tip_org_jedinice) REFERENCES igea_tk.tip_organizacijske_jedinice(sifra),
    FOREIGN KEY (org_jedinica) REFERENCES igea_tk.organizacijska_jedinica(sifra)
);

CREATE TABLE igea_tk.grad(
    sifra SERIAL PRIMARY KEY,
    naziv VARCHAR(50) NOT NULL,
    velicina INTEGER NOT NULL,
    org_jedinica INTEGER NOT NULL,
    FOREIGN KEY (velicina) REFERENCES igea_tk.velicina_grada(sifra),
    FOREIGN KEY (org_jedinica) REFERENCES igea_tk.organizacijska_jedinica(sifra)
);

CREATE TABLE igea_tk.dogadaj(
    sifra SERIAL PRIMARY KEY,
    naziv VARCHAR(250) NOT NULL,
    vrijeme_od TIMESTAMP WITHOUT TIME ZONE NOT NULL,  
    vrijeme_do TIMESTAMP,
    slobodan_ulaz VARCHAR(2) NOT NULL,
    grad INTEGER NOT NULL,
    FOREIGN KEY (grad) REFERENCES igea_tk.grad(sifra)
);




INSERT INTO igea_tk.tip_organizacijske_jedinice(sifra, naziv, aktivan) VALUES (0, 'REGIJA', true);
INSERT INTO igea_tk.tip_organizacijske_jedinice(sifra, naziv, aktivan) VALUES (1, 'ŽUPANIJA', true);



INSERT INTO igea_tk.velicina_grada(sifra, naziv, aktivan) VALUES (0, 'MALI', true);
INSERT INTO igea_tk.velicina_grada(sifra, naziv, aktivan) VALUES (1, 'SREDNJI', true);
INSERT INTO igea_tk.velicina_grada(sifra, naziv, aktivan) VALUES (2, 'VELIKI', true);

--regije
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (0, 'Sjeverozapadna Hrvatska', 'Eurostat regija', 0, null);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (1, 'Središnja i Istočna (Panonska) Hrvatska', 'Eurostat regija', 0, null);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (2, 'Jadranska Hrvatska', 'Eurostat regija', 0, null);
--zupanije
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (3, 'Grad Zagreb', null, 1, 0);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (4, 'Zagrebačka županija', null, 1, 0);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (5, 'Krapinsko-zagorska županija', null, 1, 0);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (6, 'Varaždinska županija', null, 1, 0);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (7, 'Koprivničko-križevačka županija', null, 1, 0);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (8, 'Međimurska županija', null, 1, 0);

INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (9, 'Bjelovarsko-bilogorska županija', null, 1, 1);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (10, 'Virovitičko-podravska županija', null, 1, 1);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (11, 'Požeško-slavonska županija', null, 1, 1);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (12, 'Brodsko-posavska županija', null, 1, 1);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (13, 'Osječko-baranjska županija', null, 1, 1);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (14, 'Vukovarsko-srijemska županija', null, 1, 1);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (15, 'Karlovačka županija', null, 1, 1);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (16, 'Sisačko-moslavačka županija', null, 1, 1);

INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (17, 'Primorsko-goranska županija', null, 1, 2);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (18, 'Ličko-senjska županija', null, 1, 2);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (19, 'Zadarska županija', null, 1, 2);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (20, 'Šibensko-kninska županija', null, 1, 2);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (21, 'Splitsko-dalmatinska županija', null, 1, 2);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (22, 'Istarska županija', null, 1, 2);
INSERT INTO igea_tk.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (23, 'Dubrovačko-neretvanska županija', null, 1, 2);

--gradovi Zag. županija 
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (0, 'Dugo Selo', 0, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (1, 'Ivanić Grad', 0, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (2, 'Jastrebarsko', 0, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (3, 'Samobor', 0, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (4, 'Sveta Nedelja', 0, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (5, 'Sveti Ivan Zelina', 0, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (6, 'Velika Gorica', 1, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (7, 'Vrbovec', 0, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (8, 'Zagreb', 2, 4);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (9, 'Zaprešić', 1, 4);


--gradovi Krapinsko zagorska
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (10, 'Donja Stubica', 0, 5);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (11, 'Klanjec', 0, 5);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (12, 'Krapina', 1, 5);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (13, 'Oroslavje', 0, 5);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (14, 'Pregrada', 0, 5);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (15, 'Zabok', 0, 5);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (16, 'Zlatar', 0, 5);

--gradovi Varaždinska žup
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (17, 'Ivanec', 0, 6);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (18, 'Lepoglava', 0, 6);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (19, 'Ludbreg', 0, 6);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (20, 'Novi Marof', 0, 6);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (21, 'Varaždin', 2, 6);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (22, 'Varaždinske Toplice', 0, 6);


--gradovi Koprivničko-križevačka
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (23, 'Đurđevac', 0, 7);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (24, 'Korpivnica', 1, 7);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (25, 'Križevci', 0, 7);

--gradovi Međimurska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (26, 'Čakovec', 1, 8);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (27, 'Mursko Središće', 0, 8);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (28, 'Prelog', 0, 8);

--gradovi Bjelovarsko-bilogorska
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (29, 'Bjelovar', 1, 9);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (30, 'Čazma', 0, 9);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (31, 'Daruvar', 0, 9);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (32, 'Garešnica', 0, 9);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (33, 'Grubišno Polje', 0, 9);

--gradovi Viroviticko-podravska 
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (34, 'Orahovica', 0, 10);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (35, 'Slatina', 0, 10);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (36, 'Virovitica ', 0, 10);

--gradovi Požeško-slavonska
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (37, 'Kutjevo', 0, 11);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (38, 'Lipik', 0, 11);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (39, 'Pakrac ', 0, 11);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (40, 'Pleternica ', 0, 11);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (41, 'Požega ', 0, 11);

--gradovi Brodsko-posavska
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (42, 'Nova Gradiška', 0, 12);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (43, 'Slavonski Brod', 1, 12);

--gradovi Osječko-baranjska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (44, 'Beli Manastir', 0, 13);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (45, 'Belišće', 0, 13);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (46, 'Donji Miholjac', 0, 13);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (47, 'Đakovo', 0, 13);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (48, 'Našice', 0, 13);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (49, 'Osijek', 2, 13);

--gradovi Vukovarsko-srijemska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (50, 'Ilok', 0, 14);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (51, 'Otok', 0, 14);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (52, 'Vinkovci', 0, 14);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (53, 'Vukovar', 1, 14);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (54, 'Županja', 0, 14);


--gradovikarlovačka županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (55, 'Duga Resa', 0, 15);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (56, 'Karlovac', 2, 15);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (57, 'Ogulin', 0, 15);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (58, 'Ozalj', 0, 15);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (59, 'Slunj', 0, 15);

--gradoviSisačko-moslavačka županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (60, 'Glina', 0, 16);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (61, 'Hrvatska Kostajnica', 0, 16);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (62, 'Kutina', 0, 16);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (63, 'Novska', 0, 16);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (64, 'Petrinja', 0, 16);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (65, 'Popovača', 0, 16);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (66, 'Sisak ', 1, 16);

--gradovi Primorsko-goranska
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (67, 'Bakar', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (68, 'Cres', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (69, 'Crikvenica', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (70, 'Čabar', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (71, 'Delnice', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (72, 'Kastav', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (73, 'Kraljevica', 1, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (74, 'Krk', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (75, 'Mali Lošinj', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (76, 'Novi Vinodolski', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (77, 'Opatija', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (78, 'Rab', 0, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (79, 'Rijeka', 2, 17);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (80, 'Vrbovsko ', 0, 17);

--gradovi Ličko-senjska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (81, 'Gospić ', 0, 18);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (82, 'Novalja', 0, 18);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (83, 'Otočac', 0, 18);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (84, 'Senj', 0, 18);

--gradovi Zadarska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (85, 'Benkovac ', 0, 19);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (86, 'Biograd na Moru', 0, 19);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (87, 'Nin', 0, 19);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (88, 'Obrovac', 0, 19);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (89, 'Pag', 0, 19);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (90, 'Zadar', 2, 19);

--gradovi Šibensko-kninska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (91, 'Drniš ', 0, 20);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (92, 'Knin', 0, 20);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (93, 'Skradin', 0, 20);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (94, 'Šibenik', 2, 20);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (95, 'Vodice', 0, 20);


--gradovi Splitsko-dalmatinska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (96, 'Hvar ', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (97, 'Imotski', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (98, 'Kaštela', 1, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (99, 'Komiža', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (100, 'Makarska', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (101, 'Omiš', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (102, 'Sinj', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (103, 'Solin', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (104, 'Split ', 2, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (105, 'Stari Grad', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (106, 'Supetar', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (107, 'Trilj', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (108, 'Trogir ', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (109, 'Vis', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (110, 'Vrgorac', 0, 21);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (111, 'Vrlika', 2, 21);

--gradovi Istarska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (112, 'Buje', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (113, 'Buzet', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (114, 'Labin ', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (115, 'Novigrad', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (116, 'Pazin', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (117, 'Poreč', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (118, 'Pula ', 2, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (119, 'Rovinj', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (120, 'Umag', 0, 22);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (121, 'Vodnjan', 0, 22);

--gradovi Dubrovačko-neretvanska županija
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (122, 'Dubrovnik', 2, 23);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (123, 'Korčula ', 2, 23);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (124, 'Metković', 0, 23);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (125, 'Opuzen', 0, 23);
INSERT INTO igea_tk.grad(sifra, naziv, velicina, org_jedinica) VALUES (126, 'Ploče', 0, 23);



---------------------------događaji INSERT----------------------------------------------------
--Varaždin events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Max Raabe & Palast Orchester', 21, 'NE', '2020-03-14 21:30:00'::timestamp, '2020-03-14  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava HOTEL BABILON', 21, 'NE', '2020-03-18 18:30:00'::timestamp, '2020-03-18  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava HOTEL BABILON 2', 21, 'NE', '2020-03-19 19:30:00'::timestamp, '2020-03-19  21:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('HK Varaždin - NK Istra 1961', 21, 'NE', '2020-04-11 18:30:00'::timestamp, '2020-04-11  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - David Maljković: S kolekcijom', 21, 'DA', '2020-03-20 08:30:00'::timestamp, '2020-03-20  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - David Maljković: S kolekcijom', 21, 'DA', '2020-04-07 08:30:00'::timestamp, '2020-04-07  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Rock opera', 21, 'NE', '2020-04-04 20:30:00'::timestamp, '2020-04-04  22:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Izlazak iz ormara', 21, 'NE', '2020-04-16 18:00:00'::timestamp, '2020-04-16  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Izlazak iz ormara', 21, 'NE', '2020-04-11 18:00:00'::timestamp, '2020-04-11  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 21, 'NE', '2020-04-01 18:00:00'::timestamp, '2020-04-01  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 21, 'NE', '2020-04-02 18:00:00'::timestamp, '2020-04-02  20:00:00'::timestamp); 
values ('Čitanje - Jürgen von der Lippe', 21, 'DA', '2020-05-10 16:00:00'::timestamp, '2020-05-10  17:00:00'::timestamp); 
values ('Čitanje - Jürgen von der Lippe', 21, 'DA', '2020-05-12 16:00:00'::timestamp, '2020-05-12  17:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('GRK Varaždin - RK Zagreb', 21, 'NE', '2020-05-05 18:30:00'::timestamp, '2020-05-05  20:30:00'::timestamp); 


--Ivanec events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Suzi Quatro', 17, 'NE', '2020-03-15 21:30:00'::timestamp, '2020-03-15  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Vjetar', 17, 'NE', '2020-03-19 18:30:00'::timestamp, '2020-03-19  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - David Maljković: S kolekcijom', 17, 'DA', '2020-03-21 08:30:00'::timestamp, '2020-03-21  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Izlazak iz ormara', 17, 'NE', '2020-04-20 18:00:00'::timestamp, '2020-04-20  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 17, 'NE', '2020-04-19 18:00:00'::timestamp, '2020-04-19  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Čitanje - Matej Matković', 17, 'DA', '2020-04-10 16:00:00'::timestamp, '2020-04-10  17:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('GRK Ivanec - RK Dubrava', 17, 'NE', '2020-05-04 18:00:00'::timestamp, '2020-05-04  20:00:00'::timestamp); 


--Mali Lošinj events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Suzi Quatro', 75, 'NE', '2020-06-15 21:30:00'::timestamp, '2020-06-15  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Ljeto', 75, 'NE', '2020-06-19 18:30:00'::timestamp, '2020-06-19  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - Leon Kraljić: S kolekcijom', 75, 'DA', '2020-05-21 08:30:00'::timestamp, '2020-05-21  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Izlazak iz ormara', 75, 'NE', '2020-05-20 18:00:00'::timestamp, '2020-05-20  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 75, 'NE', '2020-06-19 18:00:00'::timestamp, '2020-06-19  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Čitanje - Lubomir Dlesk', 75, 'DA', '2020-06-10 16:00:00'::timestamp, '2020-06-10  17:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('NK Polet Lošinj - Nk Omladinac', 75, 'DA', '2020-05-04 18:00:00'::timestamp, '2020-05-04  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Downhill World Cup', 75, 'NE', '2020-05-11 16:00:00'::timestamp, '2020-05-13  19:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Downhill World Cup', 75, 'NE', '2020-06-18 16:00:00'::timestamp, '2020-06-20  19:00:00'::timestamp); 

--Rijeka events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Suzi Quatro', 79, 'NE', '2020-06-20 21:30:00'::timestamp, '2020-06-20  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Ljeto', 75, 'DA', '2020-06-21 18:30:00'::timestamp, '2020-06-21  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - Leon Kraljić: S kolekcijom', 79, 'DA', '2020-05-19 08:30:00'::timestamp, '2020-05-19  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Izlazak iz ormara', 79, 'NE', '2020-06-20 18:00:00'::timestamp, '2020-06-20  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 79, 'NE', '2020-06-15 18:00:00'::timestamp, '2020-06-15  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Čitanje - Lubomir Dlesk', 79, 'DA', '2020-06-04 16:00:00'::timestamp, '2020-06-04  17:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('NK Rijeka - Nk Dinamo', 79, 'DA', '2020-06-01 18:00:00'::timestamp, '2020-06-01  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Triatlon World Cup', 79, 'NE', '2020-05-11 14:00:00'::timestamp, '2020-05-13  19:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Mediteranske ljetne igre', 79, 'NE', '2020-06-02 14:00:00'::timestamp, '2020-06-15  19:00:00'::timestamp); 


--Slavnoski Brod events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Brkovi', 43, 'NE', '2020-04-14 21:30:00'::timestamp, '2020-04-14  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Plašt', 43, 'NE', '2020-03-24 18:30:00'::timestamp, '2020-03-24  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Plašt', 43, 'NE', '2020-03-25 19:30:00'::timestamp, '2020-03-25  21:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - Mislav Dinjar: S kolekcijom', 43, 'DA', '2020-04-22 08:30:00'::timestamp, '2020-04-22  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Rock opera', 43, 'NE', '2020-04-09 20:30:00'::timestamp, '2020-04-09  22:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Šteta', 43, 'NE', '2020-04-01 18:00:00'::timestamp, '2020-04-01  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Mirando', 43, 'NE', '2020-04-07 18:00:00'::timestamp, '2020-04-07  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Mirando', 43, 'NE', '2020-04-09 18:00:00'::timestamp, '2020-04-09  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Nk Slavnoski Brod - Nk Razvitak', 43, 'DA', '2020-04-05 17:30:00'::timestamp, '2020-04-05  19:30:00'::timestamp); 

--Osijek events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Josipa Lisac', 49, 'NE', '2020-05-20 21:30:00'::timestamp, '2020-05-20  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Napredak', 49, 'NE', '2020-05-21 18:30:00'::timestamp, '2020-05-21  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Dangube', 49, 'NE', '2020-05-20 18:00:00'::timestamp, '2020-05-20  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Quatro', 49, 'NE', '2020-05-15 18:00:00'::timestamp, '2020-05-15  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('NK Osijek - Nk hajduk', 49, 'DA', '2020-05-01 18:00:00'::timestamp, '2020-05-01  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Stolni tenis - Kids', 49, 'DA', '2020-05-11 14:00:00'::timestamp, '2020-05-13  19:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Drava KUP 2020', 49, 'DA', '2020-05-02 14:00:00'::timestamp, '2020-05-15  19:00:00'::timestamp); 

--Belišće events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Alen', 45, 'NE', '2020-06-20 21:30:00'::timestamp, '2020-06-20  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Kao nekad', 45, 'NE', '2020-06-21 18:30:00'::timestamp, '2020-06-21  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Gdje ste', 45, 'NE', '2020-06-20 18:00:00'::timestamp, '2020-06-20  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Kolorado', 45, 'NE', '2020-06-15 18:00:00'::timestamp, '2020-06-15  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('NK Belišće - Nk Našice', 45, 'DA', '2020-06-01 18:00:00'::timestamp, '2020-06-01  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Atletika - Kids', 45, 'DA', '2020-06-11 14:00:00'::timestamp, '2020-06-13  19:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Drava KUP 2020', 45, 'DA', '2020-06-02 14:00:00'::timestamp, '2020-06-15  19:00:00'::timestamp); 

--Zadar events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Max Raabe & Palast Orchester', 90, 'NE', '2020-06-14 21:30:00'::timestamp, '2020-06-14  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Ljeto nam se vratilo', 90, 'NE', '2020-06-18 18:30:00'::timestamp, '2020-06-18  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Ljeto nam se vratilo', 90, 'NE', '2020-06-19 19:30:00'::timestamp, '2020-06-19  21:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('NK Zadar - NK Istra 1961', 90, 'NE', '2020-06-11 18:30:00'::timestamp, '2020-06-11  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - David Maljković: S kolekcijom', 90, 'DA', '2020-06-20 08:30:00'::timestamp, '2020-06-20  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - David Maljković: S kolekcijom', 90, 'DA', '2020-06-07 08:30:00'::timestamp, '2020-06-07  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Rock opera', 90, 'NE', '2020-06-04 20:30:00'::timestamp, '2020-06-04  22:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Izlazak iz ormara', 90, 'NE', '2020-07-16 18:00:00'::timestamp, '2020-07-16  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Željko Pervan - Izlazak iz ormara', 90, 'NE', '2020-07-11 18:00:00'::timestamp, '2020-07-11  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 90, 'NE', '2020-07-01 18:00:00'::timestamp, '2020-07-01  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 90, 'NE', '2020-07-02 18:00:00'::timestamp, '2020-07-02  20:00:00'::timestamp); 
values ('Čitanje - Jürgen von der Lippe', 90, 'DA', '2020-06-10 16:00:00'::timestamp, '2020-06-10  17:00:00'::timestamp); 
values ('Čitanje - Jürgen von der Lippe', 90, 'DA', '2020-06-12 16:00:00'::timestamp, '2020-06-12  17:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('GRK zadar - RK Zagreb', 90, 'NE', '2020-06-05 18:30:00'::timestamp, '2020-06-05  20:30:00'::timestamp); 


--Biograd na moru events
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - T&T', 86, 'NE', '2020-06-14 21:30:00'::timestamp, '2020-06-14  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Ljeto i more', 86, 'NE', '2020-06-18 18:30:00'::timestamp, '2020-06-18  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Ljeto i more', 86, 'NE', '2020-06-19 19:30:00'::timestamp, '2020-06-19  21:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('NK Biograd - NK Istra 1961', 86, 'NE', '2020-06-11 18:30:00'::timestamp, '2020-06-11  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - Dino: S kolekcijom', 86, 'DA', '2020-06-20 08:30:00'::timestamp, '2020-06-20  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Izložba - Dino: S kolekcijom', 86, 'DA', '2020-06-07 08:30:00'::timestamp, '2020-06-07  19:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Opera', 86, 'NE', '2020-06-04 20:30:00'::timestamp, '2020-06-04  22:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Dundo - Izlazak iz ormara', 86, 'NE', '2020-07-16 18:00:00'::timestamp, '2020-07-16  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Dundo - Izlazak iz ormara', 86, 'NE', '2020-07-11 18:00:00'::timestamp, '2020-07-11  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - Cirque du Soleil - Corteo', 86, 'NE', '2020-07-01 18:00:00'::timestamp, '2020-07-01  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Cirkus - N1', 86, 'NE', '2020-07-02 18:00:00'::timestamp, '2020-07-02  20:00:00'::timestamp); 
values ('Čitanje - Danijel', 86, 'DA', '2020-06-10 16:00:00'::timestamp, '2020-06-10  17:00:00'::timestamp); 
values ('Čitanje - Miro', 86, 'DA', '2020-06-12 16:00:00'::timestamp, '2020-06-12  17:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('GRK Biograd - RK Vodice', 86, 'NE', '2020-06-05 18:30:00'::timestamp, '2020-06-05  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Koncert - Aurelije', 86, 'NE', '2020-06-20 21:30:00'::timestamp, '2020-06-20  23:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Predstava Uvijek', 86, 'NE', '2020-06-21 18:30:00'::timestamp, '2020-06-21  20:30:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Mirko - Gdje ste', 86, 'NE', '2020-06-20 18:00:00'::timestamp, '2020-06-20  20:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Plivanje', 86, 'DA', '2020-06-11 14:00:00'::timestamp, '2020-06-13  19:00:00'::timestamp); 
insert into igea_tk.dogadaj (naziv, grad, slobodan_ulaz, vrijeme_od, vrijeme_do) 
values ('Ljetne igre', 86, 'DA', '2020-06-02 14:00:00'::timestamp, '2020-06-15  19:00:00'::timestamp); 

