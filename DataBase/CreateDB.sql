-- Crear la base de datos
CREATE DATABASE LibraryDB;
GO

USE LibraryDB;
GO

-- ===========================================
-- TABLA: Usuarios (Estudiantes y Profesores)
-- ===========================================
CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    user_type NVARCHAR(20) CHECK (user_type IN ('student', 'professor')) NOT NULL,
    registration_date DATETIME DEFAULT GETDATE()
);
GO

-- ===========================================
-- TABLA: Materiales (Libros, Revistas, Tesis)
-- ===========================================
CREATE TABLE Materials (
    material_id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(200) NOT NULL,
    author NVARCHAR(100),
    material_type NVARCHAR(20) CHECK (material_type IN ('book', 'journal', 'thesis')) NOT NULL,
    total_quantity INT NOT NULL CHECK (total_quantity >= 0),
    available_quantity INT NOT NULL CHECK (available_quantity >= 0),
    publication_date DATE
);
GO

-- ===========================================
-- TABLA: Pr�stamos
-- ===========================================
CREATE TABLE Loans (
    loan_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    material_id INT NOT NULL,
    loan_date DATETIME DEFAULT GETDATE(),
    due_date DATE NOT NULL,
    return_date DATE NULL,
    fine DECIMAL(8,2) DEFAULT 0 CHECK (fine >= 0),
    CONSTRAINT FK_Loans_Users FOREIGN KEY (user_id) REFERENCES Users(user_id),
    CONSTRAINT FK_Loans_Materials FOREIGN KEY (material_id) REFERENCES Materials(material_id)
);
GO

-- ===========================================
-- TABLA: Devoluciones
-- ===========================================
CREATE TABLE Returns (
    return_id INT IDENTITY(1,1) PRIMARY KEY,
    loan_id INT NOT NULL,
    return_date DATETIME DEFAULT GETDATE(),
    fine DECIMAL(8,2) DEFAULT 0 CHECK (fine >= 0),
    CONSTRAINT FK_Returns_Loans FOREIGN KEY (loan_id) REFERENCES Loans(loan_id)
);
GO


-- ===========================================
-- INSERTS: Usuarios (Estudiantes y Profesores)
-- ===========================================
INSERT INTO Users (name, email, user_type)
VALUES
('Juan P�rez', 'juan.perez@universidad.edu', 'student'),
('Mar�a L�pez', 'maria.lopez@universidad.edu', 'student'),
('Carlos G�mez', 'carlos.gomez@universidad.edu', 'student'),
('Ana Torres', 'ana.torres@universidad.edu', 'professor'),
('Luis Rodr�guez', 'luis.rodriguez@universidad.edu', 'professor');
GO

-- ===========================================
-- INSERTS: Materiales (Libros, Revistas, Tesis)
-- ===========================================
INSERT INTO Materials (title, author, material_type, total_quantity, available_quantity, publication_date)
VALUES
('El Quijote', 'Miguel de Cervantes', 'book', 5, 5, '1605-01-01'),
('Cien años de soledad', 'Gabriel García Márquez', 'book', 4, 4, '1967-05-30'),
('Revista Científica de Tecnología', 'Varios autores', 'journal', 10, 10, '2024-01-01'),
('Revista de Historia Moderna', 'Instituto de Historia', 'journal', 8, 8, '2023-06-15'),
('Tesis sobre Energías Renovables', 'José Martínez', 'thesis', 2, 2, '2022-09-01'),
('Programación en Java', 'James Gosling', 'book', 6, 6, '2020-03-01'),
('Introducción a la Inteligencia Artificial', 'Andrew Ng', 'book', 5, 5, '2018-06-01'),
('Avances en Medicina 2025', 'OMS', 'journal', 12, 12, '2025-01-15'),
('Energía Solar en el Siglo XXI', 'Laura Herrera', 'thesis', 3, 3, '2023-08-20'),
('Revista de Computación Cuántica', 'IBM Research', 'journal', 7, 7, '2024-10-05');
GO

-- ===========================================
-- INSERTS: Pr�stamos
-- ===========================================
INSERT INTO Loans (user_id, material_id, loan_date, due_date, fine)
VALUES
(1, 1, GETDATE(), DATEADD(DAY, 7, GETDATE()), 0),   -- Juan presta El Quijote
(2, 2, GETDATE(), DATEADD(DAY, 7, GETDATE()), 0),   -- María presta Cien años de soledad
(3, 3, GETDATE(), DATEADD(DAY, 5, GETDATE()), 0),   -- Carlos presta Revista Científica
(4, 4, GETDATE(), DATEADD(DAY, 10, GETDATE()), 0),  -- Ana presta Revista Historia Moderna
(5, 5, GETDATE(), DATEADD(DAY, 15, GETDATE()), 0),  -- Luis presta Tesis Energías Renovables
(1, 6, GETDATE(), DATEADD(DAY, 10, GETDATE()), 0),  -- Juan presta Programación en Java
(2, 7, GETDATE(), DATEADD(DAY, 10, GETDATE()), 0),  -- María presta Inteligencia Artificial
(3, 8, GETDATE(), DATEADD(DAY, 5, GETDATE()), 0),   -- Carlos presta Avances en Medicina
(4, 9, GETDATE(), DATEADD(DAY, 12, GETDATE()), 0),  -- Ana presta Energía Solar
(5, 10, GETDATE(), DATEADD(DAY, 8, GETDATE()), 0);  -- Luis presta Computación Cuántica
GO

-- ===========================================
-- INSERTS: Devoluciones
-- ===========================================
INSERT INTO Returns (loan_id, return_date, fine)
VALUES
(1, DATEADD(DAY, 6, GETDATE()), 0),       -- Juan devolvió antes del vencimiento
(2, DATEADD(DAY, 10, GETDATE()), 6.00),   -- María devolvió 3 días tarde
(3, DATEADD(DAY, 5, GETDATE()), 0),       -- Carlos a tiempo
(4, DATEADD(DAY, 13, GETDATE()), 6.00),   -- Ana 3 días tarde
(5, DATEADD(DAY, 14, GETDATE()), 0),      -- Luis antes de tiempo
(6, DATEADD(DAY, 12, GETDATE()), 4.00),   -- Juan 2 días tarde
(7, DATEADD(DAY, 9, GETDATE()), 0),       -- María a tiempo
(8, DATEADD(DAY, 8, GETDATE()), 6.00),    -- Carlos 3 días tarde
(9, DATEADD(DAY, 13, GETDATE()), 2.00),   -- Ana 1 día tarde
(10, DATEADD(DAY, 7, GETDATE()), 0);      -- Luis a tiempo
GO

-- ===========================================
-- CONSULTAS DE PRUEBA
-- ===========================================
SELECT * FROM Users;
SELECT * FROM Materials;
SELECT * FROM Loans;
SELECT * FROM Returns;
GO