
namespace SIMULACION_TP1_V1._0._1.Formularios
{
    partial class FrmGenerador
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
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.rbMultiplicativo = new System.Windows.Forms.RadioButton();
            this.rbLineal = new System.Windows.Forms.RadioButton();
            this.label2 = new System.Windows.Forms.Label();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.grid011 = new SIMULACION_TP1_V1._0._1.ClasesBase.Grid01();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.txtC = new System.Windows.Forms.MaskedTextBox();
            this.txtN = new System.Windows.Forms.MaskedTextBox();
            this.txtK = new System.Windows.Forms.MaskedTextBox();
            this.txtM = new System.Windows.Forms.MaskedTextBox();
            this.txtA = new System.Windows.Forms.MaskedTextBox();
            this.txtG = new System.Windows.Forms.MaskedTextBox();
            this.txtX = new System.Windows.Forms.MaskedTextBox();
            this.btnGenerar = new System.Windows.Forms.Button();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.pnl_titulo.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.btn_restaurar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_minimizar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_maximizar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_cerrar)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.groupBox3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid011)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // pnl_titulo
            // 
            this.pnl_titulo.Margin = new System.Windows.Forms.Padding(3);
            this.pnl_titulo.Size = new System.Drawing.Size(1337, 50);
            // 
            // btn_restaurar
            // 
            this.btn_restaurar.Location = new System.Drawing.Point(1251, 7);
            this.btn_restaurar.Margin = new System.Windows.Forms.Padding(3);
            // 
            // btn_minimizar
            // 
            this.btn_minimizar.Location = new System.Drawing.Point(1211, 7);
            this.btn_minimizar.Margin = new System.Windows.Forms.Padding(3);
            // 
            // btn_maximizar
            // 
            this.btn_maximizar.Location = new System.Drawing.Point(1251, 7);
            this.btn_maximizar.Margin = new System.Windows.Forms.Padding(3);
            // 
            // btn_cerrar
            // 
            this.btn_cerrar.Location = new System.Drawing.Point(1291, 7);
            this.btn_cerrar.Margin = new System.Windows.Forms.Padding(3);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Segoe UI", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 95);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(292, 30);
            this.label1.TabIndex = 11;
            this.label1.Text = "Generadores Congruenciales";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.rbMultiplicativo);
            this.groupBox1.Controls.Add(this.rbLineal);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Location = new System.Drawing.Point(36, 178);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(248, 156);
            this.groupBox1.TabIndex = 12;
            this.groupBox1.TabStop = false;
            // 
            // rbMultiplicativo
            // 
            this.rbMultiplicativo.AutoSize = true;
            this.rbMultiplicativo.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rbMultiplicativo.Location = new System.Drawing.Point(11, 103);
            this.rbMultiplicativo.Name = "rbMultiplicativo";
            this.rbMultiplicativo.Size = new System.Drawing.Size(122, 25);
            this.rbMultiplicativo.TabIndex = 10;
            this.rbMultiplicativo.TabStop = true;
            this.rbMultiplicativo.Text = "Multiplicativo";
            this.rbMultiplicativo.UseVisualStyleBackColor = true;
            this.rbMultiplicativo.CheckedChanged += new System.EventHandler(this.rbMultiplicativo_CheckedChanged);
            // 
            // rbLineal
            // 
            this.rbLineal.AutoSize = true;
            this.rbLineal.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rbLineal.Location = new System.Drawing.Point(11, 63);
            this.rbLineal.Name = "rbLineal";
            this.rbLineal.Size = new System.Drawing.Size(69, 25);
            this.rbLineal.TabIndex = 9;
            this.rbLineal.TabStop = true;
            this.rbLineal.Text = "Lineal";
            this.rbLineal.UseVisualStyleBackColor = true;
            this.rbLineal.CheckedChanged += new System.EventHandler(this.rbLineal_CheckedChanged);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(5, 20);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(167, 21);
            this.label2.TabIndex = 7;
            this.label2.Text = "Seleccione una opcion:";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.grid011);
            this.groupBox3.Location = new System.Drawing.Point(427, 178);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(883, 566);
            this.groupBox3.TabIndex = 14;
            this.groupBox3.TabStop = false;
            this.groupBox3.Enter += new System.EventHandler(this.groupBox3_Enter);
            // 
            // grid011
            // 
            this.grid011.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.grid011.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.grid011.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid011.Location = new System.Drawing.Point(16, 22);
            this.grid011.Name = "grid011";
            this.grid011.ReadOnly = true;
            this.grid011.RowHeadersWidth = 51;
            this.grid011.RowTemplate.Height = 24;
            this.grid011.Size = new System.Drawing.Size(252, 524);
            this.grid011.TabIndex = 0;
            this.grid011.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.grid011_CellContentClick);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(9, 73);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(47, 21);
            this.label3.TabIndex = 11;
            this.label3.Text = "X0 = ";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(20, 171);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(38, 21);
            this.label4.TabIndex = 12;
            this.label4.Text = "g = ";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(21, 122);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(37, 21);
            this.label5.TabIndex = 13;
            this.label5.Text = "k = ";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(21, 269);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(37, 21);
            this.label6.TabIndex = 13;
            this.label6.Text = "a = ";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(15, 316);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(43, 21);
            this.label7.TabIndex = 13;
            this.label7.Text = "m = ";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(24, 218);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(36, 21);
            this.label8.TabIndex = 14;
            this.label8.Text = "c = ";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label9.Location = new System.Drawing.Point(20, 25);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(38, 21);
            this.label9.TabIndex = 21;
            this.label9.Text = "n = ";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label9);
            this.groupBox2.Controls.Add(this.txtC);
            this.groupBox2.Controls.Add(this.txtN);
            this.groupBox2.Controls.Add(this.label8);
            this.groupBox2.Controls.Add(this.txtK);
            this.groupBox2.Controls.Add(this.txtM);
            this.groupBox2.Controls.Add(this.txtA);
            this.groupBox2.Controls.Add(this.txtG);
            this.groupBox2.Controls.Add(this.txtX);
            this.groupBox2.Controls.Add(this.label7);
            this.groupBox2.Controls.Add(this.label6);
            this.groupBox2.Controls.Add(this.label5);
            this.groupBox2.Controls.Add(this.label4);
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Location = new System.Drawing.Point(36, 379);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(248, 366);
            this.groupBox2.TabIndex = 13;
            this.groupBox2.TabStop = false;
            // 
            // txtC
            // 
            this.txtC.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtC.Location = new System.Drawing.Point(75, 218);
            this.txtC.Mask = "999999999";
            this.txtC.Name = "txtC";
            this.txtC.Size = new System.Drawing.Size(108, 26);
            this.txtC.TabIndex = 27;
            this.txtC.ValidatingType = typeof(int);
            // 
            // txtN
            // 
            this.txtN.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtN.Location = new System.Drawing.Point(75, 22);
            this.txtN.Mask = "999999999";
            this.txtN.Name = "txtN";
            this.txtN.Size = new System.Drawing.Size(108, 26);
            this.txtN.TabIndex = 23;
            this.txtN.ValidatingType = typeof(int);
            // 
            // txtK
            // 
            this.txtK.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtK.Location = new System.Drawing.Point(73, 122);
            this.txtK.Mask = "999999999";
            this.txtK.Name = "txtK";
            this.txtK.Size = new System.Drawing.Size(108, 26);
            this.txtK.TabIndex = 28;
            this.txtK.ValidatingType = typeof(int);
            // 
            // txtM
            // 
            this.txtM.Enabled = false;
            this.txtM.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtM.Location = new System.Drawing.Point(75, 316);
            this.txtM.Mask = "999999999";
            this.txtM.Name = "txtM";
            this.txtM.Size = new System.Drawing.Size(108, 26);
            this.txtM.TabIndex = 26;
            this.txtM.ValidatingType = typeof(int);
            // 
            // txtA
            // 
            this.txtA.Enabled = false;
            this.txtA.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtA.Location = new System.Drawing.Point(75, 269);
            this.txtA.Mask = "999999999";
            this.txtA.Name = "txtA";
            this.txtA.Size = new System.Drawing.Size(108, 26);
            this.txtA.TabIndex = 25;
            this.txtA.ValidatingType = typeof(int);
            // 
            // txtG
            // 
            this.txtG.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtG.Location = new System.Drawing.Point(75, 171);
            this.txtG.Mask = "999999999";
            this.txtG.Name = "txtG";
            this.txtG.Size = new System.Drawing.Size(108, 26);
            this.txtG.TabIndex = 24;
            this.txtG.ValidatingType = typeof(int);
            // 
            // txtX
            // 
            this.txtX.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtX.Location = new System.Drawing.Point(75, 73);
            this.txtX.Mask = "999999999";
            this.txtX.Name = "txtX";
            this.txtX.Size = new System.Drawing.Size(108, 26);
            this.txtX.TabIndex = 22;
            this.txtX.ValidatingType = typeof(int);
            // 
            // btnGenerar
            // 
            this.btnGenerar.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnGenerar.Location = new System.Drawing.Point(300, 706);
            this.btnGenerar.Name = "btnGenerar";
            this.btnGenerar.Size = new System.Drawing.Size(101, 38);
            this.btnGenerar.TabIndex = 15;
            this.btnGenerar.Text = "Generar";
            this.btnGenerar.UseVisualStyleBackColor = true;
            this.btnGenerar.Click += new System.EventHandler(this.btnGenerar_Click);
            // 
            // FrmGenerador
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 17F);
            this.ClientSize = new System.Drawing.Size(1333, 766);
            this.Controls.Add(this.btnGenerar);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.label1);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "FrmGenerador";
            this.Load += new System.EventHandler(this.FrmGenerador_Load);
            this.Controls.SetChildIndex(this.label1, 0);
            this.Controls.SetChildIndex(this.groupBox1, 0);
            this.Controls.SetChildIndex(this.groupBox2, 0);
            this.Controls.SetChildIndex(this.groupBox3, 0);
            this.Controls.SetChildIndex(this.pnl_titulo, 0);
            this.Controls.SetChildIndex(this.btnGenerar, 0);
            this.pnl_titulo.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.btn_restaurar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_minimizar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_maximizar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btn_cerrar)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid011)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton rbMultiplicativo;
        private System.Windows.Forms.RadioButton rbLineal;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.MaskedTextBox txtC;
        private System.Windows.Forms.MaskedTextBox txtN;
        private System.Windows.Forms.MaskedTextBox txtK;
        private System.Windows.Forms.MaskedTextBox txtM;
        private System.Windows.Forms.MaskedTextBox txtA;
        private System.Windows.Forms.MaskedTextBox txtG;
        private System.Windows.Forms.MaskedTextBox txtX;
        private System.Windows.Forms.Button btnGenerar;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private ClasesBase.Grid01 grid011;
    }
}
