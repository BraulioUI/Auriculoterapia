using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Auriculoterapia.Api.Repository;
using Auriculoterapia.Api.Repository.Implementation;
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
using Auriculoterapia.Api.Service;
using Auriculoterapia.Api.Service.Implementation;


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

            services.AddTransient<ICitaRepository, CitaRepository>();
            services.AddTransient<IUsuarioRepository, UsuarioRepository>();
            services.AddTransient<IRol_UsuarioRepository, Rol_UsuarioRepository>();

            services.AddTransient<ICitaService,CitaService>(); 
            services.AddTransient<IUsuarioService,UsuarioService>(); 

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

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
