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

SELECT * FROM USUARIOS;

/*ROL_USUARIOS 1*/
INSERT INTO ROL_USUARIOS(RolId,UsuarioId) VALUES(1, 1);

/*ROL_USUARIOS 2*/
INSERT INTO ROL_USUARIOS(RolId,UsuarioId) VALUES(2, 2);

/*ESPECIALISTA 1 - USUARIO 1*/
INSERT INTO ESPECIALISTAS(AnhoExperiencia,UsuarioId) VALUES (15, 1);

/*PACIENTE 1 - USUARIO 2*/
INSERT INTO PACIENTES(FechaNacimiento,Celular,UsuarioId) VALUES ('1994-07-15', '999645223', 2);

SELECT * FROM ESPECIALISTAS;
SELECT * FROM PACIENTES;


INSERT INTO CITAS(PacienteId,Fecha,HoraInicioAtencion,HoraFinAtencion,Estado) 
VALUES(1, '2020-08-24', '0000-00-00 09:00:00', '0000-00-00 09:30:00', 'En proceso');

DELETE FROM CITAS WHERE Id = 1;
ALTER TABLE CITAS AUTO_INCREMENT = 1;
SELECT * FROM CITAS;


