--CREATE TABLE MAHN_MUSEUMS (
    --museum_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --name VARCHAR2(100) NOT NULL,
    --museum_type VARCHAR2(50) NOT NULL,
    --location VARCHAR2(255) NOT NULL,
    --foundation_date DATE,
    --director VARCHAR2(100),
    --website VARCHAR2(100)
--);

--CREATE TABLE MAHN_CREDIT_CARDS (
    --card_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --type VARCHAR2(50) NOT NULL,
    --commission_rate NUMBER(5,2) NOT NULL
--);

--CREATE TABLE MAHN_VISITORS (
    --visitor_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --name VARCHAR2(100) NOT NULL,
    --email VARCHAR2(100),
    --phone VARCHAR2(20)
--);

--CREATE TABLE MAHN_ROOMS (
    --room_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --museum_id NUMBER NOT NULL,
    --name VARCHAR2(100) NOT NULL,
    --description VARCHAR2(500) NOT NULL,
    --main_theme VARCHAR2(100),
   -- FOREIGN KEY (museum_id) REFERENCES MAHN_MUSEUMS(museum_id)
--);

--CREATE TABLE MAHN_COLLECTIONS (
    --collection_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --room_id NUMBER NOT NULL,
    --name VARCHAR2(100) NOT NULL,
    --century VARCHAR2(50),
    --description VARCHAR2(500) NOT NULL,
    --FOREIGN KEY (room_id) REFERENCES MAHN_ROOMS(room_id)
--);

--CREATE TABLE MAHN_SPECIES (
    --species_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --collection_id NUMBER NOT NULL,
    --scientific_name VARCHAR2(100) NOT NULL,
    --common_name VARCHAR2(100),
   -- extinction_date VARCHAR2(50),
   -- era VARCHAR2(100),
    --weight VARCHAR2(50),
   -- species_size VARCHAR2(50),
   -- characteristics VARCHAR2(500),
   -- FOREIGN KEY (collection_id) REFERENCES MAHN_COLLECTIONS(collection_id)
--);

--CREATE TABLE MAHN_RATINGS (
    --rating_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --room_id NUMBER NOT NULL,
    --score NUMBER(1) NOT NULL CHECK (score BETWEEN 1 AND 5),
    --review VARCHAR2(500),
    --rating_date DATE DEFAULT SYSDATE,
    --FOREIGN KEY (room_id) REFERENCES MAHN_ROOMS(room_id)
--);

--CREATE TABLE MAHN_VISITORS (
    --visitor_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --name VARCHAR2(100) NOT NULL,
    --email VARCHAR2(100),
   -- phone VARCHAR2(20)
--);

--CREATE TABLE MAHN_TICKETS (
   -- ticket_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   -- visitor_id NUMBER NOT NULL,
   -- purchase_date DATE NOT NULL,
   -- card_id NUMBER NOT NULL,
   -- total_amount NUMBER(10,2) NOT NULL,
   -- commission_amount NUMBER(10,2) NOT NULL,
    --qr_code VARCHAR2(100) UNIQUE NOT NULL,
    --FOREIGN KEY (visitor_id) REFERENCES MAHN_VISITORS(visitor_id),
    --FOREIGN KEY (card_id) REFERENCES MAHN_CREDIT_CARDS(card_id)
--);

--CREATE TABLE MAHN_TICKET_ROOM (
    --ticket_room_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --ticket_id NUMBER NOT NULL,
    --room_id NUMBER NOT NULL,
    --visit_date DATE NOT NULL,
    --FOREIGN KEY (ticket_id) REFERENCES MAHN_TICKETS(ticket_id),
    --FOREIGN KEY (room_id) REFERENCES MAHN_ROOMS(room_id)
--);

--CREATE TABLE MAHN_TOPICS (
    --topic_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --name VARCHAR2(100) NOT NULL,
    --characteristics VARCHAR2(500) NOT NULL,
    --era VARCHAR2(100)
    --FOREIGN KEY (room_id) REFERENCES MAHN_ROOMS(room_id)
--);

--CREATE TABLE MAHN_PRICES (
    --price_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    --room_id NUMBER NOT NULL,
    --weekday_price NUMBER(10,2) NOT NULL,
    --sunday_price NUMBER(10,2) NOT NULL,
   -- FOREIGN KEY (room_id) REFERENCES MAHN_ROOMS(room_id)
--);

