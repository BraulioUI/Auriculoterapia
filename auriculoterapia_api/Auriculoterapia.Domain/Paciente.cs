using System;

namespace Auriculoterapia.Domain
{
    public class Paciente
    {   
        public int Id {get; set;}
        public Date FechaNacimiento {get; set;}
        public string Celular {get; set;}

        public int UsuarioId {get; set;}
        public Usuario Usuario {get; set;}

        
    }
}
