using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
using System.Collections.Generic;


namespace Auriculoterapia.Api.Service.Implementation
{
    public class CitaService: ICitaService
    {
        private ICitaRepository CitaRepository;


        public CitaService(ICitaRepository CitaRepository){
            this.CitaRepository = CitaRepository;
        }

        public void Save(Cita entity){
            CitaRepository.Save(entity);
        }

        public IEnumerable<Cita> FindAll(){
           return CitaRepository.FindAll();
        }



    }
}