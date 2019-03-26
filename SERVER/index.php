<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <title>BiB Web</title>
		<style>
		
		body{
			font-family: Sans-serif;
		}
		
		input#button{
			background:silver; 
			border: solid 1px grey; margin-top:5px;
		}

				
		.catcell{
			border:1px solid silver;
			
		}
		
		#catdetail{
			color:silver;
		}
		
		#catrow:hover{
			background: #eee;
		}
		
		#controls{
			background:silver; 
			color: white; 
			font-size:35pt; 
			width: 64px; 
			text-align:center; 
			border-radius: 10px; 
			padding-bottom: 7px;
			cursor:pointer;
		}
		
		#controls:hover{
			background:grey; 
		}

		a:link{
			color: black;
			text-decoration:none;
		}
		
		</style>
		
    </head>
    <body>
		<?php
		//error_reporting(-1);
		error_reporting(E_ALL);
		
		if ( isset($_POST['level']) ){
			$level = $_POST['level'];
		} else {
			$level = 0;
		}
		if ( isset($_POST['parent']) ){
			$pArr = array(0, 0, 0, 0, 0);
			$parent = $_POST['parent'];
		}  else {
			$parent = 0;
			$pArr = array(0, 0, 0, 0, 0);
		}
		
		//print_r($pArr);
		?>
	
		<table border="0" width="100%">
		<tr> 
			<td rowspan="2" width="64"> <img id="ico" src="ic_launcher-web.png" width="64"> </td> 
			<td height="15"> <b>SCAD's BillBaord [BiB]</b> </td>
			<td rowspan="2" align="right"> 
				<a href='/bib/'> 
				<div id="controls">&#8666;</div> 
				</a> 
			</td> 
		</tr>
		<tr>
			<td height="15"> <i>Web UI mode</i> </td>
		</tr>
		</table>
					   
	   <table id="resTB" cellspacing="0" width="100%" border="0">
	  	<?php
		
		function getcurrentpath(){ 
			$curPageURL = "";
			if ($_SERVER["HTTPS"] != "on"){
				$curPageURL .= "http://";
			} else {
				$curPageURL .= "https://" ;
			}
			if ($_SERVER["SERVER_PORT"] == "80"){
				$curPageURL .= $_SERVER["SERVER_NAME"].$_SERVER["REQUEST_URI"];
			}else{
				$curPageURL .= $_SERVER["SERVER_NAME"].":".$_SERVER["SERVER_PORT"].$_SERVER["REQUEST_URI"];
			}
			
			$count = strlen(basename($curPageURL));			
			if($count > 3){
				$path = substr($curPageURL, 0, -$count);
			} else {
				$path = $curPageURL;
			}
			return $path ;
		}			
		
		$url = getcurrentpath().'category.php';	
		$data = 'level='.$level.'&parent='.$parent;
		
		//print($url."<hr>");		
		//print($_SERVER["REQUEST_URI"]."<hr>");
		
		$curl = curl_init();
		curl_setopt($curl, CURLOPT_URL, $url);
		curl_setopt($curl, CURLOPT_HEADER, true);
        curl_setopt($curl, CURLOPT_NOBODY, true);
        curl_setopt($curl, CURLOPT_REFERER, $url);
        curl_setopt($curl, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($curl, CURLOPT_MAXREDIRS, 15);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_CONNECTTIMEOUT, 60);
        curl_setopt($curl, CURLOPT_FAILONERROR, true);
		curl_setopt($curl, CURLOPT_POST, true);
		curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
		curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		$response = curl_exec($curl);
		curl_close($curl);
				
		print('Resp:['.$response.']');
		
		if (0 === strpos(bin2hex($response), 'efbbbf')) {
			$response = substr($response, 3);
			print($response);
		}
		$jArr =	json_decode(trim($response), false);


		/*
		switch (json_last_error()) {
        case JSON_ERROR_NONE:
            echo ' - Ошибок нет';
        break;
        case JSON_ERROR_DEPTH:
            echo ' - Достигнута максимальная глубина стека';
        break;
        case JSON_ERROR_STATE_MISMATCH:
            echo ' - Некорректные разряды или не совпадение режимов';
        break;
        case JSON_ERROR_CTRL_CHAR:
            echo ' - Некорректный управляющий символ';
        break;
        case JSON_ERROR_SYNTAX:
            echo ' - Синтаксическая ошибка, не корректный JSON';
        break;
        case JSON_ERROR_UTF8:
            echo ' - Некорректные символы UTF-8, возможно неверная кодировка';
        break;
        default:
            echo ' - Неизвестная ошибка';
        break;
		}
			*/			
		//print('<pre>'); var_dump( $jArr ); print('</pre>');
		
			
		switch($level){
				case 0:
					$level++;	
					for ($i=0; $i < count($jArr); $i++){
					$pArr[$level] = $jArr[$i]->catid;
					print(					
					"<tr id='catrow'>".
					"<td class='catcell' >" . 
					"<a href='?l=".$level."&p=".$jArr[$i]->catid."&pa=".json_encode($pArr)."'>".
					$jArr[$i]->catname . "<br>".
					"<span id='catdetail'>" . $jArr[$i]->total_ads . "</span>".
					"</a>".
					"</td></tr>" );
					}
				break;
				
				case 1:
					$level++;	
					for ($i=0; $i < count($jArr); $i++){
					$pArr[$level] = $jArr[$i]->subcatid;
					print(
					"<tr id='catrow' >".
					"<td class='catcell' >" . 
					"<a href='?l=".$level."&p=".$jArr[$i]->subcatid."&pa=".json_encode($pArr)."'>".
					$jArr[$i]->subcatname . "<br>".
					"<span id='catdetail'>" . $jArr[$i]->total_ads . "</span>".
					"</a>".
					"</td></tr>" );
					}
				break;
				
				case 2:
					$level++;	
					for ($i=0; $i < count($jArr); $i++){
					$pArr[$level] = $jArr[$i]->adid;
					if ( $jArr[$i]->total_ads != " - " ){
					print(
					"<tr id='catrow'>".
					"<td class='catcell' >" . 
					"<a href='?l=".$level."&p=".$jArr[$i]->adid."&pa=".json_encode($pArr)."'>".
					$jArr[$i]->adtitle . "<br>".
					"<span id='catdetail' style='color:red'>" . $jArr[$i]->total_ads . "</span>".
					"</a></td></tr>" );
					} else {
						print("<tr id='catrow'><td class='catcell' > В этой категории нет объявлений. <br> Вернитесь назад! </td></tr>");
					}	
					}
				break;
				
				case 3:
					$level++;	
				
					if ( $jArr[0]->picfile != '' ){
						$imgtag = "<img src='" . $jArr[0]->picfile . "' height='128'>"; 
					} else {
						$imgtag = "";
					}
					
					for ($i=0; $i< count($jArr); $i++){
					Print(
					"<tr>
						<td colspan='3'> <hr><span style='color:blue; font-size: 15pt;'> <b>" . $jArr[$i]->adtitle . "</b></span>
					</tr>".
					"</td></tr>".
					"<tr id='catrow'>".
						"<td rowspan='3' width='1'>".$imgtag."</td>".
						"<td class='catcell' style='background:#55d46d;' width='128'> E-Mail </td> ".
						"<td class='catcell' style='background:#c8e6ce;'>" .  $jArr[$i]->email . "</td>".
					"</tr><tr id='catrow'> ".
						"<td class='catcell' style='background:#96beff;'> Опубликовано: </td> ".
						"<td class='catcell' style='background:#cadeff;'>" . str_ireplace(" ", "<br>", $jArr[$i]->createdon) . "</td>".
					"</tr><tr id='catrow'> ".
						"<td class='catcell' style='background:#96beff;'> Истекает: </td> ".
						"<td class='catcell' style='background:#cadeff;'>" . str_ireplace(" ", "<br>", $jArr[$i]->expireson) . "</td>".
					"</tr>".
					"<tr > <td class='catcell' colspan='3' style='background:#ffcbcb; color:red; font-size:13pt; font-weight:bold; padding: 10px; text-align: center'>".
					"Цена: $" .  $jArr[$i]->price . "</td></tr>".
					"<tr id='catrow'> <td class='catcell' colspan='3' style='padding:10px'>" . $jArr[$i]->addesc . "</td></tr>"	);
					}
					break;
				}
		
		?>
	   </table>
	   
    </body>
</html>
