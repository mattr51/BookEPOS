<?php
require "conn.php";
$search = "test";


$query = "select b.title, b.authors, i.salePrice from book b left join inventory i
on b.bookID = i.bookID where b.ISBN = " .$search";

$result = mysqli_query($conn,$query);
$count = mysqli_num_rows($result);

$row = mysqli_fetch_assoc($result);
$title = (int)$row["title"];
$refund = $row["authors"];

if($count > 0)
{
echo "testing".$title." ";
}
else
{
echo "no records";
}

mysqli_close($conn);
?>


