select
 tb_hobby.description as tb_hobby_description,
 tb_name_hobby.id as tb_name_hobby_id
 from tb_hobby
  left join (tb_name_hobby)
   on tb_name_hobby.hobby_id = tb_hobby.id
   
select
 tb_name.name as tb_name_name,
 tb_address.street1 as tb_address_street_1,
 tb_address.city as tb_address_city
 from tb_name
  left join tb_address on tb_name.address_id = tb_address.id
--  join tb_name on tb_name.name_id = tb_name.id
--  join tb_address on tb_name.address_id = tb_address.id
;

select
 tb_name.name as tb_name_name,
 tb_address.street1 as tb_address_street1,
 tb_address.city as tb_address_city,
 tb_address_category.description as tb_address_category_description,
 tb_hobby.description as tb_hobby_description
 from tb_name
  left join (tb_name_address, tb_address)
   on tb_name_address.name_id = tb_name.id
   and tb_name_address.address_id = tb_address.id
  left join (tb_name_hobby, tb_hobby)
   on tb_name_hobby.name_id = tb_name.id
   and tb_name_hobby.hobby_id = tb_hobby.id
  left join (tb_address_address_category, tb_address_category)
   on tb_address_address_category.address_id = tb_address.id
   and tb_address_address_category.address_category_id = tb_address_category.id
  where tb_address.street1 like 'Rio'
    or tb_name.name like 'Paul'
    
select 
 tb_name_name,
 tb_address_street1 from (select
 tb_name.name as tb_name_name,
 tb_address.street1 as tb_address_street1
 from tb_name
  join (tb_name_address, tb_address)
   on tb_name_address.name_id = tb_name.id
   and tb_name_address.address_id = tb_address.id
 order by name) t limit 10 offset 0