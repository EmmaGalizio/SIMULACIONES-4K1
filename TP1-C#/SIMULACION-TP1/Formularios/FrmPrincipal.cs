using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace SIMULACION_TP1_V1._0._1.Formularios
{
    public partial class FrmPrincipal : SIMULACION_TP1_V1._0._1.Formularios.FrmBase
    {
        public FrmPrincipal()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            FrmGenerador generador = new FrmGenerador();
            generador.ShowDialog();
        }

        private void FrmPrincipal_Load(object sender, EventArgs e)
        {
            _FormularioMovil = true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            FrmChiCuadrado generador = new FrmChiCuadrado();
            generador.ShowDialog();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            FrmChiCuadradoMCMixto generador = new FrmChiCuadradoMCMixto();
            generador.ShowDialog();
        }
    }
}
