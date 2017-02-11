'use strict';

angular.module('stepApp')
    .controller('DlContUpldController',
    ['$scope', '$state', '$modal', 'DlContUpld', 'FindAllByAdminUpload', 'FindAllByUserUpload', 'DlContCatSet', 'DlContSCatSet', 'DlContTypeSet', 'DlContUpldSearch', 'ParseLinks', 'getallbyid', 'getallbysid', 'getallbytype', 'FindActiveTypes', 'FindActivcategory', 'FindActiveSubcategory','FindAllByUserUploadByCurrentInstitiute',
        function ($scope, $state, $modal, DlContUpld, FindAllByAdminUpload, FindAllByUserUpload, DlContCatSet, DlContSCatSet, DlContTypeSet, DlContUpldSearch, ParseLinks, getallbyid, getallbysid, getallbytype, FindActiveTypes, FindActivcategory, FindActiveSubcategory,FindAllByUserUploadByCurrentInstitiute) {


            //$scope.contUplds = DlContUpld.query();

            $scope.contUpldsBYadmin = FindAllByAdminUpload.query();
            $scope.contUpldsBYuser = FindAllByUserUpload.query();
            $scope.dlcontcatsets = DlContCatSet.query();
            $scope.dlcontscatsets = DlContSCatSet.query();
            $scope.dlconttypesets = DlContTypeSet.query();
            $scope.dlContTypeSets = FindActiveTypes.query();
            $scope.dlContCatSets = FindActivcategory.query();
            $scope.dlContCatSetsFilter = $scope.dlContCatSets;

            $scope.dlContSubCatSets = FindActiveSubcategory.query();
            $scope.dlContSubCatSetsFilter = $scope.dlContSubCatSets;


            $scope.dlContUplds = [];
            $scope.page = 0;

            $scope.currentPage = 1;
            $scope.pageSize = 10;

            /*  $scope.loadAll = function() {
             DlContUpld.query({page: $scope.page, size: 20}, function(result, headers) {
             $scope.links = ParseLinks.parse(headers('link'));
             $scope.dlContUplds = result;
             console.log("===============More Jah ================");
             console.log($scope.dlContUplds);
             });
             }; */
            //$scope.loadAll = function () {
            FindAllByAdminUpload.query({page: $scope.page, size: 20000}, function (result) {
                //$scope.links = ParseLinks.parse(headers('link'));
                $scope.dlContUplds = result;
                console.log("===============Total Contents ================");
                console.log($scope.dlContUplds);
            });
            //};

           FindAllByUserUpload.query({page: $scope.page, size: 200}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlContUpldsbyUser = result;
                console.log("uploaded contents by user");
                console.log($scope.dlContUpldsbyUser);


            });

            FindAllByUserUploadByCurrentInstitiute.query({page: $scope.page, size: 200}, function (result) {
                $scope.dlContUpldsbyCurrentInstitiute = result;
                console.log("uploaded contents by CurrentInstitiute");
                console.log($scope.dlContUpldsbyCurrentInstitiute);


            });


            //$scope.loadPage = function (page) {
            //    $scope.page = page;
            //    $scope.loadAll();
            //};
            ////$scope.loadAll();

            $scope.search = function () {
                DlContUpldSearch.query({query: $scope.searchQuery}, function (result) {
                    $scope.dlContUplds = result;
                }, function (response) {
                    if (response.status === 404) {
                        $scope.loadAll();
                    }
                });
            };

            $scope.refresh = function () {
                $scope.loadAll();
                $scope.clear();
            };


            $scope.clear = function () {
                $scope.dlContUpld = {
                    code: null,
                    authName: null,
                    edition: null,
                    isbnNo: null,
                    copyright: null,
                    publisher: null,
                    createdDate: null,
                    updatedDate: null,
                    createdBy: null,
                    updatedBy: null,
                    status: null,
                    id: null
                };
            };



            //this code is for type-category wise book show
            $scope.loadCategoryByType = function (typeObj) {
                console.log(typeObj);
                $scope.typeId = typeObj.id;

                //$scope.getBookByType = function () {

                $scope.getallbytype = getallbytype.get({id: $scope.typeId}, function (result) {
                        $scope.dlContUplds = result;
                        console.log($scope.dlContUplds);
                    }
                );
                //};
                $scope.dlContCatSetsFilter = [];
                angular.forEach($scope.dlContCatSets, function (categoryObj) {
                    //console.log(categoryObj.dlContTypeSet.id);
                    if (typeObj.id == categoryObj.dlContTypeSet.id) {
                        $scope.dlContCatSetsFilter.push(categoryObj);
                        //$scope.getBookByType();
                    }
                });
            };
            $scope.loadSubCatByCategory = function (categoryObj) {
                $scope.categoryID = categoryObj.id;
                console.log($scope.categoryID);

                //this code is for category wise book show
                    $scope.getallbyid = getallbyid.get({id: $scope.categoryID}, function (result) {
                            $scope.dlContUplds = result;
                        }
                    );
                ////Cascading part
                $scope.dlContSubCatSetsFilter = [];
                angular.forEach($scope.dlContSubCatSets, function (subCategoryObj) {
                    if (categoryObj.id == subCategoryObj.dlContCatSet.id) {
                        $scope.dlContSubCatSetsFilter.push(subCategoryObj);
                    }
                });
            };


            //this code is for sub-category wise book show
            $scope.getBookBySCategory = function (getSubCatObj) {
                console.log(getSubCatObj);
                $scope.getSubCatObj=getSubCatObj.id;
                $scope.getallbysid = getallbysid.get({id: $scope.getSubCatObj}, function (result) {
                        $scope.dlContUplds = result;
                        console.log($scope.dlContUplds);
                    }
                );
            };



        }]);
