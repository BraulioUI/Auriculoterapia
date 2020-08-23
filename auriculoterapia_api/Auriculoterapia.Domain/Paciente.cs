using System;

namespace Auriculoterapia.Domain
{
    public class Paciente
    {   
        public int Id {get; set;}
        public DateTime FechaNacimiento {get; set;}
        public string Celular {get; set;}

        //One to One Relationship
        public int UsuarioId {get; set;}
        public Usuario Usuario {get; set;}

        
    }
}
