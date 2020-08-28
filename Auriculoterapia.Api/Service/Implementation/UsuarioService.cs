using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
using Auriculoterapia.Api.Helpers;
using System.Collections.Generic;


namespace Auriculoterapia.Api.Service.Implementation
{
    public class UsuarioService: IUsuarioService
    {
        private IUsuarioRepository UsuarioRepository;


        public UsuarioService(IUsuarioRepository UsuarioRepository){
            this.UsuarioRepository = UsuarioRepository;
        }

        public void Save(Usuario entity){
            UsuarioRepository.Save(entity);
        }

        public IEnumerable<Usuario> FindAll(){
           return UsuarioRepository.FindAll();
        }

        public Response Autenticar(string nombreUsuario, string password){
            return UsuarioRepository.Autenticar(nombreUsuario,password);
        }

        public Usuario FinbyId(int id){
            return UsuarioRepository.FindById(id);
        }

        public ResponseActualizarPassword actualizar_Contrasena(string nombreUsuario,string palabraClave, string password){
            return UsuarioRepository.actualizar_Contrasena(nombreUsuario,palabraClave,password);
        
        }

    }
}