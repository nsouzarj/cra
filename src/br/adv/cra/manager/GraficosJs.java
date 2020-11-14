package br.adv.cra.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name="grafico")
@ViewScoped
public class GraficosJs implements Serializable {
	private static final long serialVersionUID = 1L;
	private PieChartModel model;
	private List<Teste> teste;

	@PostConstruct
	public void init() {
		model= new PieChartModel();
		model.set("Janeiro", 90);
		model.set("Fevereiro", 80);
		model.set("Janeiro", 100);
	//	model.setTitle("DEMOSTRATIVO DE DESEMPENHO");
	//	model.setShowDataLabels(true);
	//	model.setLegendPosition("e");
	//	model.setShowDatatip(true);
	
	}
	

	public PieChartModel getModel() {
		return model;
	}


	public void setModel(PieChartModel model) {
		this.model = model;
	}


	public class Teste {
		private String mes;
		private int quantidade;

		public Teste(String mes, int quantidade) {
			super();
			this.mes = mes;
			this.quantidade = quantidade;
		}

		public String getMes() {
			return mes;
		}

		public void setMes(String mes) {
			this.mes = mes;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}

	}

}
