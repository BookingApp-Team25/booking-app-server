INSERT INTO users (id, username, password, blocked,role) VALUES
                                                             ('123e4567-e89b-12d3-a456-426614174001', 'guest@gmail.com', '$2a$12$H5WNUI1bouupDyphboFBUuHjyG5FsuYQhZzcU5c400T4AiRszJmhK', false,'Guest'),
                                                             ('5894d69d-fc8d-4f06-bf0c-dc695b40901b', 'host@gmail.com', '$2a$12$4Mdt37hn1AnDQw6qJceKcu7j6Lshpp7D9ylxHEhi/hYlSeBunEbwq', false,'Host'),
                                                             ('123e4567-e89b-12d3-a456-426614174002', 'admin@admin.com', '$2a$12$Bgm0go1sTPpOtUvyI2gyCu0L0138D4iEKTHAwhdG8QHz0H9nOwbHi', false,'Admin');

INSERT INTO guest (id) VALUES
    ('123e4567-e89b-12d3-a456-426614174001');


INSERT INTO host (id) VALUES
    ('5894d69d-fc8d-4f06-bf0c-dc695b40901b');

INSERT INTO admin (id) VALUES
    ('123e4567-e89b-12d3-a456-426614174002');