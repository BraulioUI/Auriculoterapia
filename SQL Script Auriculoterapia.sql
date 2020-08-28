use auriculoterapiadb;

/*ROL 1 - ESPECIALISTA*/
INSERT INTO roles(Descripcion) VALUES ('ESPECIALISTA');
/*ROL 2 - PACIENTE*/
INSERT INTO roles(Descripcion) VALUES ('PACIENTE');


SELECT * FROM ROLES;

/*USUARIO 1 - ESPECIALISTA*/
INSERT INTO USUARIOS(Nombre,Apellido,Email,Contrasena,NombreUsuario,Sexo,PalabraClave,Token)
VALUES ('Paul', 'Alejos', 'palejos@gmail.com', 'auriculoterapia', 'palejos', 'Masculino', 'Marmota', '1553215dwsagwavgwaeg');

/*USUARIO 2 - PACIENTE*/
INSERT INTO USUARIOS(Nombre,Apellido,Email,Contrasena,NombreUsuario,Sexo,PalabraClave,Token)
VALUES ('Marcos Alonso', 'Rivas Torres', 'marcosrt@gmail.com', 'primerpaciente', 'marcosrt', 'Masculino', 'Marmota', '46584658sdfghsadgh');
INSERT INTO USUARIOS(Nombre,Apellido,Email,Contrasena,NombreUsuario,Sexo,PalabraClave,Token)
VALUES ('Julio Alonso', 'Alvarado Reynoso', 'julioa@gmail.com', 'julioa', 'julioa', 'Masculino', 'Perro', 'asdgadsg584658sdfghsadgh');
INSERT INTO USUARIOS(Nombre,Apellido,Email,Contrasena,NombreUsuario,Sexo,PalabraClave,Token)
VALUES ('Braulio Martin', 'Uribe Iraola', 'brauliou@gmail.com', 'brauliou', 'brauliou', 'Masculino', 'Gato', 'asddsag8sdfghsadgh');
SELECT * FROM USUARIOS;

/*ROL_USUARIOS 1*/
INSERT INTO ROL_USUARIOS(RolId,UsuarioId) VALUES(1, 1);

/*ROL_USUARIOS 2*/
INSERT INTO ROL_USUARIOS(RolId,UsuarioId) VALUES(2, 2);

/*ESPECIALISTA 1 - USUARIO 1*/
INSERT INTO ESPECIALISTAS(AnhoExperiencia,UsuarioId) VALUES (15, 1);

/*PACIENTE 1 - USUARIO 2*/
INSERT INTO PACIENTES(FechaNacimiento,Celular,UsuarioId) VALUES ('1994-07-15', '999645223', 2);
INSERT INTO PACIENTES(FechaNacimiento,Celular,UsuarioId) VALUES ('1994-07-15', '955645443', 3);
INSERT INTO PACIENTES(FechaNacimiento,Celular,UsuarioId) VALUES ('2000-03-11', null, 4);

SELECT * FROM ESPECIALISTAS;
SELECT * FROM PACIENTES;

/*Tipo de atenciones*/
INSERT INTO TIPOATENCIONS(Descripcion) VALUES ('Presencial');
INSERT INTO TIPOATENCIONS(Descripcion) VALUES ('Virtual');
SELECT * FROM TIPOATENCIONS;

INSERT INTO CITAS(PacienteId,Fecha,HoraInicioAtencion,HoraFinAtencion,Estado, TipoAtencionId) 
VALUES(1, '2020-08-24', '2020-08-24 09:00:00', '2020-08-24 09:30:00', 'En proceso', 2);

DELETE FROM CITAS WHERE Id > 1 AND Id <= 5;
ALTER TABLE CITAS AUTO_INCREMENT = 1;
SELECT * FROM CITAS;


