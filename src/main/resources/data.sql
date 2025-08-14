-- User authentication is handled by InMemoryUserDetailsManager in SecurityConfig
-- No need for user tables in database


INSERT INTO tb_category (name) VALUES ('Livros');
INSERT INTO tb_category (name) VALUES ('Eletrônicos');
INSERT INTO tb_category (name) VALUES ('Computadores');


INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('The Lord of the Rings', 90.5, TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z',
     'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg');

INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('Smart TV', 2190.0, TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z',
     'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg');

INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('Macbook Pro', 1250.0, TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z',
     'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg');

INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('PC Gamer', 1200.0, TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z',
     'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg');

INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('Rails for Dummies', 100.99, TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z',
     'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/5-big.jpg');

INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('PC Gamer Ex', 1350.0, TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z',
     'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg');

INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('PC Gamer X', 1350.0, TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z',
     'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/7-big.jpg');

INSERT INTO tb_product (name, price, date, description, img_url) VALUES
    ('PC Gamer Alfa', 1850.0, TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z',
     'Lorem ipsum dolor sit amet, colnsectetur adipiscing elit. Phasellus feugiat nisi sit amet felis ultrices, nec ultricies turpis rhoncus. Curabitur vehicula elit nec lorem molestie, sed mollis purus fermentum.',
     'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/7-big.jpg');

-- Inserindo relacionamento entre produtos e categorias
INSERT INTO tb_product_category (product_id, category_id) VALUES
    (1, 1); -- The Lord of the Rings -> Livros

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (2, 2); -- Smart TV -> Eletrônicos

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (3, 3); -- Macbook Pro -> Computadores

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (3, 2);

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (4, 3); -- PC Gamer -> Computadores

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (5, 1); -- Rails for Dummies -> Livros

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (6, 3); -- PC Gamer Ex -> Computadores

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (7, 3); -- PC Gamer X -> Computadores

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (8, 3); -- PC Gamer Alfa -> Computadores

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (8, 2); -- PC Gamer Alfa -> Computadores

INSERT INTO tb_product_category (product_id, category_id) VALUES
    (7, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES
    (6, 2);
