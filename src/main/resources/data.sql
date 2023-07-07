INSERT INTO Game (id,name,assign_roles_active) VALUES (1,'League of Legends',true);
INSERT INTO Game (id,name,assign_roles_active) VALUES (2,'CSGO',true);
INSERT INTO Game (id,name,assign_roles_active) VALUES (3,'IRL',false);

INSERT INTO Category (id,name, basic_Max_Users, game_id,can_assign_roles) VALUES (1,'SoloQ',2,1,true);
INSERT INTO Category (id,name, basic_Max_Users, game_id,can_assign_roles) VALUES (2,'Flex',5,1,true);
INSERT INTO Category (id,name, basic_Max_Users, game_id,can_assign_roles) VALUES (3,'Normal',5,1,true);

INSERT INTO Category (id,name, basic_Max_Users, game_id,can_assign_roles) VALUES (4,'Normal',5,2,true);

INSERT INTO Category (id,name, basic_Max_Users, game_id,can_assign_roles) VALUES (5,'MatchMaking',5,2,true);

INSERT INTO Category (id,name, basic_Max_Users, game_id,can_assign_roles) VALUES (6,'WingMan',2,2,true);

INSERT INTO Category (id,name, basic_Max_Users, game_id,can_assign_roles) VALUES (7,'Volleyball',10,3,false);

INSERT INTO In_Game_Role (id,name,game_id) VALUES (1,'Mid',1);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (2,'Bot',1);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (3,'Top',1);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (4,'Jungle',1);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (5,'Supp',1);

INSERT INTO In_Game_Role (id,name,game_id) VALUES (6,'Fragger',2);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (7,'Sniper',2);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (8,'Lurker',2);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (9,'Leader',2);
INSERT INTO In_Game_Role (id,name,game_id) VALUES (10,'Support',2);


INSERT INTO Group_Room (id,name, description, category_id, game_id, max_Users,
                        in_game_roles_active,open,join_code,city,deleted,
                        group_Leader_id) VALUES (1,'Grupa 1','przykladowy',
                                                  1,1,5,false,true,'12314123','Lublin',false,
                                                  2);
INSERT INTO users_groups (user_id,group_id) VALUES (2,1);
INSERT INTO users_groups (user_id,group_id) VALUES (1,1);
INSERT INTO users_groups (user_id,group_id) VALUES (3,1);
INSERT INTO users_groups (user_id,group_id) VALUES (4,1);
INSERT INTO users_groups (user_id,group_id) VALUES (5,1);
