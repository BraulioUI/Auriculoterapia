using System;

namespace Auriculoterapia.Domain
{
    public class Usuario 
    {
        public int Id {get; set;}
        public string Nombres {get; set;}
        public string Apellidos {get; set;}
        public string Email {get; set;}
        public string Contrasena {get; set;}
        public string NombreUsuario {get; set;}
        public string Sexo {get; set;}
        public string PalabraClave {get; set;}

        //OneToOne Relationship
        public int PacienteId {get; set;}
        public Paciente Paciente {get; set;}

        public int EspecialistaId {get; set;}
        public Especialista Especialista {get; set;}
    }
}
