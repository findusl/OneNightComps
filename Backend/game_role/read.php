<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");

include_once '../database.php';

$database = getConnection();

try {
    $query = "SELECT r.id AS id, r.name AS name, description, game_faction_id, f.name AS game_faction_name ".
        "FROM game_role r JOIN game_faction f ON r.game_faction_id = f.id";
    $queryResult = mysqli_query($database, $query);
    $result = array();
    while($row = $queryResult->fetch_assoc()) {
        $faction = array("id"=>$row["game_faction_id"],"name"=>$row["game_faction_name"]);
        $gameRole = array("id"=>$row["id"],"name"=>$row["name"],"description"=>$row["description"],
            "faction"=>$faction);
        $result[] = $gameRole;
    }
    $response = array("errorMessage"=>"", "result"=>$result);
    echo json_encode($response);
} catch(mysqli_sql_exception $exception){
    exit("query error: " . $exception->getMessage());
}

function utf8ize($d) {
    if (is_array($d)) {
        foreach ($d as $k => $v) {
            $d[$k] = utf8ize($v);
        }
    } else if (is_string ($d)) {
        return utf8_encode($d);
    }
    return $d;
}
?>