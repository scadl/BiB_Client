function getXmlHttp() {
    var xmlhttp;
    try {
        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
        try {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (E) {
            xmlhttp = false;
        }
    }
    if (!xmlhttp && typeof XMLHttpRequest != 'undefined') {
        xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
}

// ----------------------------------------------------------------
var oldLvl = Array();

function aSyncRq(lvl, postvars) {

    var xhr = getXmlHttp()
    xhr.open("POST", "category.php", true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {

                // Все ок
                //alert(xhr.responseText);

                var printer = '';
                var dbdata = JSON.parse(xhr.responseText);

                if (lvl < 3) {

                    var fname = Array();
                    switch (lvl) {
                        case 0:
                            fname[0] = 'catname';
                            fname[1] = 'catid';
                            break;
                        case 1:
                            fname[0] = 'subcatname';
                            fname[1] = 'subcatid';
                            break;
                        case 2:
                            fname[0] = 'adtitle';
                            fname[1] = 'adid';
                            break;
                    }

                    lvl += 1;
                    for (var i = 0; i < dbdata.length; i++) {
                        printer +=
                            "<tr id='catrow'>" +
                            "<td class='catcell' onclick='aSyncRq(" + lvl + ", {level:" + lvl + ", parent:" + dbdata[i][fname[1]] + "});'>" +
                            "   <a href='#'>" +
                            "       " + dbdata[i][fname[0]] + "<br>" +
                            "       <span id='catdetail'>" + dbdata[i]['total_ads'] + "</span>" +
                            "   </a>" +
                            "</td></tr>";
                    }

                } else {

                    if (lvl == 3) {

                        dbdata = dbdata[0];

                        if (dbdata['picfile'] != '') {
                            var imgtag = "<img src='" + dbdata['picfile'] + "' height='128'>";
                        } else {
                            var imgtag = "";
                        }

                        printer +=
                            "<tr>" +
                            "<td colspan='3'> <hr><span style='color:blue; font-size: 15pt;'> <b>" + dbdata['adtitle'] + "</b></span>" +
                            "</tr>" +
                            "</td></tr>" +
                            "<tr id='catrow'>" +
                            "<td rowspan='3' width='1'>" + imgtag + "</td>" +
                            "<td class='catcell' style='background:#55d46d;' width='128'> E-Mail </td> " +
                            "<td class='catcell' style='background:#c8e6ce;'>" + dbdata['email'] + "</td>" +
                            "</tr><tr id='catrow'> " +
                            "<td class='catcell' style='background:#96beff;'> Опубликовано: </td> " +
                            "<td class='catcell' style='background:#cadeff;'>" + dbdata['createdon'] + "</td>" +
                            "</tr><tr id='catrow'> " +
                            "<td class='catcell' style='background:#96beff;'> Истекает: </td> " +
                            "<td class='catcell' style='background:#cadeff;'>" + dbdata['expireson'] + "</td>" +
                            "</tr>" +
                            "<tr > <td class='catcell' colspan='3' style='background:#ffcbcb; color:red; font-size:13pt; font-weight:bold; padding: 10px; text-align: center'>" +
                            "Цена: $" + dbdata['price'] + "</td></tr>" +
                            "<tr id='catrow'> <td class='catcell' colspan='3' style='padding:10px'>" + dbdata['addesc'] + "</td></tr>";


                    } else {

                        printer +=
                            "<tr id='catrow'>" +
                            "<td class='catcell'>" +
                            "   <a href='#'> Неопознанный ответ:" + lvl + " </a>" +
                            "</td></tr>";
                    }
                }



                // Remove ANY EventListeners 
                /*var el = document.getElementById("btnBack"),
                    elClone = el.cloneNode(true);
                el.parentNode.replaceChild(elClone, el);*/
                //printer += oldLvl.level + ' ' + oldLvl.parent + '<br>';

                document.getElementById("btnBack").setAttribute("level", oldLvl.level);
                document.getElementById("btnBack").setAttribute("parent", oldLvl.parent);
                oldLvl = postvars;

                //printer += oldLvl.level + ' ' + oldLvl.parent;
                document.getElementById("resTB").innerHTML = printer;

            } else {
                alert(xhr.statusText) // вызвать обработчик ошибки с текстом ответа
            }
        }
    }


    var queryString = Object.keys(postvars).map(function(key) {
        return key + '=' + encodeURIComponent(postvars[key])
    }).join('&');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(queryString);

}

// ----------------------------------------------------------------
document.onreadystatechange = function() {
    if (document.readyState === 'complete') {

        aSyncRq(0, { level: 0, parent: 0 });

        document.getElementById("btnBack").addEventListener("click", function() {
            aLvl = document.getElementById("btnBack").getAttribute("level");
            aPr = document.getElementById("btnBack").getAttribute("parent");
            aSyncRq(Number(aLvl), { level: Number(aLvl), parent: Number(aPr) });
        });
    }
}