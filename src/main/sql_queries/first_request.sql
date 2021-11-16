select w.org_sender
from waybill w
join waybill_position wp
on w.waybill_num = wp.waybill
order by wp.amount DESC LIMIT 10;