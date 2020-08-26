using Auriculoterapia.Api.Domain;
namespace Auriculoterapia.Api.Service
{
    public interface IUsuarioService: IService<Usuario>
    {
        Usuario Autenticar(string nombreUsuario, string password);

        Usuario FinbyId(int id);
    }
}