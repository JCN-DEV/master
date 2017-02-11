alter table inst_play_ground_info add institute_ply_grnd_inf_id NUMBER(38);
alter table inst_play_ground_info add constraint FKinstPlayGroundGnfo2 foreign key (institute_ply_grnd_inf_id) references institute_play_ground_info;
alter table inst_lab_info add institute_lab_info_id NUMBER(38);
alter table inst_lab_info add constraint FKInstLabInfo2 foreign key (institute_lab_info_id) references institute_lab_info;
alter table inst_shop_info add institute_shop_info_id NUMBER(38);
alter table inst_shop_info add constraint FKinstShopInfo2 foreign key (institute_shop_info_id) references institute_shop_info;
alter table inst_land rename column lst_tax_payment_date to last_tax_payment_date;
alter table inst_land rename column LAND_REG_LEDGER_NO to LAND_REGISTRATION_LEDGER_NO;
alter table inst_land rename column LAND_REG_DATE to LAND_REGISTRATION_DATE;
alter table jhi_user add district_id NUMBER(38);
/*update sequence*/
alter sequence hibernate_sequence increment by  2958;

alter table attachment drop column ATTACHMENTCATEGORY_ID;
alter table attachment drop column INSTEMPLOYEE_ID;

alter table inst_employee add teacher_level varchar(100);
alter table inst_employee add teacher_quota varchar(100);
alter table inst_gen_info add submited_date TIMESTAMP;

/*get duplicate rows */
select login,count(login) from jhi_user group by login having count(login) > 1;

/*create sequence*/
CREATE SEQUENCE bank_assign_seq  START WITH  1000 INCREMENT BY   1  NOCACHE NOCYCLE;
