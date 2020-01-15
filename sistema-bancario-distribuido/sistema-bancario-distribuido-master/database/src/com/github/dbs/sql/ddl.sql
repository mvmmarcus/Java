CREATE DATABASE dbs;

CREATE TABLE cliente (
	id SERIAL PRIMARY KEY,
	nome VARCHAR (50) NOT NULL,
	cpf VARCHAR (11) UNIQUE NOT NULL,
	endereco VARCHAR (50) NOT NULL,
	telefone VARCHAR (13) NOT NULL,
	data_nascimento DATE NOT NULL
);

CREATE TABLE conta (
	id SERIAL PRIMARY KEY,
	cliente_id INT REFERENCES cliente (id),
	saldo NUMERIC DEFAULT 0,
	senha VARCHAR NOT NULL
);

CREATE TABLE transacao (
	id SERIAL PRIMARY KEY,
	conta_origem_id INT REFERENCES conta (id),
	conta_destino_id INT REFERENCES conta (id),
	operacao VARCHAR NOT NULL,
	valor NUMERIC NOT NULL,
	data_ocorrencia DATE NOT NULL
);