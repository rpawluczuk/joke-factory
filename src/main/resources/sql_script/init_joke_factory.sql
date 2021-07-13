create table if not exists mydb.joke
(
    id           bigint auto_increment,
    structure_id bigint,
    author_id    bigint,
    title        varchar(255),
    content      varchar(3000),
    grade        int,
    author       varchar(255),
    date_created datetime,
    last_updated datetime,
    constraint PK_Joke primary key (id),
    constraint FK_JokeStructure foreign key (structure_id) references structure (id),
    constraint FK_JokeAuthor foreign key (author_id) references author (id)
);
create index INDX_structure_id on mydb.joke(structure_id ASC);
create index INDX_author_id on mydb.joke(author_id ASC);

create table if not exists mydb.structure
(
    id           bigint auto_increment,
    name         varchar(255),
    description  varchar(2000),
    date_created datetime,
    last_updated datetime,
    constraint PK_Structure primary key (id)
);

create table if not exists mydb.jokes_structures
(
    joke_id      bigint,
    structure_id bigint,
    foreign key (joke_id) references joke (id),
    foreign key (structure_id) references structure (id)
);

create table if not exists mydb.author
(
    id           bigint auto_increment,
    name         varchar(255),
    surname      varchar(255),
    description  varchar(2000),
    date_created datetime,
    last_updated datetime,
    constraint PK_Author primary key (id)
);

create table if not exists mydb.block
(
    id           bigint auto_increment,
    blockType    varchar(255),
    position     int,
    title        varchar(255),
    description  varchar(2000),
    date_created datetime,
    last_updated datetime,
    constraint PK_Block primary key (id)
);

create table block
(
    id           bigint auto_increment primary key,
    block_type   varchar(255) null,
    date_created datetime     null,
    description  varchar(255) null,
    last_updated datetime     null,
    position     int          not null,
    title        varchar(255) null,
    structure_id bigint       null
);