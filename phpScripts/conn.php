﻿<?php
$server_name = "bookepos.cbdbs0kdb8us.eu-west-1.rds.amazonaws.com:3306";
$mysql_user = "beposADMIN";
$mysql_pass = "40034823";
$db_name = "beposdb";

// Create connection
$conn = new mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

// Check connection
if (!$conn) {
    exho("Connection failed: " . mysqli_connect_error();
} 
echo "Connected successfully";
?>
