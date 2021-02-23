DROP TYPE IF EXISTS contact_person_t;
CREATE TYPE contact_person_t AS (
    cp_name text,
    cp_phone text
);

DROP TYPE IF EXISTS client_type_t;
CREATE TYPE client_type_t AS enum (
    'person',
    'organization'
);

CREATE TABLE clients (
    client_id serial PRIMARY KEY,
	client_type client_type_t NOT NULL,
	client_name text NOT NULL,
	client_addresses text[],
	client_phones text[],
	client_emails text[],
	client_contact_persons contact_person_t[]
);

CREATE TABLE employees (
    employee_id serial PRIMARY KEY,
	employee_name text NOT NULL,
	employee_address text NOT NULL,
	employee_phones text[] NOT NULL,
	employee_emails text[] NOT NULL,
	education text NOT NULL,
	position text NOT NULL
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
