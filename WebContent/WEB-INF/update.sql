--update solicitacao set idstatus=5 where idsolicitacao=48871
UPDATE solicitacao
   SET valor = renumeracao.valor
  FROM renumeracao
WHERE solicitacao.idrenumeracao = renumeracao.idrenumeracao
--AND renumeracao.idcorrespondente=79
AND solicitacao.valor=0
AND solicitacao.dataprazo >='01/10/2015' 



------------------------------------------------------------------

SELECT count(solicitacao.idsolicitacao) as quantidade, 
  solicitacao.idprocesso, 
  processo.numeroprocesso, 
  processo.adverso,
  processo.parte,
  tiposolicitacao.especie, 
  tiposolicitacao.tipo
FROM 
  public.solicitacao, 
  public.renumeracao, 
  public.tiposolicitacao, 
  public.processo
WHERE 
  solicitacao.idrenumeracao = renumeracao.idrenumeracao AND
  solicitacao.idprocesso = processo.idprocesso AND
  tiposolicitacao.idtiposolicitacao = renumeracao.idsolicitacao AND
  solicitacao.idstatus=7 and
  tiposolicitacao.especie='AUDIENCIA' and
  solicitacao.dataprazo >='01/01/2015'
 group by  solicitacao.idprocesso, 
  processo.numeroprocesso, 
  processo.parte,
  processo.adverso,
  tiposolicitacao.especie, 
  tiposolicitacao.tipo
order by   quantidade desc