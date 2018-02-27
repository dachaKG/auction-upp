insert into category(name) values ('sport');
insert into category(name) values ('poljoprivreda');
insert into category(name) values ('nauka');

insert into firm(distance, name, category_id) values (20, 'firma a', 3);
insert into firm(distance, name, category_id) values (20, 'firma b', 3);
insert into firm(distance, name, category_id) values (20, 'firma c', 3);


insert into user(address, city, confirmation_mail, confirmed, email, first_name, last_name, password, role, username, zip_code, firm_id) values ('adresa', 'bor', null, true, 'uppauction1@gmail.com', 'uros', 'vasiljevic', '$2a$10$DbFXNtri3FfQK/8QDgfXYO/97LyY9Jje1fxAJUCUVMssYeszD6AlK', 'ROLE_USER', 'parti', '3333', null)
insert into user(address, city, confirmation_mail, confirmed, email, first_name, last_name, password, role, username, zip_code, firm_id) values ('adresa', 'bor', null, true, 'uppauctionmain@gmail.com', 'uros', 'vasiljevic', '$2a$10$DbFXNtri3FfQK/8QDgfXYO/97LyY9Jje1fxAJUCUVMssYeszD6AlK', 'ROLE_FIRM', 'partibor', '3333', '1')
insert into user(address, city, confirmation_mail, confirmed, email, first_name, last_name, password, role, username, zip_code, firm_id) values ('adresa', 'bor', null, true, 'uppauction2@gmail.com', 'uros', 'vasiljevic', '$2a$10$DbFXNtri3FfQK/8QDgfXYO/97LyY9Jje1fxAJUCUVMssYeszD6AlK', 'ROLE_FIRM', 'pera', '3333', '2')
insert into user(address, city, confirmation_mail, confirmed, email, first_name, last_name, password, role, username, zip_code, firm_id) values ('adresa', 'bor', NULL, true, 'uppauction1@gmail.com', 'milan', 'milanovic', '$2a$10$FIopgYlolxjM.PAjt7OgAuEwbzAzdNiv6WR7m/gQIefbg5l.fOio2', 'ROLE_FIRM', 'milan', '3333', '3')
