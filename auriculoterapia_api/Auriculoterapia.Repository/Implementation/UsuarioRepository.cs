using System;
using System.Linq;
using Auriculoterapia.Domain;
using Auriculoterapia.Repository.Context;

using System.Collections.Generic;
namespace Auriculoterapia.Repository.Implementation
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
    }
}
