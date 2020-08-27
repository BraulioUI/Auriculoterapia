using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
namespace Auriculoterapia.Api.Service
{
    public interface IUsuarioService: IService<Usuario>
    {
        Response Autenticar(string nombreUsuario, string password);

        Usuario FinbyId(int id);
    }
}