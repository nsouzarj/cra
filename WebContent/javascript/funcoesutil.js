/**
 * 
 */

/* Autor: Mario Costa */
/* Variavel global */
 var cont=1;
 var cont1=1;
 
function formatar_moeda(campo, separador_milhar, separador_decimal, tecla) {
	//var sep = 0;
	var key = '';
	var i = j = 0;
	var len = len2 = 0;
	var strCheck = '0123456789';
	var aux = aux2 = '';
	var whichCode = (window.Event) ? tecla.which : tecla.keyCode;

	if (whichCode == 13) return true; // Tecla Enter
	if (whichCode == 8) return true; // Tecla Delete
	key = String.fromCharCode(whichCode); // Pegando o valor digitado
	if (strCheck.indexOf(key) == -1) return false; // Valor inv�lido (n�o inteiro)
	len = campo.value.length;
	for(i = 0; i < len; i++)
	if ((campo.value.charAt(i) != '0') && (campo.value.charAt(i) != separador_decimal)) break;
	aux = '';
	for(; i < len; i++)
	if (strCheck.indexOf(campo.value.charAt(i))!=-1) aux += campo.value.charAt(i);
	aux += key;
	len = aux.length;
	if (len == 0) campo.value = '';
	if (len == 1) campo.value = '0'+ separador_decimal + '0' + aux;
	if (len == 2) campo.value = '0'+ separador_decimal + aux;

	if (len > 2) {
		aux2 = '';

		for (j = 0, i = len - 3; i >= 0; i--) {
			if (j == 3) {
				aux2 += separador_milhar;
				j = 0;
			}
			aux2 += aux.charAt(i);
			j++;
		}

		campo.value = '';
		len2 = aux2.length;
		for (i = len2 - 1; i >= 0; i--)
		campo.value += aux2.charAt(i);
		campo.value += separador_decimal + aux.substr(len - 2, len);
	}

	return false;
}

/*Funcao de mascara campos */
function mascaravalor(src, mask){
	var i = src.value.length;
	var saida = mask.substring(1,2);
	var texto = mask.substring(i);
	if (texto.substring(0,1) != saida){
	   src.value += texto.substring(0,1);
	}
} 

function confirmarRemover() {
	if( confirm("Deseja realmente excluir ?") )	{
		return true;
	}
	return false;
} 


