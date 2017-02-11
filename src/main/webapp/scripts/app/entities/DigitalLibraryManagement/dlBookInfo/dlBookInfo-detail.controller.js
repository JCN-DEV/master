'use strict';

angular.module('stepApp')
    .controller('DlBookInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlBookInfo','DlBookEdition', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet','findByallType','getAllEditionById',
    function ($scope, $rootScope, $stateParams, entity, DlBookInfo,DlBookEdition, DlContTypeSet, DlContCatSet, DlContSCatSet,findByallType,getAllEditionById) {

        $scope.dlBookEditions=DlBookEdition.query();

        //$scope.dlBookInfo = entity;

        //console.log("More jah");
       // console.log($stateParams.id);

        DlBookInfo.get({id: $stateParams.id}, function(result) {
      //  console.log($stateParams.id);
            $scope.dlBookInfo = result;
            //console.log(result) ;
                 $scope.catSetId = $scope.dlBookInfo.dlContCatSet;
                 $scope.cTypeId = $scope.dlBookInfo.dlContTypeSet;

                // console.log($scope.catSetId);
                // console.log($scope.cTypeId);

                 if($scope.catSetId != null && $scope.cTypeId != null){
                     $scope.id1 = $scope.catSetId.id;
                     $scope.id3 = $scope.cTypeId.id;
                     findByallType.query({dlContCatSet: $scope.id1, dlContTypeSet: $scope.id3}, function(result) {
                         $scope.dlBookInfoByTypes = result;
                         // console.log("===== **** Data  * Found **** ======");
                         // console.log($scope.dlBookInfoByTypes);
                     });
                 }else{
                    $scope.dlBookInfoByTypes = null;
                    $scope.noContent = 'No Similar Content Found';
                   // console.log($scope.noContent);
                 }
        });

        var unsubscribe = $rootScope.$on('stepApp:dlBookInfoUpdate', function(event, result) {
            $scope.dlBookInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);





          getAllEditionById.query({id: $stateParams.id}, function(result) {
              console.log($stateParams.id);
                    $scope.dlBookEditionsss = result;
                   console.log($scope.dlBookEditionsss);
        });

    }]);
