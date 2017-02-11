'use strict';

angular.module('stepApp').controller('DlBookRequisitionDialogController',
    ['$scope', '$stateParams','$state', 'entity', 'DlBookRequisition', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet', 'FindActivcategory', 'FindActiveSubcategory', 'FindActiveTypes',
        function ($scope, $stateParams,$state, entity, DlBookRequisition, DlContTypeSet, DlContCatSet, DlContSCatSet, FindActivcategory, FindActiveSubcategory, FindActiveTypes) {

            $scope.dlBookRequisition = entity;
            $scope.dlconttypesets = FindActiveTypes.query();
            $scope.dlcontcatsets = FindActivcategory.query();
            $scope.dlcontscatsets = FindActiveSubcategory.query();
            $scope.dlBookRequisition.status=true;
            $scope.load = function (id) {
                DlBookRequisition.get({id: id}, function (result) {
                    $scope.dlBookRequisition = result;

                });
            };


            var allCategory = FindActivcategory.query({page: $scope.page, size: 2000}, function (result, headers) {
                return result;
            });
            var allSubCategory = FindActiveSubcategory.query({
                page: $scope.page,
                size: 2000
            }, function (result, headers) {
                return result;
            });
            $scope.getCategory = function (select) {
                $scope.dlContCatSets = [];
                angular.forEach(allCategory, function (dlContCatSet) {
                    if ((dlContCatSet.dlContTypeSet && select) && (select.id != dlContCatSet.dlContTypeSet.id)) {
                    } else {
                        $scope.dlContCatSets.push(dlContCatSet);
                    }
                });
            };
            $scope.getSubCategory = function (select) {
                $scope.dlContSCatSets = [];
                angular.forEach(allSubCategory, function (dlContSCatSet) {
                    if (select.id == dlContSCatSet.dlContCatSet.id) {
                        $scope.dlContSCatSets.push(dlContSCatSet);
                    }
                });

            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:dlBookRequisitionUpdate', result);
                //$modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.dlBookRequisition.id != null) {
                    DlBookRequisition.update($scope.dlBookRequisition, onSaveFinished);
                } else {

                    $scope.dlBookRequisition.status=true;
                    DlBookRequisition.save($scope.dlBookRequisition, onSaveFinished);
                    $state.go('libraryInfo', {}, {reload: true});

                }
            };

            $scope.clear = function () {
                //$modalInstance.dismiss('cancel');
            };
        }]);
