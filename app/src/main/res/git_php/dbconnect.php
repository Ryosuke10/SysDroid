<?php

pg_set_client_encoding("utf-8");

//foreach (getallheaders() as $name => $value) {
//    echo "$name: $value\n";
//}
print(file_get_contents('php://input'));
//print_r($_POST);
//print_r($_GET);

//if ($_SERVER["REQUEST_METHOD"] === "POST") {
//	if (!empty($_POST["json"])){
//		$obj = json_decode($_POST["json"]);
//		print("get");
//	}else{
//		print("missget");
//	}
//}else{
//	print($_SERVER["REQUEST_METHOD"]);
//}


$conn = "host=localhost dbname=arai user=postgres password=NN12bt54_r";
try{
	$link = pg_connect($conn);
	if (!$link) {
  	  die('接続失敗です。'.pg_last_error());
	}

	// PostgreSQLに対する処理
	$result = pg_query('SELECT item_id, item_name FROM sysroid.item');
	if (!$result) {
    	die('クエリーが失敗しました。'.pg_last_error());
	}

	for ($i = 0 ; $i < pg_num_rows($result) ; $i++){
    	$rows = pg_fetch_array($result, NULL, PGSQL_ASSOC);
		
	}
	$json = json_encode($obj,JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
	echo $json;
}catch(Exception $e){
	echo $e->getMessage();	
}finally{
	$close_flag = pg_close($link);
}

?>