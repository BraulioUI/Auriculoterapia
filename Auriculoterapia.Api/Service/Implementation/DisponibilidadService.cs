


using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
using Auriculoterapia.Api.Repository;

namespace Auriculoterapia.Api.Service.Implementation
{
    public class DisponibilidadService : IDisponibilidadService
    {

        private IDisponibilidadRepository DisponibilidadRepository;
        private IHorarioDescartadoRepository HorarioDescartadoRepository;
        private IEspecialistaRepository EspecialistaRepository;

        public DisponibilidadService(IDisponibilidadRepository DisponibilidadRepository,
                                     IHorarioDescartadoRepository HorarioDescartadoRepository,
                                    IEspecialistaRepository EspecialistaRepository){
            this.DisponibilidadRepository = DisponibilidadRepository;
            this.HorarioDescartadoRepository = HorarioDescartadoRepository;
            this.EspecialistaRepository = EspecialistaRepository;
        }

        public IEnumerable<Disponibilidad> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public void Save(Disponibilidad entity)
        {
            throw new System.NotImplementedException();
        }

        public void registrarDisponibilidad(FormularioDisponibilidad entity, int especialistaId){
            
            var conversor = new ConversorDeFechaYHora(); 
            var disponibilidad =  new Disponibilidad();
            var horariosDescartados = new List<HorarioDescartado>();
            try{
                disponibilidad.Dia = conversor.TransformarAFecha(entity.dia);
                disponibilidad.HoraInicio = conversor.TransformarAHora(entity.horaInicio, entity.dia);
                disponibilidad.HoraFin = conversor.TransformarAHora(entity.horaFin, entity.dia);
                disponibilidad.EspecialistaId = especialistaId;
                disponibilidad.Especialista = this.EspecialistaRepository.FindById(especialistaId);
                var disInserted = this.DisponibilidadRepository.guardarDisponibilidad(disponibilidad);    
                
                foreach(var horario in entity.horariosDescartados){
                    var horarioDescartado = new HorarioDescartado();
                    horarioDescartado.HoraInicio = conversor.TransformarAHora(
                        horario.horaInicio, entity.dia
                    );
                    horarioDescartado.HoraFin = conversor.TransformarAHora(
                        horario.horaFin, entity.dia
                    );
                    horarioDescartado.DisponibilidadId = disInserted.Id;
                    horarioDescartado.Disponibilidad = disInserted;
                    this.HorarioDescartadoRepository.Save(horarioDescartado);

                }

            } catch(System.Exception){
                throw;
            }
            
        }


        public Disponibilidad listarPorFecha(string fecha){
            return DisponibilidadRepository.listarPorFecha(fecha);
        }

    }
}