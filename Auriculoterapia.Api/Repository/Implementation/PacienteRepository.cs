using System;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;
using System.Linq;
using System.Collections.Generic;

namespace Auriculoterapia.Api.Repository.Implementation
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
