<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");

include_once '../database.php';

$database = Database.getConnection();

$query = "SELECT * FROM game_role r JOIN game_faction f ON r.game_faction_id = f.id";
$result = mysqli_query($database, $query);
echo $result
?>