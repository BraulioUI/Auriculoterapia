using System;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;
using System.Collections.Generic;
using System.Linq;

namespace Auriculoterapia.Api.Repository.Implementation
{
    public class CitaRepository: ICitaRepository
    {
        private ApplicationDbContext context;

        public CitaRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<Cita> FindAll(){
            var citas = new List<Cita>();
            try{
                citas = this.context.Citas.ToList();
            } catch(System.Exception){
                throw;
            }
            return citas;
        }
        public void Save(Cita entity){
            try{
                this.context.Add(entity);
                this.context.SaveChanges();
            }catch(System.Exception){
                throw;
            }
        }

        public Cita FindById(int id){
            return new Cita();
        }
    }
}
