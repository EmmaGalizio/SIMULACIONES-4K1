using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using SIMULACION_TP1_V1._0._1;
using System.Windows.Forms.DataVisualization.Charting;


namespace SIMULACION_TP1_V1._0._1.Formularios
{
    public partial class FrmChiCuadrado : SIMULACION_TP1_V1._0._1.Formularios.FrmBase
    {
        public FrmChiCuadrado()
        {
            InitializeComponent();
        }

        private void groupBox2_Enter(object sender, EventArgs e)
        {

        }

        private void label8_Click(object sender, EventArgs e)
        {

        }

        private void txt_tamMuestra_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            grid011.Rows.Clear();
            grid012.Rows.Clear();

            chart1.Series.Clear();
            chart1.Series.Add("Frecuencia Observada");
            

            ControladorTP1 controlador = new ControladorTP1();
            Resultado resultado = new Resultado();

            

            if (validarDatos())
            {
                // crea variables con los valores
     
                int n = int.Parse(txt_TamMuestra.Text);
                int cantIntervalos = int.Parse(cmb_subintervalos.SelectedItem.ToString());
                int precision = -1;
                // verifica que metodo congruencial se quiere usar y llama al metodo generar del objeto correspondiente

                resultado = controlador.generarPruebaChiCuadradoDefault(n, precision, cantIntervalos);
                cargarGrilla1(resultado);
                cargarGrilla2(resultado);
                GraficoFrecuenciaObservada(resultado);
                txt_ValorTabulado.Text = resultado.EstadisticoPruebaEsp.ToString();
                

                if (resultado.EstadisticoPruebaEsp >= resultado.EstadisticoPruebaObs)
                {
                    MessageBox.Show("Se acepta la hipotesis!", "Resultado", MessageBoxButtons.OK, MessageBoxIcon.Information);

                } 
                else
                {
                    MessageBox.Show("NO se acepta la hipotesis!", "Resultado", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }

        }
            

        private void cargarGrilla1(Resultado resultado)
        {
            List<PseudoAleatorio> lista = resultado.ValoresGenerados;
            float valor;

            for (int i = 0; i < resultado.N; i++)
            {
                valor = lista[i].Random;
                grid011.Rows.Add(i, valor);
            }
        }

        private void cargarGrilla2(Resultado resultado)
        {
            List<Intervalo> lista = resultado.Intervalos;
            //la siguiente variable no tiene sentido
            float valor;

            foreach (Intervalo intervalo in lista)
            {
                float desde = intervalo.LimInferior;
                float hasta = intervalo.LimSuperior;
                string desdehasta = desde + " - " + hasta;
                int fo = intervalo.FrecAbsObs;
                float fe = intervalo.FrecAbsEsp;
                float c = intervalo.Estadistico;
                float ca = intervalo.EstadisticoAcum;



                grid012.Rows.Add(desdehasta, fo, fe, c , ca);
            }
        }


        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void FrmChiCuadrado_Load(object sender, EventArgs e)
        {
            
            grid011.Formatear("Iteracion,80, C; N° Aleatorio, 100, C");
            grid012.Formatear("Intervalo,100, C; Frecuencia Observada, 100, C; Frecuencia Esperada, 100, C; C, 100, C;C Acumulado, 100, C");
            _FormularioMovil = true;
            txtNivelSignificancia.Text = "95 %";
            txtNivelSignificancia.Enabled = false;
        }

        private void grid012_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
        private bool validarDatos()
        {
            if (cmb_subintervalos.SelectedItem != null && txt_TamMuestra.Text != "")
            {
                return true;
            }

            
            MessageBox.Show("Falta completar alguno de los campos!", "Importante", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            return false;
        }

        private void cmb_subintervalos_SelectedIndexChanged(object sender, EventArgs e)
        {
            
        }

        private void txt_ValorTabulado_TextChanged(object sender, EventArgs e)
        {

        }

        private void groupBox4_Enter(object sender, EventArgs e)
        {

        }

        private void maskedTextBox1_MaskInputRejected(object sender, MaskInputRejectedEventArgs e)
        {

        }

        private void chart1_Click(object sender, EventArgs e)
        {

        }
        //VER
        private void GraficoFrecuenciaObservada(Resultado resultado)
        {

            List<Intervalo> intervalos = resultado.Intervalos;

            foreach (Intervalo intervalo in intervalos)
            {
                double marcaClase = (intervalo.LimInferior + intervalo.LimSuperior) / 2;
                marcaClase = Math.Truncate(marcaClase * 1000) / 1000;
                chart1.Series["Frecuencia Observada"].Points.AddXY(marcaClase ,intervalo.FrecAbsObs);
            }



        }

        private void groupBox3_Enter(object sender, EventArgs e)
        {

        }
    }
}
