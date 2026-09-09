CREATE TABLE usuarios (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          created_at DATETIME NOT NULL,
                          updated_at DATETIME NOT NULL
);