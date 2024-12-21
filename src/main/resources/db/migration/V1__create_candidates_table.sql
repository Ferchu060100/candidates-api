CREATE TABLE candidates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    salary_expected DECIMAL(10, 2)
);

-- Insertar datos de prueba
INSERT INTO candidates (name, email, gender, salary_expected) VALUES
('Juan Pérez', 'juan.perez@example.com', 'Male', 3000.00),
('Ana López', 'ana.lopez@example.com', 'Female', 3200.00),
('Carlos Sánchez', 'carlos.sanchez@example.com', 'Male', 2800.00),
('Laura García', 'laura.garcia@example.com', 'Female', 3500.00),
('José Martínez', 'jose.martinez@example.com', 'Male', 4000.00);