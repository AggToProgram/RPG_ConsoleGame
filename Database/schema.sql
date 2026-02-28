create table players (
    id int PRIMARY KEY auto_increment,
    playerName varchar(50) not null,
    hp int not null,
    dmg int not null,
    descript text not null,
    mana int not null,
    gold int not null,
    lvl int not null,
    room_id int,
    exp int not null,
    FOREIGN KEY (room_id) references rooms(id)
);

create table rooms (
    id int PRIMARY KEY auto_increment,
    roomName varchar(50) not null,
    descript text not null,
    requiredLvl int not null
);

insert into rooms(roomName, descript, requiredLvl)
values
    ("Entrance of the dungeon", "The beginning of your adventure", 1),
    ("The room of the living", "A greatly lit room full of statues of people, seemingly happy", 1),
    ("The room of the martyrs", "A gently lit room, almost identical to the last one, but the statues appear to be in pain", 3),
    ("The room of the corpses", "A dark room whose only source of light being a candle. The statues are lifeless and destroyed", 5),
    ("The room of the spirits", "A fully dark room, the statues are gone, but you are not alone", 7),
    ("The room of the Hellborn", "The statues have returned in the form of demons. They are watching you. They are watching you.", 12)
;

create table monsters (
    id int PRIMARY KEY auto_increment,
    monsterName varchar(50) not null,
    hp int not null,
    dmg int not null,
    descript text not null,

    monsterType varchar(50) not null,
    ogHp int not null
);

insert into monsters (monsterName, hp, dmg, descript, monsterType, ogHp)
values
    ("Kalashnikov", 100, 10, "A weak monster used to being tormented", "Normal", 100),
    ("Adamovich", 150, 35, "Kalashnikov's older brother, he is mad at you!", "Fire", 150),
    ("Karelia", 250, 50, "He will make sure you abandon this place as a ghost", "Ice", 250),
    ("Krasnodar", 500, 100, "The final boss, be careful, he is powerful!", "Thunder", 500)
;

create table items (
    id int PRIMARY KEY auto_increment,
    itemName varchar(50) not null,
    descript text not null,
    itemType varchar(50) not null,
    dmg int not null,

    manaDmg int,
    buffType varchar(50)
);

insert into items (itemName, descript, itemType, dmg, manaDmg, buffType)
values
    ("Sword of the living", "Looks like a normal sword to me!", "Sword", 10, 0, null),
    ("Sword of the Undead", "A sword blessed by the heavenly Gods", "Sword", 50, 10, null),
    ("Potion of healing", "A potion that heals", "Potion", 50, null, "Healing"),
    ("Potion of the fallen angel", "Heals/adds 100hp", "Potion", 100, null, "Healing")
;

create table playerInventory (
    player_id int,
    item_id int,
    PRIMARY KEY (player_id, item_id),
    FOREIGN KEY (player_id) references players(id) on delete cascade,
    FOREIGN KEY (item_id) references items(id)
);