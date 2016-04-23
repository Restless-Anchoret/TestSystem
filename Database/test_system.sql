-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- PostgreSQL version: 9.2
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: test_system_test | type: DATABASE --
-- CREATE DATABASE test_system_test
-- ;
-- -- ddl-end --
-- 

-- object: public.user | type: TABLE --
CREATE TABLE public.user(
	id serial NOT NULL,
	login varchar(30) NOT NULL,
	password_hash varchar(100) NOT NULL,
	hash_salt varchar(40) NOT NULL,
	role varchar(15) NOT NULL,
	registration_date timestamp NOT NULL,
	actual boolean NOT NULL DEFAULT true,
	CONSTRAINT "PK_user_id" PRIMARY KEY (id),
	CONSTRAINT "UN_user_login" UNIQUE (login),
	CONSTRAINT "CH_user_role" CHECK (role='participant' or role='moderator' or role='admin')

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


COMMENT ON COLUMN public.user.login IS 'Логин';
-- ddl-end --
COMMENT ON COLUMN public.user.password_hash IS 'Хэш пароля';
-- ddl-end --
COMMENT ON COLUMN public.user.hash_salt IS 'Байты соли, добавленной в хэш пароля';
-- ddl-end --
COMMENT ON COLUMN public.user.role IS 'Роль. Допустимые значения: "admin", "moderator", "participant"';
-- ddl-end --
COMMENT ON COLUMN public.user.registration_date IS 'Дата регистрации';
-- ddl-end --
COMMENT ON COLUMN public.user.actual IS 'Обозначает, разрешено ли пользователю заходить в систему';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_user_role" ON public.user IS 'Ограничение на имя роли: "participant", "moderator", "admin"';
-- ddl-end --
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


COMMENT ON COLUMN public.personal_data.first_name IS 'Имя';
-- ddl-end --
COMMENT ON COLUMN public.personal_data.last_name IS 'Фамилия';
-- ddl-end --
COMMENT ON COLUMN public.personal_data.patronymic IS 'Отчество';
-- ddl-end --
COMMENT ON COLUMN public.personal_data.organization IS 'Организация';
-- ddl-end --
COMMENT ON COLUMN public.personal_data.course IS 'Курс (или класс)';
-- ddl-end --
COMMENT ON COLUMN public.personal_data.country IS 'Страна';
-- ddl-end --
COMMENT ON COLUMN public.personal_data.city IS 'Город';
-- ddl-end --
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
	folder_name varchar(20) DEFAULT null,
	competition_start timestamp DEFAULT null,
	competition_interval integer DEFAULT null,
	interval_frozen integer DEFAULT null,
	CONSTRAINT "PK_competition_id" PRIMARY KEY (id),
	CONSTRAINT "UN_competition_name" UNIQUE (name),
	CONSTRAINT "UN_competition_folder_name" UNIQUE (folder_name),
	CONSTRAINT "CH_competition_registration_type" CHECK (registration_type='public' or registration_type='moderation' or registration_type='closed'),
	CONSTRAINT "CH_competition_competition_interval" CHECK (competition_interval>0),
	CONSTRAINT "CH_competition_interval_frozen" CHECK (interval_frozen>=0 and interval_frozen<=competition_interval)

);
-- ddl-end --
-- object: "IX_competition_id" | type: INDEX --
CREATE INDEX "IX_competition_id" ON public.competition
	USING btree
	(
	  id ASC NULLS LAST
	);
-- ddl-end --


