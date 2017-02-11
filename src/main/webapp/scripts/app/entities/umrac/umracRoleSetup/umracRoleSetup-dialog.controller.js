'use strict';

angular.module('stepApp').controller('UmracRoleSetupDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'UmracRoleSetup', 'TreeService', 'UserRoleRightTree','UserRoleRightTreeById',
        function ($scope, $state, $stateParams, entity, UmracRoleSetup, TreeService, UserRoleRightTree, UserRoleRightTreeById) {

            $scope.umracRoleSetup = entity;
            $scope.load = function (id) {
                buildTreeById(id);
                //UmracRoleSetup.get({id: id}, function (result) {
                //    $scope.umracRoleSetup = result;
                //    console.log("Update: "+$scope.umracRoleSetup);
                //    //buildTree();
                //
                //});
            };

            $scope.tc = {};

            if($stateParams.id == null){
                buildTree();
            }else if($stateParams.id != null){
                $scope.load($stateParams.id);
            }
            //buildTree();
            function buildTree() {
                TreeService.getTree().then(function (result) {
                    //$scope.tc.tree = result.data;
                    //console.log(result)
                    UserRoleRightTree.query(function(result) {
                     $scope.tc.tree = result;
                     console.log(result);
                     });
                }, function (result) {
                    alert("Tree no available, Error: " + result);
                });
            }

            function buildTreeById(id) {
                TreeService.getTree().then(function (result) {
                    //$scope.tc.tree = result.data;
                    //console.log(result)
                    UserRoleRightTreeById.query({id: id}, function(result) {
                        $scope.tc.tree = result;
                        console.log(result);
                    });
                }, function (result) {
                    alert("Tree no available, Error: " + result);
                });
            }

            /*$scope.toggleSelection = function toggleSelection(code) {
             var idx = $scope.selection.indexOf(code);

             // is currently selected
             if (idx > -1) {
             $scope.selection.splice(idx, 1);
             }

             // is newly selected
             else {
             $scope.selection.push(employeeName);
             }
             };*/


            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:umracRoleSetupUpdate', result);
                $scope.isSaving = false;
                $state.go('umracRoleSetup');
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                /*console.log($scope.tc.tree);
                console.log($scope.tc.tree[0].children);
                console.log($scope.tc.tree[0].children[0]);
                console.log($scope.tc.tree[0].children[0].children);
                console.log($scope.tc.tree.length);
                console.log($scope.tc.tree[0].children.length);
                console.log($scope.tc.tree[0].children.length);*/
                //console.log($scope.tc.tree[0].children[0]);
                //console.log($scope.tc.tree[0].children[0].children);
                var rights = '';
                $scope.umracRoleSetup.roleContext = '';
                for (var i = 0; i < $scope.tc.tree.length; i++) {
                    for (var j = 0; j < $scope.tc.tree[i].children.length; j++) {
                        //console.log(i);
                        //console.log(j);
                        //console.log($scope.tc.tree[i].children[j].children);
                        for (var k = 0; k < $scope.tc.tree[i].children[j].children.length; k++) {
                            //console.log(k);

                            if($scope.tc.tree[i].children[j].children[k].checked == true){
                                console.log($scope.tc.tree[i].children[j].children[k].name);
                                console.log($scope.tc.tree[i].children[j].children[k].code);
                                rights +=$scope.tc.tree[i].children[j].children[k].code+',';
                            }
                        }
                    }
                }
                console.log(rights);
                $scope.umracRoleSetup.roleContext = rights.substring(0,rights.length-1);
                $scope.isSaving = true;
                 if ($scope.umracRoleSetup.id != null) {
                 UmracRoleSetup.update($scope.umracRoleSetup, onSaveSuccess);
                 } else {
                 UmracRoleSetup.save($scope.umracRoleSetup, onSaveSuccess);
                 }
            };

            $scope.clear = function () {
                $scope.isSaving = false;
                $state.go('umracRoleSetup');
            };
        }]);

/*'use strict';

 angular.module('stepApp').controller('UmracRoleSetupDialogController',
 ['$scope', '$stateParams', '$modalInstance', 'entity', 'UmracRoleSetup',
 function($scope, $stateParams, $modalInstance, entity, UmracRoleSetup) {

 $scope.umracRoleSetup = entity;
 $scope.load = function(id) {
 UmracRoleSetup.get({id : id}, function(result) {
 $scope.umracRoleSetup = result;
 });
 };

 var onSaveFinished = function (result) {
 $scope.$emit('stepApp:umracRoleSetupUpdate', result);
 $modalInstance.close(result);
 };

 $scope.save = function () {
 if ($scope.umracRoleSetup.id != null) {
 UmracRoleSetup.update($scope.umracRoleSetup, onSaveFinished);
 } else {
 UmracRoleSetup.save($scope.umracRoleSetup, onSaveFinished);
 }
 };

 $scope.clear = function() {
 $modalInstance.dismiss('cancel');
 };
 }]);
 */

