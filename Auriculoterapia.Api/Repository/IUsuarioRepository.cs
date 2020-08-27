using System;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
namespace Auriculoterapia.Api.Repository
{
    public interface IUsuarioRepository: IRepository<Usuario>
    {
       Response Autenticar(string nombreUsuario, string password);

       void Asignar_Paciente(Usuario usuario);
    }
}
