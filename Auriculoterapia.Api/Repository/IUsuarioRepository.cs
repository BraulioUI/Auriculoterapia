using System;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
namespace Auriculoterapia.Api.Repository
{
    public interface IUsuarioRepository: IRepository<Usuario>
    {
       Response Autenticar(string nombreUsuario, string password);

       void Asignar_Paciente(Usuario usuario);

       ResponseActualizarPassword actualizar_Contrasena(string nombreUsuario,string palabraClave, string password);
    
       bool IsValidEmail(string email);
    }
}
