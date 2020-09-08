use auriculoterapiadb;
use m9wogfp1a7afp76v;
aadsgfsdg
/* por si me equivoco al poner el ejecutar */
/*ROL 1 - ESPECIALISTA*/
INSERT INTO Roles(Descripcion) VALUES ('ESPECIALISTA');
/*ROL 2 - PACIENTE*/
INSERT INTO Roles(Descripcion) VALUES ('PACIENTE');


SELECT * FROM Roles;

/*USUARIO 1 - ESPECIALISTA*/
INSERT INTO Usuarios(Nombre,Apellido,Email,Contrasena,NombreUsuario,Sexo,PalabraClave,Token)
VALUES ('Paul', 'Alejos', 'palejos@gmail.com', 'auriculoterapia', 'palejos', 'Masculino', 'Marmota', '1553215dwsagwavgwaeg');

/*USUARIO 2 - PACIENTE*/
INSERT INTO Usuarios(Nombre,Apellido,Email,Contrasena,NombreUsuario,Sexo,PalabraClave,Token)
VALUES ('Marcos Alonso', 'Rivas Torres', 'marcosrt@gmail.com', 'primerpaciente', 'marcosrt', 'Masculino', 'Marmota', '46584658sdfghsadgh');
INSERT INTO Usuarios(Nombre,Apellido,Email,Contrasena,NombreUsuario,Sexo,PalabraClave,Token)
VALUES ('Julio Alonso', 'Alvarado Reynoso', 'julioa@gmail.com', 'julioa', 'julioa', 'Masculino', 'Perro', 'asdgadsg584658sdfghsadgh');

SELECT * FROM Usuarios;
DELETE FROM Usuarios Where Id = 5;
ALTER TABLE Usuarios auto_increment=1;
SELECT * FROM Rol_Usuarios;
SELECT * FROM Pacientes;
DELETE FROM Rol_Usuarios Where Id = 5;
ALTER TABLE Rol_Usuarios auto_increment=1;
SELECT * FROM especialistas;
/*ROL_USUARIOS 1*/
INSERT INTO Rol_Usuarios(RolId,UsuarioId) VALUES(1, 1);

/*ROL_USUARIOS 2*/
INSERT INTO Rol_Usuarios(RolId,UsuarioId) VALUES(2, 2);
INSERT INTO Rol_Usuarios(RolId,UsuarioId) VALUES(2, 3);
INSERT INTO Rol_Usuarios(RolId,UsuarioId) VALUES(2, 4);

/*ESPECIALISTA 1 - USUARIO 1*/
INSERT INTO ESPECIALISTAS(AnhoExperiencia,UsuarioId) VALUES (15, 1);

/*PACIENTE 1 - USUARIO 2*/
INSERT INTO Pacientes(FechaNacimiento,Celular,UsuarioId) VALUES ('1994-07-15', '999645223', 2);
INSERT INTO Pacientes(FechaNacimiento,Celular,UsuarioId) VALUES ('1994-07-15', '955645443', 3);

SELECT * FROM Especialistas;
SELECT * FROM Pacientes;
SELECT * FROM Rol_Usuarios;

/*Tipo de atenciones*/

INSERT INTO TipoAtencions(Descripcion) VALUES ('Presencial');
INSERT INTO TipoAtencions(Descripcion) VALUES ('Virtual');
SELECT * FROM TipoAtencions;



INSERT INTO CITAS(PacienteId,Fecha,HoraInicioAtencion,HoraFinAtencion,Estado, TipoAtencionId) 
VALUES(1, '2020-08-24', '2020-08-24 09:00:00', '2020-08-24 09:30:00', 'En proceso', 2);

DELETE FROM CITAS WHERE Id > 1 AND Id <= 5;
ALTER TABLE CITAS AUTO_INCREMENT = 1;
SELECT * FROM Citas;

UPDATE CITAS SET Estado = "En Proceso" WHERE Id >= 1;


SELECT* FROM SOLICITUDTRATAMIENTOS;
INSERT INTO SOLICITUDTRATAMIENTOS(Id,Edad,Peso,Altura,Sintomas,ImagenAreaAfectada,OtrosSintomas,Estado,fechaInicio,PacienteId)
VALUES(3,20,60.9,1.70,"DOLOR LUMBAR","imagen.jpg","Dolor de cabeza","En proceso","2020-08-28",4);


delete from solicitudtratamientos where Id = 2;
ALTER TABLE solicitudtratamientos AUTO_INCREMENT = 1;
INSERT INTO solicitudtratamientos(Edad,Peso,Altura,Sintomas,ImagenAreaAfectada,OtrosSintomas,PacienteId,Estado,fechaInicio) 
VALUES(26, 67, 1.71, 'Malestar lumbar', 'sdgdsgdsagsdag', 'Me duele la cabeza y tengo mucha ansiedad', 1,"En proceso", "2020-08-31");
select * from solicitudtratamientos;
DELETE FROM solicitudtratamientos WHERE id = 2;
ALTER TABLE solicitudtratamientos AUTO_INCREMENT = 1;

SELECT * FROM DISPONIBILIDADES;
SELECT * FROM HORARIOSDESCARTADOS;

DELETE FROM HORARIOSDESCARTADOS WHERE DisponibilidadId = 5;
DELETE FROM DISPONIBILIDADES WHERE Id = 5;

ALTER TABLE DISPONIBILIDADES AUTO_INCREMENT = 1;
ALTER TABLE tratamientos AUTO_INCREMENT = 1;
SELECT * FROM tratamientos;
UPDATE tratamientos SET Id = 1 WHERE Id = 2;