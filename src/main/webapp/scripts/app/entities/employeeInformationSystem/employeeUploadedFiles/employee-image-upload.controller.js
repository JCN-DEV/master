angular.module('stepApp')
    .controller('EmployeeImageUploadController',
    ['$scope', '$state', '$modal', 'DataUtils', 'InstEmployee', 'InstEmployeeSearch', 'ParseLinks', 'Principal','CurrentInstEmployee',
     function ($scope, $state, $modal, DataUtils, InstEmployee, InstEmployeeSearch, ParseLinks, Principal,CurrentInstEmployee) {

            $scope.abbreviate = DataUtils.abbreviate;
            $scope.byteSize = DataUtils.byteSize;


            CurrentInstEmployee.get({},function(result){
                 console.log(result);
                $scope.instEmployee = result;
            });
            $scope.setImage = function ($file, instEmployee) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            instEmployee.image = base64Data;
                            instEmployee.imageContentType = $file.type;
                        });
                    };
                }
            };

            $scope.save = function(){
                InstEmployee.update($scope.instEmployee,function(result){
                    console.log(result);
                    if(result.length > 0){
                        $state.go('employeeInfo.imageUpload',{},{reload: true});
                    }
                });
            }
    }]);

