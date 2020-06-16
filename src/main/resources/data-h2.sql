INSERT INTO category(category_id, name)
VALUES(1, 'Seasonal');

--INSERT INTO recipe(id, calories, date, description, name, preparation_time, score, serving, info_id, user_id)
--VALUES (1, 450, CURRENT_TIMESTAMP(), '100g chocolate, 100g butter', 'Christmas Pudding', 45, 0.00, 2, null,
--        (SELECT user_id FROM user where user_name = 'Admin'));
--
--INSERT INTO info(id, info, recipe_id)
--VALUES(1, 'Reipe description', 1);
--
--UPDATE recipe SET info_id = 1 WHERE id = 1;
--
--INSERT INTO recipe_category(id, category_id)
--VALUES(1, 1);
