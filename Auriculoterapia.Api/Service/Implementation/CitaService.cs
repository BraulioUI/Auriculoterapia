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
        private readonly ITipoAtencionRepository tipoAtencionRepository;


        public CitaService(ICitaRepository CitaRepository, IPacienteRepository PacienteRepository, ITipoAtencionRepository tipoAtencionRepository){
            this.CitaRepository = CitaRepository;
            this.PacienteRepository = PacienteRepository;
            this.tipoAtencionRepository = tipoAtencionRepository;
        }

        public void Save(Cita entity){
            CitaRepository.Save(entity);
        }

        public void RegistrarCita(FormularioCita entity, int PacienteId){
            var cita = new Cita();
            var conversor = new ConversorDeFechaYHora(); 
            try {
                var tipoAtencion = tipoAtencionRepository.FindByDescription(entity.TipoAtencion);
                var paciente = PacienteRepository.FindById(PacienteId);
                cita.Fecha = conversor.TransformarAFecha(entity.Fecha);
                cita.HoraInicioAtencion = conversor.TransformarAHora(entity.HoraInicioAtencion, entity.Fecha);
                cita.HoraFinAtencion = conversor.TransformarAHora(entity.HoraFinAtencion, entity.Fecha);
                cita.Estado = "En Proceso";
                cita.PacienteId = paciente.Id;
                cita.Paciente = paciente;
                cita.TipoAtencionId = tipoAtencion.Id;
                cita.TipoAtencion = tipoAtencion;
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