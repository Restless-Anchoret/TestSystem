-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- PostgreSQL version: 9.2
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: test_system | type: DATABASE --
-- CREATE DATABASE test_system
-- ;
-- -- ddl-end --
-- 

-- object: public.user | type: TABLE --
CREATE TABLE public.user(
	id serial NOT NULL,
	login varchar(30) NOT NULL,
	password_hash varchar(100) NOT NULL,
	role varchar(15) NOT NULL,
	registration_date date NOT NULL,
	actual boolean NOT NULL DEFAULT true,
	CONSTRAINT "PK_user_id" PRIMARY KEY (id),
	CONSTRAINT "UN_user_login" UNIQUE (login)

);
-- ddl-end --
-- object: "IX_user_id" | type: INDEX --
CREATE INDEX "IX_user_id" ON public.user
	USING btree
	(
	  id ASC NULLS LAST
	);
-- ddl-end --

-- object: "IX_user_login" | type: INDEX --
CREATE INDEX "IX_user_login" ON public.user
	USING btree
	(
	  login ASC NULLS LAST
	);
-- ddl-end --


-- object: public.personal_data | type: TABLE --
CREATE TABLE public.personal_data(
	id serial NOT NULL,
	first_name varchar(30),
	last_name varchar(30),
	patronymic varchar(30),
	organization varchar(100),
	course smallint,
	country varchar(50),
	city varchar(50),
	CONSTRAINT "PK_personal_data_id" PRIMARY KEY (id)

);
-- ddl-end --
-- object: "IX_personal_data_id" | type: INDEX --
CREATE INDEX "IX_personal_data_id" ON public.personal_data
	USING btree
	(
	  id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.competition | type: TABLE --
CREATE TABLE public.competition(
	id serial NOT NULL,
	name varchar(200) NOT NULL,
	hold_competition boolean NOT NULL,
	evaluation_type varchar(5) NOT NULL,
	registration_type varchar(10) NOT NULL,
	visible boolean NOT NULL DEFAULT false,
	show_monitor boolean NOT NULL DEFAULT false,
	pretests_only boolean NOT NULL DEFAULT false,
	practice_permition boolean NOT NULL DEFAULT false,
	finished boolean NOT NULL DEFAULT false,
	folder_name varchar(20) NOT NULL,
	competition_start timestamp DEFAULT null,
	competition_interval interval DEFAULT null,
	interval_before_frozen interval DEFAULT null,
	CONSTRAINT "PK_competition_id" PRIMARY KEY (id),
	CONSTRAINT "UN_competition_name" UNIQUE (name),
	CONSTRAINT "UN_competition_folder_name" UNIQUE (folder_name)

);
-- ddl-end --
-- object: "IX_competition_id" | type: INDEX --
CREATE INDEX "IX_competition_id" ON public.competition
	USING btree
	(
	  id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.participation | type: TABLE --
CREATE TABLE public.participation(
	id serial NOT NULL,
	competition_id integer NOT NULL,
	user_id integer NOT NULL,
	personal_data_id integer DEFAULT null,
	registered boolean NOT NULL DEFAULT false,
	points smallint DEFAULT null,
	fine integer DEFAULT null,
	place smallint DEFAULT null,
	solved_problems smallint DEFAULT null,
	CONSTRAINT "PK_participation_id" PRIMARY KEY (id),
	CONSTRAINT "UN_participation_competition_id_user_id" UNIQUE (competition_id,user_id),
	CONSTRAINT "UN_participation_personal_data_id" UNIQUE (personal_data_id)

);
-- ddl-end --
-- object: "IX_participation_competition_id" | type: INDEX --
CREATE INDEX "IX_participation_competition_id" ON public.participation
	USING btree
	(
	  competition_id ASC NULLS LAST
	);
-- ddl-end --

-- object: "IX_participation_user_id" | type: INDEX --
CREATE INDEX "IX_participation_user_id" ON public.participation
	USING btree
	(
	  user_id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.problem | type: TABLE --
CREATE TABLE public.problem(
	id serial NOT NULL,
	type varchar(8) NOT NULL,
	name varchar(40) NOT NULL,
	checker_type varchar(15) NOT NULL DEFAULT 'default',
	time_limit integer NOT NULL DEFAULT 1000,
	memory_limit smallint NOT NULL DEFAULT 64,
	description_file_exists boolean NOT NULL DEFAULT false,
	validated boolean NOT NULL DEFAULT false,
	folder_name varchar(20) NOT NULL,
	CONSTRAINT "PK_problem_id" PRIMARY KEY (id),
	CONSTRAINT "UN_problem_problem_folder_path" UNIQUE (folder_name),
	CONSTRAINT "UN_problem_name" UNIQUE (name)

);
-- ddl-end --
-- object: "IX_problem_id" | type: INDEX --
CREATE INDEX "IX_problem_id" ON public.problem
	USING btree
	(
	  id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.competition_problem | type: TABLE --
CREATE TABLE public.competition_problem(
	id serial NOT NULL,
	competition_id integer NOT NULL,
	problem_id integer NOT NULL,
	problem_number varchar(5) NOT NULL,
	CONSTRAINT "PK_competition_problem_id" PRIMARY KEY (id),
	CONSTRAINT "UN_competition_problem_competition_id_problem_id" UNIQUE (competition_id,problem_id),
	CONSTRAINT "UN_competition_problem_competition_id_problem_number" UNIQUE (competition_id,problem_number)

);
-- ddl-end --
-- object: "IX_competition_problem_id" | type: INDEX --
CREATE INDEX "IX_competition_problem_id" ON public.competition_problem
	USING btree
	(
	  id ASC NULLS LAST
	);
-- ddl-end --

-- object: "IX_competition_problem_competition_id" | type: INDEX --
CREATE INDEX "IX_competition_problem_competition_id" ON public.competition_problem
	USING btree
	(
	  competition_id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.submission | type: TABLE --
CREATE TABLE public.submission(
	id serial NOT NULL,
	competition_problem_id integer NOT NULL,
	user_id integer NOT NULL,
	compilator_id integer NOT NULL,
	submission_time timestamp NOT NULL,
	folder_name varchar(20) NOT NULL,
	verdict varchar(15) NOT NULL DEFAULT 'waiting',
	wrong_test_number smallint DEFAULT null,
	decision_time integer DEFAULT null,
	decision_memory integer DEFAULT null,
	points smallint DEFAULT null,
	CONSTRAINT "PK_submission_id" PRIMARY KEY (id),
	CONSTRAINT "UN_submission_folder_name" UNIQUE (folder_name)

);
-- ddl-end --
-- object: "IX_submission_competition_problem_id" | type: INDEX --
CREATE INDEX "IX_submission_competition_problem_id" ON public.submission
	USING btree
	(
	  competition_problem_id ASC NULLS LAST
	);
-- ddl-end --

-- object: "IX_submission_user_id" | type: INDEX --
CREATE INDEX "IX_submission_user_id" ON public.submission
	USING btree
	(
	  user_id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.compilator | type: TABLE --
CREATE TABLE public.compilator(
	id serial NOT NULL,
	name varchar(30) NOT NULL,
	CONSTRAINT "PK_compilator_id" PRIMARY KEY (id),
	CONSTRAINT "UN_compilator_name" UNIQUE (name)

);
-- ddl-end --
-- object: public.test_group | type: TABLE --
CREATE TABLE public.test_group(
	id serial NOT NULL,
	problem_id integer NOT NULL,
	test_group_type varchar(10) NOT NULL,
	tests_quantity smallint NOT NULL,
	points_for_test smallint NOT NULL,
	CONSTRAINT "PK_test_group_id" PRIMARY KEY (id)

);
-- ddl-end --
-- object: "IX_test_group_problem_id" | type: INDEX --
CREATE INDEX "IX_test_group_problem_id" ON public.test_group
	USING btree
	(
	  problem_id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.participation_result | type: TABLE --
CREATE TABLE public.participation_result(
	id serial NOT NULL,
	user_id integer NOT NULL,
	competition_problem_id integer NOT NULL,
	points smallint NOT NULL DEFAULT 0,
	fine integer NOT NULL DEFAULT 0,
	CONSTRAINT "PK_participation_result" PRIMARY KEY (id),
	CONSTRAINT "UN_participation_result_user_id_competition_problem_id" UNIQUE (user_id,competition_problem_id)

);
-- ddl-end --
-- object: "IX_participation_result_user_id" | type: INDEX --
CREATE INDEX "IX_participation_result_user_id" ON public.participation_result
	USING btree
	(
	  user_id ASC NULLS LAST
	);
-- ddl-end --

-- object: "IX_participation_result_competition_problem_id" | type: INDEX --
CREATE INDEX "IX_participation_result_competition_problem_id" ON public.participation_result
	USING btree
	(
	  competition_problem_id ASC NULLS LAST
	);
-- ddl-end --


-- object: public.author_decision | type: TABLE --
CREATE TABLE public.author_decision(
	id serial NOT NULL,
	problem_id integer NOT NULL,
	folder_name varchar(15) NOT NULL,
	compilator_id integer,
	CONSTRAINT "PK_author_decision_id" PRIMARY KEY (id),
	CONSTRAINT "UN_author_decision_problem_id_folder_name" UNIQUE (problem_id,folder_name)

);
-- ddl-end --
-- object: "IX_author_decision_problem_id" | type: INDEX --
CREATE INDEX "IX_author_decision_problem_id" ON public.author_decision
	USING btree
	(
	  problem_id ASC NULLS LAST
	);
-- ddl-end --


-- object: "FK_participation_competition_id" | type: CONSTRAINT --
ALTER TABLE public.participation ADD CONSTRAINT "FK_participation_competition_id" FOREIGN KEY (competition_id)
REFERENCES public.competition (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_participation_user_id" | type: CONSTRAINT --
ALTER TABLE public.participation ADD CONSTRAINT "FK_participation_user_id" FOREIGN KEY (user_id)
REFERENCES public.user (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_participation_personal_data_id" | type: CONSTRAINT --
ALTER TABLE public.participation ADD CONSTRAINT "FK_participation_personal_data_id" FOREIGN KEY (personal_data_id)
REFERENCES public.personal_data (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_competition_problem_competition_id" | type: CONSTRAINT --
ALTER TABLE public.competition_problem ADD CONSTRAINT "FK_competition_problem_competition_id" FOREIGN KEY (competition_id)
REFERENCES public.competition (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_competition_problem_problem_id" | type: CONSTRAINT --
ALTER TABLE public.competition_problem ADD CONSTRAINT "FK_competition_problem_problem_id" FOREIGN KEY (problem_id)
REFERENCES public.problem (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_submission_competition_problem_id" | type: CONSTRAINT --
ALTER TABLE public.submission ADD CONSTRAINT "FK_submission_competition_problem_id" FOREIGN KEY (competition_problem_id)
REFERENCES public.competition_problem (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_submission_user_id" | type: CONSTRAINT --
ALTER TABLE public.submission ADD CONSTRAINT "FK_submission_user_id" FOREIGN KEY (user_id)
REFERENCES public.user (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_submission_compilator_id" | type: CONSTRAINT --
ALTER TABLE public.submission ADD CONSTRAINT "FK_submission_compilator_id" FOREIGN KEY (compilator_id)
REFERENCES public.compilator (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_test_group_problem_id" | type: CONSTRAINT --
ALTER TABLE public.test_group ADD CONSTRAINT "FK_test_group_problem_id" FOREIGN KEY (problem_id)
REFERENCES public.problem (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_participation_result_user_id" | type: CONSTRAINT --
ALTER TABLE public.participation_result ADD CONSTRAINT "FK_participation_result_user_id" FOREIGN KEY (user_id)
REFERENCES public.user (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_participation_result_competition_problem_id" | type: CONSTRAINT --
ALTER TABLE public.participation_result ADD CONSTRAINT "FK_participation_result_competition_problem_id" FOREIGN KEY (competition_problem_id)
REFERENCES public.competition_problem (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_author_decision_problem_id" | type: CONSTRAINT --
ALTER TABLE public.author_decision ADD CONSTRAINT "FK_author_decision_problem_id" FOREIGN KEY (problem_id)
REFERENCES public.problem (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: "FK_author_decision_compilator_id" | type: CONSTRAINT --
ALTER TABLE public.author_decision ADD CONSTRAINT "FK_author_decision_compilator_id" FOREIGN KEY (compilator_id)
REFERENCES public.compilator (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --



