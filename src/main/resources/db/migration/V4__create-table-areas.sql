CREATE TABLE areas (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(255) NOT NULL,
                       usuario_id BIGINT NOT NULL,
                       created_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL,
                       CONSTRAINT uk_areas_nome_usuario UNIQUE (nome, usuario_id),
                       CONSTRAINT fk_areas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);