BEGIN TRANSACTION;
CREATE TABLE [CardColors] (
[MatchID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[CardID] INTEGER  NOT NULL,
[ColorID] INTEGER  NOT NULL
);
INSERT INTO CardColors VALUES(16422,1,5);
INSERT INTO CardColors VALUES(16423,2,2);
INSERT INTO CardColors VALUES(16424,4,3);
INSERT INTO CardColors VALUES(16425,5,5);
INSERT INTO CardColors VALUES(16426,6,3);
INSERT INTO CardColors VALUES(16427,7,2);
INSERT INTO CardColors VALUES(16428,8,2);
INSERT INTO CardColors VALUES(16429,9,4);
INSERT INTO CardColors VALUES(16430,10,1);
INSERT INTO CardColors VALUES(16431,11,1);
INSERT INTO CardColors VALUES(16432,13,1);
INSERT INTO CardColors VALUES(16433,14,1);
INSERT INTO CardColors VALUES(16434,15,1);
INSERT INTO CardColors VALUES(16435,16,4);
INSERT INTO CardColors VALUES(16436,17,2);
INSERT INTO CardColors VALUES(16437,18,3);
INSERT INTO CardColors VALUES(16438,19,3);
INSERT INTO CardColors VALUES(16439,20,2);
INSERT INTO CardColors VALUES(16440,21,1);
INSERT INTO CardColors VALUES(16441,22,5);
INSERT INTO CardColors VALUES(16442,23,1);
INSERT INTO CardColors VALUES(16443,24,2);
INSERT INTO CardColors VALUES(16444,25,2);
INSERT INTO CardColors VALUES(16445,26,1);
INSERT INTO CardColors VALUES(16446,27,1);
INSERT INTO CardColors VALUES(16447,29,4);
INSERT INTO CardColors VALUES(16448,30,1);
INSERT INTO CardColors VALUES(16449,31,3);
INSERT INTO CardColors VALUES(16450,32,1);
INSERT INTO CardColors VALUES(16451,33,5);
INSERT INTO CardColors VALUES(16452,34,5);
INSERT INTO CardColors VALUES(16453,35,4);
INSERT INTO CardColors VALUES(16454,36,4);
INSERT INTO CardColors VALUES(16455,37,4);
INSERT INTO CardColors VALUES(16456,38,3);
INSERT INTO CardColors VALUES(16457,39,4);
INSERT INTO CardColors VALUES(16458,40,2);
INSERT INTO CardColors VALUES(16459,43,2);
INSERT INTO CardColors VALUES(16460,44,5);
INSERT INTO CardColors VALUES(16461,46,2);
INSERT INTO CardColors VALUES(16462,48,1);
INSERT INTO CardColors VALUES(16463,51,5);
INSERT INTO CardColors VALUES(16464,52,2);
INSERT INTO CardColors VALUES(16465,53,2);
INSERT INTO CardColors VALUES(16466,54,2);
INSERT INTO CardColors VALUES(16467,57,5);
INSERT INTO CardColors VALUES(16468,59,1);
INSERT INTO CardColors VALUES(16469,60,4);
INSERT INTO CardColors VALUES(16470,61,3);
INSERT INTO CardColors VALUES(16471,62,3);
INSERT INTO CardColors VALUES(16472,63,2);
INSERT INTO CardColors VALUES(16473,64,2);
INSERT INTO CardColors VALUES(16474,65,5);
INSERT INTO CardColors VALUES(16475,66,5);
INSERT INTO CardColors VALUES(16476,68,3);
INSERT INTO CardColors VALUES(16477,69,4);
INSERT INTO CardColors VALUES(16478,70,3);
INSERT INTO CardColors VALUES(16479,71,2);
INSERT INTO CardColors VALUES(16480,72,2);
INSERT INTO CardColors VALUES(16481,73,4);
INSERT INTO CardColors VALUES(16482,75,1);
INSERT INTO CardColors VALUES(16483,76,2);
INSERT INTO CardColors VALUES(16484,77,3);
INSERT INTO CardColors VALUES(16485,78,2);
INSERT INTO CardColors VALUES(16486,79,3);
INSERT INTO CardColors VALUES(16487,80,3);
INSERT INTO CardColors VALUES(16488,82,4);
INSERT INTO CardColors VALUES(16489,84,2);
INSERT INTO CardColors VALUES(16490,85,3);
INSERT INTO CardColors VALUES(16491,86,3);
INSERT INTO CardColors VALUES(16492,87,4);
INSERT INTO CardColors VALUES(16493,88,3);
INSERT INTO CardColors VALUES(16494,89,4);
INSERT INTO CardColors VALUES(16495,90,5);
INSERT INTO CardColors VALUES(16496,91,5);
INSERT INTO CardColors VALUES(16497,92,5);
INSERT INTO CardColors VALUES(16498,93,5);
INSERT INTO CardColors VALUES(16499,94,5);
INSERT INTO CardColors VALUES(16500,95,3);
INSERT INTO CardColors VALUES(16501,96,2);
INSERT INTO CardColors VALUES(16502,98,3);
INSERT INTO CardColors VALUES(16503,99,5);
INSERT INTO CardColors VALUES(16504,100,3);
CREATE TABLE [CardNames] (
[MatchID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[CardID] INTEGER  NOT NULL,
[NameID] INTEGER  NOT NULL
);
CREATE TABLE [CardSubtypes] (
[MatchID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[CardID] INTEGER  NOT NULL,
[SubtypeID] INTEGER  NOT NULL
);
INSERT INTO CardSubtypes VALUES(15216,2,34);
INSERT INTO CardSubtypes VALUES(15217,2,35);
INSERT INTO CardSubtypes VALUES(15218,7,36);
INSERT INTO CardSubtypes VALUES(15219,8,37);
INSERT INTO CardSubtypes VALUES(15220,8,35);
INSERT INTO CardSubtypes VALUES(15221,9,38);
INSERT INTO CardSubtypes VALUES(15222,10,34);
INSERT INTO CardSubtypes VALUES(15223,10,39);
INSERT INTO CardSubtypes VALUES(15224,11,40);
INSERT INTO CardSubtypes VALUES(15225,15,41);
INSERT INTO CardSubtypes VALUES(15226,16,42);
INSERT INTO CardSubtypes VALUES(15227,17,35);
INSERT INTO CardSubtypes VALUES(15228,18,43);
INSERT INTO CardSubtypes VALUES(15229,22,44);
INSERT INTO CardSubtypes VALUES(15230,23,45);
INSERT INTO CardSubtypes VALUES(15231,23,46);
INSERT INTO CardSubtypes VALUES(15232,24,45);
INSERT INTO CardSubtypes VALUES(15233,24,46);
INSERT INTO CardSubtypes VALUES(15234,25,45);
INSERT INTO CardSubtypes VALUES(15235,25,46);
INSERT INTO CardSubtypes VALUES(15236,25,35);
INSERT INTO CardSubtypes VALUES(15237,26,34);
INSERT INTO CardSubtypes VALUES(15238,26,47);
INSERT INTO CardSubtypes VALUES(15239,32,34);
INSERT INTO CardSubtypes VALUES(15240,32,48);
INSERT INTO CardSubtypes VALUES(15241,33,45);
INSERT INTO CardSubtypes VALUES(15242,34,42);
INSERT INTO CardSubtypes VALUES(15243,36,49);
INSERT INTO CardSubtypes VALUES(15244,37,50);
INSERT INTO CardSubtypes VALUES(15245,38,51);
INSERT INTO CardSubtypes VALUES(15246,39,36);
INSERT INTO CardSubtypes VALUES(15247,39,52);
INSERT INTO CardSubtypes VALUES(15248,41,53);
INSERT INTO CardSubtypes VALUES(15249,44,54);
INSERT INTO CardSubtypes VALUES(15250,46,55);
INSERT INTO CardSubtypes VALUES(15251,46,35);
INSERT INTO CardSubtypes VALUES(15252,48,34);
INSERT INTO CardSubtypes VALUES(15253,48,47);
INSERT INTO CardSubtypes VALUES(15254,51,56);
INSERT INTO CardSubtypes VALUES(15255,51,57);
INSERT INTO CardSubtypes VALUES(15256,51,58);
INSERT INTO CardSubtypes VALUES(15257,52,59);
INSERT INTO CardSubtypes VALUES(15258,53,36);
INSERT INTO CardSubtypes VALUES(15259,54,60);
INSERT INTO CardSubtypes VALUES(15260,56,61);
INSERT INTO CardSubtypes VALUES(15261,58,61);
INSERT INTO CardSubtypes VALUES(15262,62,42);
INSERT INTO CardSubtypes VALUES(15263,64,34);
INSERT INTO CardSubtypes VALUES(15264,64,35);
INSERT INTO CardSubtypes VALUES(15265,65,62);
INSERT INTO CardSubtypes VALUES(15266,71,42);
INSERT INTO CardSubtypes VALUES(15267,76,63);
INSERT INTO CardSubtypes VALUES(15268,80,34);
INSERT INTO CardSubtypes VALUES(15269,80,39);
INSERT INTO CardSubtypes VALUES(15270,80,64);
INSERT INTO CardSubtypes VALUES(15271,84,52);
INSERT INTO CardSubtypes VALUES(15272,85,65);
INSERT INTO CardSubtypes VALUES(15273,85,66);
INSERT INTO CardSubtypes VALUES(15274,86,67);
INSERT INTO CardSubtypes VALUES(15275,87,68);
INSERT INTO CardSubtypes VALUES(15276,88,69);
INSERT INTO CardSubtypes VALUES(15277,89,36);
INSERT INTO CardSubtypes VALUES(15278,90,56);
INSERT INTO CardSubtypes VALUES(15279,91,56);
INSERT INTO CardSubtypes VALUES(15280,91,70);
INSERT INTO CardSubtypes VALUES(15281,92,56);
INSERT INTO CardSubtypes VALUES(15282,93,56);
INSERT INTO CardSubtypes VALUES(15283,93,71);
INSERT INTO CardSubtypes VALUES(15284,94,72);
INSERT INTO CardSubtypes VALUES(15285,98,42);
INSERT INTO CardSubtypes VALUES(15286,99,34);
INSERT INTO CardSubtypes VALUES(15287,99,73);
INSERT INTO CardSubtypes VALUES(15288,100,65);
INSERT INTO CardSubtypes VALUES(15289,100,74);
CREATE TABLE [CardSupertypes] (
[MatchID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[CardID] INTEGER  NOT NULL,
[SupertypeID] INTEGER  NOT NULL
);
INSERT INTO CardSupertypes VALUES(1428,8,2);
INSERT INTO CardSupertypes VALUES(1429,17,2);
INSERT INTO CardSupertypes VALUES(1430,18,2);
INSERT INTO CardSupertypes VALUES(1431,48,2);
CREATE TABLE [CardTypes] (
[MatchID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[CardID] INTEGER  NOT NULL,
[TypeID] INTEGER  NOT NULL
);
INSERT INTO CardTypes VALUES(18924,1,5);
INSERT INTO CardTypes VALUES(18925,2,6);
INSERT INTO CardTypes VALUES(18926,3,7);
INSERT INTO CardTypes VALUES(18927,4,10);
INSERT INTO CardTypes VALUES(18928,5,10);
INSERT INTO CardTypes VALUES(18929,6,12);
INSERT INTO CardTypes VALUES(18930,7,6);
INSERT INTO CardTypes VALUES(18931,8,6);
INSERT INTO CardTypes VALUES(18932,9,6);
INSERT INTO CardTypes VALUES(18933,10,6);
INSERT INTO CardTypes VALUES(18934,11,6);
INSERT INTO CardTypes VALUES(18935,12,4);
INSERT INTO CardTypes VALUES(18936,13,12);
INSERT INTO CardTypes VALUES(18937,14,5);
INSERT INTO CardTypes VALUES(18938,15,6);
INSERT INTO CardTypes VALUES(18939,16,5);
INSERT INTO CardTypes VALUES(18940,17,6);
INSERT INTO CardTypes VALUES(18941,18,6);
INSERT INTO CardTypes VALUES(18942,19,12);
INSERT INTO CardTypes VALUES(18943,20,10);
INSERT INTO CardTypes VALUES(18944,21,5);
INSERT INTO CardTypes VALUES(18945,22,6);
INSERT INTO CardTypes VALUES(18946,23,6);
INSERT INTO CardTypes VALUES(18947,24,6);
INSERT INTO CardTypes VALUES(18948,25,6);
INSERT INTO CardTypes VALUES(18949,26,6);
INSERT INTO CardTypes VALUES(18950,27,10);
INSERT INTO CardTypes VALUES(18951,28,7);
INSERT INTO CardTypes VALUES(18952,29,10);
INSERT INTO CardTypes VALUES(18953,30,10);
INSERT INTO CardTypes VALUES(18954,31,12);
INSERT INTO CardTypes VALUES(18955,32,6);
INSERT INTO CardTypes VALUES(18956,33,6);
INSERT INTO CardTypes VALUES(18957,34,5);
INSERT INTO CardTypes VALUES(18958,35,12);
INSERT INTO CardTypes VALUES(18959,36,6);
INSERT INTO CardTypes VALUES(18960,37,6);
INSERT INTO CardTypes VALUES(18961,38,6);
INSERT INTO CardTypes VALUES(18962,39,6);
INSERT INTO CardTypes VALUES(18963,40,10);
INSERT INTO CardTypes VALUES(18964,41,4);
INSERT INTO CardTypes VALUES(18965,41,6);
INSERT INTO CardTypes VALUES(18966,42,7);
INSERT INTO CardTypes VALUES(18967,43,10);
INSERT INTO CardTypes VALUES(18968,44,6);
INSERT INTO CardTypes VALUES(18969,45,7);
INSERT INTO CardTypes VALUES(18970,46,6);
INSERT INTO CardTypes VALUES(18971,47,4);
INSERT INTO CardTypes VALUES(18972,48,6);
INSERT INTO CardTypes VALUES(18973,49,4);
INSERT INTO CardTypes VALUES(18974,50,4);
INSERT INTO CardTypes VALUES(18975,51,6);
INSERT INTO CardTypes VALUES(18976,52,6);
INSERT INTO CardTypes VALUES(18977,53,6);
INSERT INTO CardTypes VALUES(18978,54,6);
INSERT INTO CardTypes VALUES(18979,55,4);
INSERT INTO CardTypes VALUES(18980,56,4);
INSERT INTO CardTypes VALUES(18981,56,6);
INSERT INTO CardTypes VALUES(18982,57,12);
INSERT INTO CardTypes VALUES(18983,58,4);
INSERT INTO CardTypes VALUES(18984,58,6);
INSERT INTO CardTypes VALUES(18985,59,10);
INSERT INTO CardTypes VALUES(18986,60,12);
INSERT INTO CardTypes VALUES(18987,61,12);
INSERT INTO CardTypes VALUES(18988,62,5);
INSERT INTO CardTypes VALUES(18989,63,12);
INSERT INTO CardTypes VALUES(18990,64,6);
INSERT INTO CardTypes VALUES(18991,65,6);
INSERT INTO CardTypes VALUES(18992,66,12);
INSERT INTO CardTypes VALUES(18993,67,4);
INSERT INTO CardTypes VALUES(18994,68,12);
INSERT INTO CardTypes VALUES(18995,69,12);
INSERT INTO CardTypes VALUES(18996,70,12);
INSERT INTO CardTypes VALUES(18997,71,5);
INSERT INTO CardTypes VALUES(18998,72,10);
INSERT INTO CardTypes VALUES(18999,73,12);
INSERT INTO CardTypes VALUES(19000,74,4);
INSERT INTO CardTypes VALUES(19001,75,10);
INSERT INTO CardTypes VALUES(19002,76,6);
INSERT INTO CardTypes VALUES(19003,77,12);
INSERT INTO CardTypes VALUES(19004,78,10);
INSERT INTO CardTypes VALUES(19005,79,12);
INSERT INTO CardTypes VALUES(19006,80,6);
INSERT INTO CardTypes VALUES(19007,81,4);
INSERT INTO CardTypes VALUES(19008,82,5);
INSERT INTO CardTypes VALUES(19009,83,4);
INSERT INTO CardTypes VALUES(19010,84,6);
INSERT INTO CardTypes VALUES(19011,85,6);
INSERT INTO CardTypes VALUES(19012,86,6);
INSERT INTO CardTypes VALUES(19013,87,6);
INSERT INTO CardTypes VALUES(19014,88,6);
INSERT INTO CardTypes VALUES(19015,89,6);
INSERT INTO CardTypes VALUES(19016,90,6);
INSERT INTO CardTypes VALUES(19017,91,6);
INSERT INTO CardTypes VALUES(19018,92,6);
INSERT INTO CardTypes VALUES(19019,93,6);
INSERT INTO CardTypes VALUES(19020,94,6);
INSERT INTO CardTypes VALUES(19021,95,12);
INSERT INTO CardTypes VALUES(19022,96,10);
INSERT INTO CardTypes VALUES(19023,97,7);
INSERT INTO CardTypes VALUES(19024,98,5);
INSERT INTO CardTypes VALUES(19025,99,6);
INSERT INTO CardTypes VALUES(19026,100,6);
CREATE TABLE [Cards] (
[CardID] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,
[SetID] INTEGER  NOT NULL,
[MultiverseID] INTEGER  UNIQUE NOT NULL,
[Layout] VARCHAR(12) DEFAULT '''''''normal''''''' NOT NULL,
[Rarity] VARCHAR(12) DEFAULT '''''''common''''''' NOT NULL,
[Type] VARCHAR(50)  NOT NULL,
[Name] VARCHAR(40)  NOT NULL,
[CMC] INTEGER  NOT NULL,
[Artist] VARCHAR(45)  NOT NULL,
[ManaCost] VARCHAR(40) DEFAULT '''''''NULL''''''' NULL,
[Power] VARCHAR(5) DEFAULT '''''''NULL''''''' NULL,
[Toughness] VARCHAR(5) DEFAULT '''''''NULL''''''' NULL,
[StartingLoyalty] INTEGER DEFAULT '''''''NULL''''''' NULL,
[Text] BLOB DEFAULT '''''''NULL''''''' NULL,
[FlavorText] BLOB DEFAULT '''''''NULL''''''' NULL
);
INSERT INTO Cards VALUES(1,1,130483,'normal','Rare','Enchantment','Abundance',4,'Rebecca Guay','{2}{G}{G}',NULL,NULL,NULL,'If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order.',NULL);
INSERT INTO Cards VALUES(2,1,132072,'normal','Uncommon','Creature - Human Wizard','Academy Researchers',3,'Stephen Daniele','{1}{U}{U}',2,2,NULL,'When Academy Researchers enters the battlefield, you may put an Aura card from your hand onto the battlefield attached to Academy Researchers.','They brandish their latest theories as warriors would wield weapons.');
INSERT INTO Cards VALUES(3,1,129458,'normal','Rare','Land','Adarkar Wastes',0,'John Avon',NULL,NULL,NULL,NULL,'{T}: Add {1} to your mana pool.

{T}: Add {W} or {U} to your mana pool. Adarkar Wastes deals 1 damage to you.',NULL);
INSERT INTO Cards VALUES(4,1,135206,'normal','Common','Instant','Afflict',3,'Roger Raupp','{2}{B}',NULL,NULL,NULL,'Target creature gets -1/-1 until end of turn.

Draw a card.','One rarely notices a heartbeat, save when it is stolen.');
INSERT INTO Cards VALUES(5,1,130525,'normal','Common','Instant','Aggressive Urge',2,'Christopher Moeller','{1}{G}',NULL,NULL,NULL,'Target creature gets +1/+1 until end of turn.

Draw a card.','The power of the wild, concentrated in a single charge.');
INSERT INTO Cards VALUES(6,1,135228,'normal','Uncommon','Sorcery','Agonizing Memories',4,'Adam Rex','{2}{B}{B}',NULL,NULL,NULL,'Look at target player''s hand and choose two cards from it. Put them on top of that player''s library in any order.','In the aftermath of war, when the slaying is long done, the greatest miseries come home to roost.');
INSERT INTO Cards VALUES(7,1,129459,'normal','Uncommon','Creature - Elemental','Air Elemental',5,'Kev Walker','{3}{U}{U}',4,4,NULL,'Flying','"The East Wind, an interloper in the dominions of Westerly Weather, is an impassive-faced tyrant with a sharp poniard held behind his back for a treacherous stab."

—Joseph Conrad, The Mirror of the Sea');
INSERT INTO Cards VALUES(8,1,129913,'normal','Rare','Legendary Creature - Merfolk Wizard','Ambassador Laquatus',3,'Jim Murray','{1}{U}{U}',1,3,NULL,'{3}: Target player puts the top three cards of his or her library into his or her graveyard.','"Life is a game. The only thing that matters is whether you''re a pawn or a player."');
INSERT INTO Cards VALUES(9,1,134753,'normal','Common','Creature - Minotaur','Anaba Bodyguard',4,'Greg Staples','{3}{R}',2,3,NULL,'First strike (This creature deals combat damage before creatures without first strike.)','"They who challenge a minotaur enjoy the taste of their own blood."

—Mirri of the Weatherlight');
INSERT INTO Cards VALUES(10,1,130550,'normal','Uncommon','Creature - Human Cleric','Ancestor''s Chosen',7,'Pete Venters','{5}{W}{W}',4,4,NULL,'First strike (This creature deals combat damage before creatures without first strike.)

When Ancestor''s Chosen enters the battlefield, you gain 1 life for each card in your graveyard.','"The will of all, by my hand done."');
INSERT INTO Cards VALUES(11,1,129465,'normal','Uncommon','Creature - Angel','Angel of Mercy',5,'Volkan Baga','{4}{W}',3,3,NULL,'Flying

When Angel of Mercy enters the battlefield, you gain 3 life.','Every tear shed is a drop of immortality.');
INSERT INTO Cards VALUES(12,1,129466,'normal','Uncommon','Artifact','Angel''s Feather',2,'Alan Pollack','{2}',NULL,NULL,NULL,'Whenever a player casts a white spell, you may gain 1 life.','If taken, it cuts the hand that clutches it. If given, it heals the hand that holds it.');
INSERT INTO Cards VALUES(13,1,129711,'normal','Common','Sorcery','Angelic Blessing',3,'Mark Zug','{2}{W}',NULL,NULL,NULL,'Target creature gets +3/+3 and gains flying until end of turn. (It can''t be blocked except by creatures with flying or reach.)','Only the warrior who can admit mortal weakness will be bolstered by immortal strength.');
INSERT INTO Cards VALUES(14,1,129710,'normal','Rare','Enchantment','Angelic Chorus',5,'Jim Murray','{3}{W}{W}',NULL,NULL,NULL,'Whenever a creature enters the battlefield under your control, you gain life equal to its toughness.','The harmony of the glorious is a dirge to the wicked.');
INSERT INTO Cards VALUES(15,1,129671,'normal','Common','Creature - Wall','Angelic Wall',2,'John Avon','{1}{W}',0,4,NULL,'Defender, flying','"The Ancestor protects us in ways we can''t begin to comprehend."

—Mystic elder');
INSERT INTO Cards VALUES(16,1,130530,'normal','Uncommon','Enchantment - Aura','Arcane Teachings',3,'Dan Dos Santos','{2}{R}',NULL,NULL,NULL,'Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)

Enchanted creature gets +2/+2 and has "{T}: This creature deals 1 damage to target creature or player."','Not all knowledge is learned from parchment.');
INSERT INTO Cards VALUES(17,1,106426,'normal','Rare','Legendary Creature - Wizard','Arcanis the Omnipotent',6,'Justin Sweet','{3}{U}{U}{U}',3,4,NULL,'{T}: Draw three cards.

{2}{U}{U}: Return Arcanis the Omnipotent to its owner''s hand.','"Do not concern yourself with my origin, my race, or my ancestry. Seek my record in the pits, and then make your wager."');
INSERT INTO Cards VALUES(18,1,106525,'normal','Rare','Legendary Creature - Vampire','Ascendant Evincar',6,'Mark Zug','{4}{B}{B}',3,3,NULL,'Flying (This creature can''t be blocked except by creatures with flying or reach.)

Other black creatures get +1/+1. 

Nonblack creatures get -1/-1.','His soul snared by an angel''s curse, Crovax twisted heroism into its purest shadow.');
INSERT INTO Cards VALUES(19,1,135194,'normal','Common','Sorcery','Assassinate',3,'Kev Walker','{2}{B}',NULL,NULL,NULL,'Destroy target tapped creature.','"This is how wars are won—not with armies of soldiers but with a single knife blade, artfully placed."

—Yurin, royal assassin');
INSERT INTO Cards VALUES(20,1,130976,'normal','Uncommon','Instant','Aura Graft',2,'Ray Lago','{1}{U}',NULL,NULL,NULL,'Gain control of target Aura that''s attached to a permanent. Attach it to another permanent it can enchant.','"It''s not really stealing. It''s more like extended borrowing."');
INSERT INTO Cards VALUES(21,1,132127,'normal','Uncommon','Enchantment','Aura of Silence',3,'D. Alexander Gregory','{1}{W}{W}',NULL,NULL,NULL,'Artifact and enchantment spells your opponents cast cost {2} more to cast.

Sacrifice Aura of Silence: Destroy target artifact or enchantment.','Not all silences are easily broken.');
INSERT INTO Cards VALUES(22,1,135249,'normal','Rare','Creature - Avatar','Avatar of Might',8,'rk post','{6}{G}{G}',8,8,NULL,'If an opponent controls at least four more creatures than you, Avatar of Might costs {6} less to cast.

Trample (If this creature would assign enough damage to its blockers to destroy them, you may have it assign the rest of its damage to defending player or planeswalker.)','In their most desperate hour, the elves of Llanowar had one song left to sing.');
INSERT INTO Cards VALUES(23,1,129470,'normal','Common','Creature - Bird Soldier','Aven Cloudchaser',4,'Justin Sweet','{3}{W}',2,2,NULL,'Flying (This creature can''t be blocked except by creatures with flying or reach.) 

When Aven Cloudchaser enters the battlefield, destroy target enchantment.','"At the Reapportionment, Eagle begged to be human. The Ancestor granted half that prayer."

—Nomad myth');
INSERT INTO Cards VALUES(24,1,130985,'normal','Common','Creature - Bird Soldier','Aven Fisher',4,'Christopher Moeller','{3}{U}',2,2,NULL,'Flying (This creature can''t be blocked except by creatures with flying or reach.) 

When Aven Fisher dies, you may draw a card.','The same spears that catch their food today will defend their homes tomorrow.');
INSERT INTO Cards VALUES(25,1,129473,'normal','Common','Creature - Bird Soldier Wizard','Aven Windreader',5,'Greg Hildebrandt','{3}{U}{U}',3,3,NULL,'Flying (This creature can''t be blocked except by creatures with flying or reach.)

{1}{U}: Target player reveals the top card of his or her library.','"The tiniest ripple tells a story ten fathoms deep."');
INSERT INTO Cards VALUES(26,1,129477,'normal','Uncommon','Creature - Human Rebel','Ballista Squad',4,'Matthew D. Wilson','{3}{W}',2,2,NULL,'{X}{W}, {T}: Ballista Squad deals X damage to target attacking or blocking creature.','The perfect antidote for a tightly packed formation.');
INSERT INTO Cards VALUES(27,1,132106,'normal','Common','Instant','Bandage',1,'Rebecca Guay','{W}',NULL,NULL,NULL,'Prevent the next 1 damage that would be dealt to target creature or player this turn.

Draw a card.','Life is measured in inches. To a healer, every one of those inches is precious.');
INSERT INTO Cards VALUES(28,1,129479,'normal','Rare','Land','Battlefield Forge',0,'Darrell Riche',NULL,NULL,NULL,NULL,'{T}: Add {1} to your mana pool.

{T}: Add {R} or {W} to your mana pool. Battlefield Forge deals 1 damage to you.',NULL);
INSERT INTO Cards VALUES(29,1,135262,'normal','Rare','Instant','Beacon of Destruction',5,'Greg Hildebrandt','{3}{R}{R}',NULL,NULL,NULL,'Beacon of Destruction deals 5 damage to target creature or player. Shuffle Beacon of Destruction into its owner''s library.','The Great Furnace''s blessing is a spectacular sight, but the best view comes at a high cost.');
INSERT INTO Cards VALUES(30,1,130553,'normal','Rare','Instant','Beacon of Immortality',6,'Rob Alexander','{5}{W}',NULL,NULL,NULL,'Double target player''s life total. Shuffle Beacon of Immortality into its owner''s library.','The cave floods with light. A thousand rays shine forth and meld into one.');
INSERT INTO Cards VALUES(31,1,135270,'normal','Rare','Sorcery','Beacon of Unrest',5,'Alan Pollack','{3}{B}{B}',NULL,NULL,NULL,'Put target artifact or creature card from a graveyard onto the battlefield under your control. Shuffle Beacon of Unrest into its owner''s library.','A vertical scream pierces the night air and echoes doom through the clouds.');
INSERT INTO Cards VALUES(32,1,136279,'normal','Common','Creature - Human Knight','Benalish Knight',3,'Zoltan Boros & Gabor Szikszai','{2}{W}',2,2,NULL,'Flash (You may cast this spell any time you could cast an instant.)

First strike (This creature deals combat damage before creatures without first strike.)','"We called them ‘armored lightning.''"

—Gerrard of the Weatherlight');
INSERT INTO Cards VALUES(33,1,129906,'normal','Rare','Creature - Bird','Birds of Paradise',1,'Marcelo Vignali','{G}',0,1,NULL,'Flying

{T}: Add one mana of any color to your mana pool.',NULL);
INSERT INTO Cards VALUES(34,1,135267,'normal','Uncommon','Enchantment - Aura','Blanchwood Armor',3,'Parente','{2}{G}',NULL,NULL,NULL,'Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)

Enchanted creature gets +1/+1 for each Forest you control.','"Before armor, there was bark. Before blades, there were thorns."

—Molimo, maro-sorcerer');
INSERT INTO Cards VALUES(35,1,129484,'normal','Uncommon','Sorcery','Blaze',1,'Alex Horley-Orlandelli','{X}{R}',NULL,NULL,NULL,'Blaze deals X damage to target creature or player.',NULL);
INSERT INTO Cards VALUES(36,1,129709,'normal','Rare','Creature - Giant','Bloodfire Colossus',8,'Greg Staples','{6}{R}{R}',6,6,NULL,'{R}, Sacrifice Bloodfire Colossus: Bloodfire Colossus deals 6 damage to each creature and each player.','It took all its strength to contain the fire within.');
INSERT INTO Cards VALUES(37,1,130384,'normal','Common','Creature - Cyclops','Bloodrock Cyclops',3,'Alex Horley-Orlandelli','{2}{R}',3,3,NULL,'Bloodrock Cyclops attacks each turn if able.','"There are only fifty words in the cyclops language, and ten of them mean ‘kill.''"

—Ertai');
INSERT INTO Cards VALUES(38,1,129491,'normal','Uncommon','Creature - Wraith','Bog Wraith',4,'Daarken','{3}{B}',3,3,NULL,'Swampwalk (This creature is unblockable as long as defending player controls a Swamp.)','Knowing Takenuma Swamp to be dangerous, Hisata set wards to warn him of predators. He never imagined that his murderer would pass through them unhindered.');
INSERT INTO Cards VALUES(39,1,130534,'normal','Common','Creature - Elemental Spirit','Bogardan Firefiend',3,'Terese Nielsen','{2}{R}',2,1,NULL,'When Bogardan Firefiend dies, it deals 2 damage to target creature.','"The next one who tells me to relax and curl up by a fire is dead."

—Mirri of the Weatherlight');
INSERT INTO Cards VALUES(40,1,129494,'normal','Common','Instant','Boomerang',2,'Arnie Swekel','{U}{U}',NULL,NULL,NULL,'Return target permanent to its owner''s hand.','Early Jamuraan hunters devised a weapon that would return to its source. Tolarian Æthermancers developed a spell that skipped the weapon entirely.');
INSERT INTO Cards VALUES(41,1,129495,'normal','Uncommon','Artifact Creature - Gnome','Bottle Gnomes',3,'Ben Thompson','{3}',1,3,NULL,'Sacrifice Bottle Gnomes: You gain 3 life.','Reinforcements . . . or refreshments?');
INSERT INTO Cards VALUES(42,1,129496,'normal','Rare','Land','Brushland',0,'Scott Bailey',NULL,NULL,NULL,NULL,'{T}: Add {1} to your mana pool.

{T}: Add {G} or {W} to your mana pool. Brushland deals 1 damage to you.',NULL);
INSERT INTO Cards VALUES(43,1,129882,'normal','Common','Instant','Cancel',3,'Mark Poole','{1}{U}{U}',NULL,NULL,NULL,'Counter target spell.',NULL);
INSERT INTO Cards VALUES(44,1,135185,'normal','Common','Creature - Spider','Canopy Spider',2,'Christopher Rush','{1}{G}',1,3,NULL,'Reach (This creature can block creatures with flying.)','It keeps the upper reaches of the forest free of every menace . . . except for the spider itself.');
INSERT INTO Cards VALUES(45,1,129497,'normal','Rare','Land','Caves of Koilos',0,'Jim Nelson',NULL,NULL,NULL,NULL,'{T}: Add {1} to your mana pool.

{T}: Add {W} or {B} to your mana pool. Caves of Koilos deals 1 damage to you.',NULL);
INSERT INTO Cards VALUES(46,1,135261,'normal','Rare','Creature - Cephalid Wizard','Cephalid Constable',3,'Alan Pollack','{1}{U}{U}',1,1,NULL,'Whenever Cephalid Constable deals combat damage to a player, return up to that many target permanents that player controls to their owners'' hands.','Cephalids don''t police people. They police loyalties.');
INSERT INTO Cards VALUES(47,1,135254,'normal','Rare','Artifact','Chimeric Staff',4,'Michael Sutfin','{4}',NULL,NULL,NULL,'{X}: Chimeric Staff becomes an X/X Construct artifact creature until end of turn.','The staff unraveled in a chaotic chorus of clanging, becoming an improbable beast of hissing blades.');
INSERT INTO Cards VALUES(48,1,130554,'normal','Rare','Legendary Creature - Human Rebel','Cho-Manno, Revolutionary',4,'Steven Belledin','{2}{W}{W}',2,2,NULL,'Prevent all damage that would be dealt to Cho-Manno, Revolutionary.','"Mercadia''s masks can no longer hide the truth. Our day has come at last."');
INSERT INTO Cards VALUES(49,1,135279,'normal','Uncommon','Artifact','Chromatic Star',1,'Alex Horley-Orlandelli','{1}',NULL,NULL,NULL,'{1}, {T}, Sacrifice Chromatic Star: Add one mana of any color to your mana pool.

When Chromatic Star is put into a graveyard from the battlefield, draw a card.',NULL);
INSERT INTO Cards VALUES(50,1,135244,'normal','Rare','Artifact','Citanul Flute',5,'Greg Hildebrandt','{5}',NULL,NULL,NULL,'{X}, {T}: Search your library for a creature card with converted mana cost X or less, reveal it, and put it into your hand. Then shuffle your library.','Each note of the flute mimics the call of a different beast.');
INSERT INTO Cards VALUES(51,1,130522,'normal','Common','Creature - Elf Warrior Druid','Civic Wayfinder',3,'Cyril Van Der Haegen','{2}{G}',2,2,NULL,'When Civic Wayfinder enters the battlefield, you may search your library for a basic land card, reveal it, and put it into your hand. If you do, shuffle your library.','"These alleys are not safe. Come, I can guide you back to the market square."');
INSERT INTO Cards VALUES(52,1,129501,'normal','Rare','Creature - Shapeshifter','Clone',4,'Kev Walker','{3}{U}',0,0,NULL,'You may have Clone enter the battlefield as a copy of any creature on the battlefield.',NULL);
INSERT INTO Cards VALUES(53,1,129804,'normal','Common','Creature - Elemental','Cloud Elemental',3,'Michael Sutfin','{2}{U}',2,3,NULL,'Flying

Cloud Elemental can block only creatures with flying.','The soratami surround their sky-castles with living clouds that serve as both sword and shield.');
INSERT INTO Cards VALUES(54,1,132069,'normal','Common','Creature - Faerie','Cloud Sprite',1,'Mark Zug','{U}',1,1,NULL,'Flying (This creature can''t be blocked except by creatures with flying or reach.)

Cloud Sprite can block only creatures with flying.','The delicate sprites carry messages for Saprazzans, but they refuse to land in Mercadia City''s filthy markets.');
INSERT INTO Cards VALUES(55,1,129502,'normal','Rare','Artifact','Coat of Arms',5,'Scott M. Fischer','{5}',NULL,NULL,NULL,'Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it. (For example, if two Goblin Warriors and a Goblin Shaman are on the battlefield, each gets +2/+2.)','"Hup, two, three, four,

Dunno how to count no more."');
INSERT INTO Cards VALUES(56,1,135268,'normal','Rare','Artifact Creature - Golem','Colossus of Sardia',9,'Greg Staples','{9}',9,9,NULL,'Trample (If this creature would assign enough damage to its blockers to destroy them, you may have it assign the rest of its damage to defending player or planeswalker.)

Colossus of Sardia doesn''t untap during your untap step.

{9}: Untap Colossus of Sardia. Activate this ability only during your upkeep.','Buried under a thin layer of dirt, it was known for centuries as Mount Sardia.');
INSERT INTO Cards VALUES(57,1,130521,'normal','Common','Sorcery','Commune with Nature',1,'Lars Grant-West','{G}',NULL,NULL,NULL,'Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.',NULL);
INSERT INTO Cards VALUES(58,1,135275,'normal','Uncommon','Artifact Creature - Golem','Composite Golem',6,'Mark Tedin','{6}',4,4,NULL,'Sacrifice Composite Golem: Add {W}{U}{B}{R}{G} to your mana pool.','An artificer''s experiment in cross-material engineering found its own purpose as an interpreter between cultures.');
INSERT INTO Cards VALUES(59,1,130528,'normal','Uncommon','Instant','Condemn',1,'Daren Bader','{W}',NULL,NULL,NULL,'Put target attacking creature on the bottom of its owner''s library. Its controller gains life equal to its toughness.','"No doubt the arbiters would put you away, after all the documents are signed. But I will have justice now"

—Alovnek, Boros guildmage');
INSERT INTO Cards VALUES(60,1,130535,'normal','Uncommon','Sorcery','Cone of Flame',5,'Chippy','{3}{R}{R}',NULL,NULL,NULL,'Cone of Flame deals 1 damage to target creature or player, 2 damage to another target creature or player, and 3 damage to a third target creature or player.',NULL);
INSERT INTO Cards VALUES(61,1,129505,'normal','Uncommon','Sorcery','Consume Spirit',2,'Matt Thompson','{X}{1}{B}',NULL,NULL,NULL,'Spend only black mana on X.

Consume Spirit deals X damage to target creature or player and you gain X life.','"Your blood, your marrow, your spirit—all are mine."

—Mayvar, minion of Geth');
INSERT INTO Cards VALUES(62,1,129590,'normal','Common','Enchantment - Aura','Contaminated Bond',2,'Thomas M. Baxa','{1}{B}',NULL,NULL,NULL,'Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)

Whenever enchanted creature attacks or blocks, its controller loses 3 life.','"My favorite curses are those that manifest slowly, blackening the soul in ways the victim cannot hope to understand."

—Erissa, bog witch');
INSERT INTO Cards VALUES(63,1,134757,'normal','Common','Sorcery','Counsel of the Soratami',3,'Randy Gallegos','{2}{U}',NULL,NULL,NULL,'Draw two cards.','"Wisdom is not the counting of all the drops in a waterfall. Wisdom is learning why the water seeks the earth."');
INSERT INTO Cards VALUES(64,1,134758,'normal','Common','Creature - Human Wizard','Crafty Pathmage',3,'Wayne England','{2}{U}',1,1,NULL,'{T}: Target creature with power 2 or less is unblockable this turn.','Follow the pathmage

—Otarian expression meaning

"escape quickly"');
INSERT INTO Cards VALUES(65,1,130527,'normal','Common','Creature - Wurm','Craw Wurm',6,'Richard Sardinha','{4}{G}{G}',6,4,NULL,NULL,'The most terrifying thing about the craw wurm is probably the horrible crashing sound it makes as it speeds through the forest. This noise is so loud it echoes through the trees and seems to come from all directions at once.');
INSERT INTO Cards VALUES(66,1,129512,'normal','Uncommon','Sorcery','Creeping Mold',4,'Gary Ruddell','{2}{G}{G}',NULL,NULL,NULL,'Destroy target artifact, enchantment, or land.','Mold crept over the walls and into every crevice until the gleaming white stone strained and burst.');
INSERT INTO Cards VALUES(67,1,129480,'normal','Rare','Artifact','Crucible of Worlds',3,'Ron Spencer','{3}',NULL,NULL,NULL,'You may play land cards from your graveyard.','Amidst the darkest ashes grow the strongest seeds.');
INSERT INTO Cards VALUES(68,1,129514,'normal','Uncommon','Sorcery','Cruel Edict',2,'Michael Sutfin','{1}{B}',NULL,NULL,NULL,'Target opponent sacrifices a creature.','"Choose your next words carefully. They will be your last."

—Phage the Untouchable');
INSERT INTO Cards VALUES(69,1,129909,'normal','Uncommon','Sorcery','Cryoclasm',3,'Zoltan Boros & Gabor Szikszai','{2}{R}',NULL,NULL,NULL,'Destroy target Plains or Island. Cryoclasm deals 3 damage to that land''s controller.','The people of Terisiare had come to live on frozen fields as though on solid ground. Nothing reminded them of the difference more clearly than the rifts brought on by the Thaw.');
INSERT INTO Cards VALUES(70,1,129910,'normal','Uncommon','Sorcery','Deathmark',1,'Jeremy Jarvis','{B}',NULL,NULL,NULL,'Destroy target green or white creature.','"I hope it''s true that every snowflake is unique, because I never want to see one like this again. Now clean up that body."

—Thangbrand Gyrdsson, Kjeldoran patrol');
INSERT INTO Cards VALUES(71,1,129521,'normal','Common','Enchantment - Aura','Dehydration',4,'Arnie Swekel','{3}{U}',NULL,NULL,NULL,'Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)

Enchanted creature doesn''t untap during its controller''s untap step.','The viashino used the spell to dry foods for crossing the Great Desert. The Suq''Ata later discovered its use in war.');
INSERT INTO Cards VALUES(72,1,135223,'normal','Uncommon','Instant','Deluge',3,'Wayne England','{2}{U}',NULL,NULL,NULL,'Tap all creatures without flying.','"From the sea came all life, and to the sea it will return. The sooner the better."

—Emperor Aboshan');
INSERT INTO Cards VALUES(73,1,129522,'normal','Common','Sorcery','Demolish',4,'Gary Ruddell','{3}{R}',NULL,NULL,NULL,'Destroy target artifact or land.','"Pound the steel until it fits.

Doesn''t work? Bash to bits."

—Dwarven forging song');
INSERT INTO Cards VALUES(74,1,129523,'normal','Uncommon','Artifact','Demon''s Horn',2,'Alan Pollack','{2}',NULL,NULL,NULL,'Whenever a player casts a black spell, you may gain 1 life.','Its curve mimics the twists of life and death.');
INSERT INTO Cards VALUES(75,1,129524,'normal','Common','Instant','Demystify',1,'Christopher Rush','{W}',NULL,NULL,NULL,'Destroy target enchantment.','"Illusion is a crutch for those with no grounding in reality."

—Cho-Manno');
INSERT INTO Cards VALUES(76,1,135250,'normal','Rare','Creature - Serpent','Denizen of the Deep',8,'Jim Pavelec','{6}{U}{U}',11,11,NULL,'When Denizen of the Deep enters the battlefield, return each other creature you control to its owner''s hand.','According to merfolk legend, a denizen of the deep swallows the horizon at the end of each day, bringing on the cold blanket of night.');
INSERT INTO Cards VALUES(77,1,129525,'normal','Uncommon','Sorcery','Diabolic Tutor',4,'Greg Staples','{2}{B}{B}',NULL,NULL,NULL,'Search your library for a card and put that card into your hand. Then shuffle your library.','The best ideas often come from the worst minds.');
INSERT INTO Cards VALUES(78,1,134749,'normal','Uncommon','Instant','Discombobulate',4,'Alex Horley-Orlandelli','{2}{U}{U}',NULL,NULL,NULL,'Counter target spell. Look at the top four cards of your library, then put them back in any order.','"I said ‘pick his brain,'' not ‘tear off his head.''"

—Riptide Project researcher');
INSERT INTO Cards VALUES(79,1,135219,'normal','Common','Sorcery','Distress',2,'Michael Sutfin','{B}{B}',NULL,NULL,NULL,'Target player reveals his or her hand. You choose a nonland card from it. That player discards that card.','"They say the eyes are the windows to the soul. I like to break windows and take what''s inside."

—Braids, dementia summoner');
INSERT INTO Cards VALUES(80,1,129880,'normal','Rare','Creature - Human Cleric Mercenary','Doomed Necromancer',3,'Volkan Baga','{2}{B}',2,2,NULL,'{B}, {T}, Sacrifice Doomed Necromancer: Return target creature card from your graveyard to the battlefield.','Many necromancers share the same last words—the ones that conclude the undeath ritual.');
INSERT INTO Cards VALUES(81,1,135243,'normal','Rare','Artifact','Doubling Cube',2,'Mark Tedin','{2}',NULL,NULL,NULL,'{3}, {T}: Double the amount of each type of mana in your mana pool.','The cube''s surface is pockmarked with jagged runes that seem to shift when unobserved.');
INSERT INTO Cards VALUES(82,1,143024,'normal','Rare','Enchantment','Dragon Roost',6,'Jim Pavelec','{4}{R}{R}',NULL,NULL,NULL,'{5}{R}{R}: Put a 5/5 red Dragon creature token with flying onto the battlefield. (It can''t be blocked except by creatures with flying or reach.)','Dragons erupt from peaks of chaos and flow from rivers of molten rage.');
INSERT INTO Cards VALUES(83,1,129527,'normal','Uncommon','Artifact','Dragon''s Claw',2,'Alan Pollack','{2}',NULL,NULL,NULL,'Whenever a player casts a red spell, you may gain 1 life.','Though no longer attached to the hand, it still holds its adversary in its grasp.');
INSERT INTO Cards VALUES(84,1,135246,'normal','Rare','Creature - Spirit','Dreamborn Muse',4,'Kev Walker','{2}{U}{U}',2,2,NULL,'At the beginning of each player''s upkeep, that player puts the top X cards of his or her library into his or her graveyard, where X is the number of cards in his or her hand.','"Her voice is insight, piercing and true."

—Ixidor, reality sculptor');
INSERT INTO Cards VALUES(85,1,135216,'normal','Common','Creature - Zombie Crocodile','Dross Crocodile',4,'Carl Critchlow','{3}{B}',5,1,NULL,NULL,'"As soon as it surfaced, we could all smell it. Its rancid breath reeked of half-digested carrion and its own rotting innards."

—Dafri, Auriok champion');
INSERT INTO Cards VALUES(86,1,129529,'normal','Uncommon','Creature - Skeleton','Drudge Skeletons',2,'Jim Nelson','{1}{B}',1,1,NULL,'{B}: Regenerate Drudge Skeletons. (The next time this creature would be destroyed this turn, it isn''t. Instead tap it, remove all damage from it, and remove it from combat.)','"The dead make good soldiers. They can''t disobey orders, never surrender, and don''t stop fighting when a random body part falls off."

—Nevinyrral, Necromancer''s Handbook');
INSERT INTO Cards VALUES(87,1,129579,'normal','Common','Creature - Insect','Duct Crawler',1,'Stephen Daniele','{R}',1,1,NULL,'{1}{R}: Target creature can''t block Duct Crawler this turn.','"Boss told us to try and train ''em. Trained it to attack—it ate Flugg. Trained it to run fast—it got away. Success"

—Dlig, goblin spelunker');
INSERT INTO Cards VALUES(88,1,129490,'normal','Common','Creature - Imp','Dusk Imp',3,'Pete Venters','{2}{B}',2,1,NULL,'Flying','Imps are just intelligent enough to have an understanding of cruelty.');
INSERT INTO Cards VALUES(89,1,129554,'normal','Uncommon','Creature - Elemental','Earth Elemental',5,'Anthony S. Waters','{3}{R}{R}',4,5,NULL,NULL,'Earth elementals have the eternal strength of stone and the endurance of mountains. Primordially connected to the land they inhabit, they take a long-term view of things, scorning the impetuous haste of short-lived mortal creatures.');
INSERT INTO Cards VALUES(90,1,135266,'normal','Uncommon','Creature - Elf','Elven Riders',5,'Darrell Riche','{3}{G}{G}',3,3,NULL,'Elven Riders can''t be blocked except by Walls and/or creatures with flying.','"Wirewood cannot hide great size. Only with speed and skill can we survive here."');
INSERT INTO Cards VALUES(91,1,129533,'normal','Common','Creature - Elf Berserker','Elvish Berserker',1,'Parente','{G}',1,1,NULL,'Whenever Elvish Berserker becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.','Their fury scatters enemies like a pile of dry leaves.');
INSERT INTO Cards VALUES(92,1,129534,'normal','Rare','Creature - Elf','Elvish Champion',3,'D. Alexander Gregory','{1}{G}{G}',2,2,NULL,'Other Elf creatures get +1/+1 and have forestwalk. (They''re unblockable as long as defending player controls a Forest.)','"For what are leaves but countless blades

To fight a countless foe on high."

—Elvish hymn');
INSERT INTO Cards VALUES(93,1,129535,'normal','Rare','Creature - Elf Shaman','Elvish Piper',4,'Rebecca Guay','{3}{G}',1,1,NULL,'{G}, {T}: You may put a creature card from your hand onto the battlefield.','From Gaea grew the world, and the world was silent. From Gaea grew the world''s elves, and the world was silent no more.

—Elvish teaching');
INSERT INTO Cards VALUES(94,1,135263,'normal','Uncommon','Creature - Beast','Enormous Baloth',7,'Mark Tedin','{6}{G}',7,7,NULL,NULL,'Its diet consists of fruits, plants, small woodland animals, large woodland animals, woodlands, fruit groves, fruit farmers, and small cities.');
INSERT INTO Cards VALUES(95,1,135191,'normal','Common','Sorcery','Essence Drain',5,'Jim Nelson','{4}{B}',NULL,NULL,NULL,'Essence Drain deals 3 damage to target creature or player and you gain 3 life.','"The elves are right: Death inevitably leads to life. But the truly powerful don''t just experience this cycle. They control it."

—Crovax, ascendant evincar');
INSERT INTO Cards VALUES(96,1,129541,'normal','Rare','Instant','Evacuation',5,'Franz Vohwinkel','{3}{U}{U}',NULL,NULL,NULL,'Return all creatures to their owners'' hands.','"Once I supply the breeze, you''ll see your warriors for the smoke they truly are."

—Alexi, zephyr mage');
INSERT INTO Cards VALUES(97,1,106531,'normal','Uncommon','Land','Faerie Conclave',0,'Stephan Martiniere',NULL,NULL,NULL,NULL,'Faerie Conclave enters the battlefield tapped.

{T}: Add {U} to your mana pool.

{1}{U}: Faerie Conclave becomes a 2/1 blue Faerie creature with flying until end of turn. It''s still a land. (It can''t be blocked except by creatures with flying or reach.)',NULL);
INSERT INTO Cards VALUES(98,1,129544,'normal','Common','Enchantment - Aura','Fear',2,'Adam Rex','{B}{B}',NULL,NULL,NULL,'Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)

Enchanted creature has fear. (It can''t be blocked except by artifact creatures and/or black creatures.)','"Even the bravest of warriors yet knows the dark clutch of fright upon his stalwart heart."

—Lim-Dûl the Necromancer');
INSERT INTO Cards VALUES(99,1,135186,'normal','Uncommon','Creature - Human Archer','Femeref Archers',3,'Zoltan Boros & Gabor Szikszai','{2}{G}',2,2,NULL,'{T}: Femeref Archers deals 4 damage to target attacking creature with flying.','"Bet you can''t put it through the eye."

"Left or right?"');
INSERT INTO Cards VALUES(100,1,129546,'normal','Common','Creature - Zombie Goblin','Festering Goblin',1,'Thomas M. Baxa','{B}',1,1,NULL,'When Festering Goblin dies, target creature gets -1/-1 until end of turn.','In life, it was a fetid, disease-ridden thing. In death, not much changed.');
CREATE TABLE [Colors] (
[ColorID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[Name] VARCHAR(9)  NOT NULL
);
INSERT INTO Colors VALUES(1,'White');
INSERT INTO Colors VALUES(2,'Blue');
INSERT INTO Colors VALUES(3,'Black');
INSERT INTO Colors VALUES(4,'Red');
INSERT INTO Colors VALUES(5,'Green');
CREATE TABLE [Names] (
[NameID] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,
[Name] VARCHAR(30)  NOT NULL
);
INSERT INTO Names VALUES(1,'Order');
INSERT INTO Names VALUES(2,'Chaos');
INSERT INTO Names VALUES(3,'Night');
INSERT INTO Names VALUES(4,'Day');
INSERT INTO Names VALUES(5,'Life');
INSERT INTO Names VALUES(6,'Death');
INSERT INTO Names VALUES(7,'Fire');
INSERT INTO Names VALUES(8,'Ice');
INSERT INTO Names VALUES(9,'Illusion');
INSERT INTO Names VALUES(10,'Reality');
INSERT INTO Names VALUES(11,'Cunning Bandit');
INSERT INTO Names VALUES(12,'Azamuki, Treachery Incarnate');
INSERT INTO Names VALUES(13,'Budoka Pupil');
INSERT INTO Names VALUES(14,'Ichiga, Who Topples Oaks');
INSERT INTO Names VALUES(15,'Callow Jushi');
INSERT INTO Names VALUES(16,'Jaraku the Interloper');
INSERT INTO Names VALUES(17,'Faithful Squire');
INSERT INTO Names VALUES(18,'Kaiso, Memory of Loyalty');
INSERT INTO Names VALUES(19,'Hired Muscle');
INSERT INTO Names VALUES(20,'Scarmaker');
INSERT INTO Names VALUES(21,'Akki Lavarunner');
INSERT INTO Names VALUES(22,'Tok-Tok, Volcano Born');
INSERT INTO Names VALUES(23,'Kitsune Mystic');
INSERT INTO Names VALUES(24,'Autumn-Tail, Kitsune Sage');
INSERT INTO Names VALUES(25,'Budoka Gardener');
INSERT INTO Names VALUES(26,'Dokai, Weaver of Life');
INSERT INTO Names VALUES(27,'Bushi Tenderfoot');
INSERT INTO Names VALUES(28,'Kenzo the Hardhearted');
INSERT INTO Names VALUES(29,'Initiate of Blood');
INSERT INTO Names VALUES(30,'Goka the Unjust');
INSERT INTO Names VALUES(31,'Jushi Apprentice');
INSERT INTO Names VALUES(32,'Tomoya the Revealer');
INSERT INTO Names VALUES(33,'Nezumi Graverobber');
INSERT INTO Names VALUES(34,'Nighteyes the Desecrator');
INSERT INTO Names VALUES(35,'Nezumi Shortfang');
INSERT INTO Names VALUES(36,'Stabwhisker the Odious');
INSERT INTO Names VALUES(37,'Orochi Eggwatcher');
INSERT INTO Names VALUES(38,'Shidako, Broodmistress');
INSERT INTO Names VALUES(39,'Student of Elements');
INSERT INTO Names VALUES(40,'Tobita, Master of Winds');
INSERT INTO Names VALUES(41,'Alive');
INSERT INTO Names VALUES(42,'Well');
INSERT INTO Names VALUES(43,'Armed');
INSERT INTO Names VALUES(44,'Dangerous');
INSERT INTO Names VALUES(45,'Far');
INSERT INTO Names VALUES(46,'Away');
INSERT INTO Names VALUES(47,'Beck');
INSERT INTO Names VALUES(48,'Call');
INSERT INTO Names VALUES(49,'Flesh');
INSERT INTO Names VALUES(50,'Blood');
INSERT INTO Names VALUES(51,'Breaking');
INSERT INTO Names VALUES(52,'Entering');
INSERT INTO Names VALUES(53,'Turn');
INSERT INTO Names VALUES(54,'Burn');
INSERT INTO Names VALUES(55,'Catch');
INSERT INTO Names VALUES(56,'Release');
INSERT INTO Names VALUES(57,'Down');
INSERT INTO Names VALUES(58,'Dirty');
INSERT INTO Names VALUES(59,'Give');
INSERT INTO Names VALUES(60,'Take');
INSERT INTO Names VALUES(61,'Profit');
INSERT INTO Names VALUES(62,'Loss');
INSERT INTO Names VALUES(63,'Protect');
INSERT INTO Names VALUES(64,'Serve');
INSERT INTO Names VALUES(65,'Ready');
INSERT INTO Names VALUES(66,'Willing');
INSERT INTO Names VALUES(67,'Wear');
INSERT INTO Names VALUES(68,'Tear');
INSERT INTO Names VALUES(69,'Toil');
INSERT INTO Names VALUES(70,'Trouble');
INSERT INTO Names VALUES(71,'Bound');
INSERT INTO Names VALUES(72,'Determined');
INSERT INTO Names VALUES(73,'Crime');
INSERT INTO Names VALUES(74,'Punishment');
INSERT INTO Names VALUES(75,'Supply');
INSERT INTO Names VALUES(76,'Demand');
INSERT INTO Names VALUES(77,'Research');
INSERT INTO Names VALUES(78,'Development');
INSERT INTO Names VALUES(79,'Odds');
INSERT INTO Names VALUES(80,'Ends');
INSERT INTO Names VALUES(81,'Trial');
INSERT INTO Names VALUES(82,'Error');
INSERT INTO Names VALUES(83,'Rise');
INSERT INTO Names VALUES(84,'Fall');
INSERT INTO Names VALUES(85,'Hide');
INSERT INTO Names VALUES(86,'Seek');
INSERT INTO Names VALUES(87,'Hit');
INSERT INTO Names VALUES(88,'Run');
INSERT INTO Names VALUES(89,'Pure');
INSERT INTO Names VALUES(90,'Simple');
INSERT INTO Names VALUES(91,'Afflicted Deserter');
INSERT INTO Names VALUES(92,'Werewolf Ransacker');
INSERT INTO Names VALUES(93,'Ravenous Demon');
INSERT INTO Names VALUES(94,'Archdemon of Greed');
INSERT INTO Names VALUES(95,'Chalice of Life');
INSERT INTO Names VALUES(96,'Chalice of Death');
INSERT INTO Names VALUES(97,'Chosen of Markov');
INSERT INTO Names VALUES(98,'Markov''s Servant');
INSERT INTO Names VALUES(99,'Elbrus, the Binding Blade');
INSERT INTO Names VALUES(100,'Withengar Unbound');
INSERT INTO Names VALUES(101,'Soul Seizer');
INSERT INTO Names VALUES(102,'Ghastly Haunting');
INSERT INTO Names VALUES(103,'Hinterland Hermit');
INSERT INTO Names VALUES(104,'Hinterland Scourge');
INSERT INTO Names VALUES(105,'Huntmaster of the Fells');
INSERT INTO Names VALUES(106,'Ravager of the Fells');
INSERT INTO Names VALUES(107,'Wolfbitten Captive');
INSERT INTO Names VALUES(108,'Krallenhorde Killer');
INSERT INTO Names VALUES(109,'Lambholt Elder');
INSERT INTO Names VALUES(110,'Silverpelt Werewolf');
INSERT INTO Names VALUES(111,'Loyal Cathar');
INSERT INTO Names VALUES(112,'Unhallowed Cathar');
INSERT INTO Names VALUES(113,'Mondronen Shaman');
INSERT INTO Names VALUES(114,'Tovolar''s Magehunter');
INSERT INTO Names VALUES(115,'Scorned Villager');
INSERT INTO Names VALUES(116,'Moonscarred Werewolf');
INSERT INTO Names VALUES(117,'Assault');
INSERT INTO Names VALUES(118,'Battery');
INSERT INTO Names VALUES(119,'Stand');
INSERT INTO Names VALUES(120,'Deliver');
INSERT INTO Names VALUES(121,'Spite');
INSERT INTO Names VALUES(122,'Malice');
INSERT INTO Names VALUES(123,'Pain');
INSERT INTO Names VALUES(124,'Suffering');
INSERT INTO Names VALUES(125,'Wax');
INSERT INTO Names VALUES(126,'Wane');
INSERT INTO Names VALUES(127,'Hanweir Watchkeep');
INSERT INTO Names VALUES(128,'Bane of Hanweir');
INSERT INTO Names VALUES(129,'Bloodline Keeper');
INSERT INTO Names VALUES(130,'Lord of Lineage');
INSERT INTO Names VALUES(131,'Civilized Scholar');
INSERT INTO Names VALUES(132,'Homicidal Brute');
INSERT INTO Names VALUES(133,'Cloistered Youth');
INSERT INTO Names VALUES(134,'Unholy Fiend');
INSERT INTO Names VALUES(135,'Daybreak Ranger');
INSERT INTO Names VALUES(136,'Nightfall Predator');
INSERT INTO Names VALUES(137,'Delver of Secrets');
INSERT INTO Names VALUES(138,'Insectile Aberration');
INSERT INTO Names VALUES(139,'Garruk Relentless');
INSERT INTO Names VALUES(140,'Garruk, the Veil-Cursed');
INSERT INTO Names VALUES(141,'Gatstaf Shepherd');
INSERT INTO Names VALUES(142,'Gatstaf Howler');
INSERT INTO Names VALUES(143,'Grizzled Outcasts');
INSERT INTO Names VALUES(144,'Krallenhorde Wantons');
INSERT INTO Names VALUES(145,'Mayor of Avabruck');
INSERT INTO Names VALUES(146,'Howlpack Alpha');
INSERT INTO Names VALUES(147,'Villagers of Estwald');
INSERT INTO Names VALUES(148,'Howlpack of Estwald');
INSERT INTO Names VALUES(149,'Instigator Gang');
INSERT INTO Names VALUES(150,'Wildblood Pack');
INSERT INTO Names VALUES(151,'Village Ironsmith');
INSERT INTO Names VALUES(152,'Ironfang');
INSERT INTO Names VALUES(153,'Kruin Outlaw');
INSERT INTO Names VALUES(154,'Terror of Kruin Pass');
INSERT INTO Names VALUES(155,'Ludevic''s Test Subject');
INSERT INTO Names VALUES(156,'Ludevic''s Abomination');
INSERT INTO Names VALUES(157,'Reckless Waif');
INSERT INTO Names VALUES(158,'Merciless Predator');
INSERT INTO Names VALUES(159,'Tormented Pariah');
INSERT INTO Names VALUES(160,'Rampaging Werewolf');
INSERT INTO Names VALUES(161,'Screeching Bat');
INSERT INTO Names VALUES(162,'Stalking Vampire');
INSERT INTO Names VALUES(163,'Thraben Sentry');
INSERT INTO Names VALUES(164,'Thraben Militia');
INSERT INTO Names VALUES(165,'Ulvenwald Mystics');
INSERT INTO Names VALUES(166,'Ulvenwald Primordials');
INSERT INTO Names VALUES(167,'Boom');
INSERT INTO Names VALUES(168,'Bust');
INSERT INTO Names VALUES(169,'Dead');
INSERT INTO Names VALUES(170,'Gone');
INSERT INTO Names VALUES(171,'Rough');
INSERT INTO Names VALUES(172,'Tumble');
INSERT INTO Names VALUES(173,'Erayo, Soratami Ascendant');
INSERT INTO Names VALUES(174,'Erayo''s Essence');
INSERT INTO Names VALUES(175,'Homura, Human Ascendant');
INSERT INTO Names VALUES(176,'Homura''s Essence');
INSERT INTO Names VALUES(177,'Kuon, Ogre Ascendant');
INSERT INTO Names VALUES(178,'Kuon''s Essence');
INSERT INTO Names VALUES(179,'Rune-Tail, Kitsune Ascendant');
INSERT INTO Names VALUES(180,'Rune-Tail''s Essence');
INSERT INTO Names VALUES(181,'Sasaya, Orochi Ascendant');
INSERT INTO Names VALUES(182,'Sasaya''s Essence');
CREATE TABLE [Sets] (
[SetID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[Name] VARCHAR(30)  NOT NULL,
[Code] VARCHAR(3)  NOT NULL,
[ReleaseDate] VARCHAR(10)  NOT NULL,
[Border] VARCHAR(6)  NOT NULL,
[Block] VARCHAR(25) DEFAULT 'NULL' NULL
);
INSERT INTO Sets VALUES(1,'Tenth Edition','10E','2007-07-13','black','');
INSERT INTO Sets VALUES(2,'Unlimited Edition','2ED','1993-12-01','white','');
INSERT INTO Sets VALUES(3,'Revised Edition','3ED','1994-04-01','white','');
INSERT INTO Sets VALUES(4,'Fourth Edition','4ED','1995-04-01','white','');
INSERT INTO Sets VALUES(5,'Fifth Dawn','5DN','2004-06-04','black','Mirrodin');
INSERT INTO Sets VALUES(6,'Fifth Edition','5ED','1997-03-24','white','');
INSERT INTO Sets VALUES(7,'Classic Sixth Edition','6ED','1999-04-21','white','');
INSERT INTO Sets VALUES(8,'Seventh Edition','7ED','2001-04-11','white','');
INSERT INTO Sets VALUES(9,'Eighth Edition','8ED','2003-07-28','white','');
INSERT INTO Sets VALUES(10,'Ninth Edition','9ED','2005-07-29','white','');
INSERT INTO Sets VALUES(11,'Shards of Alara','ALA','2008-10-03','black','Alara');
INSERT INTO Sets VALUES(12,'Alliances','ALL','1996-06-10','black','Ice Age');
INSERT INTO Sets VALUES(13,'Apocalypse','APC','2001-06-04','black','Invasion');
INSERT INTO Sets VALUES(14,'Alara Reborn','ARB','2009-04-30','black','Alara');
INSERT INTO Sets VALUES(15,'Arabian Nights','ARN','1993-12-01','black','');
INSERT INTO Sets VALUES(16,'Antiquities','ATQ','1994-03-01','black','');
INSERT INTO Sets VALUES(17,'Avacyn Restored','AVR','2012-05-04','black','Innistrad');
INSERT INTO Sets VALUES(18,'Betrayers of Kamigawa','BOK','2005-02-04','black','Kamigawa');
INSERT INTO Sets VALUES(19,'Battle Royale Box Set','BRB','1999-11-12','white','');
INSERT INTO Sets VALUES(20,'Beatdown Box Set','BTD','2000-10-01','white','');
INSERT INTO Sets VALUES(21,'Champions of Kamigawa','CHK','2004-10-01','black','Kamigawa');
INSERT INTO Sets VALUES(22,'Chronicles','CHR','1995-07-01','white','');
INSERT INTO Sets VALUES(23,'Conflux','CON','2009-02-06','black','Alara');
INSERT INTO Sets VALUES(24,'Coldsnap','CSP','2006-07-21','black','Ice Age');
INSERT INTO Sets VALUES(25,'Dragon''s Maze','DGM','2013-05-03','black','Return to Ravnica');
INSERT INTO Sets VALUES(26,'Dissension','DIS','2006-05-05','black','Ravnica');
INSERT INTO Sets VALUES(27,'Dark Ascension','DKA','2012-02-03','black','Innistrad');
INSERT INTO Sets VALUES(28,'The Dark','DRK','1994-08-01','black',NULL);
INSERT INTO Sets VALUES(29,'Darksteel','DST','2004-02-06','black','Mirrodin');
INSERT INTO Sets VALUES(30,'Eventide','EVE','2008-07-25','black','Shadowmoor');
INSERT INTO Sets VALUES(31,'Exodus','EXO','1998-06-15','black','Tempest');
INSERT INTO Sets VALUES(32,'Fallen Empires','FEM','1994-11-01','black',NULL);
INSERT INTO Sets VALUES(33,'Future Sight','FUT','2007-05-04','black','Time Spiral');
INSERT INTO Sets VALUES(34,'Guildpact','GPT','2006-02-03','black','Ravnica');
INSERT INTO Sets VALUES(35,'Gatecrash','GTC','2013-02-01','black','Return to Ravnica');
INSERT INTO Sets VALUES(36,'Homelands','HML','1995-10-01','black',NULL);
INSERT INTO Sets VALUES(37,'Ice Age','ICE','1995-06-01','black','Ice Age');
INSERT INTO Sets VALUES(38,'Invasion','INV','2000-10-02','black','Invasion');
INSERT INTO Sets VALUES(39,'Innistrad','ISD','2011-09-30','black','Innistrad');
INSERT INTO Sets VALUES(40,'Judgment','JUD','2002-05-27','black','Odyssey');
INSERT INTO Sets VALUES(41,'Limited Edition Alpha','LEA','1993-08-05','black',NULL);
INSERT INTO Sets VALUES(42,'Limited Edition Beta','LEB','1993-10-01','black',NULL);
INSERT INTO Sets VALUES(43,'Legends','LEG','1994-06-01','black',NULL);
INSERT INTO Sets VALUES(44,'Legions','LGN','2003-02-03','black','Onslaught');
INSERT INTO Sets VALUES(45,'Lorwyn','LRW','2007-10-12','black','Lorwyn');
INSERT INTO Sets VALUES(46,'Magic 2010','M10','2009-07-17','black',NULL);
INSERT INTO Sets VALUES(47,'Magic 2011','M11','2010-07-16','black',NULL);
INSERT INTO Sets VALUES(48,'Magic 2012','M12','2011-07-15','black',NULL);
INSERT INTO Sets VALUES(49,'Magic 2013','M13','2012-07-13','black',NULL);
INSERT INTO Sets VALUES(50,'Mirrodin Besieged','MBS','2011-02-04','black','Scars of Mirrodin');
INSERT INTO Sets VALUES(51,'Mirage','MIR','1996-10-08','black','Mirage');
INSERT INTO Sets VALUES(52,'Modern Masters','MMA','2013-06-07','black',NULL);
INSERT INTO Sets VALUES(53,'Mercadian Masques','MMQ','1999-10-04','black','Masques');
INSERT INTO Sets VALUES(54,'Morningtide','MOR','2008-02-01','black','Lorwyn');
INSERT INTO Sets VALUES(55,'Mirrodin','MRD','2003-10-02','black','Mirrodin');
INSERT INTO Sets VALUES(56,'Nemesis','NMS','2000-02-14','black','Masques');
INSERT INTO Sets VALUES(57,'New Phyrexia','NPH','2011-05-13','black','Scars of Mirrodin');
INSERT INTO Sets VALUES(58,'Odyssey','ODY','2001-10-01','black','Odyssey');
INSERT INTO Sets VALUES(59,'Onslaught','ONS','2002-10-07','black','Onslaught');
INSERT INTO Sets VALUES(60,'Prophecy','PCY','2000-06-05','black','Masques');
INSERT INTO Sets VALUES(61,'Planar Chaos','PLC','2007-02-02','black','Time Spiral');
INSERT INTO Sets VALUES(62,'Planeshift','PLS','2001-02-05','black','Invasion');
INSERT INTO Sets VALUES(63,'Ravnica: City of Guilds','RAV','2005-10-07','black','Ravnica');
INSERT INTO Sets VALUES(64,'Rise of the Eldrazi','ROE','2010-04-23','black','Zendikar');
INSERT INTO Sets VALUES(65,'Return to Ravnica','RTR','2012-10-05','black','Return to Ravnica');
INSERT INTO Sets VALUES(66,'Scourge','SCG','2003-05-26','black','Onslaught');
INSERT INTO Sets VALUES(67,'Shadowmoor','SHM','2008-05-02','black','Shadowmoor');
INSERT INTO Sets VALUES(68,'Saviors of Kamigawa','SOK','2005-06-03','black','Kamigawa');
INSERT INTO Sets VALUES(69,'Scars of Mirrodin','SOM','2010-10-01','black','Scars of Mirrodin');
INSERT INTO Sets VALUES(70,'Stronghold','STH','1998-03-02','black','Tempest');
INSERT INTO Sets VALUES(71,'Tempest','TMP','1997-10-14','black','Tempest');
INSERT INTO Sets VALUES(72,'Torment','TOR','2002-02-04','black','Odyssey');
INSERT INTO Sets VALUES(73,'Time Spiral','TSP','2006-10-06','black','Time Spiral');
INSERT INTO Sets VALUES(74,'Urza''s Destiny','UDS','1999-06-07','black','Urza''s');
INSERT INTO Sets VALUES(75,'Urza''s Legacy','ULG','1999-02-15','black','Urza''s');
INSERT INTO Sets VALUES(76,'Urza''s Saga','USG','1998-10-12','black','Urza''s');
INSERT INTO Sets VALUES(77,'Visions','VIS','1997-02-03','black','Mirage');
INSERT INTO Sets VALUES(78,'Weatherlight','WTH','1997-06-09','black','Mirage');
INSERT INTO Sets VALUES(79,'Worldwake','WWK','2010-02-05','black','Zendikar');
INSERT INTO Sets VALUES(80,'Zendikar','ZEN','2009-10-02','black','Zendikar');
CREATE TABLE [Subtypes] (
[SubtypeID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[Name] VARCHAR(20)  NOT NULL
);
INSERT INTO Subtypes VALUES(34,'Human');
INSERT INTO Subtypes VALUES(35,'Wizard');
INSERT INTO Subtypes VALUES(36,'Elemental');
INSERT INTO Subtypes VALUES(37,'Merfolk');
INSERT INTO Subtypes VALUES(38,'Minotaur');
INSERT INTO Subtypes VALUES(39,'Cleric');
INSERT INTO Subtypes VALUES(40,'Angel');
INSERT INTO Subtypes VALUES(41,'Wall');
INSERT INTO Subtypes VALUES(42,'Aura');
INSERT INTO Subtypes VALUES(43,'Vampire');
INSERT INTO Subtypes VALUES(44,'Avatar');
INSERT INTO Subtypes VALUES(45,'Bird');
INSERT INTO Subtypes VALUES(46,'Soldier');
INSERT INTO Subtypes VALUES(47,'Rebel');
INSERT INTO Subtypes VALUES(48,'Knight');
INSERT INTO Subtypes VALUES(49,'Giant');
INSERT INTO Subtypes VALUES(50,'Cyclops');
INSERT INTO Subtypes VALUES(51,'Wraith');
INSERT INTO Subtypes VALUES(52,'Spirit');
INSERT INTO Subtypes VALUES(53,'Gnome');
INSERT INTO Subtypes VALUES(54,'Spider');
INSERT INTO Subtypes VALUES(55,'Cephalid');
INSERT INTO Subtypes VALUES(56,'Elf');
INSERT INTO Subtypes VALUES(57,'Warrior');
INSERT INTO Subtypes VALUES(58,'Druid');
INSERT INTO Subtypes VALUES(59,'Shapeshifter');
INSERT INTO Subtypes VALUES(60,'Faerie');
INSERT INTO Subtypes VALUES(61,'Golem');
INSERT INTO Subtypes VALUES(62,'Wurm');
INSERT INTO Subtypes VALUES(63,'Serpent');
INSERT INTO Subtypes VALUES(64,'Mercenary');
INSERT INTO Subtypes VALUES(65,'Zombie');
INSERT INTO Subtypes VALUES(66,'Crocodile');
INSERT INTO Subtypes VALUES(67,'Skeleton');
INSERT INTO Subtypes VALUES(68,'Insect');
INSERT INTO Subtypes VALUES(69,'Imp');
INSERT INTO Subtypes VALUES(70,'Berserker');
INSERT INTO Subtypes VALUES(71,'Shaman');
INSERT INTO Subtypes VALUES(72,'Beast');
INSERT INTO Subtypes VALUES(73,'Archer');
INSERT INTO Subtypes VALUES(74,'Goblin');
INSERT INTO Subtypes VALUES(75,'Mutant');
INSERT INTO Subtypes VALUES(76,'Forest');
INSERT INTO Subtypes VALUES(77,'Dragon');
INSERT INTO Subtypes VALUES(78,'Bear');
INSERT INTO Subtypes VALUES(79,'Horror');
INSERT INTO Subtypes VALUES(80,'Rogue');
INSERT INTO Subtypes VALUES(81,'Crab');
INSERT INTO Subtypes VALUES(82,'Specter');
INSERT INTO Subtypes VALUES(83,'Island');
INSERT INTO Subtypes VALUES(84,'Juggernaut');
INSERT INTO Subtypes VALUES(85,'Barbarian');
INSERT INTO Subtypes VALUES(86,'Yeti');
INSERT INTO Subtypes VALUES(87,'Kavu');
INSERT INTO Subtypes VALUES(88,'Equipment');
INSERT INTO Subtypes VALUES(89,'Shade');
INSERT INTO Subtypes VALUES(90,'Demon');
INSERT INTO Subtypes VALUES(91,'Elephant');
INSERT INTO Subtypes VALUES(92,'Djinn');
INSERT INTO Subtypes VALUES(93,'Cat');
INSERT INTO Subtypes VALUES(94,'Lhurgoyf');
INSERT INTO Subtypes VALUES(95,'Mountain');
INSERT INTO Subtypes VALUES(96,'Assassin');
INSERT INTO Subtypes VALUES(97,'Nightmare');
INSERT INTO Subtypes VALUES(98,'Horse');
INSERT INTO Subtypes VALUES(99,'Nomad');
INSERT INTO Subtypes VALUES(100,'Orc');
INSERT INTO Subtypes VALUES(101,'Thopter');
INSERT INTO Subtypes VALUES(102,'Minion');
INSERT INTO Subtypes VALUES(103,'Illusion');
INSERT INTO Subtypes VALUES(104,'Plains');
INSERT INTO Subtypes VALUES(105,'Dryad');
INSERT INTO Subtypes VALUES(106,'Rat');
INSERT INTO Subtypes VALUES(107,'Masticore');
INSERT INTO Subtypes VALUES(108,'Rhino');
INSERT INTO Subtypes VALUES(109,'Badger');
INSERT INTO Subtypes VALUES(110,'Lizard');
INSERT INTO Subtypes VALUES(111,'Metathran');
INSERT INTO Subtypes VALUES(112,'Drake');
INSERT INTO Subtypes VALUES(113,'Antelope');
INSERT INTO Subtypes VALUES(114,'Swamp');
INSERT INTO Subtypes VALUES(115,'Basilisk');
INSERT INTO Subtypes VALUES(116,'Thrull');
INSERT INTO Subtypes VALUES(117,'Troll');
INSERT INTO Subtypes VALUES(118,'Wolf');
INSERT INTO Subtypes VALUES(119,'Bat');
INSERT INTO Subtypes VALUES(120,'Vedalken');
INSERT INTO Subtypes VALUES(121,'Monk');
INSERT INTO Subtypes VALUES(122,'Viashino');
INSERT INTO Subtypes VALUES(123,'Scout');
INSERT INTO Subtypes VALUES(124,'Griffin');
INSERT INTO Subtypes VALUES(125,'Cockatrice');
INSERT INTO Subtypes VALUES(126,'Dwarf');
INSERT INTO Subtypes VALUES(127,'Fungus');
INSERT INTO Subtypes VALUES(128,'Gargoyle');
INSERT INTO Subtypes VALUES(129,'Ogre');
INSERT INTO Subtypes VALUES(130,'Treefolk');
INSERT INTO Subtypes VALUES(131,'Pegasus');
INSERT INTO Subtypes VALUES(132,'Unicorn');
INSERT INTO Subtypes VALUES(133,'Incarnation');
INSERT INTO Subtypes VALUES(134,'Pirate');
INSERT INTO Subtypes VALUES(135,'Hydra');
INSERT INTO Subtypes VALUES(136,'Plant');
INSERT INTO Subtypes VALUES(137,'Atog');
INSERT INTO Subtypes VALUES(138,'Construct');
INSERT INTO Subtypes VALUES(139,'Artificer');
INSERT INTO Subtypes VALUES(140,'Fish');
INSERT INTO Subtypes VALUES(141,'Ape');
INSERT INTO Subtypes VALUES(142,'Efreet');
INSERT INTO Subtypes VALUES(143,'Kithkin');
INSERT INTO Subtypes VALUES(144,'Manticore');
INSERT INTO Subtypes VALUES(145,'Boar');
INSERT INTO Subtypes VALUES(146,'Turtle');
INSERT INTO Subtypes VALUES(147,'Hound');
INSERT INTO Subtypes VALUES(148,'Leech');
INSERT INTO Subtypes VALUES(149,'Leviathan');
INSERT INTO Subtypes VALUES(150,'Snake');
INSERT INTO Subtypes VALUES(151,'Scorpion');
INSERT INTO Subtypes VALUES(152,'Drone');
INSERT INTO Subtypes VALUES(153,'Bringer');
INSERT INTO Subtypes VALUES(154,'Hellion');
INSERT INTO Subtypes VALUES(155,'Rigger');
INSERT INTO Subtypes VALUES(156,'Myr');
INSERT INTO Subtypes VALUES(157,'Ouphe');
INSERT INTO Subtypes VALUES(158,'Aurochs');
INSERT INTO Subtypes VALUES(159,'Advisor');
INSERT INTO Subtypes VALUES(160,'Frog');
INSERT INTO Subtypes VALUES(161,'Eye');
INSERT INTO Subtypes VALUES(162,'Werewolf');
INSERT INTO Subtypes VALUES(163,'Homarid');
INSERT INTO Subtypes VALUES(164,'Worm');
INSERT INTO Subtypes VALUES(165,'Goat');
INSERT INTO Subtypes VALUES(166,'Orgg');
INSERT INTO Subtypes VALUES(167,'Ooze');
INSERT INTO Subtypes VALUES(168,'Wombat');
INSERT INTO Subtypes VALUES(169,'Urza’s');
INSERT INTO Subtypes VALUES(170,'Mine');
INSERT INTO Subtypes VALUES(171,'Power-Plant');
INSERT INTO Subtypes VALUES(172,'Tower');
INSERT INTO Subtypes VALUES(173,'Wolverine');
INSERT INTO Subtypes VALUES(174,'Nightstalker');
INSERT INTO Subtypes VALUES(175,'Hippo');
INSERT INTO Subtypes VALUES(176,'Elk');
INSERT INTO Subtypes VALUES(177,'Octopus');
INSERT INTO Subtypes VALUES(178,'Rabbit');
INSERT INTO Subtypes VALUES(179,'Carrier');
INSERT INTO Subtypes VALUES(180,'Homunculus');
INSERT INTO Subtypes VALUES(181,'Kraken');
INSERT INTO Subtypes VALUES(182,'Phoenix');
INSERT INTO Subtypes VALUES(183,'Ajani');
INSERT INTO Subtypes VALUES(184,'Elspeth');
INSERT INTO Subtypes VALUES(185,'Sarkhan');
INSERT INTO Subtypes VALUES(186,'Devil');
INSERT INTO Subtypes VALUES(187,'Sphinx');
INSERT INTO Subtypes VALUES(188,'Tezzeret');
INSERT INTO Subtypes VALUES(189,'Phelddagrif');
INSERT INTO Subtypes VALUES(190,'Starfish');
INSERT INTO Subtypes VALUES(191,'Volver');
INSERT INTO Subtypes VALUES(192,'Flagbearer');
INSERT INTO Subtypes VALUES(193,'Camel');
INSERT INTO Subtypes VALUES(194,'Desert');
INSERT INTO Subtypes VALUES(195,'Gremlin');
INSERT INTO Subtypes VALUES(196,'Tamiyo');
INSERT INTO Subtypes VALUES(197,'Tibalt');
INSERT INTO Subtypes VALUES(198,'Arcane');
INSERT INTO Subtypes VALUES(199,'Moonfolk');
INSERT INTO Subtypes VALUES(200,'Samurai');
INSERT INTO Subtypes VALUES(201,'Ninja');
INSERT INTO Subtypes VALUES(202,'Fox');
INSERT INTO Subtypes VALUES(203,'Jellyfish');
INSERT INTO Subtypes VALUES(204,'Soltari');
INSERT INTO Subtypes VALUES(205,'Spike');
INSERT INTO Subtypes VALUES(206,'Whale');
INSERT INTO Subtypes VALUES(207,'Zubera');
INSERT INTO Subtypes VALUES(208,'Shrine');
INSERT INTO Subtypes VALUES(209,'Elder');
INSERT INTO Subtypes VALUES(210,'Slug');
INSERT INTO Subtypes VALUES(211,'Bolas');
INSERT INTO Subtypes VALUES(212,'Centaur');
INSERT INTO Subtypes VALUES(213,'Gate');
INSERT INTO Subtypes VALUES(214,'Weird');
INSERT INTO Subtypes VALUES(215,'Gorgon');
INSERT INTO Subtypes VALUES(216,'Ral');
INSERT INTO Subtypes VALUES(217,'Curse');
INSERT INTO Subtypes VALUES(218,'Sorin');
INSERT INTO Subtypes VALUES(219,'Scarecrow');
INSERT INTO Subtypes VALUES(220,'Hag');
INSERT INTO Subtypes VALUES(221,'Slith');
INSERT INTO Subtypes VALUES(222,'Archon');
INSERT INTO Subtypes VALUES(223,'Noggle');
INSERT INTO Subtypes VALUES(224,'Kor');
INSERT INTO Subtypes VALUES(225,'Salamander');
INSERT INTO Subtypes VALUES(226,'Dauthi');
INSERT INTO Subtypes VALUES(227,'Licid');
INSERT INTO Subtypes VALUES(228,'Thalakos');
INSERT INTO Subtypes VALUES(229,'Praetor');
INSERT INTO Subtypes VALUES(230,'Spellshaper');
INSERT INTO Subtypes VALUES(231,'Fortification');
INSERT INTO Subtypes VALUES(232,'Sliver');
INSERT INTO Subtypes VALUES(233,'Nephilim');
INSERT INTO Subtypes VALUES(234,'Domri');
INSERT INTO Subtypes VALUES(235,'Gideon');
INSERT INTO Subtypes VALUES(236,'Oyster');
INSERT INTO Subtypes VALUES(237,'Ferret');
INSERT INTO Subtypes VALUES(238,'Mongoose');
INSERT INTO Subtypes VALUES(239,'Garruk');
INSERT INTO Subtypes VALUES(240,'Liliana');
INSERT INTO Subtypes VALUES(241,'Kobold');
INSERT INTO Subtypes VALUES(242,'Spawn');
INSERT INTO Subtypes VALUES(243,'Ox');
INSERT INTO Subtypes VALUES(244,'Satyr');
INSERT INTO Subtypes VALUES(245,'Chandra');
INSERT INTO Subtypes VALUES(246,'Jace');
INSERT INTO Subtypes VALUES(247,'Siren');
INSERT INTO Subtypes VALUES(248,'Pest');
INSERT INTO Subtypes VALUES(249,'Brushwagg');
INSERT INTO Subtypes VALUES(250,'Hyena');
INSERT INTO Subtypes VALUES(251,'Dreadnought');
INSERT INTO Subtypes VALUES(252,'Nautilus');
INSERT INTO Subtypes VALUES(253,'Harpy');
INSERT INTO Subtypes VALUES(254,'Monger');
INSERT INTO Subtypes VALUES(255,'Squid');
INSERT INTO Subtypes VALUES(256,'Locus');
INSERT INTO Subtypes VALUES(257,'Sheep');
INSERT INTO Subtypes VALUES(258,'Karn');
INSERT INTO Subtypes VALUES(259,'Squirrel');
INSERT INTO Subtypes VALUES(260,'Mystic');
INSERT INTO Subtypes VALUES(261,'Anteater');
INSERT INTO Subtypes VALUES(262,'Lair');
INSERT INTO Subtypes VALUES(263,'Lammasu');
INSERT INTO Subtypes VALUES(264,'Eldrazi');
INSERT INTO Subtypes VALUES(265,'Surrakar');
INSERT INTO Subtypes VALUES(266,'Vraska');
INSERT INTO Subtypes VALUES(267,'Kirin');
INSERT INTO Subtypes VALUES(268,'Koth');
INSERT INTO Subtypes VALUES(269,'Hippogriff');
INSERT INTO Subtypes VALUES(270,'Venser');
INSERT INTO Subtypes VALUES(271,'Assembly-Worker');
INSERT INTO Subtypes VALUES(272,'Beeble');
INSERT INTO Subtypes VALUES(273,'Sponge');
INSERT INTO Subtypes VALUES(274,'Chimera');
INSERT INTO Subtypes VALUES(275,'Ally');
INSERT INTO Subtypes VALUES(276,'Trap');
INSERT INTO Subtypes VALUES(277,'Nissa');
CREATE TABLE [Supertypes] (
[SupertypeID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[Name] VARCHAR(10)  NOT NULL
);
INSERT INTO Supertypes VALUES(2,'Legendary');
INSERT INTO Supertypes VALUES(3,'Basic');
INSERT INTO Supertypes VALUES(4,'World');
INSERT INTO Supertypes VALUES(5,'Snow');
CREATE TABLE [Types] (
[TypeID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[Name] VARCHAR(15)  NOT NULL
);
INSERT INTO Types VALUES(1,'Plane');
INSERT INTO Types VALUES(2,'Scheme');
INSERT INTO Types VALUES(3,'Vanguard');
INSERT INTO Types VALUES(4,'Artifact');
INSERT INTO Types VALUES(5,'Enchantment');
INSERT INTO Types VALUES(6,'Creature');
INSERT INTO Types VALUES(7,'Land');
INSERT INTO Types VALUES(8,'Planeswalker');
INSERT INTO Types VALUES(9,'Tribal');
INSERT INTO Types VALUES(10,'Instant');
INSERT INTO Types VALUES(12,'Sorcery');
CREATE TABLE sqlite_sequence(name,seq);
INSERT INTO sqlite_sequence VALUES('CardColors',32793);
INSERT INTO sqlite_sequence VALUES('CardNames',364);
INSERT INTO sqlite_sequence VALUES('CardSubtypes',30391);
INSERT INTO sqlite_sequence VALUES('CardSupertypes',2853);
INSERT INTO sqlite_sequence VALUES('CardTypes',37792);
INSERT INTO sqlite_sequence VALUES('Cards',18224);
INSERT INTO sqlite_sequence VALUES('Colors',5);
INSERT INTO sqlite_sequence VALUES('Names',182);
INSERT INTO sqlite_sequence VALUES('Sets',80);
INSERT INTO sqlite_sequence VALUES('Subtypes',277);
INSERT INTO sqlite_sequence VALUES('Supertypes',5);
INSERT INTO sqlite_sequence VALUES('Types',12);
COMMIT;
