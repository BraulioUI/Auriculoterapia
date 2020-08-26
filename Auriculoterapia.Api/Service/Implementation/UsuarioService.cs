using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
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

        public Usuario Autenticar(string nombreUsuario, string password){
            return UsuarioRepository.Autenticar(nombreUsuario,password);
        }

        public Usuario FinbyId(int id){
            return UsuarioRepository.FindById(id);
        }

    }
}