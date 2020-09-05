using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Auriculoterapia.Api.Migrations
{
    public partial class sixth : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Disponibilidades",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    HoraInicio = table.Column<DateTime>(nullable: false),
                    HoraFin = table.Column<DateTime>(nullable: false),
                    Dia = table.Column<DateTime>(nullable: false),
                    EspecialistaId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Disponibilidades", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Disponibilidades_Especialistas_EspecialistaId",
                        column: x => x.EspecialistaId,
                        principalTable: "Especialistas",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "HorariosDescartados",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    HoraInicio = table.Column<DateTime>(nullable: false),
                    HoraFin = table.Column<DateTime>(nullable: false),
                    DisponibilidadId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_HorariosDescartados", x => x.Id);
                    table.ForeignKey(
                        name: "FK_HorariosDescartados_Disponibilidades_DisponibilidadId",
                        column: x => x.DisponibilidadId,
                        principalTable: "Disponibilidades",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Disponibilidades_EspecialistaId",
                table: "Disponibilidades",
                column: "EspecialistaId");

            migrationBuilder.CreateIndex(
                name: "IX_HorariosDescartados_DisponibilidadId",
                table: "HorariosDescartados",
                column: "DisponibilidadId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "HorariosDescartados");

            migrationBuilder.DropTable(
                name: "Disponibilidades");
        }
    }
}
