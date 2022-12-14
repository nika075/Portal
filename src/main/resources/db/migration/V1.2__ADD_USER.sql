CREATE TABLE USERS(
USERS_ID NUMERIC(10,0) CONSTRAINT NN_USERS_ID NOT NULL,
USERS_NAME VARCHAR(50) CONSTRAINT NN_USERS_NAME NOT NULL,
USERS_PASSWORD VARCHAR(50) CONSTRAINT NN_USERS_PASSWORD NOT NULL,
USERS_LAST_NAME VARCHAR(50) CONSTRAINT NN_USERS_LAST_NAME NOT NULL,
USERS_EMAIL VARCHAR(50) CONSTRAINT NN_USERS_EMAIL NOT NULL,
USERS_ACTIVE NUMERIC(1,0) DEFAULT NULL,
USERS_CONF_ID VARCHAR(100),
USERS_CONF_STATUS BOOLEAN,


CONSTRAINT USERS_PK PRIMARY KEY(USERS_ID)
);
COMMENT ON COLUMN USERS.USERS_ID IS 'Unique ID. Sequence LCS_LPG_ID';
COMMENT ON COLUMN USERS.USERS_NAME IS 'USERS name';
COMMENT ON COLUMN USERS.USERS_PASSWORD IS 'USERS password';
COMMENT ON COLUMN USERS.USERS_LAST_NAME IS 'USERS last name';
COMMENT ON COLUMN USERS.USERS_EMAIL IS 'USERS Email';

