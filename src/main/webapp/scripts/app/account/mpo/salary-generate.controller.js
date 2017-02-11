'use strict';

angular.module('stepApp')
    .controller('SalaryGenerateController',
    ['$scope', 'entity', '$state', 'InstEmployee', 'MpoApplication', 'PayScale',
    function ($scope, entity, $state, InstEmployee, MpoApplication, PayScale) {

        $scope.mpoApplication = entity;
        $scope.instEmployee = {};
        $scope.mpoApplication.instEmployee = {};

        $scope.years = [
            {'key' : '2015', 'value' : '2015'},
            {'key' : '2016', 'value' : '2016'},
            {'key' : '2017', 'value' : '2017'},
            {'key' : '2018', 'value' : '2018'}
        ];

        $scope.months = [
            {'key' : '01', 'value' : 'January'},
            {'key' : '02', 'value' : 'February'},
            {'key' : '03', 'value' : 'March'},
            {'key' : '04', 'value' : 'April'},
            {'key' : '05', 'value' : 'May'},
            {'key' : '06', 'value' : 'June'},
            {'key' : '07', 'value' : 'July'},
            {'key' : '08', 'value' : 'August'},
            {'key' : '09', 'value' : 'September'},
            {'key' : '10', 'value' : 'October'},
            {'key' : '11', 'value' : 'Nobember'},
            {'key' : '12', 'value' : 'December'}
        ];


        $scope.loadAll = function() {
            PayScale.query({page: $scope.page, size: 5000000000000}, function(result) {
                $scope.payScales = result;
                console.log($scope.payScales);
            });
        };

        $scope.loadAll();

        $scope.generateSalary = function()
        {
            $http({
                url: 'your/webservice',
                method: 'POST',
                responseType: 'arraybuffer',
                data: json, //this is your json data string
                headers: {
                    'Content-type': 'application/json',
                    'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                }
            }).success(function(data){
                var blob = new Blob([data], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                });
                saveAs(blob, 'File_Name_With_Some_Unique_Id_Time' + '.xlsx');
            }).error(function(){
                //Some error log
            });
        };

        $scope.save = function () {

            if($scope.payScaleID == '') {
                console.log('Please Select a PayScale');
                return false;
            }

           $scope.isSaving = true;

           $scope.instEmployee = $scope.mpoApplication.instEmployee;

           PayScale.get({id: $scope.payScaleID}, function(result) {
               $scope.instEmployee.payScale = result;
               console.log($scope.instEmployee);
               InstEmployee.update($scope.instEmployee);
               $state.go('mpo.payScaleApprovedList',{},{reload:true});
           });

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

    }]);
