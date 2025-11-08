------------------------------------------------------------
-- DROP TABLES (ignora erro se não existirem)
------------------------------------------------------------
DROP TABLE T_ATENDIMENTO CASCADE CONSTRAINTS;

DROP TABLE T_ESPECIALISTA CASCADE CONSTRAINTS;

DROP TABLE T_CLIENTE CASCADE CONSTRAINTS;

DROP TABLE T_EXAME CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_CLIENTE;

DROP SEQUENCE SEQ_ESPECIALISTA;

DROP SEQUENCE SEQ_ATENDIMENTO;

DROP SEQUENCE SEQ_EXAME;

------------------------------------------------------------
-- CRIA TABELA CLIENTE (atualizada)
------------------------------------------------------------
CREATE TABLE T_CLIENTE (
    ID_CLIENTE          NUMBER PRIMARY KEY,
    NM_CLIENTE          VARCHAR2(80)  NOT NULL,
    EM_CLIENTE          VARCHAR2(100) UNIQUE NOT NULL,
    CPF_CLIENTE         VARCHAR2(14)  UNIQUE NOT NULL,
    DTNASC_CLIENTE      DATE          NOT NULL,
    TEL1_CLIENTE        VARCHAR2(20)  NOT NULL,
    CONV_CLIENTE        VARCHAR2(60),
    NUM_CARTERINHA      VARCHAR2(30)
);

CREATE SEQUENCE SEQ_CLIENTE START WITH 1 INCREMENT BY 1;

------------------------------------------------------------
-- CRIA TABELA ESPECIALISTA (ATUALIZADA)
------------------------------------------------------------
CREATE TABLE T_ESPECIALISTA (
    ID_ESPECIALISTA     NUMBER PRIMARY KEY,
    NM_ESPECIALISTA     VARCHAR2(80) NOT NULL,
    EM_ESPECIALISTA     VARCHAR2(100) UNIQUE NOT NULL,
    CPF_ESPECIALISTA    VARCHAR2(14) UNIQUE NOT NULL,
    DTNASC_ESPECIALISTA DATE NOT NULL,
    TEL1_ESPECIALISTA   VARCHAR2(20) NOT NULL,
    CRM_ESPECIALISTA    VARCHAR2(20) UNIQUE NOT NULL,
    ESP_ESPECIALISTA    VARCHAR2(60) NOT NULL
);

CREATE SEQUENCE SEQ_ESPECIALISTA START WITH 1 INCREMENT BY 1;
------------------------------------------------------------
-- CRIA TABELA ATENDIMENTO
------------------------------------------------------------
CREATE TABLE T_ATENDIMENTO (
    ID_ATENDIMENTO     NUMBER PRIMARY KEY,
    ID_CLIENTE         NUMBER NOT NULL,
    ID_ESPECIALISTA    NUMBER NOT NULL,
    DS_ATENDIMENTO     VARCHAR2(500) NOT NULL,
    DT_ATENDIMENTO     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ST_ATENDIMENTO     VARCHAR2(30) DEFAULT 'AGENDADO',

    CONSTRAINT FK_ATEND_CLIENTE FOREIGN KEY (ID_CLIENTE)
        REFERENCES T_CLIENTE(ID_CLIENTE) ON DELETE CASCADE,

    CONSTRAINT FK_ATEND_ESPEC FOREIGN KEY (ID_ESPECIALISTA)
        REFERENCES T_ESPECIALISTA(ID_ESPECIALISTA) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_ATENDIMENTO START WITH 1 INCREMENT BY 1;


------------------------------------------------------------
-- CRIA TABELA EXAME
------------------------------------------------------------
CREATE TABLE T_EXAME (
    ID_EXAME          NUMBER PRIMARY KEY,
    ID_CLIENTE        NUMBER NOT NULL,
    DS_EXAME          VARCHAR2(500) NOT NULL,
    DT_ATENDIMENTO    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ST_ATENDIMENTO    VARCHAR2(30) DEFAULT 'AGENDADO',

    CONSTRAINT FK_EXAME_CLIENTE FOREIGN KEY (ID_CLIENTE)
        REFERENCES T_CLIENTE(ID_CLIENTE) ON DELETE CASCADE
);

------------------------------------------------------------
-- SEQUENCE PARA GERAÇÃO DE IDs
------------------------------------------------------------
CREATE SEQUENCE SEQ_EXAME START WITH 1 INCREMENT BY 1;

------------------------------------------------------------
-- ÍNDICE PARA PERFORMANCE
------------------------------------------------------------
CREATE INDEX IDX_EXAME_CLIENTE ON T_EXAME(ID_CLIENTE);

------------------------------------------------------------
-- CONFIRMA ALTERAÇÕES
------------------------------------------------------------
COMMIT;


------------------------------------------------------------
-- ÍNDICES PARA PERFORMANCE
------------------------------------------------------------
CREATE INDEX IDX_ATEND_CLIENTE ON T_ATENDIMENTO(ID_CLIENTE);
CREATE INDEX IDX_ATEND_ESPEC   ON T_ATENDIMENTO(ID_ESPECIALISTA);

------------------------------------------------------------
-- CONFIRMA ALTERAÇÕES
------------------------------------------------------------
COMMIT;
