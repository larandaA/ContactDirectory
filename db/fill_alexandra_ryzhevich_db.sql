# ---------------COUNTRIES----------------

INSERT INTO `country`(`name`, `code`) VALUES ('Russia', 7);
INSERT INTO `country`(`name`, `code`) VALUES ('GB', 44);
INSERT INTO `country`(`name`, `code`) VALUES ('Germany', 49);
INSERT INTO `country`(`name`, `code`) VALUES ('Belarus', 375);

#----------------CONTACTS------------------

INSERT INTO `contact`(`first_name`, `last_name`, `patronymic`, `birth_date`, `gender`, `country_id`, `marital_status`, `web_site`, `email`, `place_of_work`) 
		VALUES ('Ivan', 'Ivanov', 'Ivanovich', '1990-09-15', 'male', 1, 'married', 'https://google.com', 'ivan.ivanov@google.com', 'Google Inc.');
INSERT INTO `contact`(`first_name`, `last_name`, `patronymic`, `birth_date`, `gender`, `country_id`, `marital_status`, `web_site`, `email`, `place_of_work`) 
		VALUES ('Alan', 'Turing', NULL, '1912-06-23', 'male', 2, 'single', NULL, NULL, 'British Army');
INSERT INTO `contact`(`first_name`, `last_name`, `patronymic`, `birth_date`, `gender`, `country_id`, `marital_status`, `web_site`, `email`, `place_of_work`) 
		VALUES ('Helga', 'Tern', NULL, '1985-01-01', 'female', 3, 'married', NULL, 'htern@gmail.com', 'BMW Group');
INSERT INTO `contact`(`first_name`, `last_name`, `patronymic`, `birth_date`, `gender`, `country_id`, `marital_status`, `web_site`, `email`, `place_of_work`) 
		VALUES ('Alexandra', 'Ryzhevich', NULL, '1997-07-25', 'female', 4, 'single', NULL, 'larandaansil@gmail.com', NULL);

#-----------------ADDRESSES--------------------

INSERT INTO `address`(`country_id`, `city`, `local_address`, `index`, `contact_id`) VALUES (3, "Berlin", NULL, "7456GH", 3);
INSERT INTO `address`(`country_id`, `city`, `local_address`, `index`, `contact_id`) VALUES (1, "Moscow", NULL, "234553", 1);
INSERT INTO `address`(`country_id`, `city`, `local_address`, `index`, `contact_id`) VALUES (4, "Minsk", NULL, "220047", 4);
INSERT INTO `address`(`country_id`, `city`, `local_address`, `index`, `contact_id`) VALUES (2, "London", NULL, NULL, 2);

#-----------------PHOTOS------------------------

INSERT INTO `photo`(`contact_id`, `path`) values (1, 'img/contacts/default.jpg');
INSERT INTO `photo`(`contact_id`, `path`) values (2, 'img/contacts/default.jpg');
INSERT INTO `photo`(`contact_id`, `path`) values (3, 'img/contacts/default.jpg');
INSERT INTO `photo`(`contact_id`, `path`) values (4, 'img/contacts/default.jpg');

#----------------PHONE NUMBERS---------------------

INSERT INTO `phone`(`country_id`, `operator_code`, `phone_number`, `type`, `comment`, `contact_id`) VALUES (1, 445, 8576890, "mobile", NULL, 1);
INSERT INTO `phone`(`country_id`, `operator_code`, `phone_number`, `type`, `comment`, `contact_id`) VALUES (1, 228, 7894909, "home", NULL, 1);
INSERT INTO `phone`(`country_id`, `operator_code`, `phone_number`, `type`, `comment`, `contact_id`) VALUES (4, 29, 5062963, "mobile", NULL, 4);
