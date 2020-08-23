using Auriculoterapia.Domain;
using Microsoft.EntityFrameworkCore;

namespace Auriculoterapia.Repository.Context
{
    public class ApplicationDbContext:DbContext
    {
        public DbSet<Usuario> Usuarios {get; set;}
        public DbSet<Paciente> Pacientes {get;set;}
        public DbSet<Cita> Citas {get; set;}
        
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) 
        : base(options) {

            

        }
    }
}