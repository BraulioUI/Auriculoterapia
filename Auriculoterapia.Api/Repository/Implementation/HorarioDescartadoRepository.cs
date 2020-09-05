using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;

namespace Auriculoterapia.Api.Repository.Implementation
{
    public class HorarioDescartadoRepository : IHorarioDescartadoRepository
    {

        private ApplicationDbContext context;

        public HorarioDescartadoRepository(ApplicationDbContext context){
            this.context = context;
        }


        public IEnumerable<HorarioDescartado> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public HorarioDescartado FindById(int id)
        {
            throw new System.NotImplementedException();
        }

        public void Save(HorarioDescartado entity)
        {
            try{
                this.context.HorariosDescartados.Add(entity);
                this.context.SaveChanges();
            }catch(System.Exception){
                throw;
            }
        }
    }
}