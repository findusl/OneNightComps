<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../database.php';

$database = getConnection();

// get posted data
$data = json_decode(file_get_contents("php://input"), true);

// set game composition property values
$sql = "INSERT INTO game_composition (name, description, difficulty_level, creator_user_id,"
$database->query()


?>