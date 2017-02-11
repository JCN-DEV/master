angular.module('stepApp')
    .controller('EmployeeBirthCertificateController',
    ['$scope', '$state', 'CurrentInstEmployee', 'DataUtils', 'InstEmployee', 'InstEmployeeSearch', 'ParseLinks',
    function ($scope, $state, CurrentInstEmployee, DataUtils, InstEmployee, InstEmployeeSearch, ParseLinks) {

        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;

        $scope.editButton = false;
        $scope.addButton = false;

        CurrentInstEmployee.get({},function(result){
            console.log(result);
            $scope.instEmployee = result;
            if($scope.instEmployee.nid !=null || $scope.instEmployee.birthCertImageContentType != null || $scope.instEmployee.nidImageContentType != null){
                $scope.editButton = true;
                $scope.addButton = false;
            }
            else{
                $scope.editButton = false;
                $scope.addButton = true;
            }
        });
        CurrentInstEmployee.get({},function(result){
            console.log(result);
            if(!result.mpoAppStatus>=3){
                $scope.editButton = false;
                $scope.addButton = false;
            }
        });




        $scope.save = function(){
            InstEmployee.update($scope.instEmployee , onUpdateSuccess);
        }

        var onUpdateSuccess = function(result){
             $state.go('employeeInfo.nationalityAndBirthCertificate',{},{reload: true});
        }



        $scope.setNidImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.nidImage = base64Data;
                        instEmployee.nidImageContentType = $file.type;
                    });
                };
            }
        };

        $scope.setBirthCertImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.birthCertImage = base64Data;
                        instEmployee.birthCertImageContentType = $file.type;
                    });
                };
            }
        };
    }]);
