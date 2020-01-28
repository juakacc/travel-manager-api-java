CREATE TABLE viagem ( 
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT, 
	saida DATETIME NOT NULL, 
	chegada DATETIME, 
	descricao VARCHAR(255), 
	id_veiculo BIGINT(20) NOT NULL,
	id_motorista BIGINT(20) NOT NULL,
	FOREIGN KEY (id_veiculo) REFERENCES veiculo(id),
	FOREIGN KEY (id_motorista) REFERENCES motorista(id)
) ENGINE = InnoDB CHARSET=utf8;
