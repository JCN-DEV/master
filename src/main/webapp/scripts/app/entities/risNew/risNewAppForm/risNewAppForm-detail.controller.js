'use strict';

angular.module('stepApp')
    .controller('RisNewAppFormDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'RisNewAppForm', 'District', 'Upazila','risAppDetailById','GetEducations',
    function ($scope, $rootScope, $stateParams, entity, RisNewAppForm, District, Upazila, risAppDetailById,GetEducations) {
        $scope.risNewAppForm = entity;
        $scope.image='' ;
        $scope.items = [];
        $scope.load = function (id) {
            RisNewAppForm.get({id: id}, function(result) {
                $scope.risNewAppForm = result;

                console.log("=======New Data=======");
                console.log($scope.risNewAppForm);
                console.log("=======New Data=======");

            });
        };

        risAppDetailById.get({id: $stateParams.id}, function(result) {
            console.log($stateParams.id);
            $scope.risAppDetailByIds = result;

            console.log($scope.risAppDetailByIds);

            $scope.educationsItem = GetEducations.query({id: $stateParams.id}, function(result){
            console.log("=======Education Data=======");

                angular.forEach($scope.educationsItem, function (item) {
                console.log("=======Education Loop Data=======");
                    $scope.items.push(item);
                    console.log("question add : ",item );
                });
                console.log('Pushpa I hate tears: '+$scope.educationsItem);
            });
        });



        var unsubscribe = $rootScope.$on('stepApp:risNewAppFormUpdate', function(event, result) {
            $scope.risNewAppForm = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
