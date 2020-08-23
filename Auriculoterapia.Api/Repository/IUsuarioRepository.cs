using System;
using Auriculoterapia.Api.Domain;
namespace Auriculoterapia.Api.Repository
{
    public interface IUsuarioRepository: IRepository<Usuario>
    {
       Usuario Autenticar(string nombreUsuario, string password);
    }
}
