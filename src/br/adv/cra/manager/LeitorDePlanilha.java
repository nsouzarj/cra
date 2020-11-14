package br.adv.cra.manager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.primefaces.model.UploadedFile;
import br.adv.cra.entity.ProcessoCPPRO;
import br.adv.cra.persistence.ProcessoCPPRODAO;

@ManagedBean(name = "importador",eager=true)
@SessionScoped
public class LeitorDePlanilha {
	private Integer contador;
	private UploadedFile file;
	private File arquivo;
	private String nome;
	private String recebe;
	private String faz;
	private String faztudo;
	
	public LeitorDePlanilha(){
		
	}
		
	public LeitorDePlanilha(Integer contador, UploadedFile file, File arquivo,
			String nome, String recebe, String faz) {
		this.contador = contador;
		this.file = file;
		this.arquivo = arquivo;
		this.nome = nome;
		this.recebe = recebe;
		this.faz = faz;
	}
	
   /**
    * Salva nos servidor
    * @param event
    * @return
    * @throws FileNotFoundException
    */
	public String EnviarPlanilha()
			throws FileNotFoundException {
		UploadedFile arquivoanexo = file;
		File arquivocja = new File(arquivoanexo.getFileName());	
		try {
			InputStream inputStream = arquivoanexo.getInputstream();
			// event.getFile().getInputstream();
			OutputStream out = new FileOutputStream(new File(
					"C:\\planilhacppro\\"+ arquivocja.getAbsoluteFile().getName()));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Planilha salva com sucesso!", ""));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error ao salvar arquivo no servidor", ""));
		}

		return "";
	}

     /**
      * Ler a planilha
      */
	public void Leer() {
		try {
			//Antes envia a panilah para o servidor
			EnviarPlanilha();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			contador = 0;
			nome = "C:\\PLANILHACPPRO\\" + file.getFileName();
			recebe = "";
		} catch (Exception e) {
			FacesContext cont = FacesContext.getCurrentInstance();
			cont = FacesContext.getCurrentInstance();
			cont.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Selecione a planilha do CPPRO.", ""));
		}
		
		
		try {
			// Abre a planilha
			Workbook workbook = Workbook.getWorkbook(new File(nome));
			Sheet sheet = workbook.getSheet(0);
			Integer linhas = sheet.getRows();
			ProcessoCPPRODAO processoCPPRODAO = new ProcessoCPPRODAO();
			// ProcessoCPPRO processoCPPRO = new ProcessoCPPRO();
			for (int i = 1; i < linhas; i++) {

				ProcessoCPPRO processoCPPRO = new ProcessoCPPRO();
				// Ler as celulas
				Cell NUMPROCESSO = sheet.getCell(0, i);

				Cell CLIENTE = sheet.getCell(1, i);

				Cell ORGAOINICIAL = sheet.getCell(2, i);

				Cell PARTECONTRARIA = sheet.getCell(3, i);

				Cell ACAO = sheet.getCell(4, i);

				Cell LOCALIZADOR = sheet.getCell(5, i);

				Cell ELETRONICO = sheet.getCell(6, i);
				
				// Transforma em string os dados da coluna
				String as1 = NUMPROCESSO.getContents();

				String as2 = CLIENTE.getContents();

				String as3 = ORGAOINICIAL.getContents();

				String as4 = PARTECONTRARIA.getContents();

				String as5 = ACAO.getContents();

				String as6 = LOCALIZADOR.getContents();

				String as7 = ELETRONICO.getContents();

				System.out.println("NUMPROCESSO: " + as1.toUpperCase());

				System.out.println("CLIENTE: " + as2.toUpperCase());

				System.out.println("ORGAOINICIAL: " + as3.toUpperCase());

				System.out.println("PARTECONTRARIA: " + as4.toUpperCase());

				System.out.println("ACAO: " + as5.toUpperCase().trim());

				System.out.println("LOCALIZADOR: " + as6.toUpperCase());

				System.out.println("ELETRONICO: " + as7.toUpperCase());

				// Atribui nos campos

				processoCPPRO.setNumprocesso(as1.toUpperCase());

				processoCPPRO.setCliente(as2.toUpperCase());

				processoCPPRO.setOrgaoinicial(as3.toUpperCase().trim());

				processoCPPRO.setPartecontraria(as4.toUpperCase());

				processoCPPRO.setAcao(as5.toUpperCase().trim());

				processoCPPRO.setLocalizador(as6.toUpperCase());

				processoCPPRO.setEletronico(as7.toUpperCase());

				// Antes de incluir verifica se ja existe o processo na base
				// caso exista apenas atulaiza as informações

				try {
					ProcessoCPPRO cppro = new ProcessoCPPRO();
					cppro = processoCPPRODAO.BuscarProcesso(as1);
					if (cppro == null) {
						processoCPPRODAO.Salvar(processoCPPRO);
						processoCPPRO = null;
						contador = contador + 1;
						faz = "NÚMERO: " + contador + " NUMPROCESSO: "
								+ as1.toUpperCase() + "  CLIENTE: "
								+ as2.toUpperCase() + " PARTECONTRARIA: "
								+ as4.toUpperCase();
						recebe = recebe + faz + "\n";
					} else if (cppro != null) {
						cppro.setNumprocesso(as1.toUpperCase());

						cppro.setCliente(as2.toUpperCase());

						cppro.setOrgaoinicial(as3.toUpperCase().trim());

						cppro.setPartecontraria(as4.toUpperCase());

						cppro.setAcao(as5.toUpperCase().trim());

						cppro.setLocalizador(as6.toUpperCase());

						cppro.setEletronico(as7.toUpperCase());
						
						//Pega para atualizar
						processoCPPRODAO.Alterar(cppro);
						
						cppro = null;
						contador = contador + 1;
						faz = "ATUALIZOU NÚMERO: " + contador + " NUMPROCESSO: "
								+ as1.toUpperCase() + "  CLIENTE: "
								+ as2.toUpperCase() + " PARTECONTRARIA: "
								+ as4.toUpperCase();
						recebe = recebe + faz + "\n";
					}
				} catch (Exception e) {

					FacesContext cont = FacesContext.getCurrentInstance();
					cont = FacesContext.getCurrentInstance();
					cont.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Erro ao importar a planilha do CPPRO.", ""));
				}
			}

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			FacesContext cont = FacesContext.getCurrentInstance();
			cont = FacesContext.getCurrentInstance();
			cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao importar a planilha do CPPRO.", ""));

		} catch (IOException e) {
			FacesContext cont = FacesContext.getCurrentInstance();
			cont = FacesContext.getCurrentInstance();
			cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao importar a planilha do CPPRO.", ""));
		}
		// Mostra quanto foi importado
		FacesContext cont = FacesContext.getCurrentInstance();
		cont = FacesContext.getCurrentInstance();
		cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"IMPORTADO COM SUCESSO!" + "\n" + contador + " IMPORTADOS",
				""));
		System.out.println("TOTAL EXPORTADO: " + contador);
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public String getRecebe() {
		return recebe;
	}

	public void setRecebe(String recebe) {
		this.recebe = recebe;
	}

}
