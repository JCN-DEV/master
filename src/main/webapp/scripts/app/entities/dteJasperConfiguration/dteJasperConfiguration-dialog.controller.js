'use strict';

angular.module('stepApp').controller('DteJasperConfigurationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DteJasperConfiguration', 'User',
        function($scope, $stateParams, $modalInstance, entity, DteJasperConfiguration, User) {

        $scope.dteJasperConfiguration = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            DteJasperConfiguration.get({id : id}, function(result) {
                $scope.dteJasperConfiguration = result;
            });
        };
        //$scope.server_url=;
        $scope.dteJasperConfiguration.createUrl="http://"+$scope.dteJasperConfiguration.domain+":"+$scope.dteJasperConfiguration.port+"/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2FDTE%2Fhrm%2F&j_username="+$scope.dteJasperConfiguration.username+"&j_password="+$scope.dteJasperConfiguration.password+"decorate=no";
        $scope.dteJasperConfiguration.createUrl=5;


        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:dteJasperConfigurationUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            $scope.dteJasperConfiguration.createUrl="http://"+$scope.dteJasperConfiguration.domain+":"+$scope.dteJasperConfiguration.port+"/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2FDTE%2Fhrm%2F&j_username="+$scope.dteJasperConfiguration.username+"&j_password="+$scope.dteJasperConfiguration.password+"decorate=no";
            if ($scope.dteJasperConfiguration.id != null) {
                DteJasperConfiguration.update($scope.dteJasperConfiguration, onSaveFinished);
            } else {
                DteJasperConfiguration.save($scope.dteJasperConfiguration, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
