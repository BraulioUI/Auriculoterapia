using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
using System.Collections.Generic;
using Auriculoterapia.Api.Helpers;

namespace Auriculoterapia.Api.Service.Implementation
{
    public class CitaService: ICitaService
    {
        private readonly ICitaRepository CitaRepository;
        private readonly IPacienteRepository PacienteRepository;

        private ConversorDeFechaYHora conversor = new ConversorDeFechaYHora();

        public CitaService(ICitaRepository CitaRepository, IPacienteRepository PacienteRepository){
            this.CitaRepository = CitaRepository;
            this.PacienteRepository = PacienteRepository;
        }

        public void Save(Cita entity){
            CitaRepository.Save(entity);
        }

        public void RegistrarCita(FormularioCita entity, int Id){
            var cita = new Cita();
            var paciente = new Paciente();
            try {
                cita.Fecha = entity.Fecha;
                cita.HoraInicioAtencion = conversor.TransformarAHora(entity.HoraInicioAtencion);
                cita.HoraInicioAtencion = conversor.TransformarAHora(entity.HoraFinAtencion);
                cita.Estado = "En Proceso";
                paciente = PacienteRepository.FindById(Id);
                cita.Paciente = paciente;
                Save(cita);

            }catch(System.Exception){

                throw;
            }
        }

        public IEnumerable<Cita> FindAll(){
           return CitaRepository.FindAll();
        }



    }
}