using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Auriculoterapia.Api.Migrations
{
    public partial class ModificadoSolicitudTratamiento : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Estado",
                table: "SolicitudTratamientos",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "fechaInicio",
                table: "SolicitudTratamientos",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Estado",
                table: "SolicitudTratamientos");

            migrationBuilder.DropColumn(
                name: "fechaInicio",
                table: "SolicitudTratamientos");
        }
    }
}
