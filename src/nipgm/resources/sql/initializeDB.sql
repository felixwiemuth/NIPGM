create table properties(
    pkey varchar(25) primary key,
    pvalue varchar(200)
);

insert into properties values ('database_scheme_version', '0.9');

create table categories(
    id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name varchar(25) not null,
    description varchar(200),
    parentCat int, -- NULL indicates that there is no parent category
    foreign key(parentCat) references categories(id) -- null values are OK!
);

create table questions(
    id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    question varchar(100) not null,
    answer varchar(100) not null,
    catID int not null,
    foreign key(catID) references categories(id)
);

create table players(
    id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name varchar(25) not null
);

create table games(
    id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    date_played timestamp default current timestamp
);

create table game_questions(
    game_id int not null,
    question_id int not null,
    primary key(game_id, question_id),
    foreign key(game_id) references games(id),
    foreign key(question_id) references questions(id)
);

create table game_players(
    game_id int not null,
    player_id int not null,
    credits int not null,
    primary key(game_id, player_id),
    foreign key(game_id) references games(id),
    foreign key(player_id) references players(id)
);