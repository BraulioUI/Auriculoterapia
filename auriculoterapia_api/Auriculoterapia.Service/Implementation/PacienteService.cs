using Auriculoterapia.Domain;
using Auriculoterapia.Repository;
using System.Collections.Generic;


namespace Auriculoterapia.Service.Implementation
{
    public class PacienteService: IPacienteService
    {
        private IPacienteRepository PacienteRepository;


        public PacienteService(IPacienteRepository PacienteRepository){
            this.PacienteRepository = PacienteRepository;
        }

        public void Save(Paciente entity){
            PacienteRepository.Save(entity);
        }

        public IEnumerable<Paciente> FindAll(){
            return PacienteRepository.FindAll();
        }

    }
}