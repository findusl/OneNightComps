<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");

include_once '../database.php';
include_once '../util.php';

ini_set('display_errors', 1);

$database = getConnection();

try {
    $compositionsQuery = "SELECT id, name, description, difficulty_level, creator_user_id from game_composition";
    
    $queryCompositionsResult = $database->query($compositionsQuery);
    $result = array();
    while($cRow = $queryCompositionsResult->fetch_assoc()) {
        //cRow is short for compositionRow, the row of the composition currently fetched
        $composition = array("id"=>$cRow["id"],"name"=>$cRow["name"],"description"=>$cRow["description"],"difficultyLevel"=>$cRow["difficulty_level"]);
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
    echo encodeResult($result);
} catch(mysqli_sql_exception $exception){
    echo encodeResult("", $exception);
}

function findCompWithRoles($database, $roleIds) {
    $rolesAsString = $database->rescape_string(implode(',', $roleIds));
    $compositionQuery = "SELECT game_composition_id FROM game_composition_role g1 WHERE game_role_id in (";
    $compositionQuery .= $rolesAsString;
    $compositionQuery .= ") AND NOT EXISTS (SELECT * FROM game_composition_role WHERE game_composition_id = g1.game_composition_id AND game_role_id NOT IN (";
    $compositionQuery .= $rolesAsString;
    $compositionQuery .= ")) GROUP BY game_composition_id HAVING COUNT(game_role_id) = ";
    $compositionQuery .= sizeof($roleIds);
    $queryCompositionResult = $database->query($compositionsQuery);
    
    if ($row = $queryCompositionsResult->fetch_assoc()) {
        return $row['game_composition_id'];
    }
    return -1;
}
?>