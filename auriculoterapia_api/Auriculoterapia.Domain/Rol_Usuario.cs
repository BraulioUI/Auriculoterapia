using System;
using System.Collections.Generic;
namespace Auriculoterapia.Domain
{
    public class Rol_Usuario
    {
        public int RolId {get; set;}
        public Rol Rol {get; set;}
        public int UsuarioId {get; set;} 
        public Usuario Usuario {get; set;}      

        public ICollection<Usuario> Usuarios {get; set;}
        public ICollection<Rol> Roles {get; set;}
    }
}