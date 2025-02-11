CREATE TABLE REVINFO (
    REV SERIAL,
    REVTSTMP numeric,
    PRIMARY KEY (REV)
);


CREATE TABLE app_user_AUD (
    id numeric,
    REV numeric,
    oauth_id numeric,
    name varchar(2000),
    email varchar(2000),
    available_points numeric,
	last_event varchar(2000),
	last_change numeric,
	password varchar(2000),
	roles varchar(2000),
	email_confirmed boolean,
	REVTYPE numeric,
	created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
	PRIMARY KEY (id,REV)
);

CREATE INDEX idx_AUD_user_id ON app_user_AUD (id);
CREATE INDEX idx_AUD_oauth_user_id ON app_user_AUD (oauth_id);

CREATE TABLE Match_AUD (
    id numeric,
    REV numeric,
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
    REVTYPE numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id,REV)
);

CREATE INDEX idx_AUD_match_id ON Match_AUD (id);
CREATE INDEX idx_match_AUD_team1 ON Match_AUD (team1);
CREATE INDEX idx_match_AUD_team2 ON Match_AUD (team2);

CREATE TABLE Bet_AUD (
    id numeric,
    REV numeric,
    match numeric,
    app_user numeric,
    guess varchar(2000),
    point numeric,
    winning numeric,
    possible_winning numeric,
    REVTYPE numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id,REV)
);

CREATE INDEX idx_AUD_bet_id ON Bet_AUD (id);
CREATE INDEX idx_AUD_bet_match ON Bet_AUD (match);
CREATE INDEX idx_AUD_bet_user ON Bet_AUD (app_user);

CREATE TABLE Invoice_AUD (
    id numeric,
    REV numeric,
    user_id numeric,
    ap_code varchar(2000),
    block_code varchar(2000),
    block_date numeric,
    upload_date numeric,
    point numeric,
    REVTYPE numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id,REV)
);

CREATE INDEX idx_AUD_invoice_id ON Invoice_AUD (id);
CREATE INDEX idx_AUD_invoice_user_id ON Invoice_AUD (user_id);
CREATE INDEX idx_AUD_upload_date_id ON Invoice_AUD (upload_date);
CREATE INDEX idx_AUD_ap_code_block_date ON Invoice_AUD (ap_code,block_code);

CREATE TABLE Code_upload_AUD (
    id numeric,
    REV numeric,
    code_start_date numeric,
    code_value numeric,
    code_end_date numeric,
    REVTYPE numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id,REV)
);


CREATE TABLE Team_AUD (
    id numeric,
    REV numeric,
    name varchar(2000),
    flag_link varchar(2000),
    REVTYPE numeric,
    created_date numeric,
    last_modified_date numeric,
    last_modified_by varchar(2000),
    PRIMARY KEY (id,REV)
);
CREATE INDEX idx_AUD_team_id ON Team_AUD (id);