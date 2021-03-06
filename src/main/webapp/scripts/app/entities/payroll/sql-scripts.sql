
-- ================ Salary Structure Allowance Deduction based on PayScale =======

SELECT pai.BASIC_MAXIMUM,pai.BASIC_MINIMUM, nvl(sadi.ALLOW_DEDUC_VALUE, pai.BASIC_MAXIMUM) as ALLOW_DEDUC_VALUE,
sadi.ID, nvl(sadi.ALLOW_DEDUC_TYPE, 'Allowance') ALLOW_DEDUC_TYPE, nvl(sadi.ALLOW_DEDUC_INFO_ID, pai.ALLOWANCE_INFO_ID) ALLOW_DEDUC_INFO_ID, sadi.ACTIVE_STATUS,
sadi.SALARY_STRUCTURE_INFO_ID, sadi.CREATE_BY, sadi.CREATE_DATE, sadi.UPDATE_BY, sadi.UPDATE_DATE,
(SELECT NAME FROM PRL_ALLOW_DEDUCT_INFO WHERE ID = pai.ALLOWANCE_INFO_ID) as ALLOW_DEDUC_NAME
FROM PRL_PAYSCALE_ALLOWANCE_INFO pai
LEFT OUTER JOIN PRL_SALARY_ALLOW_DEDUC_INFO sadi
 ON (pai.ALLOWANCE_INFO_ID = sadi.ALLOW_DEDUC_INFO_ID AND
 sadi.SALARY_STRUCTURE_INFO_ID =
  (SELECT ID FROM PRL_SALARY_STRUCTURE_INFO WHERE PAYSCALE_INFO_ID=pai.PAYSCALE_INFO_ID AND EMPLOYEE_INFO_ID=3149)
  AND sadi.ALLOW_DEDUC_TYPE like 'Allowance')
WHERE pai.PAYSCALE_INFO_ID = 10463

UNION

SELECT 0 as BASIC_MAXIMUM, 0 as BASIC_MINIMUM, nvl(sadi.ALLOW_DEDUC_VALUE, 0) as ALLOW_DEDUC_VALUE,
sadi.ID, nvl(sadi.ALLOW_DEDUC_TYPE, pai.ALLOW_DEDUC_TYPE) ALLOW_DEDUC_TYPE, nvl(sadi.ALLOW_DEDUC_INFO_ID, pai.ID) ALLOW_DEDUC_INFO_ID, sadi.ACTIVE_STATUS,
sadi.SALARY_STRUCTURE_INFO_ID, sadi.CREATE_BY, sadi.CREATE_DATE, sadi.UPDATE_BY, sadi.UPDATE_DATE,
pai.NAME as ALLOW_DEDUC_NAME
FROM
(SELECT aldd.* FROM PRL_ALLOW_DEDUCT_INFO aldd, PRL_ALLOWDEDUCT_GRADE_MAP alddmp
WHERE aldd.ID = alddmp.PRL_ALLOW_DEDUCT_INFOS_ID AND alddmp.GRADE_INFOS_ID = 5958 AND aldd.ALLOW_DEDUC_TYPE = 'Deduction') pai
LEFT OUTER JOIN
PRL_SALARY_ALLOW_DEDUC_INFO sadi
ON(pai.ID = sadi.ALLOW_DEDUC_INFO_ID AND sadi.SALARY_STRUCTURE_INFO_ID =
  (SELECT ID FROM PRL_SALARY_STRUCTURE_INFO WHERE PAYSCALE_INFO_ID=10463 AND EMPLOYEE_INFO_ID=3149)
  AND sadi.ALLOW_DEDUC_TYPE like 'Deduction')

-- ================ ================ =======
