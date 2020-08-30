using System;
using Auriculoterapia.Api.Domain;

namespace Auriculoterapia.Api.Helpers
{
    public class ResponseUsuarioById
    {
        public int Id {get; set;}
        public string Nombre {get; set;}
        public string Apellido {get; set;}
        public string Email {get; set;}
        public string Contrasena {get; set;}
        public string NombreUsuario {get; set;}
        public string Sexo {get; set;}
        public string PalabraClave {get; set;}

        public DateTime? FechaNacimiento {get; set;}
        //public Paciente Paciente {get; set;}
        //public Especialista Especialista {get; set;}

        public ResponseUsuarioById(int Id,string Nombre,string Apellido,string Email,string Contrasena,string nombreUsuario,string Sexo,string PalabraClave,DateTime? fechaNacimiento){
            this.Id = Id;
            this.Nombre = Nombre;
            this.Apellido = Apellido;
            this.Email = Email;
            this.NombreUsuario = nombreUsuario;
            this.PalabraClave = PalabraClave;
            this.Contrasena = Contrasena;
            this.Sexo = Sexo;
            this.FechaNacimiento = fechaNacimiento;
            //this.Paciente =paciente;
            //this.Especialista = especialista;
        }
    }
}