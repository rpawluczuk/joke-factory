drop table if exists mydb.joke_copy_for_test;
create table joke_copy_for_test
as
select * from mydb.joke;

drop table if exists mydb.joke_block_copy_for_test;
create table joke_block_copy_for_test
as
select * from mydb.joke_block;

drop table if exists mydb.structure_copy_for_test;
create table structure_copy_for_test
as
select * from mydb.structure;

drop table if exists mydb.structure_block_copy_for_test;
create table structure_block_copy_for_test
as
select * from mydb.structure_block;

drop table if exists mydb.author_copy_for_test;
create table author_copy_for_test
as
select * from mydb.author;

drop table if exists mydb.topic_copy_for_test;
create table origin_copy_for_test
as
select * from mydb.topic;

drop table if exists mydb.jokes_structures_copy_for_test;
create table jokes_structures_copy_for_test
as
select * from mydb.jokes_structures;