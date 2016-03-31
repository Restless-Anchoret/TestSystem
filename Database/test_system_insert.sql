insert into public.user (login, password_hash, role, registration_date, actual) values ('admin', 'da8fec1a78bd1752cb5e07a90d3aeccf1338c73e', 'admin', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, role, registration_date, actual) values ('moderator1', 'da8fec1a78bd1752cb5e07a90d3aeccf1338c73e', 'moderator', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, role, registration_date, actual) values ('moderator2', 'da8fec1a78bd1752cb5e07a90d3aeccf1338c73e', 'moderator', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, role, registration_date, actual) values ('participant1', 'da8fec1a78bd1752cb5e07a90d3aeccf1338c73e', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, role, registration_date, actual) values ('participant2', 'da8fec1a78bd1752cb5e07a90d3aeccf1338c73e', 'participant', timestamp'01-01-2016', true);

insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Competition 1', true, 'icpc', 'public', true, false, false, true, true, '1', timestamp'01-01-2016', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Competition 2', true, 'icpc', 'public', true, false, false, true, false, '2', timestamp'01-01-2017', 120, 0);

insert into public.problem (type, name, folder_name) values ('coding', 'Problem 1', '1');
insert into public.problem (type, name, folder_name) values ('coding', 'Problem 2', '2');

insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (1, 'samples', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (1, 'pretests', 1, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (1, 'tests_1', 1, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (2, 'samples', 1, 0);

insert into public.competition_problem (competition_id, problem_id, problem_number) values (1, 1, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (1, 2, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (2, 1, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (2, 2, 'B');

insert into public.compilator (name) values ('java');

insert into public.author_decision (problem_id, folder_name, compilator_id) values (1, 'ki_decision', 1);
insert into public.author_decision (problem_id, folder_name, compilator_id) values (2, 'ki_decision', 1);

insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (1, 4, 1, timestamp'01-01-2016', '1', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (1, 4, 1, timestamp'01-01-2016', '2', 'time_limit', 2, 1000);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (1, 4, 1, timestamp'01-01-2016', '3', 'runtime_error', 2, 434);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (1, 4, 1, timestamp'01-01-2016', '4', 'secutiry_viol', 1, 455);

insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (1, 4, true, 1, 0, 1, 1);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (1, 5, true, 0, 0, 2, 0);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (2, 4, true, null, null, null, null);

insert into public.participation_result (user_id, competition_problem_id, points, fine) values (4, 1, 1, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (4, 2, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (5, 1, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (5, 2, 0, 0);