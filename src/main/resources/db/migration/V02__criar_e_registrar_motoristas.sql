CREATE TABLE motorista ( 
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT , 
	nome VARCHAR(255) NOT NULL , 
	apelido VARCHAR(255) NOT NULL , 
	cnh VARCHAR(20) NOT NULL , 
	categoria VARCHAR(10) NOT NULL , 
	telefone VARCHAR(255) NOT NULL , 
	senha VARCHAR(255) NOT NULL 
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

INSERT INTO motorista (nome, apelido, cnh, categoria, telefone, senha) VALUES ('admin', 'admin', '', 'AB', '', '$2a$10$4.EQP.0yiZBQtoy4Qf77tuHEJqiP3jovuJLhjw57.wWymrz6TzrSG');