using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
namespace Auriculoterapia.Api.Service
{
    public interface IUsuarioService: IService<Usuario>
    {
        Response Autenticar(string nombreUsuario, string password);

        Usuario FinbyId(int id);

        ResponseActualizarPassword actualizar_Contrasena(string nombreUsuario,string palabraClave, string password);

        ResponseUsuarioById BuscarPorId(int userId);

        ResponseActualizarKeyWord Actualizar_KeyWord(int idUser,string palabraClave,string nuevaPalabraClave);
    }
}