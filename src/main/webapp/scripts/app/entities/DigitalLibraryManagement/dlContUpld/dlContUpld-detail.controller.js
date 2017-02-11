'use strict';

angular.module('stepApp')
    .controller('DlContUpldDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlContUpld', 'DlContTypeSet', 'DlContCatSet', 'InstEmployee', 'DlFileType','findAllByAllType','getallbyid',
    function ($scope, $rootScope, $stateParams, entity, DlContUpld, DlContTypeSet, DlContCatSet, InstEmployee, DlFileType,findAllByAllType,getallbyid) {
        //$scope.dlContUpld = entity;

        console.log($stateParams.id);



console.log($scope.dlContCatSet)

        DlContUpld.get({id: $stateParams.id}, function(result) {
             $scope.dlContUpld = result;
             $scope.catSetId = $scope.dlContUpld.dlContCatSet;
             $scope.ScatSetId = $scope.dlContUpld.dlContSCatSet;
             $scope.cTypeId = $scope.dlContUpld.dlContTypeSet;

            ////This function is for Download counting
            console.log($scope.dlContUpld.counter);
                $scope.dlContUpld.counter=  $scope.dlContUpld.counter+0;
                $scope.increment = function() {
                $scope.dlContUpld.counter+=1;
                DlContUpld.update($scope.dlContUpld);
            };
           /////End of download counting

            if($scope.catSetId != null && $scope.ScatSetId != null && $scope.cTypeId != null){
                 $scope.id1 = $scope.catSetId.id;
                 $scope.id2 = $scope.ScatSetId.id;
                 $scope.id3 = $scope.cTypeId.id;
                 findAllByAllType.query({dlContCatSet: $scope.id1, dlContSCatSet: $scope.id2 , dlContTypeSet: $scope.id3}, function(result) {
                     $scope.dlAllByAllTypes = result;
                      console.log("===== **** Data  * Found **** ======");
                      console.log($scope.dlAllByAllTypes);
                 });
             }else{
                $scope.dlAllByAllTypes = null;
                $scope.noContent = 'No Similar Content Found';

             }
        });

        var unsubscribe = $rootScope.$on('stepApp:dlContUpldUpdate', function(event, result) {
            $scope.dlContUpld = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.previewImage = function (content, contentContentType,contentName)
        {
            var blob = $rootScope.b64toBlob(content,contentContentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = $scope.dlContUpld.contentContentType;
            $rootScope.viewerObject.pageTitle = $scope.dlContUpld.contentName;

            console.log($rootScope.viewerObject.content);
            console.log($rootScope.viewerObject.contentType);
            console.log($rootScope.viewerObject.pageTitle);

            // call the modal
            $rootScope.showFilePreviewModal();
        };

       /////counter



    }]);
