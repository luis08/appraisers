drop table if exists contact;
create table contact (
  id int not null,
  first_name varchar(100) null,
  middle_name varchar(100) null,
  last_name varchar(100) null,
  nick_name varchar(100) null,
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_contact primary key (id)
);

drop table if exists address;
create table address (
  id int not null auto_increment,
  type varchar(50) not null, 
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_address primary key (id)
);

drop table if exists phone;
create table phone (
  id int not null auto_increment,
  phone_number varchar(50) not null,
  type varchar(50) not null,
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_phone primary key (id)
);

drop table if exists email;
create table email (
  id int not null auto_increment,
  type varchar(50) not null,
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_email primary key (id)
);

drop table if exists client_requirement;
drop table if exists client;
create table client (
  id int not null auto_increment,
  account_number varchar(15) not null, 
  type varchar(50) not null,
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_client primary key (id),
  constraint uq_account_number unique(account_number)
);

drop table if exists client_requirement_type;
create table client_requirement_type (
  id int not null auto_increment,
  name varchar(100) not null,
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_client_requirement_type primary key (id),
  constraint uq_client_requirement_type_name unique(name)
);

/*  Are all requirements pictures?  Could it be something else, just text? */
create table client_requirement (
  id int not null auto_increment,
  client_id int not null,
  client_requirement_type_id int not null,
  name varchar(100) not null,
  description varchar(1000),
  constraint pk_client_requirement primary key (id),
  constraint fk_client_requirement_client foreign key (client_id)
    references client(id),
  constraint fk_client_requirement_client_requirement_type foreign key (client_requirement_type_id) 
    references client_requirement_type(id)
);

drop table if exists assignment_request_attachment; 
drop table if exists assignment_request;
create table assignment_request (
  id int not null auto_increment,
  identifier varchar(12) not null,
  type varchar(50) not null,
  client_id int,
  company_name varchar(100),
  account_number varchar(50),
  adjuster_last varchar(50),
  adjuster_first varchar(50),
  adjuster_phone varchar(50),
  adjuster_email varchar(100),
  company_address1 varchar(100),
  company_address2 varchar(100),
  company_city varchar(100),
  company_state varchar(10), 
  company_zip varchar(30),
  claim_number varchar(50),
  insured_or_claimant varchar(20),
  policy_number varchar(50),
  same_as_owner tinyint(1),
  deductible_amount DECIMAL(13,2),
  claimant_first varchar(50),
  claimant_last varchar(50),
  claimant_phone varchar(50),
  claimant_email varchar(100),
  claimant_address1 varchar(100),
  claimant_address2 varchar(100),
  claimant_city varchar(100),
  claimant_state varchar(50), 
  claimant_zip varchar(30),  
  vehicle_make varchar(50),  
  vehicle_model varchar(50),
  vehicle_year varchar(10),
  vehicle_vin varchar(50),
  vehicle_license varchar(50),
  vehicle_state varchar(10),
  vehicle_location_name varchar(100),
  is_vehicle_location_repair_facility tinyint(1),
  vehicle_location_address1 varchar(100),
  vehicle_location_address2 varchar(100),
  vehicle_location_city varchar(100),
  vehicle_location_state varchar(10),
  vehicle_location_zip varchar(30),
  vehicle_location_phone varchar(50),
  loss_date datetime,
  type_of_loss varchar(50),
  loss_description text,
  has_copy_of_appraisal tinyint(1),
  has_salvage_bids tinyint(1),
  has_acv tinyint(1),
  acv_evaluation_method text,
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_assignment_request primary key (id),
  constraint uq_assignment_request_identifier unique (identifier)
);


drop table if exists assignment_request_attachment; 
create table assignment_request_attachment (
  id int not null auto_increment,
  assignment_request_id int not null,
  original_file_name varchar(500) not null, 
  sanitized_file_name varchar(500) not null, 
  path varchar(500) not null, 
  file_type varchar(20),
  description varchar(1000),
  storage_id varchar(100),
  date_created timestamp not null default now(),
  date_modified datetime null,
  active tinyint(1) not null default 1,
  inactive_date datetime null,
  constraint pk_assignment_request_attachment primary key (id)
);