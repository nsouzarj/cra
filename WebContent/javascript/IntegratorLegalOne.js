/**
 * LEGAL ONE INTEGRATOR - v3.0.0.0
 * COPYRIGHT 2017 THOMSON REUTERS
 */

const urlLegalOneConector = "http://localhost:85/api"; /* IMPORTANT: Change the value of urlLegalOneConector pointing to your LegalOne Conector address */
const apiSelect = "$select=identifierNumber,courtId,actionTypeId,folder,participants";
const apiExpand = "$expand=participants";

/**
 * Build URL into Intermediate API format
 * @param {string} path
 * @param {string} parameters
 */
function UrlBuilder(path, parameters) {
    var url = urlLegalOneConector + "/" + path + "?" + apiSelect + "&" + apiExpand;
    if (parameters) {
        url += "&" + parameters;
    }
    return url;
}

/**
 * Encode string to base 64
 * @param {string} str
 */
function EncodeBase64(str) {
    var encondedStr = window.btoa(str);
    return encondedStr;
}

/**
 * Get lawsuit case data from LegalOne
 * @param {string} parameter
 */
function GetLawsuitCase(parameter) {
    var filter = "$filter=" + parameter;
    var url = UrlBuilder("LawsuitCase", filter);
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (msg) {
            Action(msg);
        },
        error: function (xhr, ajaxOptions, throwError) {
            ShowErrorMessage("Erro ao obter dados de processo");
            return null;
        }
    });
}

/**
 * Filter lawsuit case by Identifier Number (In Brazil it
 * would be "CNJ number" because it follows a standard defined
 * by the Brazilian National Council of Justice (CNJ))
 * @param {string} valueParameter
 */
function GetLawsuitCaseByIdentifierNumber(valueParameter) {
    var parameter = "contains(identifierNumber,'" + valueParameter + "')";//"identifierNumber eq '" + valueParameter + "'";
    var result = GetLawsuitCase(parameter);
}

/**
 * Filter lawsuit case that contains Customer Name
 * @param {string} valueParameter
 */
function GetLawsuitCaseByCustomerName(valueParameter) {
    var parameter = "contains(customerName,'" + valueParameter + "')";//"customerName eq '" + valueParameter + "'";
    var result = GetLawsuitCase(parameter);
}

/**
 * Filter lawsuit case that contains Other Party Name
 * @param {string} valueParameter
 */
function GetLawsuitCaseByOtherPartyName(valueParameter) {
    var parameter = "contains(otherPartyName,'" + valueParameter + "')";//"otherPartyName eq '" + valueParameter + "'";
    var result = GetLawsuitCase(parameter);
}

/**
 * Filter lawsuit case by action name
 * @param {string} valueParameter
 */
function GetLawsuitCaseByActionName(valueParameter) {
    var parameter = "contains(actionName,'" + valueParameter + "')";//"actionName eq '" + valueParameter + "'";
    var result = GetLawsuitCase(parameter);
}

/**
 * Filter lawsuit case by Folder Number
 * @param {string} valueParameter
 */
function GetLawsuitCaseByFolder(valueParameter) {
    var parameter = "contains(folder,'" + valueParameter + "')";//"folder eq '" + valueParameter + "'";
    var result = GetLawsuitCase(parameter);
}

/**
 * Filter lawsuit case by Court Name
 * @param {string} valueParameter
 */
function GetLawsuitCaseByCourtName(valueParameter) {
    var parameter = "courtName eq '" + valueParameter + "'";
    var result = GetLawsuitCase(parameter);
}

/**
 * Filter lawsuit case by any Parameters
 */
function GetLawsuitCaseByParameters() {
    var parameter = "";
    $("input:text").each(function (index) {
        if ($(this).val() != null && $(this).val() != "") {
            if (parameter != null && parameter != "") {
                parameter += " and ";
            }
            parameter += "contains(" + $(this).attr('id') + ",'" + $(this).val() + "')";
        }
    });
    var result = GetLawsuitCase(parameter);
}

/**
 * Describes the action when getting lawsuit case data is successful
 * IMPORTANT: Change action bellow to adapt to your application
 * @param {any} msg result in JSON format
 */
function Action(msg) {
    if (msg.indexOf("Exception") >= 0 || msg.indexOf("Erro") >= 0) {
        ShowErrorMessage(msg);
    }
    else {
        BuildTable(msg);
    }
}

/*** Bellow functions are for sample page only ***/
/**
 * Build lawsuit case data table at sample page
 * @param {any} msg
 */
function BuildTable(msg) {
    CleanResults();
    var json = $.parseJSON(msg);
    var $tr = $('<thead>').append($('<tr style="font-weight: bold">').append(
        $('<td>').text("Nº de CNJ"),
        $('<td>').text("Órgão"),
        $('<td>').text("Ação"),
        $('<td>').text("Pasta"),
        $('<td>').text("Contrário Principal"),
        $('<td>').text("Cliente Principal")
    )).appendTo('#tableResult');
    $.each(json, function (i, item) {
        var $tr = $('<tr>').append(
            $('<td>').text(item.identifierNumber),
            $('<td>').text(item.courtName),
            $('<td>').text(item.actionTypeName),
            $('<td>').text(item.folder),
            $('<td>').text(item.otherPartyName),
            $('<td>').text(item.customerName)
        ).appendTo('#tableResult');
    });
    $('#loader').addClass('hidden')
    $('button').removeAttr('disabled');
}

function ShowErrorMessage(msg) {
    CleanResults();
    $("<p>Error Message:</p><p>"+msg+"</p>").appendTo("#errorMessage");
    $('#loader').addClass('hidden')
    $('button').removeAttr('disabled');
}

/**
 * Clean lawsuit case data table at sample page
 */
function CleanResults() {
    $("#tableResult tbody").remove();
    $("#tableResult thead").remove();
    $("#errorMessage p").remove();
}