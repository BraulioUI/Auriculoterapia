using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using Auriculoterapia.Api.Helpers;
namespace Auriculoterapia.Api.Repository.Implementation
{
    public class DisponibilidadRepository : IDisponibilidadRepository
    {

        private ApplicationDbContext context;

        public DisponibilidadRepository(ApplicationDbContext context){
            this.context = context;
        }

        public IEnumerable<Disponibilidad> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public Disponibilidad FindById(int id)
        {
            throw new System.NotImplementedException();
        }

        public void Save(Disponibilidad entity)
        {
            try{
                this.context.Disponibilidades.Add(entity);
                this.context.SaveChanges();
            }catch(System.Exception){
                throw;
            }
        }

        public  Disponibilidad guardarDisponibilidad(Disponibilidad entity){
                var disponibilidad = new Disponibilidad();
                try{
                    this.context.Disponibilidades.Add(entity);
                    this.context.SaveChanges();
                    disponibilidad = entity;
                } catch(System.Exception){
                    throw;
                }
                return disponibilidad;
         }

         public Disponibilidad listarPorFecha(string fecha){
             var disponibilidad = new Disponibilidad();
             var conversor = new ConversorDeFechaYHora();
             try{
                disponibilidad = this.context.Disponibilidades.Include(d => d.HorariosDescartados)
                                    .FirstOrDefault(d => d.Dia == conversor.TransformarAFecha(fecha));
             } catch(System.Exception){
                    throw;
             }
             return disponibilidad;
         }
    }
}