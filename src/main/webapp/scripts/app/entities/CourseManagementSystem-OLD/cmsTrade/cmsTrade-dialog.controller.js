'use strict';

angular.module('stepApp').controller('CmsTradeDialogController',
['$scope', '$stateParams', '$state', 'entity', 'CmsTrade', 'CmsCurriculum', 'CmsTradeByCode',
            function($scope, $stateParams, $state, entity, CmsTrade, CmsCurriculum, CmsTradeByCode) {

        $scope.message = '';
        $scope.cmsTrade = entity;
            $scope.cmsTrade.status=true;

        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.load = function(id) {
            CmsTrade.get({id : id}, function(result) {
                $scope.cmsTrade = result;

            });
        };
        console.log($scope.cmsTrade);
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsTradeUpdate', result);
           /* $modalInstance.close(result);*/
            $scope.isSaving = false;
            $state.go('courseInfo.cmsTrade',{},{reload:true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsTrade.id != null) {
                CmsTrade.update($scope.cmsTrade, onSaveSuccess, onSaveError);
            } else {
               // CmsTrade.save($scope.cmsTrade, onSaveSuccess, onSaveError);

                    CmsTradeByCode.get({code: $scope.cmsTrade.code}, function (cmsTrade) {

                        $scope.message = "This Code is already existed.";
                    },
                    function (response) {
                        if (response.status === 404) {
                        console.log('not exist');
                            CmsTrade.save($scope.cmsTrade, onSaveSuccess, onSaveError);
                                            console.log($scope.cmsTrade);
                        }
                    }
                );
            }
        };

        $scope.clear = function() {
        $scope.cmsTrade=null;
        };
}]);
