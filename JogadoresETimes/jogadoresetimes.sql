-- Criar banco de dados
CREATE DATABASE jogadorestimes;
USE jogadorestimes;

-- Criar tabela de times
CREATE TABLE time (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criar tabela de jogadores
CREATE TABLE jogador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT,
    posicao VARCHAR(255),
    altura DECIMAL(5,2),
    time_id INT,
    FOREIGN KEY (time_id) REFERENCES time(id)
);
