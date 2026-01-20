-- Entrantes
INSERT IGNORE INTO entrantes (Nombre, Descripcion, Precio) VALUES
('Patatas bravas', 'Con salsa brava casera', 4.50),
('Croquetas de jamon', NULL, 6.00),
('Ensalada mixta', 'Lechuga, tomate, cebolla y aceitunas', 5.00);

-- Principales
INSERT IGNORE INTO principales (Nombre, Descripcion, Precio) VALUES
('Pollo asado', 'Con patatas panaderas', 9.50),
('Merluza a la plancha', 'Con verduras salteadas', 11.00),
('Entrecot', 'Punto de coccion a elegir', 14.00);

-- Postres
INSERT IGNORE INTO postres (Nombre, Descripcion, Precio) VALUES
('Flan casero', NULL, 3.00),
('Tarta de queso', 'Estilo Dieguinho', 4.50),
('Fruta del tiempo', NULL, 2.50);

-- Menus
INSERT IGNORE INTO menu (Nombre, Descripcion, Precio_Menu, Entrante_id, Principal_id, Postre_id) VALUES
('Menu del dia', 'El de siempre', 12.00, 1, 1, 1),
('Menu especial', NULL, 18.00, 3, 3, 2);