COMMENT ON COLUMN public.competition.name IS 'Название соревнования';
-- ddl-end --
COMMENT ON COLUMN public.competition.hold_competition IS 'true - означает соревнование, false - означает тренировку';
-- ddl-end --
COMMENT ON COLUMN public.competition.evaluation_type IS 'Тип системы оценивания. Допустимые значения: "icpc", "ioi"';
-- ddl-end --
COMMENT ON COLUMN public.competition.registration_type IS 'Тип регистрации. Допустимые значения: "public", "moderation", "closed"';
-- ddl-end --
COMMENT ON COLUMN public.competition.visible IS 'Обозначает, видимо ли данное соревнование участникам';
-- ddl-end --
COMMENT ON COLUMN public.competition.show_monitor IS 'Обозначает, показывается ли участникам монитор по данному соревнованию';
-- ddl-end --
COMMENT ON COLUMN public.competition.pretests_only IS 'Обозначает, только ли претесты используются при тестировании во время данного соревнования';
-- ddl-end --
COMMENT ON COLUMN public.competition.practice_permition IS 'Обозначает, разрешено ли дорешивание по данному соревнованию';
-- ddl-end --
COMMENT ON COLUMN public.competition.finished IS 'Обозначает, считается ли соревнование официально завершённым';
-- ddl-end --
COMMENT ON COLUMN public.competition.folder_name IS 'Имя папки соревнования в файловой системе';
-- ddl-end --
COMMENT ON COLUMN public.competition.competition_start IS 'Время начала соревнования';
-- ddl-end --
COMMENT ON COLUMN public.competition.competition_interval IS 'Длительность соревнования (в минутах)';
-- ddl-end --
COMMENT ON COLUMN public.competition.interval_frozen IS 'Длительность заморозки (в минутах)';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_competition_registration_type" ON public.competition IS 'Ограничение на имя типа регистрации: "public", "moderation", "closed"';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_competition_competition_interval" ON public.competition IS 'Ограничение на интервал соревнования: больше нуля';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_competition_interval_frozen" ON public.competition IS 'Ограничение на интервал заморозки: больше либо равен нулю, меньше либо равен интервалу соревнования';
-- ddl-end --
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
	CONSTRAINT "UN_participation_personal_data_id" UNIQUE (personal_data_id),
	CONSTRAINT "CH_participation_points" CHECK (points>=0),
	CONSTRAINT "CH_participation_fine" CHECK (fine>=0),
	CONSTRAINT "CH_participation_place" CHECK (place>0),
	CONSTRAINT "CH_participation_solved_problems" CHECK (solved_problems>=0)

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


COMMENT ON COLUMN public.participation.registered IS 'Обозначает, зарегистрирован ли окончательно участник на соревнование';
-- ddl-end --
COMMENT ON COLUMN public.participation.points IS 'Суммарные очки участника за соревнование';
-- ddl-end --
COMMENT ON COLUMN public.participation.fine IS 'Суммарный штраф участника за соревнование';
-- ddl-end --
COMMENT ON COLUMN public.participation.place IS 'Место участника по итогам соревнования';
-- ddl-end --
COMMENT ON COLUMN public.participation.solved_problems IS 'Количество решённых участником задач в течение соревнования';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_participation_points" ON public.participation IS 'Ограничение на суммарное количество очков: больше либо равно нулю';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_participation_fine" ON public.participation IS 'Ограничение на суммарный штраф: больше либо равно нулю';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_participation_place" ON public.participation IS 'Ограничение на место: больше нуля';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_participation_solved_problems" ON public.participation IS 'Ограничение на количество решённых задач: больше либо равно нулю';
-- ddl-end --
-- ddl-end --

-- object: public.problem | type: TABLE --
CREATE TABLE public.problem(
	id serial NOT NULL,
	type varchar(8) NOT NULL,
	name varchar(40) NOT NULL,
	checker_type varchar(15) NOT NULL DEFAULT 'match',
	time_limit integer NOT NULL DEFAULT 1000,
	memory_limit smallint NOT NULL DEFAULT 64,
	description_file_exists boolean NOT NULL DEFAULT false,
	validated boolean NOT NULL DEFAULT false,
	folder_name varchar(20) DEFAULT null,
	CONSTRAINT "PK_problem_id" PRIMARY KEY (id),
	CONSTRAINT "UN_problem_problem_folder_path" UNIQUE (folder_name),
	CONSTRAINT "UN_problem_name" UNIQUE (name),
	CONSTRAINT "CH_problem_time_limit" CHECK (time_limit>0),
	CONSTRAINT "CH_problem_memory_limit" CHECK (memory_limit>0)

);
-- ddl-end --
-- object: "IX_problem_id" | type: INDEX --
CREATE INDEX "IX_problem_id" ON public.problem
	USING btree
	(
	  id ASC NULLS LAST
	);
