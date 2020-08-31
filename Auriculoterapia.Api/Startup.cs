using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Auriculoterapia.Api.Repository.Context;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Auriculoterapia.Api.Repository;
using Auriculoterapia.Api.Repository.Implementation;
using Auriculoterapia.Api.Service;
using Auriculoterapia.Api.Service.Implementation;
using Microsoft.IdentityModel.Tokens;
using Auriculoterapia.Api.Helpers;
using System.Text;

namespace Auriculoterapia.Api
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {   



            services.AddDbContext<ApplicationDbContext>(x => {
                x.UseMySql(Configuration.GetConnectionString("DefaultConnection"));
            });

            

            services.AddCors();

            // configure strongly typed settings objects
            //var appSettingsSection = Configuration.GetSection("AppSettings");
            //services.Configure<AppSettings>(appSettingsSection);

            // configure jwt authentication
            //var appSettings = appSettingsSection.Get<AppSettings>();
            var key = Encoding.ASCII.GetBytes(Configuration.GetSection("AppSettings:Token").Value);
            services.AddAuthentication(x =>
            {
                x.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
                x.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
            })
            .AddJwtBearer(x =>
            {
                x.RequireHttpsMetadata = false;
                x.SaveToken = true;
                x.TokenValidationParameters = new TokenValidationParameters
                {
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(key),
                    ValidateIssuer = false,
                    ValidateAudience = false
                };
            });

            services.AddTransient<ICitaRepository, CitaRepository>();
            services.AddTransient<IPacienteRepository, PacienteRepository>();
            services.AddTransient<IUsuarioRepository, UsuarioRepository>();
            services.AddTransient<IRol_UsuarioRepository, Rol_UsuarioRepository>();
            services.AddTransient<ITipoAtencionRepository, TipoAtencionRepository>();
            services.AddTransient<ISolicitudTratamientoRepository, SolicitudTratamientoRepository>();

            services.AddTransient<ICitaService,CitaService>(); 
            services.AddTransient<IUsuarioService,UsuarioService>(); 
            services.AddTransient<IPacienteService, PacienteService>();
            services.AddTransient<ITipoAtencionService, TipoAtencionService>();
            services.AddTransient<ISolicitudTratamientoService, SolicitudTratamientoService>();


            services.AddControllers()
                 .AddNewtonsoftJson(opt => {
                    opt.SerializerSettings.ReferenceLoopHandling = 
                    Newtonsoft.Json.ReferenceLoopHandling.Ignore;
                }
            );    
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

 

            app.UseHttpsRedirection();

            app.UseRouting();
            app.UseAuthentication();
            app.UseAuthorization();

           

             app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });

            app.UseCors(x => x.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader());

        }
    }
}
