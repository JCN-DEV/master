'use strict';

angular.module('stepApp')
    .controller('reportsController',
        ['$scope', '$sce', '$state', 'DataUtils', 'ParseLinks', 'JasperReport', 'GetJasperParamByJasperReport', 'GetAllJasperReportByModule',
        function ($scope, $sce, $state, DataUtils, ParseLinks, JasperReport, GetJasperParamByJasperReport, GetAllJasperReportByModule) {

        $scope.jasperReports = [];

        var loadModule = function () {
            var moduleName = "cms";
            GetAllJasperReportByModule.query({module: 'sms'}, function (result) {
                    console.log(result);
                    $scope.jasperReports = result;
                },
                function (response) {
                    console.log(response);
                }
            );
        }
        loadModule();
        //$scope.jasperReports = JasperReport.query({page: $scope.page, size: 20}, function (result, headers) {
        //    $scope.jasperReports = result;
        //
        //});

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        $scope.jsRport;
        $scope.rptParamDiv;

        $scope.rptBaseurl = "";
        $scope.url = "";


        $scope.reportChange = function () {

            $scope.obj = $scope.jasperReport.id;
            $scope.jasperReportParameters = [];
            GetJasperParamByJasperReport.query({id: $scope.obj.id}, function (result) {
                $scope.jasperReportParameters = result;
            });
        }

        $scope.reportPreview = function () {

            var parmavar = "";
            var urlString = "";


            $scope.obj = $scope.jasperReport.id;


            console.log("object = " + $scope.obj);
            console.log("object path =" + $scope.obj.path);


            urlString = "http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F";
            urlString = urlString + $scope.obj.path + "&standAlone=true&j_username=user&j_password=jasperuser&decorate=no";

            var parmavar = "";
            angular.forEach($scope.jasperReportParameters, function (data) {

                parmavar = parmavar + "&" + data.name + "=";

                if (data.actiontype) {
                    if (data.type == 'combo') {
                        var array = $.map(data.actiontype, function (value, index) {
                            return [value];
                        });
                        if (array.length > 0) {
                            parmavar = parmavar + array[1];
                        }
                        parmavar = parmavar + "%25";
                    }

                    else if (data.type == 'text') {

                        if (data.actiontype.toString().trim() != "")
                            parmavar = parmavar + data.actiontype;

                        parmavar = parmavar + "%25";
                    }

                    else if (data.type == 'month' || data.type == 'year') {

                        if (data.actiontype.toString().trim() != "")
                            parmavar = parmavar + data.actiontype;
                    }
                }
                else {
                    parmavar = parmavar + "%25";
                }
            })

            $scope.rptBaseurl = urlString + parmavar;
            console.log($scope.rptBaseurl);
            $scope.url = $sce.trustAsResourceUrl($scope.rptBaseurl);
        };

    }]);




