using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
using System.Collections.Generic;


namespace Auriculoterapia.Api.Service.Implementation
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

        public IEnumerable<Paciente> buscarPorPalabra(string palabra){
            return PacienteRepository.busquedaPacientePorPalabra(palabra);
        }

    }
}