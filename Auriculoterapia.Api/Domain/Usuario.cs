using Newtonsoft.Json;

namespace Auriculoterapia.Api.Domain
{
    public class Usuario 
    {
        public int Id {get; set;}
        public string Nombre {get; set;}
        public string Apellido {get; set;}
        public string Email {get; set;}
        public string Contrasena {get; set;}
        public string NombreUsuario {get; set;}
        public string Sexo {get; set;}
        public string PalabraClave {get; set;}
        public string Token {get; set;}

        //OneToOne Relationship
        //public int PacienteId {get; set;}
        [JsonIgnore]
        public Paciente Paciente {get; set;}

        //public int EspecialistaId {get; set;}
        [JsonIgnore]
        public Especialista Especialista {get; set;}
        
    }
}