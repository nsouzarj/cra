SELECT 
 DATE_PART('MONTH',solicitacao.dataprazo) AS mes, count (*),sum(solicitacao.valor)
FROM 
  public.solicitacao, 
  public.renumeracao, 
  public.tiposolicitacao,
  public.processo
WHERE 
  renumeracao.idrenumeracao = solicitacao.idrenumeracao AND 
  solicitacao.idprocesso=processo.idprocesso AND
  renumeracao.idsolicitacao = tiposolicitacao.idtiposolicitacao AND
  solicitacao.idstatus = 7 AND  tiposolicitacao.especie = 'AUDIENCIA' AND
  processo.adverso like '$BRADESCO%'
  
  --solicitacao.dataprazo >='01/05/2013' and 
 -- solicitacao.dataprazo <='31/05/2013' 

group by mes
order by mes


-------------------------------

SELECT 
 DATE_PART('MONTH',solicitacao.dataprazo) AS mes, count (*),sum(solicitacao.valor)
FROM 
  public.solicitacao, 
  public.renumeracao, 
  public.tiposolicitacao,
  public.processo
WHERE 
  renumeracao.idrenumeracao = solicitacao.idrenumeracao AND 
  solicitacao.idprocesso=processo.idprocesso AND
  renumeracao.idsolicitacao = tiposolicitacao.idtiposolicitacao AND
--  solicitacao.idstatus = 7 AND  tiposolicitacao.especie = 'AUDIENCIA' AND
  renumeracao.idcorrespondente in (57,58,60) AND
  solicitacao.idstatus=7 and
  --processo.adverso like 'BRADESCO%' 
  solicitacao.dataprazo >='01/01/2014' and 
  solicitacao.dataprazo <='31/10/2014' 
group by mes
order by mes


----------
SELECT 
count (*) as total
FROM 
  public.solicitacao, 
  public.formularioaudiencia
WHERE 
  solicitacao.idformulario = formularioaudiencia.idformulario AND
  formularioaudiencia.acordorealizado=true and
  solicitacao.dataprazo >='01/01/2014' and 
  solicitacao.dataprazo <='31/10/2014' and
  solicitacao.valordaalcada > 2000 
  
  --------
  --Tira o espaco em branco
  update processo set parte = trim(parte)
  
 ------------------------------------------ 
  SELECT 
  solicitacao.idsolicitacao, 
  solicitacao.dataprazo, 
  solicitacao.datasolictacao, 
  solicitacao.valor, 
  renumeracao.valor
FROM 
  public.solicitacao, 
  public.renumeracao
WHERE 
  renumeracao.idrenumeracao = solicitacao.idrenumeracao
  and solicitacao.valor = 0
 and solicitacao.dataprazo >= '01/05/2015'
 and solicitacao.idstatus=7
 
 --------------------------------------
 --UPDATE NA ATUALIZACAO DE VALORES ---
 --------------------------------------
 
 update solicitacao set valor = renumeracao.valor 
FROM 
  --public.solicitacao, 
  public.renumeracao
WHERE 
  renumeracao.idrenumeracao = solicitacao.idrenumeracao
  and solicitacao.valor = 0
 and solicitacao.dataprazo >= '01/05/2015'
 and 
 --and solicitacao.idstatus=7
 
 
 
 ---------------------------------------------------------------------
 --Busca processo 
 --------------------------------------------------------------------
 
 SELECT 
  
  solicitacao.idsolicitacao, 
  processo.parte, 
  processo.adverso,
  solicitacao.valor, 
  statussolicitacao.status, 
  enviode.tipoenvio, 
  correspondente.nome, 
  tiposolicitacao.descricao
FROM 
  public.solicitacao, 
  public.processo, 
  public.renumeracao, 
  public.statussolicitacao,
  public.enviode, 
  public.correspondente, 
  public.tiposolicitacao,
  public.comarca
WHERE 
  solicitacao.idprocesso=processo.idprocesso AND
  solicitacao.idcomarca=comarca.idcomarca AND
  solicitacao.idenviosocititacao = enviode.idenviosocititacao AND
  solicitacao.idrenumeracao = renumeracao.idrenumeracao AND
  renumeracao.idcorrespondente = correspondente.idcorrespondente AND
  solicitacao.idstatus=statussolicitacao.idstatus AND
  processo.adverso like '%BRADESCO%' AND
  tiposolicitacao.especie like 'AUDIENCIA%' AND
  (solicitacao.dataprazo >= '25/11/2015' AND
   solicitacao.dataprazo <= '18/12/2015') AND
  solicitacao.idstatus in(4,5,7,8)  AND
  comarca.uf_iduf=3
group by     solicitacao.idsolicitacao, 
  processo.parte, 
  processo.adverso,

  statussolicitacao.status, 
  enviode.tipoenvio, 
  correspondente.nome, 
  tiposolicitacao.descricao

order by  correspondente.nome



  
  

 
 
 ------Para diligencias ATRASADAS
 
 SELECT 
  solicitacao.idsolicitacao as id_da_solicitacao, 
  to_char(solicitacao.datasolictacao,'dd/MM/YYYY') as data_da_elaboracao, 
  to_char(solicitacao.dataprazo,'dd/MM/YYYY') as data_do_prazo, 
  solicitacao.horaudiencia as hora_da_audiencia,
  statussolicitacao.status,
  solicitacao.valor, 
  usuario.login as quem_elaborou
FROM 
  public.solicitacao, 
  public.renumeracao,
  public.usuario,
  public.statussolicitacao
  
WHERE 
  renumeracao.idrenumeracao = solicitacao.idrenumeracao
 -- and solicitacao.valor = 0
 and solicitacao.dataprazo >= '01/06/2015'
  and solicitacao.dataprazo <= '24/06/2015'
 and solicitacao.idstatus=statussolicitacao.idstatus
 and solicitacao.idstatus=1 
 and solicitacao.idusuario=usuario.idusuario
 order by solicitacao.dataprazo desc
 
 
 --Update pra mudar de drive e hd na tabela de arquivos
 ---------------------------------------------------
 update arquivosanexo set linkarquivo = overlay(linkarquivo placing 'Z' from 1 for 1)
 
 
 -----Altera os valore
 
 update renumeracao set valor=(valor-(valor*10)/100)
where idcorrespondente=58