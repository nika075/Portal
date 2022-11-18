CREATE TABLE EVENTS(
EVENTS_ID NUMERIC(10,0) CONSTRAINT NN_EVENTS_ID NOT NULL,
EVENTS_NAME VARCHAR(50) CONSTRAINT NN_EVENTS_NAME NOT NULL,
EVENTS_CATEGORY_ID NUMERIC(10,0) CONSTRAINT NN_EVENTS_CATEGORY_ID NOT NULL,
EVENTS_DESCRIPTION VARCHAR(50) CONSTRAINT NN_EVENTS_DESCRIPTION NOT NULL,
EVENTS_DATE DATE CONSTRAINT NN_EVENTS_DATE NOT NULL,
EVENTS_COST VARCHAR(50) CONSTRAINT NN_EVENTS_COST NOT NULL,
EVENTS_GROUP BOOLEAN DEFAULT false,
CONSTRAINT EVENTS_PK PRIMARY KEY(EVENTS_ID),
EVENTS_AUTHOR_ID NUMERIC(10,0),
CONSTRAINT USER_FK FOREIGN KEY (EVENTS_AUTHOR_ID) REFERENCES USERS (users_id)
);
COMMENT ON COLUMN EVENTS.EVENTS_ID IS 'Unique ID';
COMMENT ON COLUMN EVENTS.EVENTS_NAME IS 'EVENTS name';
COMMENT ON COLUMN EVENTS.EVENTS_CATEGORY_ID IS 'EVENTS category';
COMMENT ON COLUMN EVENTS.EVENTS_DESCRIPTION IS 'EVENTS description';
COMMENT ON COLUMN EVENTS.EVENTS_DATE IS 'EVENTS date';
COMMENT ON COLUMN EVENTS.EVENTS_COST IS 'EVENTS cost';
COMMENT ON COLUMN EVENTS.EVENTS_GROUP IS 'EVENTS group';
COMMENT ON COLUMN EVENTS.EVENTS_AUTHOR_ID IS 'EVENTS author id';