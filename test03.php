<?php
	$m = new MongoClient();
 $db = $m->mydb;
 $collection = $db->mycol;
$strKeyword =  $_POST["txtKeyword"];
 $ASV =array();
 $resultArray = array();
if ($strKeyword!=''){
 $cursor = $collection->find(array('name'=> new MongoRegex("/^$strKeyword/")));
 }
 else{
     $cursor = $collection->find();
 }

foreach($cursor as $k => $row){
   $ASV=$row;
   array_push($resultArray,$ASV);
}
echo json_encode($resultArray);
?>