-- ddl-end --


COMMENT ON COLUMN public.problem.type IS 'Тип задачи. Допустимые значения: "coding", "test"';
-- ddl-end --
COMMENT ON COLUMN public.problem.name IS 'Название задачи';
-- ddl-end --
COMMENT ON COLUMN public.problem.checker_type IS 'Тип чекера. Допустимые значения: "match", "special"';
-- ddl-end --
COMMENT ON COLUMN public.problem.time_limit IS 'Ограничение по времени на задачу в миллисекундах';
-- ddl-end --
COMMENT ON COLUMN public.problem.memory_limit IS 'Ограничение по памяти на задачу в мегабайтах';
-- ddl-end --
COMMENT ON COLUMN public.problem.description_file_exists IS 'Обозначает, присутствует ли в файловой системе условие задачи';
-- ddl-end --
COMMENT ON COLUMN public.problem.validated IS 'Обозначает, считается ли данная задача провалидированной';
-- ddl-end --
COMMENT ON COLUMN public.problem.folder_name IS 'Имя папки с данной задачей';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_problem_time_limit" ON public.problem IS 'Ограничение по лимиту времени: больше нуля';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_problem_memory_limit" ON public.problem IS 'Ограничение по лимиту памяти: больше нуля';
-- ddl-end --
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


COMMENT ON COLUMN public.competition_problem.problem_number IS 'Порядковый номер задачи в списке задач соревнования';
-- ddl-end --
-- ddl-end --

