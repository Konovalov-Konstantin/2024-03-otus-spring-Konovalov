insert into authors(fullName)
values ('Test_Author_1'), ('Test_Author_2'), ('Test_Author_3'), ('Test_Author_4');

insert into genres(name)
values ('Test_Genre_1'), ('Test_Genre_2'), ('Test_Genre_3'), ('Test_Genre_4');

insert into books(title, author_id, genre_id)
values ('Test_BookTitle_1', 1, 1), ('Test_BookTitle_2', 2, 2), ('Test_BookTitle_3', 3, 3), ('Test_BookTitle_4', 4, 4);

insert into comments(comment, book_id)
values ('Комментарий_1_к_книге_1', 1), ('Комментарий_2_к_книге_1', 1), ('Комментарий_1_к_книге_2', 2), ('Комментарий_1_к_книге_3', 3);
