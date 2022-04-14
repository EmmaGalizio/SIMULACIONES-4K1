
namespace SIMULACION_TP1_V1._0._1.Formularios
{
    partial class FrmChiCuadradoMCMixto
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea2 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend2 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            System.Windows.Forms.DataVisualization.Charting.Series series2 = new System.Windows.Forms.DataVisualization.Charting.Series();
            this.label1 = new System.Windows.Forms.Label();
            this.chart1 = new System.Windows.Forms.DataVisualization.Charting.Chart();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.txtNivelSignificancia = new System.Windows.Forms.TextBox();
            this.txt_ValorTabulado = new System.Windows.Forms.TextBox();
            this.grid012 = new SIMULACION_TP1_V1._0._1.ClasesBase.Grid01();
            this.grid011 = new SIMULACION_TP1_V1._0._1.ClasesBase.Grid01();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.txtC = new System.Windows.Forms.MaskedTextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.txtKlineal = new System.Windows.Forms.MaskedTextBox();
            this.txtM = new System.Windows.Forms.MaskedTextBox();
            this.txtA = new System.Windows.Forms.MaskedTextBox();
            this.txtG = new System.Windows.Forms.MaskedTextBox();
            this.txtX = new System.Windows.Forms.MaskedTextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.cmb_subintervalos = new System.Windows.Forms.ComboBox();
            this.button1 = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.txt_TamMuestra = new System.Windows.Forms.MaskedTextBox();
            this.pnl_titulo.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.btn_restaurar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_minimizar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_maximizar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_cerrar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).BeginInit();
            this.groupBox4.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid012)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.grid011)).BeginInit();
            this.groupBox3.SuspendLayout();
            this.groupBox5.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // pnl_titulo
            // 
            this.pnl_titulo.Controls.Add(this.label1);
            this.pnl_titulo.Size = new System.Drawing.Size(1295, 50);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_cerrar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_maximizar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_minimizar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_restaurar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.label1, 0);
            // 
            // btn_restaurar
            // 
            this.btn_restaurar.Location = new System.Drawing.Point(1210, 7);
            // 
            // btn_minimizar
            // 
            this.btn_minimizar.Location = new System.Drawing.Point(1168, 7);
            // 
            // btn_maximizar
            // 
            this.btn_maximizar.Location = new System.Drawing.Point(1210, 7);
            // 
            // btn_cerrar
            // 
            this.btn_cerrar.Location = new System.Drawing.Point(1248, 7);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Segoe UI Black", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(16, 15);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(414, 25);
            this.label1.TabIndex = 11;
            this.label1.Text = "Chi-Cuadrado Metodo Congruencial Mixto ";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // chart1
            // 
            chartArea2.Name = "ChartArea1";
            this.chart1.ChartAreas.Add(chartArea2);
            legend2.Name = "Legend1";
            this.chart1.Legends.Add(legend2);
            this.chart1.Location = new System.Drawing.Point(883, 210);
            this.chart1.Name = "chart1";
            series2.ChartArea = "ChartArea1";
            series2.Legend = "Legend1";
            series2.Name = "Series1";
            this.chart1.Series.Add(series2);
            this.chart1.Size = new System.Drawing.Size(397, 306);
            this.chart1.TabIndex = 47;
            this.chart1.Text = "chart1";
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.label2);
            this.groupBox4.Controls.Add(this.label4);
            this.groupBox4.Controls.Add(this.txtNivelSignificancia);
            this.groupBox4.Controls.Add(this.txt_ValorTabulado);
            this.groupBox4.Location = new System.Drawing.Point(883, 522);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(245, 158);
            this.groupBox4.TabIndex = 46;
            this.groupBox4.TabStop = false;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(6, 21);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(145, 17);
            this.label2.TabIndex = 38;
            this.label2.Text = "Nivel de Significancia:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(6, 81);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(105, 17);
            this.label4.TabIndex = 40;
            this.label4.Text = "Valor Tabulado:";
            // 
            // txtNivelSignificancia
            // 
            this.txtNivelSignificancia.Location = new System.Drawing.Point(9, 41);
            this.txtNivelSignificancia.Name = "txtNivelSignificancia";
            this.txtNivelSignificancia.Size = new System.Drawing.Size(102, 25);
            this.txtNivelSignificancia.TabIndex = 37;
            // 
            // txt_ValorTabulado
            // 
            this.txt_ValorTabulado.Location = new System.Drawing.Point(9, 101);
            this.txt_ValorTabulado.Name = "txt_ValorTabulado";
            this.txt_ValorTabulado.Size = new System.Drawing.Size(102, 25);
            this.txt_ValorTabulado.TabIndex = 39;
            // 
            // grid012
            // 
            this.grid012.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.grid012.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid012.Location = new System.Drawing.Point(307, 210);
            this.grid012.Name = "grid012";
            this.grid012.Size = new System.Drawing.Size(539, 470);
            this.grid012.TabIndex = 45;
            // 
            // grid011
            // 
            this.grid011.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.grid011.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid011.Location = new System.Drawing.Point(46, 210);
            this.grid011.Name = "grid011";
            this.grid011.ReadOnly = true;
            this.grid011.Size = new System.Drawing.Size(205, 470);
            this.grid011.TabIndex = 44;
            // 
            // groupBox3
            // 
            this.groupBox3.BackColor = System.Drawing.Color.Transparent;
            this.groupBox3.Controls.Add(this.groupBox5);
            this.groupBox3.Controls.Add(this.groupBox2);
            this.groupBox3.Controls.Add(this.button1);
            this.groupBox3.Controls.Add(this.groupBox1);
            this.groupBox3.Location = new System.Drawing.Point(12, 52);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(1268, 141);
            this.groupBox3.TabIndex = 43;
            this.groupBox3.TabStop = false;
            this.groupBox3.Enter += new System.EventHandler(this.groupBox3_Enter);
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.txtC);
            this.groupBox5.Controls.Add(this.label11);
            this.groupBox5.Controls.Add(this.txtKlineal);
            this.groupBox5.Controls.Add(this.txtM);
            this.groupBox5.Controls.Add(this.txtA);
            this.groupBox5.Controls.Add(this.txtG);
            this.groupBox5.Controls.Add(this.txtX);
            this.groupBox5.Controls.Add(this.label12);
            this.groupBox5.Controls.Add(this.label13);
            this.groupBox5.Controls.Add(this.label14);
            this.groupBox5.Controls.Add(this.label15);
            this.groupBox5.Controls.Add(this.label16);
            this.groupBox5.Location = new System.Drawing.Point(553, 19);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(443, 116);
            this.groupBox5.TabIndex = 33;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "Variables Generador";
            // 
            // txtC
            // 
            this.txtC.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtC.Location = new System.Drawing.Point(179, 74);
            this.txtC.Mask = "999999999";
            this.txtC.Name = "txtC";
            this.txtC.Size = new System.Drawing.Size(88, 26);
            this.txtC.TabIndex = 27;
            this.txtC.ValidatingType = typeof(int);
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label11.Location = new System.Drawing.Point(146, 74);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(36, 21);
            this.label11.TabIndex = 14;
            this.label11.Text = "c = ";
            // 
            // txtKlineal
            // 
            this.txtKlineal.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtKlineal.Location = new System.Drawing.Point(177, 31);
            this.txtKlineal.Mask = "999999999";
            this.txtKlineal.Name = "txtKlineal";
            this.txtKlineal.Size = new System.Drawing.Size(90, 26);
            this.txtKlineal.TabIndex = 28;
            this.txtKlineal.ValidatingType = typeof(int);
            // 
            // txtM
            // 
            this.txtM.Enabled = false;
            this.txtM.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtM.Location = new System.Drawing.Point(315, 33);
            this.txtM.Mask = "999999999";
            this.txtM.Name = "txtM";
            this.txtM.Size = new System.Drawing.Size(88, 26);
            this.txtM.TabIndex = 26;
            this.txtM.ValidatingType = typeof(int);
            // 
            // txtA
            // 
            this.txtA.Enabled = false;
            this.txtA.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtA.Location = new System.Drawing.Point(317, 76);
            this.txtA.Mask = "999999999";
            this.txtA.Name = "txtA";
            this.txtA.Size = new System.Drawing.Size(88, 26);
            this.txtA.TabIndex = 25;
            this.txtA.ValidatingType = typeof(int);
            // 
            // txtG
            // 
            this.txtG.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtG.Location = new System.Drawing.Point(52, 74);
            this.txtG.Mask = "999999999";
            this.txtG.Name = "txtG";
            this.txtG.Size = new System.Drawing.Size(88, 26);
            this.txtG.TabIndex = 24;
            this.txtG.ValidatingType = typeof(int);
            // 
            // txtX
            // 
            this.txtX.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtX.Location = new System.Drawing.Point(48, 29);
            this.txtX.Mask = "999999999";
            this.txtX.Name = "txtX";
            this.txtX.Size = new System.Drawing.Size(89, 26);
            this.txtX.TabIndex = 22;
            this.txtX.ValidatingType = typeof(int);
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label12.Location = new System.Drawing.Point(273, 33);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(43, 21);
            this.label12.TabIndex = 13;
            this.label12.Text = "m = ";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label13.Location = new System.Drawing.Point(281, 76);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(37, 21);
            this.label13.TabIndex = 13;
            this.label13.Text = "a = ";
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label14.Location = new System.Drawing.Point(143, 31);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(45, 21);
            this.label14.TabIndex = 13;
            this.label14.Text = "kL = ";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label15.Location = new System.Drawing.Point(15, 74);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(38, 21);
            this.label15.TabIndex = 12;
            this.label15.Text = "g = ";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label16.Location = new System.Drawing.Point(6, 31);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(47, 21);
            this.label16.TabIndex = 11;
            this.label16.Text = "X0 = ";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.cmb_subintervalos);
            this.groupBox2.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox2.Location = new System.Drawing.Point(295, 37);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(4);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(4);
            this.groupBox2.Size = new System.Drawing.Size(216, 95);
            this.groupBox2.TabIndex = 31;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Subintervalos (k)";
            // 
            // cmb_subintervalos
            // 
            this.cmb_subintervalos.FormattingEnabled = true;
            this.cmb_subintervalos.Items.AddRange(new object[] {
            "5",
            "10",
            "15",
            "20"});
            this.cmb_subintervalos.Location = new System.Drawing.Point(8, 41);
            this.cmb_subintervalos.Margin = new System.Windows.Forms.Padding(4);
            this.cmb_subintervalos.Name = "cmb_subintervalos";
            this.cmb_subintervalos.Size = new System.Drawing.Size(160, 25);
            this.cmb_subintervalos.TabIndex = 11;
            // 
            // button1
            // 
            this.button1.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.button1.Location = new System.Drawing.Point(1061, 50);
            this.button1.Margin = new System.Windows.Forms.Padding(4);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(141, 53);
            this.button1.TabIndex = 32;
            this.button1.Text = "Generar";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.txt_TamMuestra);
            this.groupBox1.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox1.Location = new System.Drawing.Point(34, 37);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(4);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(4);
            this.groupBox1.Size = new System.Drawing.Size(237, 95);
            this.groupBox1.TabIndex = 30;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Tamaño de Muestra (N)";
            // 
            // txt_TamMuestra
            // 
            this.txt_TamMuestra.Location = new System.Drawing.Point(8, 41);
            this.txt_TamMuestra.Mask = "000000000";
            this.txt_TamMuestra.Name = "txt_TamMuestra";
            this.txt_TamMuestra.Size = new System.Drawing.Size(121, 25);
            this.txt_TamMuestra.TabIndex = 0;
            this.txt_TamMuestra.ValidatingType = typeof(int);
            // 
            // FrmChiCuadradoMCMixto
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 17F);
            this.ClientSize = new System.Drawing.Size(1292, 692);
            this.Controls.Add(this.chart1);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.grid012);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.grid011);
            this.Name = "FrmChiCuadradoMCMixto";
            this.Load += new System.EventHandler(this.FrmChiCuadradoMCMixto_Load);
            this.Controls.SetChildIndex(this.grid011, 0);
            this.Controls.SetChildIndex(this.groupBox3, 0);
            this.Controls.SetChildIndex(this.grid012, 0);
            this.Controls.SetChildIndex(this.groupBox4, 0);
            this.Controls.SetChildIndex(this.pnl_titulo, 0);
            this.Controls.SetChildIndex(this.chart1, 0);
            this.pnl_titulo.ResumeLayout(false);
            this.pnl_titulo.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.btn_restaurar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_minimizar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_maximizar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_cerrar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).EndInit();
            this.groupBox4.ResumeLayout(false);
            this.groupBox4.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid012)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.grid011)).EndInit();
            this.groupBox3.ResumeLayout(false);
            this.groupBox5.ResumeLayout(false);
            this.groupBox5.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DataVisualization.Charting.Chart chart1;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtNivelSignificancia;
        private System.Windows.Forms.TextBox txt_ValorTabulado;
        private ClasesBase.Grid01 grid012;
        private ClasesBase.Grid01 grid011;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.ComboBox cmb_subintervalos;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.MaskedTextBox txt_TamMuestra;
        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.MaskedTextBox txtC;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.MaskedTextBox txtKlineal;
        private System.Windows.Forms.MaskedTextBox txtM;
        private System.Windows.Forms.MaskedTextBox txtA;
        private System.Windows.Forms.MaskedTextBox txtG;
        private System.Windows.Forms.MaskedTextBox txtX;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
    }
}
