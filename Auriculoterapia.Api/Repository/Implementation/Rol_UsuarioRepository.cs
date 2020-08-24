using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;

namespace Auriculoterapia.Api.Repository.Implementation
{
    public class Rol_UsuarioRepository : IRol_UsuarioRepository
    {
         private ApplicationDbContext context;

         public Rol_UsuarioRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        void IRol_UsuarioRepository.Asignar_Usuario_Rol(Usuario usuario)
        {
             Rol_Usuario rol_Usuario = new Rol_Usuario {
                RolId = 2,
                UsuarioId = usuario.Id
            };
            try {
              context.Add(rol_Usuario);
              context.SaveChanges();
                
            } catch (System.Exception) {
                throw;
            }
        }
    }
}