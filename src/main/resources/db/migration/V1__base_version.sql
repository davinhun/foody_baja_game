CREATE TABLE app_user (
    id SERIAL,
    oauth_id numeric,
    name varchar(2000),
    email varchar(2000),
    available_points numeric,
	last_event varchar(2000),
	last_change numeric,
	password varchar(2000),
	roles varchar(2000),
	email_confirmed boolean,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
	PRIMARY KEY (id)
);

CREATE INDEX idx_user_id ON app_user (id);
CREATE INDEX idx_oauth_user_id ON app_user (oauth_id);

CREATE TABLE Match (
    id SERIAL,
    team1 numeric,
    team2 numeric,
    start_date numeric,
    result varchar(2000),
    numeric_result varchar(255),
    odds_team1 numeric,
    odds_draw numeric,
    odds_team2 numeric,
    status varchar(2000),
    match_group varchar(2000),
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id)
);

CREATE INDEX idx_match_id ON Match (id);
CREATE INDEX idx_match_team1 ON Match (team1);
CREATE INDEX idx_match_team2 ON Match (team2);

CREATE TABLE Bet (
    id SERIAL,
    match numeric,
    app_user numeric,
    guess varchar(2000),
    point numeric,
    winning numeric,
    possible_winning numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id)
);

CREATE INDEX idx_bet_id ON Bet (id);
CREATE INDEX idx_bet_match ON Bet (match);
CREATE INDEX idx_bet_user ON Bet (app_user);

CREATE TABLE Invoice (
    id SERIAL,
    user_id numeric,
    ap_code varchar(2000),
    block_code varchar(2000),
    block_date numeric,
    upload_date numeric,
    point numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id)
);

CREATE INDEX idx_invoice_id ON Invoice (id);
CREATE INDEX idx_invoice_user_id ON Invoice (user_id);
CREATE INDEX idx_upload_date_id ON Invoice (upload_date);
CREATE INDEX idx_ap_code_block_date ON Invoice (ap_code,block_code);

CREATE TABLE Code_upload (
    id SERIAL,
    code_start_date numeric,
    code_value numeric,
    code_end_date numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id)
);


CREATE TABLE Team (
    id SERIAL,
    name varchar(2000),
    flag_link varchar(2000),
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id)
);
CREATE INDEX idx_team_id ON Team (id);

CREATE SEQUENCE hibernate_sequence START 1;