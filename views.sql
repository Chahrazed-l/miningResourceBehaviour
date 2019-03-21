Create VIEW financial.eventsch As select distinct EventId as H from financial.eventlog where State='SCHEDULE';
Create VIEW financial.eventstart As select distinct EventId as S from financial.eventlog where State='START';
Create VIEW financial.eventcomplete As select distinct EventId as C from financial.eventlog where State='COMPLETE'and Resource<>'null' ;
Create VIEW financial.eventjoint As select S from financial.eventstart as t1 INNER JOIN financial.eventcomplete 
as t2 on t1.S=t2.C INNER JOIN financial.eventsch as t3 on t1.S=t3.H;
Create VIEW financial.eventRes As select * from financial.eventlog where EventId in (select S from financial.eventjoint);