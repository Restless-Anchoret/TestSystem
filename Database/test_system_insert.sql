﻿insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('admin', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'admin', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('RestlessAnchoret', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'moderator', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('Ataman94', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'moderator', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('Magistrazh', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'moderator', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('toxa12347', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'moderator', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('Alena97', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('Andreyxa59', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('Jokerkras', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('KalterM', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('LilyJeff', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('Rainwalker', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('vese', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('ShabanovArtemii', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('sqrA', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);
insert into public.user (login, password_hash, hash_salt, role, registration_date, actual) values ('mark36', '172e50f782d280f5503fe23fb64866f2bda2db6681a20d25263c0ccda2fc2573', '1b37f7e2b0011a2560be2bca20ba1139d94e5b75', 'participant', timestamp'01-01-2016', true);

insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Testing Round #1', true, 'icpc', 'public', true, true, false, true, true, '1', timestamp'2015-01-01 12:00:00', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Testing Round #2', true, 'icpc', 'public', true, true, false, true, true, '2', timestamp'2015-01-02 13:00:00', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Testing Round #3', true, 'icpc', 'public', true, true, false, true, true, '3', timestamp'2015-01-03 13:00:00', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Testing Round #4', true, 'icpc', 'public', true, true, false, true, true, '4', timestamp'2015-01-04 13:00:00', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Round #1', true, 'icpc', 'public', true, true, false, true, true, '5', timestamp'2015-01-05 14:00:00', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Round #2', true, 'icpc', 'public', true, true, false, true, true, '6', timestamp'2015-01-06 14:00:00', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Round #3', true, 'icpc', 'public', true, false, false, true, false, '7', timestamp'2017-01-01 15:00:00', 120, 0);
insert into public.competition (name, hold_competition, evaluation_type, registration_type, visible, show_monitor, pretests_only, practice_permition, finished, folder_name, competition_start, competition_interval, interval_frozen) values ('Kolibri Round #4', true, 'icpc', 'public', false, false, false, true, false, '8', timestamp'2017-01-02 15:00:00', 120, 0);

insert into public.problem (type, name, time_limit, description_file_exists, validated, folder_name) values ('coding', 'A + B', 2000, true, true, '1');
insert into public.problem (type, name, time_limit, description_file_exists, validated, folder_name) values ('coding', 'Набор чисел', 2000, true, true, '2');
insert into public.problem (type, name, time_limit, description_file_exists, validated, folder_name) values ('coding', 'Возведение в степень', 2000, true, true, '3');
insert into public.problem (type, name, time_limit, description_file_exists, validated, folder_name) values ('coding', 'Диалоги', 2000, true, true, '4');
insert into public.problem (type, name, time_limit, description_file_exists, validated, folder_name) values ('coding', 'Карта', 2000, true, true, '5');
insert into public.problem (type, name, time_limit, description_file_exists, validated, folder_name) values ('coding', 'Счастливые номера', 2000, true, true, '6');

insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (1, 'samples', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (1, 'pretests', 1, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (1, 'tests_1', 1, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (2, 'samples', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (2, 'tests_1', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (2, 'tests_2', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (3, 'samples', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (3, 'tests_1', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (3, 'tests_2', 3, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (4, 'samples', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (4, 'tests_1', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (4, 'tests_2', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (5, 'samples', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (5, 'tests_1', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (5, 'tests_2', 2, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (6, 'samples', 3, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (6, 'tests_1', 3, 0);
insert into public.test_group (problem_id, test_group_type, tests_quantity, points_for_test) values (6, 'tests_2', 2, 0);

insert into public.competition_problem (competition_id, problem_id, problem_number) values (1, 1, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (1, 2, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (1, 3, 'C');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (2, 1, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (2, 2, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (2, 3, 'C');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (3, 1, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (3, 2, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (3, 3, 'C');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (4, 1, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (4, 2, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (4, 3, 'C');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (5, 1, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (5, 2, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (5, 3, 'C');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (6, 2, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (6, 3, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (6, 4, 'C');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (6, 5, 'D');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (6, 6, 'E');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (7, 2, 'A');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (7, 3, 'B');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (7, 4, 'C');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (7, 5, 'D');
insert into public.competition_problem (competition_id, problem_id, problem_number) values (7, 6, 'E');

insert into public.compilator (name) values ('java');
insert into public.compilator (name) values ('visual_cpp');

insert into public.author_decision (problem_id, folder_name, compilator_id) values (1, '1', 1);
insert into public.author_decision (problem_id, folder_name, compilator_id) values (2, '1', 2);
insert into public.author_decision (problem_id, folder_name, compilator_id) values (3, '1', 1);
insert into public.author_decision (problem_id, folder_name, compilator_id) values (4, '1', 1);
insert into public.author_decision (problem_id, folder_name, compilator_id) values (5, '1', 1);
insert into public.author_decision (problem_id, folder_name, compilator_id) values (6, '1', 2);

insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 6, 2, timestamp'2015-01-06 14:23:00', '1', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 7, 2, timestamp'2015-01-06 14:23:00', '2', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 8, 2, timestamp'2015-01-06 14:23:00', '3', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 9, 2, timestamp'2015-01-06 14:23:00', '4', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 10, 2, timestamp'2015-01-06 14:23:00', '5', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 11, 2, timestamp'2015-01-06 14:23:00', '6', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 12, 2, timestamp'2015-01-06 14:23:00', '7', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 13, 2, timestamp'2015-01-06 14:23:00', '8', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 14, 2, timestamp'2015-01-06 14:23:00', '9', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (16, 15, 2, timestamp'2015-01-06 14:23:00', '10', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (17, 6, 1, timestamp'2015-01-06 14:24:00', '11', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (18, 7, 1, timestamp'2015-01-06 14:25:00', '12', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (20, 8, 2, timestamp'2015-01-06 14:26:00', '13', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (19, 9, 1, timestamp'2015-01-06 14:27:00', '14', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (20, 10, 2, timestamp'2015-01-06 14:28:00', '15', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (20, 11, 2, timestamp'2015-01-06 14:29:00', '16', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (17, 12, 1, timestamp'2015-01-06 14:30:00', '17', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (19, 13, 1, timestamp'2015-01-06 14:31:00', '18', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (18, 14, 1, timestamp'2015-01-06 14:32:00', '19', 'accepted', null, 942);
insert into public.submission (competition_problem_id, user_id, compilator_id, submission_time, folder_name, verdict, wrong_test_number, decision_time) values (18, 15, 1, timestamp'2015-01-06 14:33:00', '20', 'accepted', null, 942);

insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 6, true, 2, 47, 1, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 7, true, 2, 48, 2, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 8, true, 2, 49, 3, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 9, true, 2, 50, 4, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 10, true, 2, 51, 5, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 11, true, 2, 52, 6, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 12, true, 2, 53, 7, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 13, true, 2, 54, 8, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 14, true, 2, 55, 9, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (6, 15, true, 2, 56, 10, 2);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (7, 6, true, null, null, null, null);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (7, 7, true, null, null, null, null);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (7, 8, true, null, null, null, null);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (7, 9, true, null, null, null, null);
insert into public.participation (competition_id, user_id, registered, points, fine, place, solved_problems) values (7, 10, true, null, null, null, null);

insert into public.participation_result (user_id, competition_problem_id, points, fine) values (6, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (6, 17, 1, 24);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (6, 18, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (6, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (6, 20, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (7, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (7, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (7, 18, 1, 25);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (7, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (7, 20, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (8, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (8, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (8, 18, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (8, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (8, 20, 1, 26);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (9, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (9, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (9, 18, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (9, 19, 1, 27);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (9, 20, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (10, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (10, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (10, 18, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (10, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (10, 20, 1, 28);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (11, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (11, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (11, 18, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (11, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (11, 20, 1, 29);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (12, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (12, 17, 1, 30);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (12, 18, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (12, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (12, 20, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (13, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (13, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (13, 18, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (13, 19, 1, 31);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (13, 20, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (14, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (14, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (14, 18, 1, 32);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (14, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (14, 20, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (15, 16, 1, 23);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (15, 17, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (15, 18, 1, 33);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (15, 19, 0, 0);
insert into public.participation_result (user_id, competition_problem_id, points, fine) values (15, 20, 0, 0);