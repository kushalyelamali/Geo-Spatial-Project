CREATE TABLE Buildings (
  building_id  VARCHAR2(32) PRIMARY KEY,
  building_name VARCHAR2(32),
  vertices_num int,
  building_coordinates SDO_GEOMETRY);
  
  
CREATE TABLE students (student_id VARCHAR(40) PRIMARY KEY, point SDO_GEOMETRY);
 
CREATE TABLE AnounSys(as_id VARCHAR(40) PRIMARY KEY,Anoun_coordinate SDO_GEOMETRY,radius INT);


insert into user_sdo_geom_metadata values('buildings', 'building_coordinates',SDO_DIM_ARRAY(SDO_DIM_ELEMENT('X',0,820,0.001),SDO_DIM_ELEMENT('Y',0,580,0.001)),NULL);
insert into user_sdo_geom_metadata values('students','point',SDO_DIM_ARRAY(SDO_DIM_ELEMENT('X',0,820,0.001),SDO_DIM_ELEMENT('Y',0,580,0.001)),NULL);
insert into user_sdo_geom_metadata values('AnounSys', 'Anoun_coordinate',SDO_DIM_ARRAY(SDO_DIM_ELEMENT('X',0,820,0.001),SDO_DIM_ELEMENT('Y',0,580,0.001)),NULL);



create index student_index ON students(point) INDEXTYPE IS MDSYS.SPATIAL_INDEX;
create index building_index ON Buildings(building_coordinates) INDEXTYPE IS MDSYS.SPATIAL_INDEX;
create index anoun_idx ON AnounSys(Anoun_coordinate) INDEXTYPE IS MDSYS.SPATIAL_INDEX;




 
 
  
  
