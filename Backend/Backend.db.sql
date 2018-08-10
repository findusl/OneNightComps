BEGIN TRANSACTION;
DROP TABLE IF EXISTS "user";
CREATE TABLE IF NOT EXISTS "user" (
	"id"	int AUTOINCREMENT PRIMARY KEY,
	"user_name"	TEXT NOT NULL UNIQUE INDEX,
	"e_mail"	TEXT NOT NULL UNIQUE INDEX,
	"pw_hash_hex"	TEXT,
	"hash_salt_hex"	TEXT,
);
DROP TABLE IF EXISTS "game_role";
CREATE TABLE IF NOT EXISTS "game_role" (
	"id"	int AUTOINCREMENT PRIMARY KEY,
	"name"	TEXT,
	"description"	TEXT,
	"last_modified"	timestamp DEFAULT (now()),
	'game_faction_id' int REFERENCES "game_faction"("id") ON UPDATE cascade
);
INSERT INTO "game_role" VALUES (1,'Villager','The Villager has no special abilities, but he is definitly not a werewolf. Players may often claim to be a villager.',1533494726,1),
 (2,'Werewolf','At night, all Werewolves open their eyes and look for other werewolves. If no one else opens their eyes, the other Werewolves are in the center. ',1533495772,2),
 (3,'Minion','Immediately following the Werewolf phase at night, the Minion wakes up and sees who the Werewolves are. During this phase, all Werewolves put their thumbs up
so the Minion can see who they are. The Werewolves don’t know who the Minion is. If the Minion dies and no Werewolves die, the Werewolves (and the Minion) win. If no players are Werewolves, the Minion wins as long as one other player (not the Minion) dies. This role can be a very powerful ally for the werewolf team. ',1533495817,2),
 (4,'Mason
','When using the Masons always put both Masons in the game. The Mason wakes up at night and looks for the other Mason. If the Mason doesn’t see another Mason, it means the other Mason card is in the center.',1533429170,1),
 (5,'Seer','At night, the Seer may look either at one other player’s card or at two of the center cards, but does not move them.',1533459015,1),
 (6,'Robber','At night, the Robber may choose to rob a card from another player and place his Robber card where
the other card was. Then the Robber looks at his new card. The player who receives the Robber card is on the village team. The Robber is on the team of the card he takes, however, he does not do the action of his new role at night.',1533459046,1),
 (7,'Troublemaker','At night, the Troublemaker may switch the cards of two other players without looking at those cards. The players who receive a different card are now the role (and team) of their new card, even though they don’t know what role that is until the end of the game. The Troublemaker is on the village team.',1533459111,1),
 (8,'Drunk','The Drunk is so drunk that he doesn’t remember his role. When it comes time to wake up at night, he must exchange his Drunk card for any card in the center, but he does not look at it. The Drunk is now the new role in front of him (even though he doesn’t know what that new role is) and is on that team.',1533460617,1),
 (9,'Insomniac','The Insomniac wakes up and looks at her card (to see if it has changed). Only use the Insomniac if the Robber and/or the Troublemaker are in the game.',1533473002,1),
 (10,'Tanner','The Tanner hates his job so much that he wants to die. The Tanner only wins if he dies. If the Tanner dies and no Werewolves die, the Werewolves do not win. If the Tanner dies and a Werewolf also dies, the village team wins too. The Tanner is considered a member of the village (but is not on their team), so if the Tanner dies when all werewolves are in the center, the village team loses.',1533473002,3),
 (11,'Hunter','If the Hunter dies, the player he is pointing at dies as well (regardless of how many votes his target receives).',1533495435,1);
DROP TABLE IF EXISTS "game_faction";
CREATE TABLE IF NOT EXISTS "game_faction" (
	"id"	int AUTOINCREMENT PRIMARY KEY,
	"name"	text
);
INSERT INTO "game_faction" VALUES (1,'Villagers'),
 (2,'Werewolf'),
 (3,'Tanner');
DROP TABLE IF EXISTS "game_composition";
CREATE TABLE IF NOT EXISTS "game_composition" (
	"id"	int AUTOINCREMENT PRIMARY KEY,
	"name"	TEXT NOT NULL INDEX,
	"description"	TEXT,
	"role_count"	INT NOT NULL,
	"difficulty_level"	int,
	"creator_user_id"	INT REFERENCES "user"("id") on update cascade,
	"last_modified"	timestamp DEFAULT (now()),
);
DROP TABLE IF EXISTS "game_composition_role";
CREATE TABLE IF NOT EXISTS "game_composition_role" (
	"game_composition_id"	int REFERENCES "game_compositions"("id") on update cascade,
	"game_role_id"	int REFERENCES "game_role"("id") on update cascade,
	PRIMARY KEY("game_composition_id","game_role_id"),
);
DROP TRIGGER IF EXISTS "update_game_role_timestamp";
CREATE TRIGGER update_game_role_timestamp after UPDATE ON "game_role" for each row BEGIN UPDATE 
game_roles set last_modified = (now()) WHERE id = NEW.id; END;
DROP TRIGGER IF EXISTS "update_game_composition_timestamp";
CREATE TRIGGER update_game_composition_timestamp after UPDATE ON game_composition for each row 
BEGIN UPDATE game_composition set last_modified = (now()) WHERE id = NEW.id; END;
DROP TRIGGER IF EXISTS "update_game_composition_role_timestamp";
CREATE TRIGGER update_game_composition_role_timestamp after UPDATE ON game_composition_role for each row 
BEGIN UPDATE game_composition set last_modified = (now()) WHERE id = NEW.game_composition_id; END;
COMMIT;