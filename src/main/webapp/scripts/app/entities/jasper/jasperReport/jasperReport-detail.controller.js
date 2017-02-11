'use strict';

angular.module('stepApp')
    .controller('JasperReportDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'entity', 'JasperReport', 'GetJasperParamByJasperReport',
    function ($scope, $rootScope, $sce, $stateParams, entity, JasperReport, GetJasperParamByJasperReport) {

        $scope.jasperReport = entity;
        $scope.jasperReportParameters = [];


        GetJasperParamByJasperReport.query({id: $stateParams.id}, function (result) {
            $scope.jasperReportParameters = result;
        });


        $scope.load = function (id) {
            JasperReport.get({id: id}, function (result) {
                $scope.jasperReport = result;
            });
        };

        $scope.paramfieldNames = [];
        $scope.errorParameterField = "";
        if ($scope.jasperReport.listtablestatus) {
            console.log("Jasper Report" + $scope.jasperReport.displayfieldname);
            console.log("Jasper Report" + $scope.jasperReport.fieldtype);

            var arrayDisplayName = $scope.jasperReport.displayfieldname.split(',');
            var arrayType = $scope.jasperReport.fieldtype.split(',');
            var arrayFieldname = $scope.jasperReport.fieldname.split(',');

            for (var i = 0; i < arrayDisplayName.length; i++) {
                var tempVar = {

                    'fieldName':arrayFieldname[i].trim(),
                    'displayName':arrayDisplayName[i].trim(),
                    'typeName':arrayType[i].trim().toLowerCase(),
                    'value':""

                };
                $scope.paramfieldNames.push(tempVar);
            }

        }
        console.log($scope.paramfieldNames);

        $scope.reportPreviewList = function () {

            var Str ="";
            $scope.errorParameterField = "";

            for(var i=0;i<$scope.paramfieldNames.length;i++)
            {

                if($scope.paramfieldNames[i].value.length > 0)
                {

                    if(validfieldInput($scope.paramfieldNames[i].typeName,$scope.paramfieldNames[i].value))
                    {
                        Str = Str +concateWhereCondition($scope.paramfieldNames[i].typeName,$scope.paramfieldNames[i].value,$scope.paramfieldNames[i].fieldName);
                    }
                    else
                    {
                        $scope.errorParameterField  = $scope.errorParameterField + $scope.paramfieldNames[i].displayName + " , ";
                    }
                }

            }
            if($scope.errorParameterField.length > 0 )
            {
                $scope.errorParameterField = "INVAID INPUT " + $scope.errorParameterField;
            }
            console.log(Str);


        }

        function validfieldInput(vartypeName, varFieldValue)
        {
            if(vartypeName == "number")
            {
                var RE = /^[0-9<>=]{1}[0-9 ]{1}[0-9]+$/;
                return RE.test(varFieldValue);
            }

            if(vartypeName == "double")
            {
                var RE = /^[<>=]?\d*(\.\d+)?$/;
                return RE.test(varFieldValue);
            }
            return true;
        }

        function concateWhereCondition(vartypeName, varFieldValue,varFieldName)
        {
            var result = " AND UPPER(" + varFieldName + ") ";
            if(vartypeName == "number" ||  vartypeName == "double") {
                if (varFieldValue[0] == '=' || varFieldValue[0] == '<' || varFieldValue[0] == '>') {

                    result = result + varFieldValue;
                    if(varFieldValue.substring(1, varFieldValue.length).trim()==0)
                        result = result + "0";
                    return result;
                }
                else
                {
                    result =  result + " LIKE " + varFieldValue + "%";
                }
            }
            else {
                result = result + " LIKE  '" + varFieldValue.toUpperCase() + "%'";
            }
            return result;
        }

    }]);
