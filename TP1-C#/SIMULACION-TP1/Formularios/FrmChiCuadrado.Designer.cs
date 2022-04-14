
namespace SIMULACION_TP1_V1._0._1.Formularios
{
    partial class FrmChiCuadrado
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
            this.cmb_subintervalos = new System.Windows.Forms.ComboBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.txt_TamMuestra = new System.Windows.Forms.MaskedTextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.grid011 = new SIMULACION_TP1_V1._0._1.ClasesBase.Grid01();
            this.grid012 = new SIMULACION_TP1_V1._0._1.ClasesBase.Grid01();
            this.txtNivelSignificancia = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.txt_ValorTabulado = new System.Windows.Forms.TextBox();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.chart1 = new System.Windows.Forms.DataVisualization.Charting.Chart();
            this.pnl_titulo.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.btn_restaurar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_minimizar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_maximizar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_cerrar)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid011)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.grid012)).BeginInit();
            this.groupBox4.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).BeginInit();
            this.SuspendLayout();
            // 
            // pnl_titulo
            // 
            this.pnl_titulo.Controls.Add(this.label1);
            this.pnl_titulo.Size = new System.Drawing.Size(1287, 50);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_cerrar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_maximizar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_minimizar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.btn_restaurar, 0);
            this.pnl_titulo.Controls.SetChildIndex(this.label1, 0);
            // 
            // btn_restaurar
            // 
            this.btn_restaurar.Location = new System.Drawing.Point(1202, 7);
            // 
            // btn_minimizar
            // 
            this.btn_minimizar.Location = new System.Drawing.Point(1160, 7);
            // 
            // btn_maximizar
            // 
            this.btn_maximizar.Location = new System.Drawing.Point(1202, 7);
            // 
            // btn_cerrar
            // 
            this.btn_cerrar.Location = new System.Drawing.Point(1240, 7);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Segoe UI Black", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(16, 9);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(140, 25);
            this.label1.TabIndex = 6;
            this.label1.Text = "Chi-Cuadrado";
            this.label1.Click += new System.EventHandler(this.label1_Click);
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
            this.cmb_subintervalos.SelectedIndexChanged += new System.EventHandler(this.cmb_subintervalos_SelectedIndexChanged);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.cmb_subintervalos);
            this.groupBox2.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox2.Location = new System.Drawing.Point(273, 25);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(4);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(4);
            this.groupBox2.Size = new System.Drawing.Size(216, 95);
            this.groupBox2.TabIndex = 31;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Subintervalos (k)";
            this.groupBox2.Enter += new System.EventHandler(this.groupBox2_Enter);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.txt_TamMuestra);
            this.groupBox1.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox1.Location = new System.Drawing.Point(12, 25);
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
            this.txt_TamMuestra.MaskInputRejected += new System.Windows.Forms.MaskInputRejectedEventHandler(this.maskedTextBox1_MaskInputRejected);
            // 
            // button1
            // 
            this.button1.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.button1.Location = new System.Drawing.Point(579, 44);
            this.button1.Margin = new System.Windows.Forms.Padding(4);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(143, 66);
            this.button1.TabIndex = 32;
            this.button1.Text = "Generar";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // groupBox3
            // 
            this.groupBox3.BackColor = System.Drawing.Color.Transparent;
            this.groupBox3.Controls.Add(this.groupBox2);
            this.groupBox3.Controls.Add(this.button1);
            this.groupBox3.Controls.Add(this.groupBox1);
            this.groupBox3.Location = new System.Drawing.Point(12, 57);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(812, 141);
            this.groupBox3.TabIndex = 33;
            this.groupBox3.TabStop = false;
            this.groupBox3.Enter += new System.EventHandler(this.groupBox3_Enter);
            // 
            // grid011
            // 
            this.grid011.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.grid011.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid011.Location = new System.Drawing.Point(24, 215);
            this.grid011.Name = "grid011";
            this.grid011.ReadOnly = true;
            this.grid011.Size = new System.Drawing.Size(205, 336);
            this.grid011.TabIndex = 34;
            // 
            // grid012
            // 
            this.grid012.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.grid012.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid012.Location = new System.Drawing.Point(285, 215);
            this.grid012.Name = "grid012";
            this.grid012.Size = new System.Drawing.Size(539, 336);
            this.grid012.TabIndex = 35;
            this.grid012.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.grid012_CellContentClick);
            // 
            // txtNivelSignificancia
            // 
            this.txtNivelSignificancia.Location = new System.Drawing.Point(9, 41);
            this.txtNivelSignificancia.Name = "txtNivelSignificancia";
            this.txtNivelSignificancia.Size = new System.Drawing.Size(102, 25);
            this.txtNivelSignificancia.TabIndex = 37;
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
            // txt_ValorTabulado
            // 
            this.txt_ValorTabulado.Location = new System.Drawing.Point(9, 101);
            this.txt_ValorTabulado.Name = "txt_ValorTabulado";
            this.txt_ValorTabulado.Size = new System.Drawing.Size(102, 25);
            this.txt_ValorTabulado.TabIndex = 39;
            this.txt_ValorTabulado.TextChanged += new System.EventHandler(this.txt_ValorTabulado_TextChanged);
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.label2);
            this.groupBox4.Controls.Add(this.label4);
            this.groupBox4.Controls.Add(this.txtNivelSignificancia);
            this.groupBox4.Controls.Add(this.txt_ValorTabulado);
            this.groupBox4.Location = new System.Drawing.Point(927, 393);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(249, 158);
            this.groupBox4.TabIndex = 41;
            this.groupBox4.TabStop = false;
            this.groupBox4.Enter += new System.EventHandler(this.groupBox4_Enter);
            // 
            // chart1
            // 
            chartArea2.Name = "ChartArea1";
            this.chart1.ChartAreas.Add(chartArea2);
            legend2.Name = "Legend1";
            this.chart1.Legends.Add(legend2);
            this.chart1.Location = new System.Drawing.Point(857, 66);
            this.chart1.Name = "chart1";
            series2.ChartArea = "ChartArea1";
            series2.Legend = "Legend1";
            series2.Name = "Series1";
            this.chart1.Series.Add(series2);
            this.chart1.Size = new System.Drawing.Size(375, 287);
            this.chart1.TabIndex = 42;
            this.chart1.Text = "chart1";
            this.chart1.Click += new System.EventHandler(this.chart1_Click);
            // 
            // FrmChiCuadrado
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 17F);
            this.ClientSize = new System.Drawing.Size(1284, 636);
            this.Controls.Add(this.chart1);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.grid012);
            this.Controls.Add(this.grid011);
            this.Controls.Add(this.groupBox3);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "FrmChiCuadrado";
            this.Text = "Chi-Cuadrado";
            this.Load += new System.EventHandler(this.FrmChiCuadrado_Load);
            this.Controls.SetChildIndex(this.pnl_titulo, 0);
            this.Controls.SetChildIndex(this.groupBox3, 0);
            this.Controls.SetChildIndex(this.grid011, 0);
            this.Controls.SetChildIndex(this.grid012, 0);
            this.Controls.SetChildIndex(this.groupBox4, 0);
            this.Controls.SetChildIndex(this.chart1, 0);
            this.pnl_titulo.ResumeLayout(false);
            this.pnl_titulo.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.btn_restaurar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_minimizar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_maximizar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_cerrar)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid011)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.grid012)).EndInit();
            this.groupBox4.ResumeLayout(false);
            this.groupBox4.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox cmb_subintervalos;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.GroupBox groupBox3;
        private ClasesBase.Grid01 grid011;
        private ClasesBase.Grid01 grid012;
        private System.Windows.Forms.TextBox txtNivelSignificancia;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txt_ValorTabulado;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.MaskedTextBox txt_TamMuestra;
        private System.Windows.Forms.DataVisualization.Charting.Chart chart1;
    }
}
