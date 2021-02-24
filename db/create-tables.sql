DROP TYPE IF EXISTS contact_t;
CREATE TYPE contact_t AS enum (
    'address',
	'phone',
	'email'
);

DROP TYPE IF EXISTS client_type_t;
CREATE TYPE client_type_t AS enum (
    'person',
    'organization'
);

CREATE TABLE clients (
    client_id serial PRIMARY KEY,
	client_type client_type_t NOT NULL,
	client_name text NOT NULL
);

CREATE TABLE client_contacts (
	client_contact_id serial PRIMARY KEY,
	client_id integer REFERENCES clients (client_id) ON DELETE CASCADE ON UPDATE CASCADE,
	client_contact_type contact_t NOT NULL,
	client_contact text NOT NULL
);

CREATE TABLE client_contact_persons (
	client_cp_id serial PRIMARY KEY,
	client_id integer REFERENCES clients (client_id) ON DELETE CASCADE ON UPDATE CASCADE,
	client_cp_name text NOT NULL,
	client_cp_phone text NOT NULL
);

CREATE TABLE employees (
    employee_id serial PRIMARY KEY,
	employee_name text NOT NULL,
	education text NOT NULL,
	position text NOT NULL
);

CREATE TABLE employee_contacts (
	employee_contact_id serial PRIMARY KEY,
	employee_id integer REFERENCES employees (employee_id) ON DELETE CASCADE ON UPDATE CASCADE,
	employee_contact_type contact_t NOT NULL,
	employee_contact text NOT NULL
);


CREATE TABLE service_types (
    service_type_id text PRIMARY KEY,
	service_type_name text NOT NULL,
	service_info text
);

CREATE TABLE services (
    service_id serial PRIMARY KEY,
	service_type_id text REFERENCES service_types (service_type_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	client_id integer REFERENCES clients (client_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	service_begin date,
	service_end date,
	price numeric(30, 2)
);

CREATE TABLE tasks (
    task_id serial PRIMARY KEY,
	service_id integer REFERENCES services (service_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	employee_id integer REFERENCES employees (employee_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	description text
);
