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

        private readonly IDisponibilidadRepository disponibilidadRepository;
        private IHorarioDescartadoRepository horarioDescartadoRepository;
    

        public CitaService(ICitaRepository CitaRepository, IPacienteRepository PacienteRepository, ITipoAtencionRepository tipoAtencionRepository,
        IDisponibilidadRepository disponibilidadRepository, IHorarioDescartadoRepository horarioDescartadoRepository){
            this.CitaRepository = CitaRepository;
            this.PacienteRepository = PacienteRepository;
            this.tipoAtencionRepository = tipoAtencionRepository;
            this.disponibilidadRepository = disponibilidadRepository;
            this.horarioDescartadoRepository = horarioDescartadoRepository;
      
        }

        public void Save(Cita entity){
            CitaRepository.Save(entity);
        }

        public void RegistrarCita(FormularioCita entity, int PacienteId){
            var cita = new Cita();
            var conversor = new ConversorDeFechaYHora(); 
             var disponibilidad = new Disponibilidad();
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

                disponibilidad = disponibilidadRepository.listarPorFecha(entity.Fecha);
                

                var horarioDescartado = new HorarioDescartado();
                    horarioDescartado.HoraInicio = cita.HoraInicioAtencion;
                    horarioDescartado.HoraFin = cita.HoraFinAtencion;
                    horarioDescartado.DisponibilidadId = disponibilidad.Id;
                    horarioDescartado.Disponibilidad = disponibilidad;
                
                this.horarioDescartadoRepository.Save(horarioDescartado);

            }catch(System.Exception){

                throw;
            }
        }

        public void RegistrarCitaPaciente(FormularioCitaPaciente entity, int PacienteId){
            var cita = new Cita();
            var conversor = new ConversorDeFechaYHora(); 
            var disponibilidad = new Disponibilidad();
            try {

                var tipoAtencion = tipoAtencionRepository.FindByDescription(entity.TipoAtencion);
                var paciente = PacienteRepository.buscarPorUsuarioId(PacienteId);
                PacienteRepository.ActualizarNumeroPaciente(entity.Celular, paciente);
                cita.Fecha = conversor.TransformarAFecha(entity.Fecha);
                cita.HoraInicioAtencion = conversor.TransformarAHora(entity.HoraInicioAtencion, entity.Fecha);
                cita.HoraFinAtencion = conversor.TransformarAHora(entity.HoraFinAtencion, entity.Fecha);
                cita.Estado = "En Proceso";
                cita.PacienteId = paciente.Id;
                cita.Paciente = paciente;
                cita.TipoAtencionId = tipoAtencion.Id;
                cita.TipoAtencion = tipoAtencion;
                Save(cita);
                disponibilidad = disponibilidadRepository.listarPorFecha(entity.Fecha);
                

                var horarioDescartado = new HorarioDescartado();
                    horarioDescartado.HoraInicio = cita.HoraInicioAtencion;
                    horarioDescartado.HoraFin = cita.HoraFinAtencion;
                    horarioDescartado.DisponibilidadId = disponibilidad.Id;
                    horarioDescartado.Disponibilidad = disponibilidad;
                
                this.horarioDescartadoRepository.Save(horarioDescartado);
                

            }catch(System.Exception){

                throw;
            }
        }

        public IEnumerable<Cita> FindAll(){
           return CitaRepository.FindAll();
        }

        public IEnumerable<Cita> listarCitasPorUsuarioId(int usuarioId){
            return CitaRepository.listarCitasPorUsuarioId(usuarioId);
        }
        public bool actualizarEstadoCita(int citaId, string estado){
            return CitaRepository.actualizarEstadoCita(citaId, estado);
        }
    }
}