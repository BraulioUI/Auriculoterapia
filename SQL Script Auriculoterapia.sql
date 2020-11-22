use auriculoterapiadb;
use m9wogfp1a7afp76v;

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
select * from notificaciones;

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

UPDATE Pacientes SET Edad = 26 WHERE Id = 6;

/*UPDATE Usuarios SET FechaEnvioCorreoConfirmacion = '2020-10-28 17:24:42.613813' WHERE Id = 14;
UPDATE Usuarios SET FechaConfirmacionCodigo = '0001-01-01 00:00:00.000000' WHERE Id = 14;
UPDATE Usuarios SET EmailExist = 1 WHERE Id =18;
SELECT* FROM Usuarios;*/
/*Tipo de atenciones*/

INSERT INTO TipoAtencions(Descripcion) VALUES ('Presencial');
INSERT INTO TipoAtencions(Descripcion) VALUES ('Virtual');
SELECT * FROM TipoAtencions;



INSERT INTO CITAS(PacienteId,Fecha,HoraInicioAtencion,HoraFinAtencion,Estado, TipoAtencionId) 
VALUES(1, '2020-08-24', '2020-08-24 09:00:00', '2020-08-24 09:30:00', 'En proceso', 2);

DELETE FROM CITAS WHERE Id > 1 AND Id <= 5;
ALTER TABLE CITAS AUTO_INCREMENT = 1;
SELECT * FROM Citas;
DELETE FROM Citas Where Id = 52;

UPDATE CITAS SET Estado = "En Proceso" WHERE Id = 48;

UPDATE USUARIOS SET Email = "pruebasmndrd@gmail.com" Where Id = 1;

SELECT* FROM SOLICITUDTRATAMIENTOS;
INSERT INTO SOLICITUDTRATAMIENTOS(Edad,Peso,Altura,Sintomas,ImagenAreaAfectada,OtrosSintomas,Estado,fechaInicio,PacienteId)
VALUES(20,60.9,1.70,"DOLOR LUMBAR","imagen.jpg","Dolor de cabeza","En proceso","2020-09-09",1);

update solicitudtratamientos set ImagenAreaAfectada = 'http://res.cloudinary.com/dyifsbjuf/image/upload/v1599423450/vgnzh4wmpn5d9xuniehu.jpg' WHERE Id = 2;
update solicitudtratamientos set Sintomas = 'Dolor lumbar' WHERE Id = 2;


delete from solicitudtratamientos where Id = 2;
ALTER TABLE solicitudtratamientos AUTO_INCREMENT = 1;
INSERT INTO solicitudtratamientos(Edad,Peso,Altura,Sintomas,ImagenAreaAfectada,OtrosSintomas,PacienteId,Estado,fechaInicio) 
VALUES(26, 67, 1.71, 'Malestar lumbar', 'sdgdsgdsagsdag', 'Me duele la cabeza y tengo mucha ansiedad', 1,"En proceso", "2020-08-31");
select * from solicitudtratamientos;
DELETE FROM solicitudtratamientos WHERE id = 2;
ALTER TABLE solicitudtratamientos AUTO_INCREMENT = 1;
UPDATE solicitudtratamientos SET Estado = "En proceso" where Id = 2;
SELECT * FROM DISPONIBILIDADES;
SELECT * FROM HORARIOSDESCARTADOS;

DELETE FROM HORARIOSDESCARTADOS WHERE DisponibilidadId = 5;
DELETE FROM DISPONIBILIDADES WHERE Id = 5;
select * from disponibilidades;
ALTER TABLE DISPONIBILIDADES AUTO_INCREMENT = 1;
ALTER TABLE tratamientos AUTO_INCREMENT = 1;
SELECT * FROM tratamientos;
DELETE FROM tratamientos where id = 4;
UPDATE tratamientos set ImagenEditada = "http://res.cloudinary.com/dyifsbjuf/image/upload/v1600646069/wwzvqizz4voddcsoxpoe.jpg" where Id = 1;
DELETE FROM tratamientos where SolicitudTratamientoId = 2;
UPDATE tratamientos SET Id = 1 WHERE Id = 2;


select* from evoluciones;

INSERT INTO tratamientos(TipoTratamiento,FechaInicio,FechaFin,FrecuenciaAlDia,TiempoPorTerapia,SolicitudTratamientoId,FechaEnvio,ImagenEditada,Estado)
VALUES("Dolor lumbar","2020-09-07", "2020-09-14", 5, 10, 2, "2020-09-07", "asdgsdgdgds.jpg", "En Proceso" );

SELECT DISTINCT COUNT(u.Id) FROM Usuarios u JOIN Pacientes p ON p.UsuarioId = u.Id JOIN SolicitudTratamientos s ON p.Id = s.PacienteId
JOIN Tratamientos t ON t.SolicitudTratamientoId = s.Id WHERE u.Sexo = "Femenino";

SELECT p.Id from tratamientos t 
JOIN solicitudtratamientos s 
ON s.Id = t.SolicitudTratamientoId
JOIN pacientes p
ON p.Id = s.PacienteId 
WHERE p.Edad between 18 and 30;

select * from evoluciones;


select e.tipoTratamiento,e.Sesion, s.PacienteId, e.EvolucionNumero from evoluciones e 
join tratamientos t on e.TratamientoId = t.Id		
join solicitudtratamientos s ON t.SolicitudTratamientoId = s.Id
JOIN Pacientes p on p.Id = s.PacienteId
order by e.TipoTratamiento desc, e.Sesion desc;

select e.Sesion, e.TipoTratamiento, s.PacienteId from evoluciones e
join tratamientos t on e.TratamientoId = t.Id		
join solicitudtratamientos s ON t.SolicitudTratamientoId = s.Id
JOIN Pacientes p on p.Id = s.PacienteId;

select p.Id from evoluciones e
JOIN tratamientos t ON t.Id = e.TratamientoId
JOIN solicitudtratamientos
join solicitudtratamientos s ON t.SolicitudTratamientoId = s.Id
JOIN Pacientes p on p.Id = s.PacienteId;

DELIMITER //
CREATE PROCEDURE obtenerPacientesYUltimaSesion(IN sexo longtext, IN tipoTratamiento longtext)
BEGIN
select p.Id, MAX(e.Sesion), p.FechaNacimiento from evoluciones e 
INNER JOIN tratamientos t ON t.Id = e.TratamientoId
INNER JOIN solicitudtratamientos s ON t.SolicitudTratamientoId = s.Id
INNER JOIN pacientes p ON p.Id = s.PacienteId
INNER join usuarios u ON p.UsuarioId = u.Id
WHERE u.Sexo = "Masculino"
AND e.tipoTratamiento = "Obesidad"
group by p.Id;
END //
DELIMITER ;

CALL obtenerPacientesYUltimaSesion("Femenino", "Obesidad");

ALTER TABLE Notificaciones AUTO_INCREMENT = 1;
DELETE FROM Notificaciones WHERE Id = 2;
SELECT * FROM Notificaciones;
UPDATE Notificaciones SET Leido = 1 WHERE Id = 7;
