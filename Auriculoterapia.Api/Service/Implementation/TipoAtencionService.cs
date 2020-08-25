using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
using Auriculoterapia.Api.Service;

namespace Auriculoterapia.Api.Service.Implementation
{
    public class TipoAtencionService: ITipoAtencionService
    {

        private ITipoAtencionRepository TipoAtencionRepository;

        public TipoAtencionService(ITipoAtencionRepository TipoAtencionRepository){
            this.TipoAtencionRepository = TipoAtencionRepository;
        }
        
        public void Save(TipoAtencion entity){
            
        }
        public IEnumerable<TipoAtencion> FindAll(){
            return this.TipoAtencionRepository.FindAll();
        }
    }
}