function Limpar(valor, validos) {
	// retira caracteres invalidos da string
	var result = "";
	var aux;
	for (var i=0; i < valor.length; i++) {
	  aux = validos.indexOf(valor.substring(i, i+1));
	if (aux>=0) {
	   result += aux;
    	}
	}
	return result;
}

	//Formata n�mero tipo moeda usando o evento onKeyDown

	function Formata(campo,tammax,teclapres,decimal) {
	var tecla = teclapres.keyCode;
	vr = Limpar(campo.value,"0123456789");
	tam = vr.length;
	dec=decimal;

	if (tam < tammax && tecla != 8){ tam = vr.length + 1 ; }

	if (tecla == 8 )
	   { tam = tam - 1 ; }

	if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 )
	   {

	if ( tam <= dec )
	  { campo.value = vr ; }

	if ( (tam > dec) && (tam <= 5) ){
	  campo.value = vr.substr( 0, tam - 2 ) + "," + vr.substr( tam - dec, tam ) ; }
	if ( (tam >= 6) && (tam <= 8) ){
	  campo.value = vr.substr( 0, tam - 5 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; 
	}
	if ( (tam >= 9) && (tam <= 11) ){
	  campo.value = vr.substr( 0, tam - 8 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; }
	if ( (tam >= 12) && (tam <= 14) ){
	  campo.value = vr.substr( 0, tam - 11 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; }
	if ( (tam >= 15) && (tam <= 17) ){
	  campo.value = vr.substr( 0, tam - 14 ) + "." + vr.substr( tam - 14, 3 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - 2, tam ) ;}
	} 

}
	
	


function Validar(theCPF)
{
  
  if (theCPF.value == "")
  {
	windows.alert("Campo inv�lido. � necess�rio informar o CPF ou CNPJ");
    theCPF.focus();
    return (false);
  }
  if (((theCPF.value.length == 11) && (theCPF.value == "11111111111") ||
		  (theCPF.value == "22222222222") || (theCPF.value == "33333333333") || 
		  (theCPF.value == "44444444444") || (theCPF.value == "55555555555") ||
		  (theCPF.value == "66666666666") || (theCPF.value == "77777777777") ||
		  (theCPF.value == "88888888888") || (theCPF.value == "99999999999") || 
		  (theCPF.value == "00000000000")))
  {
    windows.alert("CPF/CNPJ inv�lido.");
    
    theCPF.focus();
    return (false);
  }


  if (!((theCPF.value.length == 11) || (theCPF.value.length == 14)))
  {
	windows.alert("CPF/CNPJ inv�lido.");
    theCPF.focus();
    return (false);
  }

  var checkOK = "0123456789";
  var checkStr = theCPF.value;
  var allValid = true;
  var allNum = "";
  for (var i = 0;  i < checkStr.length;  i++)
  {
    ch = checkStr.charAt(i);
    for (var j = 0;  j < checkOK.length;  j++)
      if (ch == checkOK.charAt(j))
        break;
    if (j == checkOK.length)
    {
      allValid = false;
      break;
    }
    allNum += ch;
  }
  if (!allValid)
  {
    windows.alert("Favor preencher somente com d�gitos o campo CPF/CNPJ.");
    theCPF.focus();
    return (false);
  }

  var chkVal = allNum;
  var prsVal = parseFloat(allNum);
  if (chkVal != "" && !(prsVal > "0"))
  {
    windows.alert("CPF zerado !");
    theCPF.focus();
    return (false);
  }

if (theCPF.value.length == 11)
{
  var tot = 0;

  for (var i = 2;  i <= 10;  i++)
    tot += i * parseInt(checkStr.charAt(10 - i));

  if ((tot * 10 % 11 % 10) != parseInt(checkStr.charAt(9)))
  {
    windows.alert("CPF/CNPJ inv�lido.");
    theCPF.focus();
    return (false);
  }
  
  tot = 0;
  
  for (var i = 2;  i <= 11;  i++)
    tot += i * parseInt(checkStr.charAt(11 - i));

  if ((tot * 10 % 11 % 10) != parseInt(checkStr.charAt(10)))
  {
    windows.alert("CPF/CNPJ inv�lido.");
    theCPF.focus();
    return (false);
  }
}
else
{
  var tot  = 0;
  var peso = 2;
  
  for (var i = 0;  i <= 11;  i++)
  {
    tot += peso * parseInt(checkStr.charAt(11 - i));
    peso++;
    if (peso == 10)
    {
        peso = 2;
    }
  }

  if ((tot * 10 % 11 % 10) != parseInt(checkStr.charAt(12)))
  {
	windows.alert("CPF/CNPJ inv�lido.");
    theCPF.focus();
    return (false);
  }
  
  tot  = 0;
  peso = 2;
  
  for (var i = 0;  i <= 12;  i++)
  {
    tot += peso * parseInt(checkStr.charAt(12 - i));
    peso++;
    if (peso == 10)
    {
        peso = 2;
    }
  }

  if ((tot * 10 % 11 % 10) != parseInt(checkStr.charAt(13)))
  {
    windows.alert("CPF/CNPJ inv�lido.");
    theCPF.focus();	
    return (false);
  }
}
  return(true);
} 


function validarCPF(cpfcli){
	var cpf=cpfcli;  
	if (cpfcli.value == "")
	  {
		windows.alert("Campo inv�lido. � necess�rio informar o CPF ou CNPJ");
	    theCPF.focus();
	    return (false);
	  }
	   
	   if(cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111" ||
		  cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" ||
		  cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" ||
		  cpf == "88888888888" || cpf == "99999999999"){
		  window.alert("CPF inv�lido. Tente novamente.");
		  cpflcli=="";
		  return false;
	   }

	   soma = 0;
	   for(var i = 0; i < 9; i++)
	   	 soma += parseInt(cpf.charAt(i)) * (10 - i);
	   resto = 11 - (soma % 11);
	   if(resto == 10 || resto == 11)
		 resto = 0;
	   if(resto != parseInt(cpf.charAt(9))){
		 window.alert("CPF inv�lido. Tente novamente.");
		 cpflcli=="";
		 return false;
	   }
	   soma = 0;
	   for(i = 0; i < 10; i ++)
		 soma += parseInt(cpf.charAt(i)) * (11 - i);
	   resto = 11 - (soma % 11);
	   if(resto == 10 || resto == 11)
		 resto = 0;
	   if(resto != parseInt(cpf.charAt(10))){
	     window.alert("CPF inv�lido. Tente novamente.");
	     cpflcli=="";
		 return false;
	   }
	   return true;
	 }

 function entra(){  
	   document.getElementById("cliente:botaobuscar").click();  
	} 
 
 
 function avisobordero(){
	
	 if (cont == 1){
		 window.alert("ANTES DE INSERIR UM OU MAIS BORDEROS POR FAVOR CLICK NO BOT�O ATUALIZAR DA PRIMEIRA ABA PELOS MENOS UMA VEZ!"); 
		 cont++;
	 } 
	
 }
 
 function avisografico(){
	 if(cont1==1){
	 window.alert("ANTES DE GERAR OS GRÁFICOS POR FAVOR CLICK NO BOT�O ATUALIZAR DA PRIMEIRA ABA PELO MENOS UMA VEZ!");
	 cont1++;
	 }
 }
 
function sair() {
	window.alert("Obrigado por usar o sistema de consulta de cobran�a via web volte sempre.");
 }

function verificavazio(form){
	var campo=form[form.id+":obs1"].value;
	if(campo==""){
		window.alert("Atencao o campo esta vazio");
		return false;
	}else{
		return true;
		form.submit();
	}
}


function abrinovajanela(janela){
 setTimeout(window.open(janela,"Dados da Solicita��o","location=yes,menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes",false),2000);
}

function fechajanela(janela){
	window.close();
}

function fecha(){
	window.close();
}

function relertela(){
	location.reload(true);
}


function verificavazio(campo){
	if (campo==""){
		window.alert("O campo "+campo+" está vazio");
		return false;
	}
	else{
		return true;
	}
}






//Mostra a solitacao finalizada
function Finalizada(){
	window.alert("Sua solicita��o foi finalizada com sucesso!!");
}
function avisa(){
	window.alert("Solicita��o finalizada com sucesso favor sair da mesma."+"\n"+"FAz  a busca novamente para atualizar a solicta��o.");
	reler();
}



//Verifica a hora da solicitacao
function VerificaHora(campo){
    var hora=campo.value;
    var min=campo.value;
	var num = parseInt(hora.substring(0,2));
    var min2 = parseInt(min.substring(3,5));
    if ((num > 19 || num < 7) || min2 > 60){
    	window.alert("A HORA ESTA FORA DE PADRAO!");
    	return false;
    }else{
    	return true;
    }
    
    
}