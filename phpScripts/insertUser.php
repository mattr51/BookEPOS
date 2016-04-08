<?php
require "conn.php";
$username = "test";
$password = "test";
$admin = "1";
$refund = "1";

//SQL Query to insert username, password, admin, and refund permissions
$query = "insert into users values('$username','$password',$admin,$refund);";

if(!mysqli_query($conn,$query))
echo " Error: ".mysqli_error($conn)

?>