-- object: public.submission | type: TABLE --
CREATE TABLE public.submission(
	id serial NOT NULL,
	competition_problem_id integer NOT NULL,
	user_id integer NOT NULL,
	compilator_id integer NOT NULL,
	submission_time timestamp NOT NULL,
	folder_name varchar(20) DEFAULT null,
	verdict varchar(15) NOT NULL DEFAULT 'waiting',
	wrong_test_number smallint DEFAULT null,
	decision_time integer DEFAULT null,
	decision_memory integer DEFAULT null,
	points smallint DEFAULT null,
	CONSTRAINT "PK_submission_id" PRIMARY KEY (id),
	CONSTRAINT "UN_submission_folder_name" UNIQUE (folder_name),
	CONSTRAINT "CH_submission_wrong_test_number" CHECK (wrong_test_number>0),
	CONSTRAINT "CH_submission_decision_time" CHECK (decision_time>=0),
	CONSTRAINT "CH_submission_decision_memory" CHECK (decision_memory>=0),
	CONSTRAINT "CH_submission_points" CHECK (points>=0)

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


COMMENT ON COLUMN public.submission.submission_time IS 'Время, когда была сделана посылка';
-- ddl-end --
COMMENT ON COLUMN public.submission.folder_name IS 'Имя папки с посылкой в файловой системе';
-- ddl-end --
COMMENT ON COLUMN public.submission.verdict IS 'Вердикт по посылке';
-- ddl-end --
COMMENT ON COLUMN public.submission.wrong_test_number IS 'Номер первого теста, не прошедшего тестирование';
-- ddl-end --
COMMENT ON COLUMN public.submission.decision_time IS 'Максимальное время, затраченное на решение';
-- ddl-end --
COMMENT ON COLUMN public.submission.decision_memory IS 'Максимальная памяти, затраченная на решение';
-- ddl-end --
COMMENT ON COLUMN public.submission.points IS 'Очки за посылку';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_submission_wrong_test_number" ON public.submission IS 'Ограничение на номер первого неуспешного теста: больше нуля';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_submission_decision_time" ON public.submission IS 'Ограничение на время решения: больше либо равно нулю';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_submission_decision_memory" ON public.submission IS 'Ограничение на затраченную память: больше либо равно нулю';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_submission_points" ON public.submission IS 'Ограничение на количество очков за посылку: больше либо равно нулю';
-- ddl-end --
-- ddl-end --

-- object: public.compilator | type: TABLE --
CREATE TABLE public.compilator(
	id serial NOT NULL,
	name varchar(30) NOT NULL,
	CONSTRAINT "PK_compilator_id" PRIMARY KEY (id),
	CONSTRAINT "UN_compilator_name" UNIQUE (name)

);
-- ddl-end --
COMMENT ON COLUMN public.compilator.name IS 'Имя компилятора. Допустимые значения: "java"';
-- ddl-end --
-- ddl-end --

-- object: public.test_group | type: TABLE --
CREATE TABLE public.test_group(
	id serial NOT NULL,
	problem_id integer NOT NULL,
	test_group_type varchar(10) NOT NULL,
	tests_quantity smallint NOT NULL,
	points_for_test smallint NOT NULL,
	CONSTRAINT "PK_test_group_id" PRIMARY KEY (id),
	CONSTRAINT "CH_test_group_tests_quantity" CHECK (tests_quantity>=0),
	CONSTRAINT "CH_test_group_test_group_type" CHECK (test_group_type='samples' or test_group_type='pretests' or test_group_type='tests_1' or test_group_type='tests_2' or test_group_type='tests_3' or test_group_type='tests_4' or test_group_type='tests_5' or test_group_type='tests_6' or test_group_type='tests_7' or test_group_type='tests_8'),
	CONSTRAINT "CH_test_group_points_for_test" CHECK (points_for_test>=0)

);
-- ddl-end --
-- object: "IX_test_group_problem_id" | type: INDEX --
CREATE INDEX "IX_test_group_problem_id" ON public.test_group
	USING btree
	(
	  problem_id ASC NULLS LAST
	);
-- ddl-end --


COMMENT ON COLUMN public.test_group.test_group_type IS 'Тип группы тестов. Допустимые значения: "samples", "pretests", "tests_1", ... , "tests_8"';
-- ddl-end --
COMMENT ON COLUMN public.test_group.tests_quantity IS 'Количество тестов в группе';
-- ddl-end --
COMMENT ON COLUMN public.test_group.points_for_test IS 'Количество очков за тест из данной группы';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_test_group_tests_quantity" ON public.test_group IS 'Ограничение на количество тестов в группе: больше либо равно нулю';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_test_group_test_group_type" ON public.test_group IS 'Ограничение на имя типа группы тестов';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_test_group_points_for_test" ON public.test_group IS 'Ограничение на количество очков за тест: больше либо равно нулю';
-- ddl-end --
-- ddl-end --

-- object: public.participation_result | type: TABLE --
CREATE TABLE public.participation_result(
	id serial NOT NULL,
	user_id integer NOT NULL,
	competition_problem_id integer NOT NULL,
	points smallint NOT NULL DEFAULT 0,
	fine integer NOT NULL DEFAULT 0,
	CONSTRAINT "PK_participation_result" PRIMARY KEY (id),
	CONSTRAINT "UN_participation_result_user_id_competition_problem_id" UNIQUE (user_id,competition_problem_id),
	CONSTRAINT "CH_participation_result_points" CHECK (points>=0),
	CONSTRAINT "CH_participation_result_fine" CHECK (fine>=0)

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


COMMENT ON COLUMN public.participation_result.points IS 'Очки за задачу';
-- ddl-end --
COMMENT ON COLUMN public.participation_result.fine IS 'Штраф за задачу';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_participation_result_points" ON public.participation_result IS 'Ограничение на количество очков за задачу: больше либо равно нулю';
-- ddl-end --
COMMENT ON CONSTRAINT "CH_participation_result_fine" ON public.participation_result IS 'Ограничение на штраф за задачу: больше либо равно нулю';
-- ddl-end --
-- ddl-end --

-- object: public.author_decision | type: TABLE --
CREATE TABLE public.author_decision(
	id serial NOT NULL,
	problem_id integer NOT NULL,
	folder_name varchar(15) DEFAULT null,
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


COMMENT ON COLUMN public.author_decision.folder_name IS 'Имя папки с авторским решением в файловой системе';
-- ddl-end --
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



