CREATE TYPE group_type AS (
    group_name VARCHAR(1024),
    group_color INTEGER
);

CREATE TYPE contact_type AS (
    contact_name VARCHAR(1024),
    contact_email VARCHAR(1024),
    contact_telegram VARCHAR(1024),
    contact_num INTEGER,
    contact_skype VARCHAR(1024),
    group_name VARCHAR(1024)
);

CREATE TYPE user_type AS (
    user_login VARCHAR(1024),
    user_pass VARCHAR(1024),
    user_ip VARCHAR(20)
);

CREATE OR REPLACE FUNCTION login(var_user user_type) RETURNS SETOF user_type AS $$ DECLARE temp_login varchar; temp_rv integer; dataReturned user_type%rowtype; BEGIN SELECT user_login, user_row_version INTO temp_login, temp_rv FROM users WHERE user_login = var_user.user_login AND user_pass = var_user.user_pass; IF temp_login IS NULL THEN RAISE EXCEPTION 'login error; %', var_user_login; END IF; UPDATE users SET user_ip = var_user.user_ip, user_row_version = user_row_version + 1 WHERE user_login = var_user.user_login; IF FOUND THEN return QUERY SELECT user_login, user_pass, user_ip FROM users WHERE users.user_login = var_user.user_login; ELSE RAISE EXCEPTION 'login failure; %', var_user.user_ip; END IF; END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION unlogin(var_user user_type) RETURNS void AS $$ BEGIN UPDATE users SET user_ip = NULL WHERE user_login = var_user.user_login; END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_access(var_user user_type) RETURNS void AS $$ DECLARE temp_login VARCHAR; BEGIN SELECT users.user_login INTO temp_login FROM users WHERE users.user_ip = var_user.user_ip; IF temp_login IS NULL THEN RAISE EXCEPTION 'unregistered user with ip %', var_user.user_ip; END IF; END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION contact_modify(var_user user_type, var_contact_name VARCHAR, var_contact contact_type) RETURNS void AS $$ DECLARE temp_contact_name VARCHAR; temp_contact_id INTEGER; temp_user_id INTEGER; BEGIN PERFORM check_access(var_user); IF var_contact IS NULL THEN DELETE FROM contacts WHERE contact_name = var_contact_name; RETURN;END IF; SELECT contacts.contact_name INTO temp_contact_name FROM contacts WHERE contacts.contact_name = var_contact.contact_name; IF temp_contact_name IS NULL THEN INSERT INTO contacts (contact_name, contact_email, contact_telegram, contact_num, contact_skype, group_id) values(var_contact.contact_name, var_contact.contact_email, var_contact.contact_telegram, var_contact.contact_num, var_contact.contact_skype, (SELECT group_id FROM groups WHERE group_name = var_contact.group_name)) RETURNING contact_id INTO temp_contact_id; SELECT user_id INTO temp_user_id FROM users WHERE user_login = var_user.user_login; INSERT INTO contact_user (contact_id, user_id) values (temp_contact_id, temp_user_id); ELSE UPDATE contacts SET contact_email = var_contact.contact_email, contact_telegram = var_contact.contact_telegram, contact_num = var_contact.contact_num, contact_skype = var_contact.contact_skype, group_id = (SELECT group_id FROM groups WHERE group_name = var_contact.group_name) WHERE contact_name = var_contact.contact_name; END IF; END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION contact_list(var_user user_type) RETURNS SETOF contact_type AS $$ DECLARE dataReturned contact_type%rowtype; BEGIN PERFORM check_access(var_user); FOR dataReturned IN SELECT  contacts.contact_name, contacts.contact_email, contacts.contact_telegram, contacts.contact_num, contacts.contact_skype, groups.group_name FROM contacts JOIN contact_user ON contact_user.contact_id = contacts.contact_id JOIN users ON users.user_id = contact_user.user_id AND users.user_login = var_user.user_login LEFT JOIN groups ON groups.group_id = contacts.group_id LOOP RETURN NEXT dataReturned; END LOOP; END; $$ LANGUAGE plpgsql;
-------------------------------------------

CREATE OR REPLACE FUNCTION group_modify(var_user user_type, var_group_name VARCHAR, var_group group_type) RETURNS void AS $$ DECLARE temp_group_name VARCHAR; temp_group_id INTEGER; temp_user_id INTEGER; BEGIN PERFORM check_access(var_user); IF var_group IS NULL THEN DELETE FROM groups WHERE group_name = var_group_name; RETURN; END IF; SELECT group_name INTO temp_group_name FROM groups WHERE group_name = var_group.group_name; IF temp_group_name IS NULL THEN INSERT INTO groups (group_name, group_color) values(var_group.group_name, var_group.group_color) RETURNING group_id INTO temp_group_id; SELECT user_id INTO temp_user_id FROM users WHERE user_login = var_user.user_login; INSERT INTO group_user (group_id, user_id) values (temp_group_id, temp_user_id); ELSE UPDATE groups SET group_color = var_group.group_color WHERE group_name = var_group.group_name; END IF; END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION group_list(var_user user_type) RETURNS SETOF group_type AS $$ DECLARE dataReturned group_type%rowtype; BEGIN PERFORM check_access(var_user); FOR dataReturned IN SELECT group_name, group_color FROM groups JOIN group_user ON group_user.group_id = groups.group_id JOIN users ON users.user_id = group_user.user_id LOOP RETURN NEXT dataReturned; END LOOP; END; $$ LANGUAGE plpgsql;

------------------------------------------

CREATE VIEW users_count AS
    SELECT count(*)
        FROM users;

CREATE VIEW user_contacts_count AS
    SELECT users.user_login, count(contacts.contact_id)
        FROM contacts
        JOIN contact_user ON contact_user.contact_id = contacts.contact_id
        JOIN users ON users.user_id = contact_user.user_id
        GROUP BY users.user_login;

CREATE VIEW user_groups_count AS
    SELECT users.user_login, count(groups.group_id)
        FROM groups
        JOIN group_user ON group_user.group_id = groups.group_id
        JOIN users ON users.user_id = group_user.user_id
        GROUP BY users.user_login;

CREATE VIEW avg_users_in_group_count AS
    SELECT avg(count_usrs)
        FROM (SELECT count(user_id) as count_usrs FROM group_user GROUP BY group_id) pr;

CREATE VIEW avg_contacts_by_user_count AS
    SELECT avg(count_cntcts)
        FROM (SELECT count(contact_id) as count_cntcts FROM contact_user GROUP BY user_id) pr;

CREATE VIEW get_inactive_users AS
    SELECT * FROM users WHERE users.user_id IN (SELECT users.user_id FROM users
            JOIN contact_user ON contact_user.user_id = users.user_id
            GROUP BY users.user_id
            HAVING count(contact_user.contact_id) < 10);