using System;
using Auriculoterapia.Domain;
using Auriculoterapia.Repository.Context;
using System.Linq;
using System.Collections.Generic;

namespace Auriculoterapia.Repository.Implementation
{
    public class PacienteRepository: IPacienteRepository
    {
        private ApplicationDbContext context;

        public PacienteRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<Paciente> FindAll(){
            return new List<Paciente>();
        }
        public void Save(Paciente entity){

        }
    }
}
