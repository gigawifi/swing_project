--------------------------------------------------------
--  파일이 생성됨 - 수요일-2월-08-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence SEQ_INFO
--------------------------------------------------------

   CREATE SEQUENCE  "SWINGTEST"."SEQ_INFO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_REGIST
--------------------------------------------------------

   CREATE SEQUENCE  "SWINGTEST"."SEQ_REGIST"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 81 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_USERNO
--------------------------------------------------------

   CREATE SEQUENCE  "SWINGTEST"."SEQ_USERNO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1180 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table INFODATA
--------------------------------------------------------

  CREATE TABLE "SWINGTEST"."INFODATA" 
   (	"INFO_NO" NUMBER(5,0), 
	"INFO_DATE" DATE DEFAULT sysdate, 
	"INFO_NAME" VARCHAR2(30 BYTE), 
	"INFO_LOCATION" VARCHAR2(30 BYTE), 
	"INFO_CAP" NUMBER(3,0), 
	"INFO_VAR" NUMBER(1,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table REGISTDATA
--------------------------------------------------------

  CREATE TABLE "SWINGTEST"."REGISTDATA" 
   (	"REGIST_NO" NUMBER(10,0), 
	"INFO_NO" NUMBER(5,0), 
	"USERID" VARCHAR2(30 BYTE), 
	"REGDATE" DATE DEFAULT sysdate
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table USERDATA
--------------------------------------------------------

  CREATE TABLE "SWINGTEST"."USERDATA" 
   (	"USERID" VARCHAR2(30 BYTE), 
	"PASSWORD" VARCHAR2(30 BYTE), 
	"USEREMAIL" VARCHAR2(50 BYTE), 
	"PHONENUM" VARCHAR2(20 BYTE), 
	"USERNO" NUMBER(6,0), 
	"USERNAME" VARCHAR2(20 BYTE), 
	"GENDER" VARCHAR2(5 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into SWINGTEST.INFODATA
SET DEFINE OFF;
Insert into SWINGTEST.INFODATA (INFO_NO,INFO_DATE,INFO_NAME,INFO_LOCATION,INFO_CAP,INFO_VAR) values (19,to_date('2022/11/12','RRRR/MM/DD'),'자격증 강좌 1','505호실',53,3);
Insert into SWINGTEST.INFODATA (INFO_NO,INFO_DATE,INFO_NAME,INFO_LOCATION,INFO_CAP,INFO_VAR) values (21,to_date('2022/11/12','RRRR/MM/DD'),'Java','203호실',56,0);
Insert into SWINGTEST.INFODATA (INFO_NO,INFO_DATE,INFO_NAME,INFO_LOCATION,INFO_CAP,INFO_VAR) values (23,to_date('2022/11/12','RRRR/MM/DD'),'인문학 강좌','101호실',32,2);
Insert into SWINGTEST.INFODATA (INFO_NO,INFO_DATE,INFO_NAME,INFO_LOCATION,INFO_CAP,INFO_VAR) values (28,to_date('2022/11/12','RRRR/MM/DD'),'파이썬','302호실',22,0);
Insert into SWINGTEST.INFODATA (INFO_NO,INFO_DATE,INFO_NAME,INFO_LOCATION,INFO_CAP,INFO_VAR) values (29,to_date('2022/11/12','RRRR/MM/DD'),'자격증 강좌2','402호실',12,2);
Insert into SWINGTEST.INFODATA (INFO_NO,INFO_DATE,INFO_NAME,INFO_LOCATION,INFO_CAP,INFO_VAR) values (30,to_date('2022/11/12','RRRR/MM/DD'),'스페인어','102호실',12,1);
Insert into SWINGTEST.INFODATA (INFO_NO,INFO_DATE,INFO_NAME,INFO_LOCATION,INFO_CAP,INFO_VAR) values (18,to_date('2022/11/12','RRRR/MM/DD'),'영어','203호실',31,1);
REM INSERTING into SWINGTEST.REGISTDATA
SET DEFINE OFF;
Insert into SWINGTEST.REGISTDATA (REGIST_NO,INFO_NO,USERID,REGDATE) values (63,23,'123',to_date('2022/11/12','RRRR/MM/DD'));
Insert into SWINGTEST.REGISTDATA (REGIST_NO,INFO_NO,USERID,REGDATE) values (64,19,'123',to_date('2022/11/12','RRRR/MM/DD'));
Insert into SWINGTEST.REGISTDATA (REGIST_NO,INFO_NO,USERID,REGDATE) values (60,19,'id1234',to_date('2022/11/12','RRRR/MM/DD'));
Insert into SWINGTEST.REGISTDATA (REGIST_NO,INFO_NO,USERID,REGDATE) values (71,21,'newid1234',to_date('2022/11/12','RRRR/MM/DD'));
REM INSERTING into SWINGTEST.USERDATA
SET DEFINE OFF;
Insert into SWINGTEST.USERDATA (USERID,PASSWORD,USEREMAIL,PHONENUM,USERNO,USERNAME,GENDER) values ('id1234','1234','1234@naver.com','010-1111-1112',1022,'홍길동','남');
Insert into SWINGTEST.USERDATA (USERID,PASSWORD,USEREMAIL,PHONENUM,USERNO,USERNAME,GENDER) values ('id5678','12345','id5678@gmail.com','010-5555-5555',1040,'이순신','남');
Insert into SWINGTEST.USERDATA (USERID,PASSWORD,USEREMAIL,PHONENUM,USERNO,USERNAME,GENDER) values ('123','123','123@123.com','123-456-789',1163,'강감찬','남');
Insert into SWINGTEST.USERDATA (USERID,PASSWORD,USEREMAIL,PHONENUM,USERNO,USERNAME,GENDER) values ('3223','11234','5128@5125','123-456-789',1142,'새 사용자','남');
Insert into SWINGTEST.USERDATA (USERID,PASSWORD,USEREMAIL,PHONENUM,USERNO,USERNAME,GENDER) values ('admin1234','1234','admin@1234.com','010-0000-0000',1155,'관리자','남');
Insert into SWINGTEST.USERDATA (USERID,PASSWORD,USEREMAIL,PHONENUM,USERNO,USERNAME,GENDER) values ('12344','123','123','123',1166,'4123','남');
Insert into SWINGTEST.USERDATA (USERID,PASSWORD,USEREMAIL,PHONENUM,USERNO,USERNAME,GENDER) values ('newid1234','1234','newnewid1234@gmail.com','010-2222-2222',1168,'이름-1','남');
--------------------------------------------------------
--  DDL for Index USERID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SWINGTEST"."USERID_PK" ON "SWINGTEST"."USERDATA" ("USERID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index INFODATA_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SWINGTEST"."INFODATA_PK" ON "SWINGTEST"."INFODATA" ("INFO_NO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  Constraints for Table USERDATA
--------------------------------------------------------

  ALTER TABLE "SWINGTEST"."USERDATA" MODIFY ("GENDER" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."USERDATA" ADD CONSTRAINT "USERID_PK" PRIMARY KEY ("USERID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "SWINGTEST"."USERDATA" MODIFY ("PHONENUM" CONSTRAINT "PHNOENUM_NN" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."USERDATA" MODIFY ("USEREMAIL" CONSTRAINT "USEREMAIL_NN" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."USERDATA" MODIFY ("PASSWORD" CONSTRAINT "PASSWORD_NN" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."USERDATA" MODIFY ("USERNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table INFODATA
--------------------------------------------------------

  ALTER TABLE "SWINGTEST"."INFODATA" ADD CONSTRAINT "INFODATA_PK" PRIMARY KEY ("INFO_NO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "SWINGTEST"."INFODATA" MODIFY ("INFO_VAR" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."INFODATA" MODIFY ("INFO_CAP" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."INFODATA" MODIFY ("INFO_LOCATION" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."INFODATA" MODIFY ("INFO_NAME" NOT NULL ENABLE);
  ALTER TABLE "SWINGTEST"."INFODATA" MODIFY ("INFO_NO" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table REGISTDATA
--------------------------------------------------------

  ALTER TABLE "SWINGTEST"."REGISTDATA" ADD PRIMARY KEY ("REGIST_NO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table REGISTDATA
--------------------------------------------------------

  ALTER TABLE "SWINGTEST"."REGISTDATA" ADD CONSTRAINT "SYS_C007359" FOREIGN KEY ("INFO_NO")
	  REFERENCES "SWINGTEST"."INFODATA" ("INFO_NO") ON DELETE CASCADE ENABLE;
  ALTER TABLE "SWINGTEST"."REGISTDATA" ADD CONSTRAINT "SYS_C007360" FOREIGN KEY ("USERID")
	  REFERENCES "SWINGTEST"."USERDATA" ("USERID") ON DELETE CASCADE ENABLE;
