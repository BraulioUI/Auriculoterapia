namespace Auriculoterapia.Api.Domain
{
    public class Especialista
    {
        public int Id {get; set;}
        public int AnhoExperiencia {get; set;}
        
        public int UsuarioId {get; set;}
        public Usuario Usuario {get; set;}
    }
}