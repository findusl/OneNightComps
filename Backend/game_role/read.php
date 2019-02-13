<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");

include_once '../database.php';
include_once '../util.php';

$database = getConnection();

try {
    $query = "SELECT r.id AS id, r.name AS name, description, game_faction_id, f.name AS game_faction_name ".
        "FROM game_role r JOIN game_faction f ON r.game_faction_id = f.id ORDER BY r.game_faction_id ASC, name ASC";
    $queryResult = mysqli_query($database, $query);
    $result = array();
    while($row = $queryResult->fetch_assoc()) {
        $faction = array("id"=>$row["game_faction_id"],"name"=>$row["game_faction_name"]);
        $gameRole = array("id"=>$row["id"],"name"=>$row["name"],"description"=>$row["description"],
            "faction"=>$faction);
        $result[] = $gameRole;
    }
    echo encodeResult($result);
} catch(mysqli_sql_exception $exception){
    echo encodeResult("", $exception);
}
?>