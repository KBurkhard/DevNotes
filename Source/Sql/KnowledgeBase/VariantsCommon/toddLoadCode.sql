

-- 20171214 - top varianst load
-- OLD metadat structure
SELECT distinct ID_PH.TBL ,
                DATASET.TBL,
                PROP_ID_PH.ID,
                PROP_ID_PH.PH
FROM PROP_ID_PH, PROP, ID_PH,DATASET
WHERE PROP_ID_PH.PROP = PROP.PROP
AND ID_PH.ID = PROP_ID_PH.ID
AND ID_PH.PH = PROP_ID_PH.PH
AND MEANING = 'P_VALUE' AND
DATASET.VER = 'mdv29'
AND DATASET.ID = PROP_ID_PH.ID ;


-- NEW metadata structure
SELECT distinct META_DATASET.TBL,META_ID_PH.ID,META_PROP_DATASET_PH.PH ,META_DATASET_PH.TBL
FROM META_PROP_DATASET_PH,
     META_PROP,
     META_MDV,
     META_ID_PH,
     META_DATASET,
     META_DATASET_PH
WHERE META_PROP_DATASET_PH.PROP = META_PROP.PROP
AND META_PROP_DATASET_PH.DATASET = META_DATASET.DATASET
AND META_PROP_DATASET_PH.DATASET = META_MDV.DATASET
AND META_MDV.ID = META_ID_PH.ID
AND META_ID_PH.PH = META_PROP_DATASET_PH.PH
AND MEANING = 'P_VALUE'
AND VER = "mdv29";


-- NEW metadata FIXED
SELECT distinct META_DATASET.TBL, META_ID_PH.ID, META_PROP_DATASET_PH.PH, META_DATASET_PH.TBL
FROM META_PROP_DATASET_PH,
     META_PROP,
     META_MDV,
     META_ID_PH,
     META_DATASET,
     META_DATASET_PH
WHERE META_MDV.DATASET = META_DATASET.DATASET
AND META_MDV.ID = META_ID_PH.ID
AND META_ID_PH.PH = META_DATASET_PH.PH
AND META_DATASET_PH.DATASET = META_MDV.DATASET
AND META_PROP_DATASET_PH.DATASET = META_DATASET_PH.DATASET
AND META_PROP_DATASET_PH.PH = META_DATASET_PH.PH
AND META_PROP_DATASET_PH.PROP = META_PROP.PROP
AND META_PROP.MEANING = 'P_VALUE'
AND META_MDV.VER = "mdv29";
     
     
-- OLD query
SELECT TBL,DB_COL,COUNT(TBL)
FROM PROP_ID_PH
JOIN PROP ON PROP_ID_PH.PROP=PROP.PROP
JOIN ID_PH ON PROP_ID_PH.ID=ID_PH.ID
WHERE  MEANING IN ('P_VALUE', 'ODDS_RATIO')
AND ID_PH.ID='ExSeq_19k_ea_genes_ek_mdv29'
AND ID_PH.PH='T2D'
order by SORT
limit 1


-- NEW fixed query
SELECT META_DATASET_PH.TBL, META_PROP.DB_COL, META_PROP.MEANING, META_PROP.SORT
FROM META_PROP_DATASET_PH,
     META_PROP,
     META_MDV,
     META_ID_PH,
     META_DATASET,
     META_DATASET_PH
WHERE META_MDV.DATASET = META_DATASET.DATASET
AND META_MDV.ID = META_ID_PH.ID
AND META_ID_PH.PH = META_DATASET_PH.PH
AND META_DATASET_PH.DATASET = META_MDV.DATASET
AND META_PROP_DATASET_PH.DATASET = META_DATASET_PH.DATASET
AND META_PROP_DATASET_PH.PH = META_DATASET_PH.PH
AND META_PROP_DATASET_PH.PROP = META_PROP.PROP
AND  META_PROP.MEANING IN ('P_VALUE', 'ODDS_RATIO')
AND META_ID_PH.ID='ExSeq_19k_ea_genes_ek_mdv29'
AND META_ID_PH.PH='T2D'
order by META_PROP.MEANING, META_PROP.SORT;
limit 1;

     
     
     
     
     
     
     
WHERE 
AND 
AND META_PROP_DATASET_PH.DATASET = META_MDV.DATASET
AND 
AND 

select tv.VAR_ID,tv.dataset,tv.P_VALUE,tv.BETA,tv.MDV,
tv.phenotype,tv.AF,kg.EAF,tv.POS,tv.GENE,tv.CHROM,tv.MOST_DEL_SCORE
from TOP_VARIANTS_MDV29  tv
inner join 1kg_phase1_dv1 kg on (tv.VAR_ID=kg.VAR_ID) limit 1;


