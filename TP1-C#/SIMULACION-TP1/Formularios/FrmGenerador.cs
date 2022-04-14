using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using SIMULACION_TP1_V1._0._1;

namespace SIMULACION_TP1_V1._0._1.Formularios
{
    public partial class FrmGenerador : SIMULACION_TP1_V1._0._1.Formularios.FrmBase
    {

        public FrmGenerador()
        {
            InitializeComponent();
        }

        private void FrmGenerador_Load(object sender, EventArgs e)
        {
            rbLineal.Checked = true;
            grid011.Formatear("Iteracion,100, C; N° Aleatorio, 100, C");
            _FormularioMovil = true;
        }


        private void rbMultiplicativo_CheckedChanged(object sender, EventArgs e)
        {
            if (rbMultiplicativo.Checked)
            {
                rbLineal.Checked = false;
                txtC.Text = "0";
                txtC.Enabled = false;               
            }
        }

        private void rbLineal_CheckedChanged(object sender, EventArgs e)
        {
            if (rbLineal.Checked)
            {
                rbMultiplicativo.Checked = false;
                txtC.Enabled = true;
            }
        }

        private void btnGenerar_Click(object sender, EventArgs e)
        {

            grid011.Rows.Clear();

            


            ControladorTP1 controlador = new ControladorTP1();
            Resultado resultado = new Resultado();

            //el if verifica que se completaron los datos (validarDatos) 
            if (validarDatos())
            {
                // crea variables con los valores
                int g = int.Parse(txtG.Text);
                int k = int.Parse(txtK.Text);
                int c = int.Parse(txtC.Text);
                int x = int.Parse(txtX.Text);
                int n = int.Parse(txtN.Text);
                int cantIntervalos = -1;
                int precision = -1;
                // verifica que metodo congruencial se quiere usar y llama al metodo generar del objeto correspondiente

                if (rbLineal.Checked)
                {
                    int a = 1 + 4 * k;
                    int m = (int) Math.Pow(2, g);

                    txtA.Text = a.ToString();
                    txtM.Text = m.ToString();

                    resultado = controlador.generarLineal(g, k, c, x, n, cantIntervalos, precision);
                    if (resultado == null)
                    {
                        MessageBox.Show("NO esta ingresando primos relativos entre C y M", "Importante", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        return;
                    }
                    cargarGrilla(resultado);
                }
                if (rbMultiplicativo.Checked)
                {
                    int a = 3 + 8 * k;
                    int m = (int)Math.Pow(2, g);

                    txtA.Text = a.ToString();
                    txtM.Text = m.ToString();

                    resultado = controlador.generarMultiplicativo(g, k, x, n, cantIntervalos, precision);
                    if (resultado == null)
                    {
                        MessageBox.Show("NO esta ingresando primos relativos entre C y M", "Importante", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        return;
                    }
                    cargarGrilla(resultado);
                }
               
            }
        }

        private void cargarGrilla(Resultado resultado)
        {
            List<PseudoAleatorio> lista = resultado.ValoresGenerados;
            float valor;

            for (int i = 0; i < resultado.N;  i++)
            {
                valor = lista[i].Random;
                grid011.Rows.Add(i, valor);
            }
        }
        
        private bool validarDatos()
        {
            if (rbLineal.Checked)
            {
                if (txtN.Text != "" && txtX.Text != "" && txtK.Text != "" && txtG.Text != "" && txtC.Text != "")
                {
                    return true;        
                }
            }

            if (rbMultiplicativo.Checked)
            {
                if(txtN.Text != "" && txtX.Text != "" && txtK.Text != "" && txtG.Text != "")
                {
                    return true;
                }
            }
            MessageBox.Show("Falta completar alguno de los campos!", "Importante", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            return false;
        }

        private void groupBox3_Enter(object sender, EventArgs e)
        {

        }

        private void grid011_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
