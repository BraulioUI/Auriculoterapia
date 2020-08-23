using System.ComponentModel.DataAnnotations;

namespace Auriculoterapia.Api.Domain
{
    public class Rol_Usuario
    {
        public int Id {get; set;}
        public int RolId {get; set;}
        public virtual Rol Rol {get; set;}
        public int UsuarioId {get; set;} 
        public virtual Usuario Usuario {get; set;} 
    }
}