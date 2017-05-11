# WebServerPhp-Mongodb-android
webserver use php mongodb to android layout
1.Create database with mongodb.
2.Start apache.
3.Create PHP file for return as Jsonarray. 
==================================================
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
==================================================
4.Create android project. In activity_main.xml using Listview for show the data(this example file have function search)
5.AndroidManifest.xml add <uses-permission android:name="android.permission.INTERNET"></uses-permission> for connect.
6.MainActivity.java import apache and add some code for show the data.
7.In build.gradle (app) add useLibrary 'org.apache.http.legacy' in android statment and add compile group: 'org.apache.httpcomponents' , name: 'httpclient-android' , version: '4.3.5.1'
in dependencies statment for add permission to get data from  apache.
