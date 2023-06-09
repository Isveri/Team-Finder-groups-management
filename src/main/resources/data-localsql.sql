INSERT INTO Game (name,assign_roles_active) VALUES ('League of Legends',true);
SET @game1 := LAST_INSERT_ID();
INSERT INTO Game (name,assign_roles_active) VALUES ('CSGO',true);
SET @game2 := LAST_INSERT_ID();
INSERT INTO Game (name,assign_roles_active) VALUES ('IRL',false);
SET @game3 := LAST_INSERT_ID();

INSERT INTO Category (name, basic_Max_Users, game_id,can_assign_roles) VALUES ('SoloQ',2,@game1,true);
SET @cat1 := LAST_INSERT_ID();
INSERT INTO Category (name, basic_Max_Users, game_id,can_assign_roles) VALUES ('Flex',5,@game1,true);
SET @cat2 := LAST_INSERT_ID();
INSERT INTO Category (name, basic_Max_Users, game_id,can_assign_roles) VALUES ('Normal',5,@game1,true);
SET @cat3 := LAST_INSERT_ID();

INSERT INTO Category (name, basic_Max_Users, game_id,can_assign_roles) VALUES ('Normal',5,@game2,true);
SET @cat4 := LAST_INSERT_ID();

INSERT INTO Category (name, basic_Max_Users, game_id,can_assign_roles) VALUES ('MatchMaking',5,@game2,true);
SET @cat5 := LAST_INSERT_ID();

INSERT INTO Category (name, basic_Max_Users, game_id,can_assign_roles) VALUES ('WingMan',2,@game2,true);
SET @cat6 := LAST_INSERT_ID();

INSERT INTO Category (name, basic_Max_Users, game_id,can_assign_roles) VALUES ('Volleyball',10,@game3,false);
SET @cat7 := LAST_INSERT_ID();

INSERT INTO In_Game_Role (name,game_id) VALUES ('Mid',@game1);
INSERT INTO In_Game_Role (name,game_id) VALUES ('Bot',@game1);
INSERT INTO In_Game_Role (name,game_id) VALUES ('Top',@game1);
INSERT INTO In_Game_Role (name,game_id) VALUES ('Jng',@game1);
INSERT INTO In_Game_Role (name,game_id) VALUES ('ADC',@game1);

INSERT INTO In_Game_Role (name,game_id) VALUES ('Riffler',@game2);
INSERT INTO In_Game_Role (name,game_id) VALUES ('Sniper',@game2);
INSERT INTO In_Game_Role (name,game_id) VALUES ('Lurker',@game2);
INSERT INTO In_Game_Role (name,game_id) VALUES ('Entry',@game2);
INSERT INTO In_Game_Role (name,game_id) VALUES ('IGL',@game2);


INSERT INTO Group_Room (name, description, category_id, game_id, max_Users,
                        in_game_roles_active,open,join_code,city,deleted,
                        group_Leader_id,) VALUES ('Grupa 1','przykladowy',
                                                  @cat1,@game1,5,true,true,12314123,'Lublin',false,
                                                  (SELECT id FROM users WHERE username = 'User'));
SET @group1 := LAST_INSERT_ID();
INSERT INTO users_groups (user_id,group_id) VALUES ((SELECT id FROM users WHERE username = 'User'),@group1);
INSERT INTO users_groups (user_id,group_id) VALUES ((SELECT id FROM users WHERE username = 'Evi'),@group1);
INSERT INTO users_groups (user_id,group_id) VALUES ((SELECT id FROM users WHERE username = 'William'),@group1);
INSERT INTO users_groups (user_id,group_id) VALUES ((SELECT id FROM users WHERE username = 'Yeager'),@group1);
INSERT INTO users_groups (user_id,group_id) VALUES ((SELECT id FROM users WHERE username = 'Satoru'),@group1);
