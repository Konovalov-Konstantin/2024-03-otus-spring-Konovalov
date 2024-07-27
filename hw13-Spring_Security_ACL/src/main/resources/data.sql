insert into authors (fullName)
values ('Достоевский Федор Михайлович'), ('Толстой Лев Николаевич'), ('Солженицын Александр Исаевич');

insert into genres (name)
values ('Роман'), ('Повесть'),('Публицистика');

insert into books (title, author_id, genre_id)
values ('Преступление и наказание', 1, 1), ('Смерть Ивана Ильича', 2, 2), ('Орбитальный путь', 3, 3);

insert into comments (comment, book_id)
values ('Комментарий_1_к_книге_1', 1), ('Комментарий_1_к_книге_2', 2), ('Комментарий_1_к_книге_3', 3);

insert into users (login, password, role)
values ('admin', '{bcrypt}$2y$10$mFCk/D3gvTQmMBeL96jAoex2vn1TyuD/aqxbrdGgN1Sjywmf2m4wi', 'ADMIN'),
       ('user', '{bcrypt}$2y$10$hIQdOiBzFSXCI5k8Mkqv1O87t3yVoILcjkKwfQYsoO/He7CFCbWBa', 'USER');
