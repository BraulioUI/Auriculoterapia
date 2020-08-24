using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Auriculoterapia.Api.Migrations
{
    public partial class Fourth : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<TimeSpan>(
                name: "HoraInicioAtencion",
                table: "Citas",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldType: "datetime(6)");

            migrationBuilder.AlterColumn<TimeSpan>(
                name: "HoraFinAtencion",
                table: "Citas",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldType: "datetime(6)");

            migrationBuilder.AddColumn<int>(
                name: "TipoAtencionId",
                table: "Citas",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_Citas_TipoAtencionId",
                table: "Citas",
                column: "TipoAtencionId");

            migrationBuilder.AddForeignKey(
                name: "FK_Citas_TipoAtencions_TipoAtencionId",
                table: "Citas",
                column: "TipoAtencionId",
                principalTable: "TipoAtencions",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Citas_TipoAtencions_TipoAtencionId",
                table: "Citas");

            migrationBuilder.DropIndex(
                name: "IX_Citas_TipoAtencionId",
                table: "Citas");

            migrationBuilder.DropColumn(
                name: "TipoAtencionId",
                table: "Citas");

            migrationBuilder.AlterColumn<DateTime>(
                name: "HoraInicioAtencion",
                table: "Citas",
                type: "datetime(6)",
                nullable: false,
                oldClrType: typeof(TimeSpan));

            migrationBuilder.AlterColumn<DateTime>(
                name: "HoraFinAtencion",
                table: "Citas",
                type: "datetime(6)",
                nullable: false,
                oldClrType: typeof(TimeSpan));
        }
    }
}
