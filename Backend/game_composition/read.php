<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");

include_once '../database.php';
ini_set('display_errors', 1);

$database = getConnection();

try {
    $compositionsQuery = "SELECT id, name, description, role_count, difficulty_level, creator_user_id from game_composition";
    $query = "SELECT r.id AS id, r.name AS name, description, game_faction_id, f.name AS game_faction_name FROM ".
                                "game_role r JOIN game_faction f ON r.game_faction_id = f.id";
    $queryCompositionsResult = $database->query($compositionsQuery);
    $result = array();
    while($cRow = $queryCompositionsResult->fetch_assoc()) {
        //cRow is short for compositionRow, the row of the composition currently fetched
        $composition = array("id"=>$cRow["id"],"name"=>$cRow["name"],"description"=>$cRow["description"],
                    "roleCount"=>$cRow["role_count"],"difficultyLevel"=>$cRow["difficulty_level"]);
        $rolesQuery = "SELECT r.id AS id, r.name AS name, description, game_faction_id, f.name AS game_faction_name FROM".
                        " game_role r JOIN game_faction f ON r.game_faction_id = f.id JOIN game_composition_role cr ON r.id".
                        " = cr.game_role_id WHERE cr.game_composition_id = ".$composition["id"];
        $queryRolesResult = $database->query($rolesQuery);
        $roles = array();
        while($rRow = $queryRolesResult->fetch_assoc()) {
            //rRow is short for roleRow, the row of the role currently fetched
            $faction = array("id"=>$rRow["game_faction_id"],"name"=>$rRow["game_faction_name"]);
            $gameRole = array("id"=>$rRow["id"],"name"=>$rRow["name"],"description"=>$rRow["description"],
                "faction"=>$faction);
            $roles[] = $gameRole;
        }
        $composition["roles"] = $roles;
        $result[] = $composition;
    }
    $response = array("errorMessage"=>"", "result"=>$result);
    echo json_encode($response);
} catch(mysqli_sql_exception $exception){
    exit("query error: " . $exception->getMessage());
}
?>