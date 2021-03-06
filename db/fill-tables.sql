INSERT INTO service_types (service_type_id, service_type_name, service_info) VALUES
    ('ОБЮ', 'Оформление банкротства юридического лица', 'Перечень необходимых документов:
Свидетельство о госрегистрации и все учредительные документы организации;
Перечень должников заявителя и список кредиторов, с указанием их адресов и расшифровкой дебиторской и кредиторской задолженности;
Составленный на последнюю учетную дату бухгалтерский баланс, а при отсутствии – другие заменяющие его данные;
Решение уполномоченного органа должника, собственников имущества либо учредителей обратиться в суд для признания несостоятельности компании (при его наличии);
Решение о назначении (избрании) представителя собственника имущества, учредителей или уполномоченного органа;
Если до подачи заявления в суд было проведено собрание работников предприятия – должника, то необходимо приложить также его протокол, согласно которому был избран представитель работников в ходе арбитражного процесса;
Подготовленный оценщиком имущества отчет о его стоимости (при наличии);
Если у руководителя имеется допуск к гостайне, документы, подтверждающие это, в которых указана форма допуска (требуется, если у юридического лица есть лицензия на работы такими сведениями);'),
	('К', 'Консультация', NULL),
	('СПТС', 'Судебное представительство по трудовым спорам', NULL);
	
INSERT INTO clients (client_type, client_name) VALUES
    ('organization', 'ООО ВодоСтрой'),
	('person', 'Иванова Иванна Ивановна'),
	('organization', 'cat and mouse');
	
INSERT INTO client_contacts (client_id, client_contact_type, client_contact) VALUES
    ('1', 'address', 'г. Иваново, ул. Ленина, д. 2'),
	('1', 'phone', '+74950044291'),
	('1', 'phone', '+79061730490'),
	('1', 'email', 'vasya_pupkin@mail.ru'),
	('2', 'phone', '+74999545279'),
	('3', 'address', 'г. Шуя, ул. Советская, д. 49'),
	('3', 'phone', '+79033473590'),
	('3', 'email', 'helen95@gmail.ru');
	
INSERT INTO client_contact_persons (client_id, client_cp_name, client_cp_phone) VALUES
    ('1', 'Галина Ивановна', '+79061689415'),
	('1', 'Иван Галинович', '+79095801659');
	
INSERT INTO employees (employee_name, education, position) VALUES
    ('Головченко Натан Самсонович', 'Ивановский государственный университет, юриспруденция', 'юрист'), 
	('Яброва Ольга Глебовна', 'Ивановский (филиал) Институт управления, юриспруденция', 'стажёр'),
	('Дрягина Ефросиния Матвеевна', 'Московский государственный университет, юриспруденция', 'главный юрист'),
	('Дмитриев Агафон Егорович', 'Ивановский государственный университет, юриспруденция', 'юрист');
	 
INSERT INTO employee_contacts (employee_id, employee_contact_type, employee_contact) VALUES
	 ('1', 'address', 'г. Иваново, Спортивный пер., д. 5'),
	 ('1', 'phone', '+74954096462'),
	 ('1', 'email', 'golovan@yandex.ru'),
	 ('2', 'address', 'г. Иваново, ул. Октябрьская, д. 3'),
	 ('2', 'phone', '+79120995303'),
	 ('2', 'email', 'olga_yabrova@mail.ru'),
	 ('3', 'address', 'г. Иваново, ул. Строителей, д. 33'),
	 ('3', 'phone', '+79061691601'),
	 ('3', 'email', 'dryagina_frosya@gmail.ru'),
	 ('4', 'address', 'г. Иваново, ул. Ленина, д. 20'),
	 ('4', 'phone', '+74951077246'),
	 ('4', 'email', 'agafon73@yandex.ru'); 
	 
INSERT INTO services (service_type_id, client_id, service_begin, service_end, price) VALUES
    ('ОБЮ', '1', DATE('2020-09-30'), DATE('2020-10-20'), '15000'),
	('К', '2', DATE('2020-11-12'), DATE('2020-11-12'), '2000'),
	('СПТС', '3', DATE('2020-12-13'), NULL, '50000');
	
INSERT INTO tasks (service_id, employee_id, description) VALUES
    ('1', '1', 'подготовка необходимых документов'),
	('1', '2', 'помощь в подготовке необходимых документов'),
	('2', '3', 'консультация по разделу имущества при разводе'),
	('3', '3', NULL),
	('3', '4', NULL);
	