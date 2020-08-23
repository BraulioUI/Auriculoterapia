using Auriculoterapia.Api.Domain;
using Microsoft.EntityFrameworkCore;

namespace Auriculoterapia.Api.Repository.Context
{
    public class ApplicationDbContext : DbContext
    {
        
        public DbSet<Cita> Citas {get;set;}
        public DbSet<Especialista> Especialistas {get;set;}
        public DbSet<Paciente> Pacientes {get;set;}
        public DbSet<Rol> Roles {get;set;}
        public DbSet<Rol_Usuario> Rol_Usuarios {get;set;}
        public DbSet<SolicitudTratamiento> SolicitudTratamientos {get;set;}
        public DbSet<TipoAtencion> TipoAtencions {get;set;}
        public DbSet<Tratamiento> Tratamientos {get;set;}
        public DbSet<Usuario> Usuarios {get;set;}

        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) 
        : base(options) {}

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            
        }
        
    }
}