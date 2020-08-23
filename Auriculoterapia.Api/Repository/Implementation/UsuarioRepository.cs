using System;
using System.Linq;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;

using System.Collections.Generic;
namespace Auriculoterapia.Api.Repository.Implementation
{
    public class UsuarioRepository: IUsuarioRepository
    {
        private ApplicationDbContext context;

        public UsuarioRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<Usuario> FindAll(){
            return new List<Usuario>();
        }
        public void Save(Usuario entity){

        }

        public Usuario FindById(int id){
            return new Usuario();
        }
    }
}
