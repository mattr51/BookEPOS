﻿<?php
require "conn.php";

$name = $_POST["name"];
$password = $_POST["password"];


//test variables if needed
//$username = "test";
//$password = "test";

//SQL Query to select username, with admin and refund permissions
$query = "select username, admin, refund from user where username = '$username' and password = '$password';";

$result = mysqli_query($conn,$query); 

$response = array();
 
while($row = mysqli_fetch_array($result)){
    $response = array("id"=>$row[0],"user"=>$row[1],"admin"=>$row[2],"refund"=>$row[3]);
}
 
echo json_encode(array("user_data"=>$response));

?>

