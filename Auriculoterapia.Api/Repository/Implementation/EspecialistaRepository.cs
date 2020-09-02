using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;
using System.Linq;

namespace Auriculoterapia.Api.Repository.Implementation
{
    public class EspecialistaRepository: IEspecialistaRepository
    {
        private ApplicationDbContext context;

        public EspecialistaRepository(ApplicationDbContext context){
            this.context = context;
        }

        public IEnumerable<Especialista> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public Especialista FindById(int id)
        {
            var especialista = new Especialista();
            try{
                especialista = this.context.Especialistas.FirstOrDefault(e => e.Usuario.Id == id);

            }catch(System.Exception){
                throw;
            }
            return especialista;
            
        }

        public void Save(Especialista entity)
        {
            throw new System.NotImplementedException();
        }
    }
}