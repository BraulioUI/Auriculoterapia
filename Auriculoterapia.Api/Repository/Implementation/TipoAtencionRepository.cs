using System;
using System.Linq;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;

using System.Collections.Generic;
namespace Auriculoterapia.Api.Repository.Implementation
{
    public class TipoAtencionRepository: ITipoAtencionRepository
    {
        private ApplicationDbContext context;

        public TipoAtencionRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<TipoAtencion> FindAll(){
           var atenciones = new List<TipoAtencion>();
           try{
               atenciones = this.context.TipoAtencions.ToList();
           }catch(System.Exception){
               throw;
           }
           return atenciones;
        }
        public void Save(TipoAtencion entity){

        }

        public TipoAtencion FindById(int id){
            TipoAtencion ta = new TipoAtencion();
            try{
                 ta = context.TipoAtencions.Single(t => t.Id == id);
            }catch(System.Exception){
                throw;
            }
            return ta;
        }

        public TipoAtencion FindByDescription(string Description){
            TipoAtencion ta = new TipoAtencion();
            try{
                ta = context.TipoAtencions.First(t => t.Descripcion.Equals(Description));

            }catch(System.Exception){
                throw;
            }
            return ta;
        }
    }
}
